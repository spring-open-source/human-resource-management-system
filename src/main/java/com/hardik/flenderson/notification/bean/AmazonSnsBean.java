package com.hardik.flenderson.notification.bean;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.hardik.flenderson.notification.configuration.AttendanceNotificationConfiguration;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(AttendanceNotificationConfiguration.class)
public class AmazonSnsBean {

	private final AttendanceNotificationConfiguration attendanceNotificationConfiguration;

	@Primary
	@Bean
	@Profile("default")
	public AmazonSNSClient getSnsClient() {
		var configuration = attendanceNotificationConfiguration.getNotificationService();
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey())))
				.build();
	}

}