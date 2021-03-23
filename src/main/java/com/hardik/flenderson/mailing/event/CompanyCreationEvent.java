package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyCreationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8611808590766263745L;

	public CompanyCreationEvent(Object source) {
		super(source);
	}

}
