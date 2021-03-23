package com.hardik.flenderson.mailing.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.enums.EmailTemplate;
import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.dto.MonthlyAttendanceReportDto;
import com.hardik.flenderson.mailing.event.MonthlyAttendanceReportEvent;
import com.hardik.flenderson.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MonthlyAttendanceReportListener {
	
	private final EmailService emailService;

	@EventListener
	public void listenToManagerAccountCreationEvent(MonthlyAttendanceReportEvent monthlyAttendanceReportEvent) {
		var monthlyAttendanceReportDto = (MonthlyAttendanceReportDto) monthlyAttendanceReportEvent.getSource();
		try {
			emailService.sendEmail(monthlyAttendanceReportDto.getEmail(), MapUtility.convert(monthlyAttendanceReportDto),
					EmailTemplate.MONTHLY_ATTENDANCE_REPORT.getName(),
					EmailTemplate.MONTHLY_ATTENDANCE_REPORT.getSubject());
		} catch (MessagingException | IOException | TemplateException e) {
			log.error("UNABLE TO SEND MONTHLY ATTENDANCE REPORT NOTIFICATION " + e.toString());
		}
	}

}
