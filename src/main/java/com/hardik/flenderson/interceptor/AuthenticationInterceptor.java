package com.hardik.flenderson.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hardik.flenderson.interceptor.dto.UserDetailDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	private static ThreadLocal<UserDetailDto> threadLocal = new ThreadLocal<UserDetailDto>();
	
	public static UserDetailDto getUserDetails() {
		return (UserDetailDto) threadLocal.get();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String idToken = (String) request.getHeader("Authentication");
		String userId = (String) request.getHeader("User-Id");
		return true;
	}
}
