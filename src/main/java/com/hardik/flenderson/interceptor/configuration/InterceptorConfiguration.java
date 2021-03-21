package com.hardik.flenderson.interceptor.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.interceptor.exclusion.InterceptorExclusionPath;

@Component
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
				.excludePathPatterns(List.of(InterceptorExclusionPath.values()).stream()
						.map(apiToBeExcluded -> apiToBeExcluded.getPath()).collect(Collectors.toList()));
	}
}
