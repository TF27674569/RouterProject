package org.router.annoation;

import org.router.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description : Action ע��
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
     * �߳�ģ��
     */
    ThreadMode threadMode() default ThreadMode.POSTING;

    /**
     * ·�ɵ�·�� Ҳ
     * ����Ψһ��ʶId
     */
    String path();

    /**
     * �Ƿ���һ���µĽ���
     */
    boolean extraProcess() default false;
}
