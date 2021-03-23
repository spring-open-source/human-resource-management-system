package com.hardik.flenderson.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class EmployeeIssueCreationRequest {
	
	private final String title;
	private final String description;
	private final Integer issueType;

}
