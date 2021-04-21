package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.ContactUsConfirmationDto;
import com.hardik.flenderson.mailing.event.ContactUsEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ContactUsListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToEmployeeAccountCreationEvent(ContactUsEvent contactUsEvent) {
		var contactUsConfirmationDto = (ContactUsConfirmationDto) contactUsEvent.getSource();
		try {
			emailService.sendEmail(contactUsConfirmationDto.getEmail(), MapUtility.convert(contactUsConfirmationDto),
					EmailTemplate.CONTACT_US_CONFIRMATION.getName(),
					EmailTemplate.CONTACT_US_CONFIRMATION.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("Unable To Send Contact Us Confirmation Mail To " + contactUsConfirmationDto.getEmail());
		}
	}

}
