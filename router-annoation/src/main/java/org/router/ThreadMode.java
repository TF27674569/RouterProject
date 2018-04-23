package org.router;



/**
 * Description : thanks  EventBus and DRouter
 *               线程调度模型
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public enum ThreadMode {

    /**
     * 在哪执行回来就在哪个线程
     */
    POSTING,

    /**
     * 回到主线程
     */
    MAIN,

    /**
     * 后台不可见线程
     */
    BACKGROUND,

    /**
     * 异步线程
     */
    ASYNC
}
