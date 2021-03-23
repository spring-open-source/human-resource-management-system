package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class CompanyJoinRequestRecievedDto {

	private String firstName;
	private String employeeEmail;
	private String companyName;
	private String employeeName;
	private String email;

}
