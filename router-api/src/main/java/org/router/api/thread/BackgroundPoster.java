package org.router.api.thread;

import org.router.api.action.IRouterAction;
import org.router.api.core.Router;
import org.router.api.extra.ActionWrapper;
import org.router.api.extra.Consts;
import org.router.api.result.RouterResult;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class BackgroundPoster implements Runnable, Poster {

    private final ActionPostQueue queue;

    private volatile boolean executorRunning;

    BackgroundPoster() {
        queue = new ActionPostQueue();
    }

    @Override
    public void enqueue(ActionPost actionPost) {
        synchronized (this) {
            queue.enqueue(actionPost);
            if (!executorRunning) {
                executorRunning = true;
                PosterSupport.getExecutorService().execute(this);
            }
        }
    }

    @Override
    public void run() {
        try {
            try {
                while (true) {
                    ActionPost actionPost = queue.poll(1000);
                    if (actionPost == null) {
                        synchronized (this) {
                            // Check again, this time in synchronized
                            actionPost = queue.poll();
                            if (actionPost == null) {
                                executorRunning = false;
                                return;
                            }
                        }
                    }

                    ActionWrapper actionWrapper = actionPost.actionWrapper;
                    IRouterAction routerAction = actionWrapper.getRouterAction();
                    RouterResult routerResult = routerAction.invokeAction(actionPost.context, actionPost.params);
                    actionPost.actionCallback.onResult(routerResult);

                    actionPost.releasePendingPost();
                }
            } catch (InterruptedException e) {
                Router.logger.e(Consts.TAG, Thread.currentThread().getName() + " was interruppted");
            }
        } finally {
            executorRunning = false;
        }
    }
}
