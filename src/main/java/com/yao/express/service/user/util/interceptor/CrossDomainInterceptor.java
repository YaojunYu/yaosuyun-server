package com.yao.express.service.user.util.interceptor;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 跨域拦截
 *
 * @author: York.Yu
 * @date: 2017/4/26
 */
public class CrossDomainInterceptor extends HeaderInterceptor {

    private boolean devModel = false;

    public CrossDomainInterceptor() {
        headers = new HashMap<>();
        if (devModel) {
            headers.put("Access-Control-Allow-Origin", "*");
        } else {
            headers.put("Access-Control-Allow-Credentials", "true");
        }
        headers.put("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        headers.put("Access-Control-Max-Age", "3600");
        headers.put("Access-Control-Allow-Headers", "token,x-requested-with,content-type,No-Cache");
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = request.getHeader("origin");
        if (StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        return super.preHandle(request, response, handler);
    }

    public void setDevModel(boolean devModel) {
        this.devModel = devModel;
    }

    public void setHeaders(Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                this.headers.put(header.getKey(), header.getValue());
            }
        }
    }
}
