package com.hardik.flenderson.mailing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ContactUsConfirmationDto {
	
	private final String name;
	private final String email;
	private final String message;

}
