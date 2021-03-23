package com.hardik.flenderson.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CompanyEventCreationRequest {
	
	private final String heading;
	private final String description;
	private final LocalDate dueDate;
	
}
