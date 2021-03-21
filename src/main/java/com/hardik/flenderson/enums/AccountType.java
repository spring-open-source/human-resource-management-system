package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
	
	EMPLOYEE("EMPLOYEE"), MANAGER("MANAGER");
	
	private final String accountType;

}
