package org.router.api.extra;

import org.router.ThreadMode;
import org.router.api.action.IRouterAction;

/**
 * Description :  Action 包装类
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class ActionWrapper {

    /**
     * action类的class
     */
    private Class<? extends IRouterAction> actionClass;
    /**
     * 累上的注解路径
     */
    private String path;
    /**
     * 返回线程调度类型
     */
    private ThreadMode threadMode;
    /**
     * 信否在一个新的进程
     */
    private boolean extraProcess;
    /**
     * routerAction
     */
    private IRouterAction routerAction;

    ActionWrapper() {

    }

    public void setRouterAction(IRouterAction routerAction) {
        this.routerAction = routerAction;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public IRouterAction getRouterAction() {
        return routerAction;
    }

    private ActionWrapper(Class<? extends IRouterAction> actionClass, String path, boolean extraProcess, ThreadMode threadMode) {
        this.actionClass = actionClass;
        this.path = path;
        this.extraProcess = extraProcess;
        this.threadMode = threadMode;
    }

    public Class<? extends IRouterAction> getActionClass() {
        return actionClass;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public String getPath() {
        return path;
    }

    public boolean isExtraProcess() {
        return extraProcess;
    }

    public static ActionWrapper build(Class<? extends IRouterAction> actionClass, String path, boolean extraProcess, ThreadMode threadMode) {
        return new ActionWrapper(actionClass, path, extraProcess, threadMode);
    }
}
