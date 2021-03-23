package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class EmployeeAccountCreationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3525820652888381887L;

	public EmployeeAccountCreationEvent(Object source) {
		super(source);
	}

}
