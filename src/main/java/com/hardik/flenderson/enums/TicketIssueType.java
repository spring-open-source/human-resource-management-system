package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketIssueType {

	NEED_HELP("NEED HELP"), COMPLAINT("COMPLAINT"), SUGGESTION("SUGGESTION"), CAREER("CAREER"), INQUIRY("INQUIRY"),
	OTHER("OTHER");

	private final String issueType;

}
