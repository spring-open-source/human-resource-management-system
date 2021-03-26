package com.hardik.flenderson.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CompanyJoinInvitationCreationRequest {
	
	private final String name;
	private final String emailId;

}
