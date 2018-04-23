package org.router.api.utils;

import org.router.api.interceptor.ActionInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class MapUtils {
    public static List<ActionInterceptor> getInterceptorClasses(Map<Integer, ActionInterceptor> map) {
        List<ActionInterceptor> list = new ArrayList();

        for (Object key : map.keySet()) {
            list.add(map.get(key));
        }

        return list;
    }
}
