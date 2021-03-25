package com.hardik.flenderson.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ManagerSocialCreationRequest {
	
	private final String name;
	private final String url;

}
