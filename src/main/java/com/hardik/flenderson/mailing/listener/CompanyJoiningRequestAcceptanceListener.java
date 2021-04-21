package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyJoinRequestAcceptDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningRequestAcceptanceEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyJoiningRequestAcceptanceListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToCompanyJoiningRequestAcceptanceEvent(
			CompanyJoiningRequestAcceptanceEvent companyJoiningRequestAcceptanceEvent) {
		var companyJoiningRequestAcceptanceDto = (CompanyJoinRequestAcceptDto) companyJoiningRequestAcceptanceEvent
				.getSource();
		try {
			emailService.sendEmail(companyJoiningRequestAcceptanceDto.getEmail(),
					MapUtility.convert(companyJoiningRequestAcceptanceDto),
					EmailTemplate.COMPANY_JOINING_REQUEST_ACCEPTED.getName(),
					EmailTemplate.COMPANY_JOINING_REQUEST_ACCEPTED.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND COMPANY JOINING REQUEST ACCEPTANCE NOTIFICATION " + e.toString());
		}
	}

}
