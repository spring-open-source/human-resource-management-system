package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.CompanyService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MonthlyCompanyCodeRefresherScheduler {
	
	private final CompanyService companyService; 

	@Scheduled(cron = "0 0 12 1 * ?")
	public void onthlyCompanyCodeRefresherJon() {
		companyService.refreshCodes();
	}

}
