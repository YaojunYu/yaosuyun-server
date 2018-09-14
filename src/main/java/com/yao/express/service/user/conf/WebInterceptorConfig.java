package com.yao.express.service.user.conf;

import com.yao.express.service.user.util.interceptor.RequestParamsLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * description:拦截器配置
 * saras_xu@163.com 2017-04-18 11:42 创建
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new RequestParamsLogInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);

    }
}
