package org.router.api.interceptor;

import android.os.Looper;

import org.router.api.action.IRouterAction;
import org.router.api.extra.ActionWrapper;
import org.router.api.result.RouterResult;
import org.router.api.thread.ActionPost;
import org.router.api.thread.PosterSupport;

/**
 * Description : 之后执行
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class CallActionInterceptor implements ActionInterceptor {
    @Override
    public void intercept(ActionChain chain) {
        // 执行 Action 方法
        ActionPost actionPost = chain.action();
        invokeAction(actionPost, Looper.myLooper() == Looper.getMainLooper());
    }

    /**
     * 处理线程切换
     *
     * @param isMainThread
     * @return
     */
    private void invokeAction(ActionPost actionPost, boolean isMainThread) {
        switch (actionPost.actionWrapper.getThreadMode()) {
            case POSTING:
                invokeAction(actionPost);
            case MAIN:
                if (isMainThread) {
                    invokeAction(actionPost);
                } else {
                    PosterSupport.getMainPoster().enqueue(actionPost);
                }
                break;
            case BACKGROUND:
                if (isMainThread) {
                    PosterSupport.getBackgroundPoster().enqueue(actionPost);
                } else {
                    invokeAction(actionPost);
                }
                break;
            case ASYNC:
                PosterSupport.getAsyncPoster().enqueue(actionPost);
                break;
            default:
                throw new IllegalStateException("Unknown thread mode: " + actionPost.actionWrapper.getThreadMode());
        }
    }

    /**
     * 执行 Action
     */
    private void invokeAction(ActionPost actionPost) {
        ActionWrapper actionWrapper = actionPost.actionWrapper;
        IRouterAction routerAction = actionWrapper.getRouterAction();
        RouterResult routerResult = routerAction.invokeAction(actionPost.context, actionPost.params);
        actionPost.actionCallback.onResult(routerResult);
    }

}
