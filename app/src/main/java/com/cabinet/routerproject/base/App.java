package com.cabinet.routerproject.base;

import android.app.Application;

import org.router.api.core.Router;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Router.get().init(this);
        Router.openDebug();
    }
}
