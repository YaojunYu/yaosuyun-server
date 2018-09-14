package com.yao.express.service.user.util.login;

import java.util.HashMap;
import java.util.Map;

/**
 * 该帮助类主要用于将登录后的用户信息保存在线程变量中，
 * 方便提取登录用户信息
 *
 * @author: York.Yu
 * @date: 2017/5/3
 */
public class UserLoginUtils {

    private static final ThreadLocal<Object> threadLocal = new InheritableThreadLocal<Object>();

    /**
     * 获取userLogin对象
     */
    public static Object getCurrentUser() {
        return getThreadLocal().get();
    }

    /**
     * 保存用户登录信息
     */
    public static void saveUserLogin(Object userLogin) {
        // 获取userLogin并放入threadLocal
        getThreadLocal().set(userLogin);
    }

    /**
     * 删除用户登录信息
     */
    public static void removeUserLogin() {
        if (threadLocal.get() != null) {
            threadLocal.remove();
        }
    }


    /**
     * 获取threadLocal
     */
    private static ThreadLocal<Object> getThreadLocal() {
        return threadLocal;
    }
}
