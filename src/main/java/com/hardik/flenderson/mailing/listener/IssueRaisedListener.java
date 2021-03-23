package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.IssueRaisedSuccessfullyDto;
import com.hardik.flenderson.mailing.event.IssueRaisedEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class IssueRaisedListener {

	private final EmailService emailService;

	@EventListener
	public void listenToIssueRaisedEvent(IssueRaisedEvent issueRaisedEvent) {
		var issueRaisedDto = (IssueRaisedSuccessfullyDto) issueRaisedEvent.getSource();
		try {
			emailService.sendEmail(issueRaisedDto.getEmail(), MapUtility.convert(issueRaisedDto),
					EmailTemplate.ISSUE_RAISED_CONFIRMATION.getName(),
					EmailTemplate.ISSUE_RAISED_CONFIRMATION.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("Unable to Send Issue Raised Confirmation Email " + e.toString());
		}
	}

}
