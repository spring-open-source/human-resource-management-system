package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.MonthlySalaryDetailService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MonthlyPayrollRefresherScheduler {

	private final MonthlySalaryDetailService monthlySalaryDetailService;

	@Scheduled(cron = "0 0 12 1 * ?")
	public void monthlyPayrollRefresherJob() {
		monthlySalaryDetailService.publishMonthlyRecords();
		monthlySalaryDetailService.refreshMonthlySalaryDetails();
	}

}
