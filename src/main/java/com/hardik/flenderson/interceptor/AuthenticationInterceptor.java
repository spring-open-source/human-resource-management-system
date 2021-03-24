package com.hardik.flenderson.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hardik.flenderson.interceptor.dto.UserDetailDto;
import com.hardik.flenderson.utility.JwtUtility;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	private static ThreadLocal<UserDetailDto> threadLocal = new ThreadLocal<UserDetailDto>();

	public static UserDetailDto getUserDetails() {
		return (UserDetailDto) threadLocal.get();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final var authentication = (String) request.getHeader("Authentication");
		final var userId = (String) request.getHeader("User-Id");
		if (authentication == null || userId == null)
			throw new RuntimeException("ID TOKEN AND USER ID NOT PRESENT");
		final var idTokenArray = authentication.split(" ");
		if (idTokenArray[0].toUpperCase() != "BEARER")
			throw new RuntimeException("USE BEARER STRATEGY");
		final var idToken = idTokenArray[1];
		if (JwtUtility.isExpired(idToken))
			throw new RuntimeException("TOKEN EXPIRED");
		threadLocal.set(UserDetailDto.builder().userId(UUID.fromString(userId))
				.accountType(JwtUtility.getAccountType(idToken)).build());
		return true;
	}
}
