package com.hardik.flenderson.notification;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.hardik.flenderson.notification.configuration.NotificationConfiguration;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(NotificationConfiguration.class)
public class NotificationService {

	private final NotificationConfiguration notificationConfiguration;

	private final AmazonSNSClient amazonSNSClient;

	public void publishMessage(String message) {
		var configuration = notificationConfiguration.getNotificationService();
		amazonSNSClient.publish(configuration.getTopicArn(), message, "Subject");
	}

}