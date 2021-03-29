package com.hardik.flenderson.utility;

import com.hardik.flenderson.entity.Company;
import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.entity.Manager;

import lombok.experimental.UtilityClass;

@UtilityClass
public class S3KeyUtility {

	public String getProfilePictureKey(Employee employee) {
		return "EMPLOYEE/" + employee.getEmailId();
	}

	public String getProfilePictureKey(Manager manager) {
		return "MANAGER/" + manager.getEmailId();
	}

	public String getCompanyLogoKey(Manager manager, String companyName) {
		return "MANAGER/" + manager.getEmailId() + "/" + companyName;
	}

	public String getCompanyDocumentKey(Manager manager, String companyName) {
		return "MANAGER/" + manager.getEmailId() + "/" + companyName + "/docs";
	}

	public String getCompanyEventImageKey(Manager manager, String companyName, String eventHeading) {
		return "MANAGER/" + manager.getEmailId() + "/" + companyName + "/" + eventHeading;
	}

	public String getMonthlyAttendanceRecordKey(Integer month, Integer year, Manager manager, Company company) {
		return "MANAGER/" + manager.getEmailId() + "/" + company.getName() + "/ATTENDANCE-RECORD/" + month + "-" + year;
	}

}
