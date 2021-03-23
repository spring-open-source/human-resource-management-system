package com.hardik.flenderson.mailing.event;

import org.springframework.context.ApplicationEvent;

public class CompanyJoiningRequestAcceptanceEvent extends ApplicationEvent {

	private static final long serialVersionUID = -786440700848498033L;

	public CompanyJoiningRequestAcceptanceEvent(Object source) {
		super(source);
	}

}
