package org.router.api.thread;

/**
 * Description : 任务调度抽象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface Poster {
    void enqueue(ActionPost actionPost);
}
