package com.hardik.flenderson.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ManagerDetailUpdationRequest {
	
	private final UUID managerId;
	private final String firstName;
	private final String middleName;
	private final String lastName;
	private final String description;
	private final String status;
	private final String gender;
	private final LocalDate dateOfBirth;
	private final Integer countryId;

}
