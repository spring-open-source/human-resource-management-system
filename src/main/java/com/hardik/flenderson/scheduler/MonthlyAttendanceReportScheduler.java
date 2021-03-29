package com.hardik.flenderson.scheduler;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.AttendanceRecordService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MonthlyAttendanceReportScheduler {
	
	private final AttendanceRecordService attendanceRecordService;
	
	@Scheduled(cron = "0 0 14 1 * ?")
	public void monthlyAttendanceReportJob() {
		final var date = LocalDate.now().minusDays(3l);
		attendanceRecordService.generateExcelFiles(date.getMonthValue(), date.getYear());
	}

}
