package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.dto.UserLoginSuccessDto;
import com.hardik.flenderson.keycloak.TokenService;
import com.hardik.flenderson.keycloak.dto.KeycloakTokenDto;
import com.hardik.flenderson.keycloak.request.LogoutUserRequest;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")	
public class TokenController {

	private final TokenService tokenService;

	@GetMapping("v1/get-token/{code}")
	public UserLoginSuccessDto tokenExchangeHandler(@PathVariable(name = "code", required = true) final String code) {
		return tokenService.getAccessToken(code);
	}

	@GetMapping("v1/get-keycloak-url/{accountType}")
	public String keyCloakUrlGenerationHandler(
			@PathVariable(name = "accountType", required = true) final String accountType) {
		return tokenService.getKeyCloakUrl(accountType);
	}

	@GetMapping("v1/refresh-token/{refreshToken}")
	public KeycloakTokenDto refreshTokenHandler(
			@PathVariable(name = "refreshToken", required = true) final String refreshToken) {
		return tokenService.refreshToken(refreshToken);
	}
	
	@PostMapping("v1/logout")
	public void logoutHandler(@RequestBody(required = true)final LogoutUserRequest logoutUserRequest) {
		tokenService.logout(logoutUserRequest);
	}
}
