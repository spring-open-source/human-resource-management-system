package com.hardik.flenderson.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.hardik.flenderson.queue.configuration.AttendanceQueueConfiguration.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.flenderson.sqs.payroll")
public class PayrollQueueConfiguration {
	
	private Configuration properties = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String queueName;
	}

}
