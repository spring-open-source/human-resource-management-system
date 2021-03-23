package com.hardik.flenderson.mailing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hardik.flenderson.mailing.configuration.EmailConfiguration;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(EmailConfiguration.class)
public class EmailService {

	private final JavaMailSender javaMailSender;

	private final Configuration configuration;

	private final EmailConfiguration emailConfiguration;

	public void sendEmail(String toMail, Map<String, String> model, String templateName, String subject)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException, MessagingException {
		final var emailConfig = emailConfiguration.getMail();
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Template t = configuration.getTemplate(templateName + ".ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		helper.setTo(toMail);
		helper.setText(html, true);
		helper.setSubject(subject);
		helper.setFrom(emailConfig.getNotificationEmail());
		javaMailSender.send(message);
	}

}
