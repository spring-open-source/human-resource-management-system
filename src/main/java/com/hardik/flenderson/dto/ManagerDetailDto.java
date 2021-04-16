package com.hardik.flenderson.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ManagerDetailDto {
	
	private final String emailId;
	private final String firstName;
	private final String lastName;
	private final String middleName;
	private final String description;
	private final String status;
	private final String gender;
	private final String profilePicture;
	private final LocalDate dateOfBirth;
	private final Integer countryId;
	private final boolean profileCompleted;
	private final boolean companyCreated;
}
