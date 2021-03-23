package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class IssueRecievedDto {
	
	private String managerName;
	private String employeeName;
	private String issueType;
	private String description;
	private String email;

}
