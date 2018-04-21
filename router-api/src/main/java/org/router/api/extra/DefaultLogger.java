package org.router.api.extra;

import android.text.TextUtils;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class DefaultLogger implements ILogger {

    boolean isDebug = false;
    private String defaultTag = "Router";

    @Override
    public void showLog(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @Override
    public void d(String tag, String message) {
        if (isDebug) {
            Log.d(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message);
        }
    }

    @Override
    public void i(String tag, String message) {
        if (isDebug) {
            Log.i(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message);
        }
    }

    @Override
    public void w(String tag, String message) {
        if (isDebug) {
            Log.w(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message);
        }
    }

    @Override
    public void e(String tag, String message) {
        if (isDebug) {
            Log.e(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message);
        }
    }


    public String getDefaultTag() {
        return defaultTag;
    }
}
