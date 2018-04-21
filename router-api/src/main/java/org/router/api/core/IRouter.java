package org.router.api.core;

import android.app.Application;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface IRouter {

    /**
     * 初始化
     */
    void init(Application context);

    /**
     * path action
     */
    RouterForward action(String actionName);

}
