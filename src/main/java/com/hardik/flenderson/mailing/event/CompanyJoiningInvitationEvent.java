package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyJoiningInvitationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5742042247443389891L;

	public CompanyJoiningInvitationEvent(Object source) {
		super(source);
	}
}
