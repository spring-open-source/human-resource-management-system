package com.hardik.flenderson.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class EmployeeIssueResponseCreationRequest {
	
	private final String response;
	private final UUID issueId;

}
