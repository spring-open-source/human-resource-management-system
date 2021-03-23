package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class IssueResponseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8035691226134369106L;

	public IssueResponseEvent(Object source) {
		super(source);
	}
}
