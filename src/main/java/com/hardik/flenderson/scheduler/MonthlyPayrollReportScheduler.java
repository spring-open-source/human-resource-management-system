package com.hardik.flenderson.scheduler;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.SalaryRecordService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MonthlyPayrollReportScheduler {
	
	private final SalaryRecordService salaryRecordService; 
	
	@Scheduled(cron = "0 0 15 1 * ?")
	public void monthlyPayrollReportGenerationJob() {
		final var date = LocalDate.now().minusDays(3l);
		salaryRecordService.generateExcelFiles(date.getMonthValue(), date.getYear());
	}


}
