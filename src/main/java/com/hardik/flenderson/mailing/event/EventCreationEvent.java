package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class EventCreationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6127601711691016452L;

	public EventCreationEvent(Object source) {
		super(source);
	}

}
