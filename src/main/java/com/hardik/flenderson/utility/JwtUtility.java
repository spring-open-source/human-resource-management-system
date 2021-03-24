package com.hardik.flenderson.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.auth0.jwt.JWT;
import com.hardik.flenderson.enums.AccountType;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtility {

	public KeycloakUserDto getUser(String token) {
		final var decodedJwt = JWT.decode(token).getClaims();
		return KeycloakUserDto.builder().firstName(decodedJwt.get("given_name").asString())
				.lastName(decodedJwt.get("family_name").asString()).email(decodedJwt.get("email").asString()).build();
	}
	
	public boolean isExpired(String token) {
		final var decodedJwt = JWT.decode(token).getClaims();
		if (LocalDateTime.now().isAfter(Instant.ofEpochMilli(decodedJwt.get("exp").asInt())
                .atZone(ZoneId.systemDefault()).toLocalDateTime()))
			return false;
		return true;
	}
	
	public String getAccountType(String idToken) {
		final var decodedJwt = JWT.decode(idToken).getClaims();
		final var scopes = decodedJwt.get("scope").asString().toUpperCase();
		if (scopes.contains(AccountType.MANAGER.getAccountType()))
			return AccountType.MANAGER.getAccountType();
		else if (scopes.contains(AccountType.EMPLOYEE.getAccountType()))
			return AccountType.EMPLOYEE.getAccountType();
		else 
			throw new RuntimeException("NO CORRECT SCOPE");
	}
}
