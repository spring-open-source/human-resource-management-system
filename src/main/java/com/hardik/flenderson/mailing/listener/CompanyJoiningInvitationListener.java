package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyJoinInvitationDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningInvitationEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyJoiningInvitationListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToCompanyJoiningInvitationEvent(CompanyJoiningInvitationEvent companyJoiningInvitationEvent) {
		var companyJoiningInvitationDto = (CompanyJoinInvitationDto) companyJoiningInvitationEvent.getSource();
		try {
			emailService.sendEmail(companyJoiningInvitationDto.getEmail(),
					MapUtility.convert(companyJoiningInvitationDto), EmailTemplate.COMPANY_JOINING_INVITATION.getName(),
					EmailTemplate.COMPANY_JOINING_INVITATION.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND COMPANY JOINING INVITATION " + e.toString());
		}
	}

}
