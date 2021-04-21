package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.MonthlyPayrollReportDto;
import com.hardik.flenderson.mailing.event.MonthlyPayrollReportEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MonthlyPayrollReportListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToManagerAccountCreationEvent(MonthlyPayrollReportEvent monthlyPayrollReportEvent) {
		var monthlyPayrollReportDto = (MonthlyPayrollReportDto) monthlyPayrollReportEvent.getSource();
		try {
			emailService.sendEmail(monthlyPayrollReportDto.getEmail(), MapUtility.convert(monthlyPayrollReportDto),
					EmailTemplate.MONTHLY_PAYROLL_REPORT.getName(), EmailTemplate.MONTHLY_PAYROLL_REPORT.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND MONTHLY PAYROLL REPORT NOTIFICATION " + e.toString());
		}
	}

}
