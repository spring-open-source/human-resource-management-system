package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.IssueResponseRecievedDto;
import com.hardik.flenderson.mailing.event.IssueResponseEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class IssueResponseListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToIssueResponseEvent(IssueResponseEvent issueResponseEvent) {
		var issueResponseReceivedDto = (IssueResponseRecievedDto) issueResponseEvent.getSource();
		try {
			emailService.sendEmail(issueResponseReceivedDto.getEmail(), MapUtility.convert(issueResponseReceivedDto),
					EmailTemplate.ISSUE_RESPONSE_RECIEVED.getName(),
					EmailTemplate.ISSUE_RESPONSE_RECIEVED.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("Unable To Send Issue Response Recieved Notification " + e.toString());
		}
	}

}
