package org.router.api.interceptor;

import java.util.List;

/**
 * Description : 路由拦截器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface IRouterInterceptor {

    /**
     * 返回所有的拦截器链
     */
    List<ActionInterceptor> getInterceptors();
}
