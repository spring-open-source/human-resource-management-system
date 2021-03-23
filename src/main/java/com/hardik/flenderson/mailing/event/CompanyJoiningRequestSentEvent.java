package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyJoiningRequestSentEvent extends ApplicationEvent {

	private static final long serialVersionUID = 938374988738194665L;

	public CompanyJoiningRequestSentEvent(Object source) {
		super(source);
	}

}
