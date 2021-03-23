package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.SimpleEmailDto;
import com.hardik.flenderson.mailing.event.ManagerAccountCreationEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ManagerAccountCreationListener {

	private final EmailService emailService;

	@EventListener
	public void listenToManagerAccountCreationEvent(ManagerAccountCreationEvent managerAccountCreationEvent) {
		var managerAccountCreationDto = (SimpleEmailDto) managerAccountCreationEvent.getSource();
		try {
			emailService.sendEmail(managerAccountCreationDto.getEmail(), MapUtility.convert(managerAccountCreationDto),
					EmailTemplate.MANAGER_ACCOUNT_CREATION.getName(),
					EmailTemplate.MANAGER_ACCOUNT_CREATION.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND MANAGER ACCOUNT CREATION EMAIL " + e.toString());
		}
	}

}
