package com.hardik.flenderson.utility;

import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.entity.Manager;

import lombok.experimental.UtilityClass;

@UtilityClass
public class S3KeyUtility {
	
	public String getProfilePictureKey(Employee employee) {
		return "EMPLOYEE/"+employee.getEmailId();
	}
	
	public String getProfilePictureKey(Manager manager) {
		return "EMPLOYEE/"+manager.getEmailId();
	}

}
