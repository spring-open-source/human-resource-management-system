package com.hardik.flenderson.queue;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.queue.configuration.QueueConfiguration;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableConfigurationProperties(QueueConfiguration.class)
public class QueueListenerService {


	@SqsListener("flenderson-attendance-listener-queue")
	public void loadMessageFromSQS(String message) {
		
	}
}