package com.hardik.flenderson.interceptor;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.AccessTokenExpiredException;
import com.hardik.flenderson.exception.AuthenticationBearerStrategyNotUsedException;
import com.hardik.flenderson.exception.MissingAccessTokenAndUserIdException;
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
			throw new MissingAccessTokenAndUserIdException(
					ExceptionMessage.MISSING_ACCESS_TOKEN_AND_USER_ID.getMessage());
		final var idTokenArray = authentication.split(" ");
		if (!idTokenArray[0].toUpperCase().equals("BEARER") || List.of(idTokenArray).size() != 2)
			throw new AuthenticationBearerStrategyNotUsedException(
					ExceptionMessage.AUTHENTICATION_BEARER_STRATEGY_NOT_USED.getMessage());
		final var idToken = idTokenArray[1];
		if (JwtUtility.isExpired(idToken))
			throw new AccessTokenExpiredException(ExceptionMessage.ACCESS_TOKEN_EXPIRED.getMessage());
		threadLocal.set(UserDetailDto.builder().userId(UUID.fromString(userId))
				.accountType(JwtUtility.getAccountType(idToken)).build());
		return true;
	}
}