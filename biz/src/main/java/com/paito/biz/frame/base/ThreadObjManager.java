package com.paito.biz.frame.base;

import com.paito.biz.auction.dto.LoginUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick on 16/1/17.
 */
public class ThreadObjManager {
    private final static String								LOGIN_USER		= "loginUser";

    private static final ThreadLocal<Map<String, Object>>	threadObjPool	= new ThreadLocal<Map<String, Object>>();


    /**
     * 存储用户信息到线程对象中
     */
    public static void setLoginUser(LoginUser user) {
        Map<String, Object> servletAttributesMap = threadObjPool.get();

        if (servletAttributesMap == null) {
            servletAttributesMap = new HashMap<String, Object>();
            threadObjPool.set(servletAttributesMap);
        }

        servletAttributesMap.put(LOGIN_USER, user);
    }

    /**
     * 取用户信息，判断取不到的情况，抛异常
     */
    public static LoginUser getLoginUser() {
        LoginUser loginUser = null;
        Map<String, Object> servletAttributesMap = threadObjPool.get();
        if (servletAttributesMap != null) {
            loginUser = (LoginUser) servletAttributesMap.get(LOGIN_USER);
        }

        if (loginUser == null) {
            return null;
        }
        return loginUser;
    }

    /**
     * 清理存放用户对象的map
     *
     * @author yihan.zhaoyh
     * @date 2010-6-9
     */
    public static void clear() {
        Map<String, Object> servletAttributesMap = threadObjPool.get();
        if (servletAttributesMap != null) {
            servletAttributesMap.clear();
        }
    }
}
