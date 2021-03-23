package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyJoinRequestRecievedDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningRequestRecievedEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyJoiningRequestRecievedListener {

	private final EmailService emailService;

	@EventListener
	public void listenToCompanyJoiningRequestRecievedEvent(
			CompanyJoiningRequestRecievedEvent companyJoiningRequestRecievedEvent) {
		var companyJoinRequestRecievedDto = (CompanyJoinRequestRecievedDto) companyJoiningRequestRecievedEvent
				.getSource();
		try {
			emailService.sendEmail(companyJoinRequestRecievedDto.getEmail(),
					MapUtility.convert(companyJoinRequestRecievedDto),
					EmailTemplate.COMPANY_JOINING_REQUEST_RECIEVED.getName(),
					EmailTemplate.COMPANY_JOINING_REQUEST_RECIEVED.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("Unable To Send Company Joining Request Recieved Email " + e.toString());
		}
	}

}
