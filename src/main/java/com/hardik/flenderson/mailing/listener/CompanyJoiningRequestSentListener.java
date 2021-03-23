package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyJoinRequestSentDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningRequestSentEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyJoiningRequestSentListener {

	private final EmailService emailService;

	@EventListener
	public void listenToCompanyJoiningRequestSentEvent(CompanyJoiningRequestSentEvent companyJoiningRequestSentEvent) {
		var companyJoinRequestSentDto = (CompanyJoinRequestSentDto) companyJoiningRequestSentEvent.getSource();
		try {
			emailService.sendEmail(companyJoinRequestSentDto.getEmail(), MapUtility.convert(companyJoinRequestSentDto),
					EmailTemplate.COMPANY_JOINING_REQUEST_SENT.getName(),
					EmailTemplate.COMPANY_JOINING_REQUEST_SENT.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND COMPANY JOIN REQUEST SENT EMAIL " + e.toString());
		}
	}

}
