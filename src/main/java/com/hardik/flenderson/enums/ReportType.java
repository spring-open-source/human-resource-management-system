package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportType {

	MONTHLY_ATTENDANCE_RECORD("MONTHLY-ATTENDANCE-RECORD", 1), MONTHLY_PAYROLL_RECORD("MONTHLY-PAYROLL-RECORD", 2);

	private final String type;
	private final Integer id;

}
