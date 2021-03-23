package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class CompanyJoinRequestRejectedDto {
	
	private String firstName;
	private String companyName;
	private String managerName;
	private String email;

}
