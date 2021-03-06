package com.hardik.flenderson.queue.configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.flenderson.sqs.attendance")
public class AttendanceQueueConfiguration {
	
	private Configuration properties = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String queueName;
	}

}