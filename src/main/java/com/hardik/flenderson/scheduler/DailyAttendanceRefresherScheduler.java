package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.EmployeeDailyAttendanceService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DailyAttendanceRefresherScheduler {
	
	private final EmployeeDailyAttendanceService employeeDailyAttendanceService; 
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void dailyAttendanceRefresherJob() {
		employeeDailyAttendanceService.publishDailyRecords();
		employeeDailyAttendanceService.refreshDailyAttendance();
	}

}
