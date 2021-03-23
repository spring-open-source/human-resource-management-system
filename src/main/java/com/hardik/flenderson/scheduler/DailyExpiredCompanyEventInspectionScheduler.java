package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DailyExpiredCompanyEventInspectionScheduler {

	@Scheduled(cron = "0 0 0 * * ?")
	public void dailyExpiredCompanyEventInspectionJob() {
		
	}
}
