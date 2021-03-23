package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class MonthlyAttendanceReportDto {
	
	private String managerName;
	private String month;
	private String companyName;
	private String email;

}
