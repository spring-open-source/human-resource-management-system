package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
	
	MALE("MALE"), FEMALE("FEMALE"), OTHER("OTHER");
	
	private final String gender;

}
