package com.hardik.flenderson.keycloak.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class KeycloakUserDto {
	
	private final String firstName;
	private final String lastName;
	private final String email;
	private final UUID userId;

}
