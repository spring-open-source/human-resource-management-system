package com.hardik.flenderson.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MasterCountryDto {
	
	private final Integer id;
	private final String code;
	private final String name;
	private final String flagUrl;

}
