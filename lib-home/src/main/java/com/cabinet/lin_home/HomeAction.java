package com.cabinet.lin_home;

import android.content.Context;
import android.content.Intent;

import org.router.*;
import org.router.annoation.Action;
import org.router.api.action.IRouterAction;
import org.router.api.result.RouterResult;

import java.util.Map;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@Action(path = "libhome/homeActivity")
public class HomeAction implements IRouterAction{
    /**
     * 回调函数 跳转是对外提供的接口
     *
     * @param context     上下文
     * @param requestData 请求参数
     * @return 路由返回结果
     */
    @Override
    public RouterResult invokeAction(Context context, Map<String, Object> requestData) {
        Intent intent = new Intent(context,HomeActivity.class);
        intent.putExtra("key","value");
        context.startActivity(intent);
        return new RouterResult.Builder().success().build();
    }
}
