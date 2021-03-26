package com.hardik.flenderson.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.flenderson.service.RejectedEmployeeCompanyMappingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RejectedEmployeeExpirationScheduler {

	private final RejectedEmployeeCompanyMappingService rejectedEmployeeCompanyMappingService;

	@Scheduled(cron = "0 0 12 1 * ?")
	public void rejectedEmployeeExpirationInspectionJob() {
		rejectedEmployeeCompanyMappingService.inspectExpiration();
	}

}
