package com.yao.express.service.user.conf;

import com.yao.express.service.user.util.filter.UserAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请求接口时身份验证
 *
 * @author: York.Yu
 * @date: 2017/4/29
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UserAuthFilter());
        registration.addUrlPatterns("/*");
//        registration.addInitParameter("include", "/*");
        registration.addInitParameter("exclude",
                "/,/index,/home,/app,/client,/client/**,/managers/login,/static/**,/apk/**,/favicon.ico,/login,/sms/send/*,/drivers/login,/drivers/register,/customers/login,/customers/register,/customers/passwords/forgot,/apps/versions/*,/wx/login,/druid,/druid/*,/swagger-ui.html,/webjars/*,/swagger-resources,/swagger-resources/*,/v2/api-docs,/users/*");
        registration.setName("userAuthFilter");
        return registration;
    }

}
