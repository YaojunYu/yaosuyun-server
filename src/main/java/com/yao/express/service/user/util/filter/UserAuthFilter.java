package com.yao.express.service.user.util.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yao.express.service.user.util.login.UserLoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 对token进行认证
 *
 * @author: York.Yu
 * @date: 2017/4/29
 */
public class UserAuthFilter extends PatternFilter {

	private Logger logger = LoggerFactory.getLogger(UserAuthFilter.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${session.expire.time}")
	private Long sessionTimeOut;

	@Override
	public void innerDoFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
//		logger.info("=================登录用户身份验证===============");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// retrieve token string
		String token = "session:"+httpRequest.getHeader("token");

		Object user;
		if(StringUtils.isBlank(token) || null == (user=redisTemplate.opsForValue().get(token))) {
			// 未找到user，认证失败
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");

			// 消息
			Map<String, Object> messageMap = new HashMap<>();
			messageMap.put("status", "FAIL");
			messageMap.put("success", false);
			messageMap.put("processing", false);
			messageMap.put("fail", true);
			messageMap.put("message", "用户身份认证失败");
			messageMap.put("code", 401);
			ObjectMapper objectMapper=new ObjectMapper();
			String writeValueAsString = objectMapper.writeValueAsString(messageMap);
//			((HttpServletResponse) response).setStatus(401);
			response.getWriter().write(writeValueAsString);

			return;
		} else {
			// 更新超时时间
//			long timeout = redisTemplate.getExpire(token, TimeUnit.SECONDS);
//			logger.info("redis expire timeout = {}", timeout);
			sessionTimeOut = (null == sessionTimeOut) ? -1 : sessionTimeOut;
			redisTemplate.expire(token, sessionTimeOut, TimeUnit.MILLISECONDS);
			// 存放信息到threadLocal中
			UserLoginUtils.saveUserLogin(user);
			// we're done checking; continue on
			filterchain.doFilter(request, response);
		}
	}

	@Override
	public void innerInit(FilterConfig filterconfig) throws ServletException {
	}

	@Override
	public String toString() {
		return null;
	}
}
