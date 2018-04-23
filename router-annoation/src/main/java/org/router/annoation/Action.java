package org.router.annoation;

import org.router.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description : Action 注解
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Action {
    /**
     * 线程模型
     */
    ThreadMode threadMode() default ThreadMode.POSTING;

    /**
     * 路由的路径 也
     * 就是唯一标识Id
     */
    String path();

    /**
     * 是否在一个新的进程
     */
    boolean extraProcess() default false;
}
