package org.router.api.core;

import android.content.Context;

import org.router.ThreadMode;
import org.router.api.extra.ActionWrapper;
import org.router.api.interceptor.ActionInterceptor;
import org.router.api.interceptor.ActionInterceptorChain;
import org.router.api.result.ActionCallback;
import org.router.api.thread.ActionPost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : 路由转发
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class RouterForward {
    private ActionWrapper mActionWrapper;
    private Context mContext;
    private Map<String, Object> mParams;
    private ThreadMode mThreadMode = null;
    // 所有拦截器表
    private List<ActionInterceptor> interceptors;

    /**
     * 指定 threadMode 这里指定的优先级高于 Action 注解上的 threadMode
     *
     * @param threadMode
     * @return
     */
    public RouterForward threadMode(ThreadMode threadMode) {
        this.mThreadMode = threadMode;
        return this;
    }

    RouterForward(ActionWrapper actionWrapper, List<ActionInterceptor> interceptors) {
        this.mActionWrapper = actionWrapper;
        mParams = new HashMap<>();
        this.interceptors = interceptors;
    }

    /**
     * 执行 Action
     *
     * @return
     */
    public void invokeAction() {
        invokeAction(ActionCallback.DEFAULT_ACTION_CALLBACK);
    }

    /**
     * 执行 Action
     *
     * @return
     */
    public void invokeAction(ActionCallback actionCallback) {
        // 先封装 actionPost
        mActionWrapper.setThreadMode(getThreadMode());
        ActionPost actionPost = ActionPost.obtainActionPost(mActionWrapper, mContext, mParams, actionCallback);
        // 开始拦截器的流程
        ActionInterceptor.ActionChain chain = new ActionInterceptorChain(interceptors, actionPost, 0);
        chain.proceed(actionPost);
    }

    /**
     * 路由转发方法传递的 threadMode 优先级高于 Action 注解上的 threadMode
     *
     * @return
     */
    public ThreadMode getThreadMode() {
        return mThreadMode == null ? mActionWrapper.getThreadMode() : mThreadMode;
    }

    public RouterForward context(Context context) {
        this.mContext = context;
        return this;
    }

    public RouterForward param(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public RouterForward param(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }
}
