package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SnsTopic {

	ATTENDANCE_TOPIC("ATTENDANCE"), PAYROLL_TOPIC("PAYROLL");

	private final String topicName;

}
