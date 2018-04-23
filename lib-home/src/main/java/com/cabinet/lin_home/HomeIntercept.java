package com.cabinet.lin_home;

import android.widget.Toast;

import org.router.annoation.Intercepter;
import org.router.api.core.Router;
import org.router.api.interceptor.ActionInterceptor;
import org.router.api.interceptor.IRouterInterceptor;
import org.router.api.thread.ActionPost;

import java.util.List;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@Intercepter(priority = 18)
public class HomeIntercept implements ActionInterceptor{


    @Override
    public void intercept(ActionChain chain) {
        ActionPost actionPost = chain.action();

        // 判断path 如果需要拦截  则处理拦截事件
        if (chain.actionPath().equals("libhome/homeActivity")) {
            Toast.makeText(actionPost.context, "拦截首页，跳转到登录", Toast.LENGTH_LONG).show();
            // 拦截
            chain.onInterrupt();
            // 跳转到登录页面
            Router.get()
                    .action("login/action")
                    .context(actionPost.context)
                    .invokeAction();
        }
        // 继续向下转发
        chain.proceed(actionPost);
    }
}
