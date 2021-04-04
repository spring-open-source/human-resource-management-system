package com.hardik.flenderson.bootstrap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.hardik.flenderson.notification.configuration.AttendanceNotificationConfiguration;
import com.hardik.flenderson.notification.configuration.PayrollNotificationConfiguration;
import com.hardik.flenderson.queue.configuration.AttendanceQueueConfiguration;
import com.hardik.flenderson.queue.configuration.PayrollQueueConfiguration;
import com.hardik.flenderson.storage.configuration.AmazonS3Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
@EnableConfigurationProperties({ AmazonS3Configuration.class, AttendanceQueueConfiguration.class,
		PayrollQueueConfiguration.class, AttendanceNotificationConfiguration.class,
		PayrollNotificationConfiguration.class })
public class AwsServiceInitializer implements ApplicationListener<ApplicationContextEvent> {

	private final AmazonS3 amazonS3;

	private final AmazonSNSClient amazonSNSClient;

	private final AmazonSQSAsync amazonSQSAsync;

	private final AmazonS3Configuration amazonS3Configuration;

	private final AttendanceNotificationConfiguration attendanceNotificationConfiguration;

	private final AttendanceQueueConfiguration attendanceQueueConfiguration;

	private PayrollNotificationConfiguration payrollNotificationConfiguration;

	private final PayrollQueueConfiguration payrollQueueConfiguration;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		final var s3Configuration = amazonS3Configuration.getStorage();
		final var attendanceSnsConfiguration = attendanceNotificationConfiguration.getNotificationService();
		final var attendanceSqsConfiguration = attendanceQueueConfiguration.getProperties();
		final var payrollSnsConfiguration = payrollNotificationConfiguration.getNotificationService();
		final var payrollSqsConfiuration = payrollQueueConfiguration.getProperties();

		// INITIALIZING AWS S3 BUCKET
		if (!amazonS3.listBuckets().parallelStream()
				.anyMatch(bucket -> bucket.getName().equals(s3Configuration.getBucketName()))) {
			log.info("CREATING NEW BUCKET " + s3Configuration.getBucketName() + ": " + LocalDateTime.now());
			amazonS3.createBucket(
					new CreateBucketRequest(s3Configuration.getBucketName(), s3Configuration.getRegion()));
			log.info("CREATED BUCKET " + s3Configuration.getBucketName() + ": " + LocalDateTime.now());
		} else
			log.info("BUCKET " + s3Configuration.getBucketName() + " ALREADY EXISTS!");

		// INITIALIZING AWS SNS TOPIC FOR ATTENDANCE SYSTEM
		if (!amazonSNSClient.listTopics().getTopics().parallelStream()
				.anyMatch(topicArn -> topicArn.getTopicArn().equals(attendanceSnsConfiguration.getTopicArn()))) {
			var topicArnSplitByColon = List.of(attendanceSnsConfiguration.getTopicArn().split(":"));
			var topicName = topicArnSplitByColon.get(topicArnSplitByColon.size() - 1);
			log.info("CREATING SNS TOPIC FOR ATTENDANCE RECORDING WITH NAME " + topicName + " " + LocalDateTime.now());
			var attendanceSnsTopic = amazonSNSClient.createTopic(new CreateTopicRequest(topicName));
			attendanceSnsTopic.setTopicArn(attendanceSnsConfiguration.getTopicArn());
			log.info("CREATED SNS TOPIC FOR ATTENDANCE RECORDING \"" + attendanceSnsTopic.getTopicArn() + "\" "
					+ LocalDateTime.now());
		} else {
			log.info("SNS TOPIC FOR ATTENDANCE RECORDING \"" + attendanceSnsConfiguration.getTopicArn()
					+ "\" ALREADY EXISTS!");
		}

		// INITIALIZING AWS SNS TOPIC FOR PAYROLL SYSTEM
		if (!amazonSNSClient.listTopics().getTopics().parallelStream()
				.anyMatch(topicArn -> topicArn.getTopicArn().equals(payrollSnsConfiguration.getTopicArn()))) {
			var topicArnSplitByColon = List.of(payrollSnsConfiguration.getTopicArn().split(":"));
			var topicName = topicArnSplitByColon.get(topicArnSplitByColon.size() - 1);
			log.info("CREATING SNS TOPIC FOR PAYROLL RECORDING WITH NAME " + topicName + " " + LocalDateTime.now());
			var payrollSnsTopic = amazonSNSClient.createTopic(new CreateTopicRequest(topicName));
			payrollSnsTopic.setTopicArn(payrollSnsConfiguration.getTopicArn());
			log.info("CREATED SNS TOPIC FOR PAYROLL RECORDING \"" + payrollSnsTopic.getTopicArn() + "\" "
					+ LocalDateTime.now());
		} else {
			log.info("SNS TOPIC FOR PAYROLL RECORDING \"" + payrollSnsConfiguration.getTopicArn()
					+ "\" ALREADY EXISTS!");
		}

		// INITIALIZING AWS SQS QUEUE FOR ATTENDANCE SYSTEM
		if (!amazonSQSAsync.listQueues().getQueueUrls().parallelStream().anyMatch(queueUrl -> {
			var queueUrlSplitByColon = List.of(queueUrl.split("/"));
			var queueName = queueUrlSplitByColon.get(queueUrlSplitByColon.size() - 1);
			if (queueName.equals(attendanceSqsConfiguration.getQueueName()))
				return true;
			return false;
		})) {
			log.info("CREATING QUEUE " + attendanceSqsConfiguration.getQueueName() + " " + LocalDateTime.now());
			amazonSQSAsync.createQueue(new CreateQueueRequest(attendanceSqsConfiguration.getQueueName()));
			log.info("SUCCESSFULLY CREATED QUEUE " + attendanceSqsConfiguration.getQueueName() + " "
					+ LocalDateTime.now());
		} else {
			log.info("SQS QUEUE FOR ATTENDANCE RECORDING \"" + attendanceSqsConfiguration.getQueueName()
					+ "\" ALREADY EXISTS!");
		}

		// INITIALIZING AWS SQS QUEUE FOR PAYROLL SYSTEM
		if (!amazonSQSAsync.listQueues().getQueueUrls().parallelStream().anyMatch(queueUrl -> {
			var queueUrlSplitByColon = List.of(queueUrl.split("/"));
			var queueName = queueUrlSplitByColon.get(queueUrlSplitByColon.size() - 1);
			if (queueName.equals(payrollSqsConfiuration.getQueueName()))
				return true;
			return false;
		})) {
			log.info("CREATING QUEUE " + payrollSqsConfiuration.getQueueName() + " " + LocalDateTime.now());
			amazonSQSAsync.createQueue(new CreateQueueRequest(payrollSqsConfiuration.getQueueName()));
			log.info("SUCCESSFULLY CREATED QUEUE " + payrollSqsConfiuration.getQueueName() + " " + LocalDateTime.now());
		} else {
			log.info("SQS QUEUE FOR PAYROLL RECORDING \"" + payrollSqsConfiuration.getQueueName()
					+ "\" ALREADY EXISTS!");
		}

		// SUBSCRIBING ATTENDANCE QUEUE WITH ATTENDANCE TOPIC
		log.info("SUBSCRIBING ATTENDANCE QUEUE " + attendanceSqsConfiguration.getQueueName()
				+ " TO ATTENDANCE SNS TOPIC " + attendanceSnsConfiguration.getTopicArn());
		var attendanceQueueUrl = amazonSQSAsync.listQueues().getQueueUrls().parallelStream()
				.filter(queueUrl -> queueUrl.contains(attendanceSqsConfiguration.getQueueName()))
				.collect(Collectors.toList()).get(0);
		var attendanceQueueAttributes = amazonSQSAsync.getQueueAttributes(attendanceQueueUrl, List.of("All"))
				.getAttributes();
		amazonSNSClient.subscribe(new SubscribeRequest(attendanceSnsConfiguration.getTopicArn(), "sqs",
				attendanceQueueAttributes.get("QueueArn")));
		log.info("SUBSCRIBED ATTENDANCE QUEUE " + attendanceSqsConfiguration.getQueueName()
				+ " TO ATTENDANCE SNS TOPIC " + attendanceSnsConfiguration.getTopicArn());

		// SUBSCRIBING PAYROLL QUEUE WITH PAYROLL TOPIC
		log.info("SUBSCRIBING PAYROLL QUEUE " + payrollSqsConfiuration.getQueueName() + " TO PAYROLL SNS TOPIC "
				+ payrollSnsConfiguration.getTopicArn());
		var payrollQueueUrl = amazonSQSAsync.listQueues().getQueueUrls().parallelStream()
				.filter(queueUrl -> queueUrl.contains(attendanceSqsConfiguration.getQueueName()))
				.collect(Collectors.toList()).get(0);
		var payrollQueueAttributes = amazonSQSAsync.getQueueAttributes(payrollQueueUrl, List.of("All")).getAttributes();
		amazonSNSClient.subscribe(new SubscribeRequest(payrollSnsConfiguration.getTopicArn(), "sqs",
				payrollQueueAttributes.get("QueueArn")));
		log.info("SUBSCRIBED PAYROLL QUEUE " + payrollSqsConfiuration.getQueueName() + " TO PAYROLL SNS TOPIC "
				+ payrollSnsConfiguration.getTopicArn());
	}

}
