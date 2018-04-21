package org.router.api.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import org.router.api.action.IRouterAction;
import org.router.api.extra.ActionWrapper;
import org.router.api.result.RouterResult;

/**
 * Description : 处理主线程切换
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class HandlerPoster extends Handler implements Poster{
    // 任务对垒
    private final ActionPostQueue queue;
    // 最大任务数
    private final int maxMillisInsideHandleMessage;
    //
    private boolean handlerActive;

    protected HandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
        super(looper);
        this.maxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
        queue = new ActionPostQueue();
    }

    @Override
    public void handleMessage(Message msg) {
        boolean rescheduled = false;
        try {
            long started = SystemClock.uptimeMillis();
            while (true) {
                ActionPost actionPost = queue.poll();
                if (actionPost == null) {
                    synchronized (this) {
                        actionPost = queue.poll();
                        if (actionPost == null) {
                            handlerActive = false;
                            return;
                        }
                    }
                }

                ActionWrapper actionWrapper = actionPost.actionWrapper;
                IRouterAction routerAction = actionWrapper.getRouterAction();
                RouterResult routerResult = routerAction.invokeAction(actionPost.context, actionPost.params);
                actionPost.actionCallback.onResult(routerResult);

                actionPost.releasePendingPost();

                long timeInMethod = SystemClock.uptimeMillis() - started;
                if (timeInMethod >= maxMillisInsideHandleMessage) {
                    if (!sendMessage(obtainMessage())) {
                        throw new RuntimeException("Could not send handler message");
                    }
                    rescheduled = true;
                    return;
                }
            }
        } finally {
            handlerActive = rescheduled;
        }
    }

    @Override
    public void enqueue(ActionPost actionPost) {
        synchronized (this) {
            queue.enqueue(actionPost);
            if (!handlerActive) {
                handlerActive = true;
                if (!sendMessage(obtainMessage())) {
                    throw new RuntimeException("Could not send handler message");
                }
            }
        }
    }
}
