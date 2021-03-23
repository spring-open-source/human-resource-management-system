package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyJoiningRequestRejectionEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6059719788508834015L;

	public CompanyJoiningRequestRejectionEvent(Object source) {
		super(source);
	}
}
