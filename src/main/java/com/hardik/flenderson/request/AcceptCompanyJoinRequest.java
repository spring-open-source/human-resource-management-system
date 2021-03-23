package com.hardik.flenderson.request;

import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
@Jacksonized
public class AcceptCompanyJoinRequest {

	private final UUID employeeId;
	
}
