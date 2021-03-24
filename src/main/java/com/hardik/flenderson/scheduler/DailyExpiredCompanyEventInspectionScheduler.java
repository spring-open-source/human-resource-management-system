package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.CompanyEventService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DailyExpiredCompanyEventInspectionScheduler {

	private final CompanyEventService companyEventService;

	@Scheduled(cron = "0 0 0 * * ?")
	public void dailyExpiredCompanyEventInspectionJob() {
		companyEventService.inspectForExpiredEvents();
	}
}
