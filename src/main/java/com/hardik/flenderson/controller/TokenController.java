package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.keycloak.TokenService;
import com.hardik.flenderson.keycloak.dto.KeycloakTokenDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TokenController {

	private final TokenService tokenService;

	@GetMapping("v1/get-token/{code}")
	public KeycloakTokenDto tokenExchangeHandler(@PathVariable(name = "code", required = true) final String code) {
		return tokenService.getAccessToken(code);
	}

	@GetMapping("v1/get-keycloak-url/{accountType}")
	public String keyCloakUrlGenerationHandler(
			@PathVariable(name = "accountType", required = true) final String accountType) {
		return tokenService.getKeyCloakUrl(accountType);
	}

}
