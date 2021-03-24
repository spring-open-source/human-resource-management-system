package com.hardik.flenderson.notification;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.hardik.flenderson.enums.SnsTopic;
import com.hardik.flenderson.notification.configuration.AttendanceNotificationConfiguration;
import com.hardik.flenderson.notification.configuration.PayrollNotificationConfiguration;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties({ AttendanceNotificationConfiguration.class, PayrollNotificationConfiguration.class })
public class NotificationService {

	private final AttendanceNotificationConfiguration attendanceNotificationConfiguration;

	private final PayrollNotificationConfiguration payrollNotificationConfiguration;

	private final AmazonSNSClient amazonSNSClient;

	public void publishMessage(String message, String topicName) {
		final var topicArn = topicName.equals(SnsTopic.ATTENDANCE_TOPIC.getTopicName())
				? attendanceNotificationConfiguration.getNotificationService().getTopicArn()
				: payrollNotificationConfiguration.getNotificationService().getTopicArn();
		amazonSNSClient.publish(topicArn, message, "Subject");
	}

}