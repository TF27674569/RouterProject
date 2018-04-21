package org.router.api.interceptor;

import org.router.api.extra.ErrorActionWrapper;
import org.router.api.thread.ActionPost;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class ErrorActionInterceptor implements ActionInterceptor{
    @Override
    public void intercept(ActionChain chain) {
        ActionPost actionPost = chain.action();
        // 拦截错误
        if (actionPost.actionWrapper instanceof ErrorActionWrapper) {
            chain.onInterrupt();
        }

        // 继续分发
        chain.proceed(actionPost);
    }
}
