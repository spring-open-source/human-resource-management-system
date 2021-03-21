package com.hardik.flenderson.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserLoginSuccessDto {
	
	private final String idToken;
	private final String refreshToken;
	private final UUID userId;
	private final String accountType;

}
