package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class CompanyEventNotificationDto {
	
	private String employeeName;
	private String companyName;
	private String email;

}
