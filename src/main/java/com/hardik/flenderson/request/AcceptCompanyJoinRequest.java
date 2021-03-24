package com.hardik.flenderson.request;

import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
@Jacksonized
public class AcceptCompanyJoinRequest {

	private final UUID employeeId;
	private final Double monthlySalary;
	private final List<RoleAssociationRequest> roles;
	
}
