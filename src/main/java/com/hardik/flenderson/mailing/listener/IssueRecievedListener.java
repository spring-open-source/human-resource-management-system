package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.IssueRecievedDto;
import com.hardik.flenderson.mailing.event.IssueRecievedEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class IssueRecievedListener {

	private final EmailService emailService;

	@EventListener
	public void listenToIssueRaisedEvent(IssueRecievedEvent issueRecievedEvent) {
		var issueReceivedDto = (IssueRecievedDto) issueRecievedEvent.getSource();
		try {
			emailService.sendEmail(issueReceivedDto.getEmail(), MapUtility.convert(issueReceivedDto),
					EmailTemplate.NEW_ISSUE_RECIEVED.getName(), EmailTemplate.NEW_ISSUE_RECIEVED.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("Unable to Send New Issue Received Email Notification " + e.toString());
		}
	}

}
