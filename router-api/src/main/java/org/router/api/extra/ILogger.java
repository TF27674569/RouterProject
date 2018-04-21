package org.router.api.extra;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface ILogger {

    void showLog(boolean isDebug);

    void d(String tag, String message);

    void i(String tag, String message);

    void w(String tag, String message);

    void e(String tag, String message);
}
