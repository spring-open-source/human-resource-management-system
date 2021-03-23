package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class ManagerAccountCreationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6048774838511986308L;

	public ManagerAccountCreationEvent(Object source) {
		super(source);
	}
}
