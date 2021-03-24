package com.hardik.flenderson.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CompanyJoinRequest {
	
	private final String companyName;
	private final String companyCode;

}
