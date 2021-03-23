package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyJoiningRequestRecievedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 3280256577362906540L;

	public CompanyJoiningRequestRecievedEvent(Object source) {
		super(source);
	}
}
