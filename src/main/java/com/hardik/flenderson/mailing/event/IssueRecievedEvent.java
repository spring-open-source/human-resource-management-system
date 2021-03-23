package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class IssueRecievedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 3241470666975416127L;

	public IssueRecievedEvent(Object source) {
		super(source);
	}
}
