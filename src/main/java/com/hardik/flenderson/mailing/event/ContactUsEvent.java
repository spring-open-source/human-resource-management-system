package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class ContactUsEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3854969402090605347L;

	public ContactUsEvent(Object source) {
		super(source);
	}

}
