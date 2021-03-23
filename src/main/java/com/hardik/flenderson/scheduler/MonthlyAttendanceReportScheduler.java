package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MonthlyAttendanceReportScheduler {
	
	@Scheduled(cron = "0 0 12 1 * ?")
	public void monthlyAttendanceReportJob() {
		
	}

}
