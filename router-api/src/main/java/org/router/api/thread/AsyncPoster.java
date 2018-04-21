package org.router.api.thread;

import org.router.api.action.IRouterAction;
import org.router.api.extra.ActionWrapper;
import org.router.api.result.RouterResult;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class AsyncPoster implements Runnable, Poster {

    private final ActionPostQueue queue;

    AsyncPoster() {
        queue = new ActionPostQueue();
    }

    @Override
    public void run() {
        ActionPost actionPost = queue.poll();
        if (actionPost == null) {
            throw new IllegalStateException("No pending post available");
        }

        ActionWrapper actionWrapper = actionPost.actionWrapper;
        IRouterAction routerAction = actionWrapper.getRouterAction();
        RouterResult routerResult = routerAction.invokeAction(actionPost.context, actionPost.params);
        actionPost.actionCallback.onResult(routerResult);

        actionPost.releasePendingPost();
    }

    @Override
    public void enqueue(ActionPost actionPost) {
        queue.enqueue(actionPost);
        PosterSupport.getExecutorService().execute(this);
    }
}