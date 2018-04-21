package org.router.api.action;

import org.router.api.extra.ActionWrapper;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface IRouterModule {
    // 通过 Action 的名称找到 Action
    ActionWrapper findAction(String actionName);
}
