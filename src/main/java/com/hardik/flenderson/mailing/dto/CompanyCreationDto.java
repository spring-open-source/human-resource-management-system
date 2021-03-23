package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class CompanyCreationDto {
	
	private String companyName;
	private String firstName;
	private String companyCode;
	private String email;

}
