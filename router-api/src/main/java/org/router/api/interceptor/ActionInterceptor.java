package org.router.api.interceptor;

import org.router.api.thread.ActionPost;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface ActionInterceptor {
    void intercept(ActionChain chain);

    interface ActionChain {
        // 打断拦截
        void onInterrupt();

        // 分发给下一个拦截器
        void proceed(ActionPost actionPost);

        // 获取 ActionPost
        ActionPost action();

        String actionPath();
    }
}
