package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MonthlyPayrollReportDto {
	
	private String managerName;
	private String month;
	private String companyName;
	private String email;

}
