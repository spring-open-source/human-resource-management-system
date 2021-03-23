package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IssueType {

	COMPLAINT(1, "COMPLAINT"), HELP(2, "HELP"), SUGGESTION(3, "SUGGESTION"), SALARY_ISSUE(4, "SALARY_ISSUE"),
	LEAVE_REQUEST(5, "LEAVE_REQUEST"), OTHER(6, "OTHER");

	private final Integer issueId;

	private final String issue;

}
