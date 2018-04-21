package com.cabinet.lib_base;

import android.app.Activity;

import org.router.api.core.Router;
import org.router.api.result.ActionCallback;
import org.router.api.result.RouterResult;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class API {
    void test() {
        Router.get()
                .action("")
                .context(new Activity())
                .invokeAction(new ActionCallback() {
                    @Override
                    public void onInterrupt() {

                    }

                    @Override
                    public void onResult(RouterResult result) {

                    }
                });
    }
}
