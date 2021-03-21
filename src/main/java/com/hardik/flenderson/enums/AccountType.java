package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
	
	EMPLOYEE("EMPLOYEE"), MANAGER("MANAGER"), NOT_REGISTERED("NOT REGISTERED");
	
	private final String accountType;

}
