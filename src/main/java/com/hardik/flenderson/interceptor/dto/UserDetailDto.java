package com.hardik.flenderson.interceptor.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserDetailDto {
	
	private final UUID userId;
	private final String accountType;

}
