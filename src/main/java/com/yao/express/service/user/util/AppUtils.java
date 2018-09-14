package com.yao.express.service.user.util;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * description:
 * saras_xu@163.com 2017-03-07 08:54 创建
 */
public class AppUtils {

    /**
     * 从request中获取所有参数放入map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String parameterName = en.nextElement();
            map.put(parameterName, request.getParameter(parameterName));
        }
        return map;
    }


}
