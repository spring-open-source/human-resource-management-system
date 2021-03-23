package com.hardik.flenderson.mailing.listener;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.CompanyEventNotificationDto;
import com.hardik.flenderson.mailing.event.EventCreationEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EventCreationListener {

	private final EmailService emailService;

	@EventListener
	public void listenToEventCreationEvent(EventCreationEvent eventCreationEvent) {
		List<Employee> employees = (List<Employee>) eventCreationEvent.getSource();

		for (Employee employee : employees) {
			try {
				emailService.sendEmail(employee.getEmailId(),
						MapUtility.convert(
								CompanyEventNotificationDto.builder().companyName(employee.getCompany().getName())
										.email(employee.getEmailId()).employeeName(employee.getFirstName()).build()),
						EmailTemplate.NEW_EVENT_CREATION.getName(), EmailTemplate.NEW_EVENT_CREATION.getSubject());
			} catch (MessagingException | IOException | TemplateException e) {
				log.error("Unable To Send Event Creation notification Event " + e.toString());
			}
		}

	}

}
