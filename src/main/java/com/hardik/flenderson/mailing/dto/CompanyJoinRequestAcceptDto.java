package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CompanyJoinRequestAcceptDto {
	
	private String firstName;
	private String companyName;
	private String managerName;
	private String email;
	
}
