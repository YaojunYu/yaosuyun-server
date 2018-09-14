package com.yao.express.service.user.util.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 抽象的路径匹配过滤器
 *
 * @author: York.Yu
 * @date: 2017/4/29
 */
public abstract class PatternFilter implements Filter {

    protected Pattern[] includePattern = null;
    // 过滤时排除路径，如 “/users/login, /mall/*”
    protected Pattern[] excludePattern = null;

    public final void init(FilterConfig filterConfig) throws ServletException {
        String include = filterConfig.getInitParameter("include");
        String exclude = filterConfig.getInitParameter("exclude");
        includePattern = initPattern(include);
        excludePattern = initPattern(exclude);
        innerInit(filterConfig);

        // 让Filter可以自动装载bean
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    private Pattern[] initPattern(String include) {
        Pattern[] patterns = null;
        if (null != include && !"".equals(include)) {
            String[] arr = include.split(",");
            patterns = new Pattern[arr.length];
            for (int i = 0; i < arr.length; i++) {
                patterns[i] = Pattern.compile(arr[i].trim().replaceAll("\\*", ".*")); // 匹配星号
            }
        }
        return patterns;
    }

    /**
     * 子类进行初始化方法
     *
     * @param filterconfig
     */
    public abstract void innerInit(FilterConfig filterconfig) throws ServletException;

    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * filter过滤方法，final子类不可覆盖，实现正则匹配规则，子类覆盖innerDoFilter
     */
    public final void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletrequest;
        HttpServletResponse response = (HttpServletResponse) servletresponse;

        // 解决rest中文乱码问题
        request.setCharacterEncoding("utf-8");

        // 解决ajax跨域问题
        String origin = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", StringUtils.isNotBlank(origin)?origin:"*");
        response.setHeader("Access-Control-Expose-Headers ", "token,x-requested-with,content-type,No-Cache"); // 允许跨域请求获取header
        response.setHeader("Access-Control-Request-Method ", "GET,POST,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Request-Headers ", "token,x-requested-with,content-type,No-Cache");
        response.setHeader("Access-Control-Allow-Methods ", "GET,POST,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers ", "token,x-requested-with,,content-type,No-Cache");

        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (null != pathInfo && pathInfo.length() > 0) {
            url += pathInfo;
        }
        // 去掉最后的斜杠/
        if(null != url && url.endsWith("/") && !url.equals("/")) {
            url = url.substring(0, url.length()-1);
        }
        if (checkExclude(url) || !checkInclude(url)) {// 无需过滤该请求，则pass
            filterchain.doFilter(servletrequest, servletresponse);
            return;
        }

        // 调用innerDoFilter进行过滤
        innerDoFilter(servletrequest, servletresponse, filterchain);
        return;
    }

    /**
     * 需子类覆盖，实现过滤逻辑
     *
     * @param servletrequest
     * @param servletresponse
     * @param filterchain
     */
    public abstract void innerDoFilter(ServletRequest servletrequest, ServletResponse servletresponse,
                                       FilterChain filterchain) throws IOException, ServletException;


    /**
     * 检验访问请求是否在include列表中
     *
     * @param requestUrl
     * @return
     */
    public final boolean checkInclude(String requestUrl) {
        boolean flag = true;
        if (null == includePattern || includePattern.length == 0) {
            return flag;
        }
        for (Pattern pat : includePattern) {
            if (flag = pat.matcher(requestUrl).matches())
                break;
        }
        return flag;
    }

    /**
     * 检验访问请求是否在exclude列表中
     *
     * @param requestUrl
     * @return
     */
    public final boolean checkExclude(String requestUrl) {
        boolean flag = false;
        if (null == excludePattern || excludePattern.length == 0) {
            return flag;
        }
        for (Pattern pat : excludePattern) {
            if (flag = pat.matcher(requestUrl).matches())
                break;
        }
        return flag;
    }
}
