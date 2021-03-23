package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class MonthlyAttendanceReportEvent extends ApplicationEvent {

	private static final long serialVersionUID = -9093559626131430029L;

	public MonthlyAttendanceReportEvent(Object source) {
		super(source);
	}

}
