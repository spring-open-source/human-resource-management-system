package com.hardik.flenderson.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AdminSupportTicketCreationRequest {
	
	private final String raisedByName;
	private final String raisedByEmail;
	private final String raisedByAccountType;
	private final String description;
	private final String ticketIssue;

}
