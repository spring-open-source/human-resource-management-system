package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class MonthlyPayrollReportEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7992857645042563775L;

	public MonthlyPayrollReportEvent(Object source) {
		super(source);
	}
}
