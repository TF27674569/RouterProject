package org.router.api.result;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface ActionCallback {
    // 被拦截了
    void onInterrupt();

    // 没被拦截返回结果
    void onResult(RouterResult result);

    // 默认的 ActionCallback
    ActionCallback DEFAULT_ACTION_CALLBACK = new ActionCallback() {

        @Override
        public void onInterrupt() {

        }

        @Override
        public void onResult(RouterResult result) {

        }
    };
}
