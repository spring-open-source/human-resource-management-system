package com.hardik.flenderson.queue.bean;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.hardik.flenderson.queue.configuration.AttendanceQueueConfiguration;

import lombok.AllArgsConstructor;

@Component
@EnableConfigurationProperties(AttendanceQueueConfiguration.class)
@AllArgsConstructor
public class AmazonSqsBean {

	private final AttendanceQueueConfiguration attendanceQueueConfiguration;

	@Primary
	@Bean
	public AmazonSQSAsync amazonSQSAsync() {
		final var queueProperties = attendanceQueueConfiguration.getProperties();
		return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(queueProperties.getAccessKey(), queueProperties.getSecretKey())))
				.build();
	}
}