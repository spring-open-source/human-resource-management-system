package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class IssueRaisedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7018962301550663696L;

	public IssueRaisedEvent(Object source) {
		super(source);
	}

}
