package com.yao.express.service.user.util.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求头处理
 *
 * @author: York.Yu
 * @date: 2017/4/26
 */
public class HeaderInterceptor extends HandlerInterceptorAdapter {

    protected Map<String, String> headers;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                response.setHeader(header.getKey(), header.getValue());
            }
        }
        return true;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
