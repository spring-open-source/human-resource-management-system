package com.hardik.flenderson.utility;

import com.auth0.jwt.JWT;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtility {

	public KeycloakUserDto getUser(String token) {
		final var decodedJwt = JWT.decode(token).getClaims();
		return KeycloakUserDto.builder().firstName(decodedJwt.get("given_name").asString())
				.lastName(decodedJwt.get("family_name").asString()).email(decodedJwt.get("email").asString()).build();
	}

}
