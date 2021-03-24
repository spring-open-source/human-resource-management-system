package com.hardik.flenderson.queue;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.flenderson.dto.MonthlySalaryWrapperDto;
import com.hardik.flenderson.service.SalaryRecordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PayrollQueueListenerService {

	private final SalaryRecordService salaryRecordService;

	@SqsListener("flenderson-payroll-listener-queue")
	public void loadMessageFromSQS(String message) {
		MonthlySalaryWrapperDto monthlySalaryWrapperDto = null;
		try {
			monthlySalaryWrapperDto = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule()).readValue(message, MonthlySalaryWrapperDto.class);
		} catch (IOException e) {
			log.error("Failed To Convert JSON to Object");
			log.error(e.toString());
		}
		salaryRecordService.saveAll(monthlySalaryWrapperDto);
		log.info("Successfully Pushed Data In Salary Record Table For " + LocalDate.now().minusDays(1).getMonthValue());

	}

}
