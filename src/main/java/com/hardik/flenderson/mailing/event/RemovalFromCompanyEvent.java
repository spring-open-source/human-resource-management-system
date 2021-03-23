package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class RemovalFromCompanyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 3587731275802728102L;

	public RemovalFromCompanyEvent(Object source) {
		super(source);
	}

}
