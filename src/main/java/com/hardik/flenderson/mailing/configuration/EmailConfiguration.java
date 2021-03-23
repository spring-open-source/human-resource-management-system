package com.hardik.flenderson.mailing.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.flenderson")
public class EmailConfiguration {
	
	private Configuration mail = new Configuration();

	@Data
	public class Configuration {
		private String notificationEmail;
	}

}
