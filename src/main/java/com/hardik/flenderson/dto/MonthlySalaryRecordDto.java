package com.hardik.flenderson.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MonthlySalaryRecordDto {
	
	private final UUID employeeId;
	private final UUID companyId;
	private final Double monthlySalary;
	private final Double bonusForMonth;
	private final Double penaltyForMonth;

}
