package org.router;



/**
 * Description : thanks  EventBus and DRouter
 *               �̵߳���ģ��
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public enum ThreadMode {

    /**
     * ����ִ�л��������ĸ��߳�
     */
    POSTING,

    /**
     * �ص����߳�
     */
    MAIN,

    /**
     * ��̨���ɼ��߳�
     */
    BACKGROUND,

    /**
     * �첽�߳�
     */
    ASYNC
}
