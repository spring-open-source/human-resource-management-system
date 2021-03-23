package com.hardik.flenderson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompanyStatus {
	
	IN_NO_COMPANY(1, "IN NO COMPANY"), REQUEST_SENT(2,"REQUEST SENT"), IN_COMPANY(3, "IN COMPANY");
	
	private final Integer statusId;
	
	private final String companyStatus;

}
