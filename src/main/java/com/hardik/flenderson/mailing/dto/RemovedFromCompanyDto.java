package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class RemovedFromCompanyDto {
	
	private String firstName;
	private String companyname;
	private String managerName;
	private String email;

}
