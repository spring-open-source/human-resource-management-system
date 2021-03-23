package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyCreationDto;
import com.hardik.flenderson.mailing.event.CompanyCreationEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyCreationListener {

	private final EmailService emailService;

	@EventListener
	public void listenToCompanyCreationEvent(CompanyCreationEvent companyCreationEvent) {
		var companyCreationDto = (CompanyCreationDto) companyCreationEvent.getSource();

		try {
			emailService.sendEmail(companyCreationDto.getEmail(), MapUtility.convert(companyCreationDto),
					EmailTemplate.COMPANY_CREATION.getName(), EmailTemplate.COMPANY_CREATION.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND COMPANY CREATION NOTIFICATION TO "+ companyCreationDto.getEmail());
		}
	}

}
