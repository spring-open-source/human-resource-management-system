package com.hardik.flenderson.utility;

import lombok.experimental.UtilityClass;
import net.bytebuddy.utility.RandomString;

@UtilityClass
public class CompanyCodeUtility {
	
	public String generateCode() {
		return RandomString.make(8).toUpperCase();
	}

}
