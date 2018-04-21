package org.router.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description : 拦截器标识符
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Intercepter{

    /**
     * 优先级 默认0  越高越先执行
     */
    int priority() default 0;
}
