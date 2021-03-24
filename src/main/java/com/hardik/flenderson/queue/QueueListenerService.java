package com.hardik.flenderson.queue;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.flenderson.dto.AttendanceWrapperDto;
import com.hardik.flenderson.queue.configuration.QueueConfiguration;
import com.hardik.flenderson.service.AttendanceRecordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(QueueConfiguration.class)
public class QueueListenerService {
	
	private final AttendanceRecordService attendanceRecordService; 


	@SqsListener("flenderson-attendance-listener-queue")
	public void loadMessageFromSQS(String message) {
		AttendanceWrapperDto attendanceWrapperDto = null;
		try {
			attendanceWrapperDto = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule()).readValue(message, AttendanceWrapperDto.class);
		} catch (IOException e) {
			log.error("Failed To Convert JSON to Object");
			log.error(e.toString());
		}
		attendanceRecordService.saveAll(attendanceWrapperDto);
		log.info("Successfully Pushed Data In Attendance Record Table For " + LocalDate.now().minusDays(1));
	}
}