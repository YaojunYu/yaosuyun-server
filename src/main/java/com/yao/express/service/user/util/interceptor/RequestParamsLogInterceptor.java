package com.yao.express.service.user.util.interceptor;

import com.yao.express.service.user.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description:日志拦截器
 * saras_xu@163.com 2017-04-18 11:32 创建
 */
public class RequestParamsLogInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(com.yao.express.service.user.util.interceptor.RequestParamsLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Map<String, String> paramMap = AppUtils.getRequestMap(httpServletRequest);
        logger.info("收到{}#{}请求-->请求参数：{}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), paramMap);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
