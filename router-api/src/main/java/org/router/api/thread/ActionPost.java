package org.router.api.thread;

import android.content.Context;

import org.router.api.extra.ActionWrapper;
import org.router.api.result.ActionCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description : 请求任务
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class ActionPost {

    //  请求池
    private final static List<ActionPost> pendingPostPool = new ArrayList<>();

    // 上下文
    public Context context;
    // action封装
    public ActionWrapper actionWrapper;
    // 传递参数
    public Map<String, Object> params;
    // action请求的回调
    public ActionCallback actionCallback;
    // 下一个任务
    ActionPost next;

    private ActionPost(ActionWrapper actionWrapper, Context context, Map<String, Object> params, ActionCallback actionCallback) {
        this.context = context;
        this.actionWrapper = actionWrapper;
        this.params = params;
        this.actionCallback = actionCallback;
    }

    /**
     * 享元设计模式
     */
    public static ActionPost obtainActionPost(ActionWrapper actionWrapper, Context context, Map<String, Object> params, ActionCallback actionCallback) {
        synchronized (pendingPostPool) {
            // 拿到池的大小
            int size = pendingPostPool.size();
            // 判断池里面是否含有任务
            if (size > 0) {
                // 取最后一个任务
                ActionPost actionPost = pendingPostPool.remove(size - 1);
                // 重行赋值任务
                actionPost.context = context;
                actionPost.actionWrapper = actionWrapper;
                actionPost.params = params;
                actionPost.next = null;
                actionPost.actionCallback = actionCallback;
                return actionPost;
            }
        }
        // 如果没有直接初始化一个任务
        return new ActionPost(actionWrapper, context, params, actionCallback);
    }

    /**
     * 释放和初始化池
     */
    public void releasePendingPost() {
        this.context = null;
        this.actionWrapper = null;
        this.next = null;
        this.actionCallback = null;
        synchronized (pendingPostPool) {
            // 池的大小
            if (pendingPostPool.size() < 10000) {
                pendingPostPool.add(this);
            }
        }
    }
}
