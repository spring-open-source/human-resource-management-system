package com.hardik.flenderson.notification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.flenderson")
public class NotificationConfiguration {
	
	private Configuration notificationService = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String topicArn;
	}

}