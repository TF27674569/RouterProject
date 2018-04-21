package org.router.api.action;

import android.content.Context;

import org.router.api.result.RouterResult;

import java.util.Map;

/**
 * Description : 路由对外提供接口抽象类
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface IRouterAction {

    /**
     * 回调函数 跳转是对外提供的接口
     *
     * @param context  上下文
     * @param requestData  请求参数
     * @return 路由返回结果
     */
    RouterResult invokeAction(Context context, Map<String, Object> requestData);
}
