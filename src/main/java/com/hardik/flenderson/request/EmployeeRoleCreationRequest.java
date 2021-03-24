package com.hardik.flenderson.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class EmployeeRoleCreationRequest {
	
	private final UUID employeeId;
	private final UUID roleId;
	private final String name;

}
