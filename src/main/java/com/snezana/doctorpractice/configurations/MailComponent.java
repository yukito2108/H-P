package com.snezana.doctorpractice.configurations;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.snezana.doctorpractice.dto.MailDto;

/**
 * Component for setting and sending email in html template
 * "fragments/emailHTML.html"
 */
@Component
public class MailComponent {

	final
	JavaMailSender javaMailSender;

	final
	TemplateEngine templateEngine;

	@Autowired
	public MailComponent(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	/**
	 * Send email over http using the injected {@link JavaMailSender}.
	 *
	 * @param mailDto mail data transfer object - containing the email message as well as the
	 *                information about the receiver mail user/account.
	 * @return {@code true} upon successful mail sending, {@code false} otherwise.
	 */
	public boolean sendHtmlMail(MailDto mailDto) {
		Context context = new Context();
		context.setVariable("mailDto", mailDto);
		final String messageHtml = templateEngine.process("fragments/emailHTML", context);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage);
		try {
			mailMessage.setTo("hoangvietnguyen2108@gmail.com"); // if you use Gmail do not forget to put your personal address
			mailMessage.setFrom(mailDto.getUserEmail());
			mailMessage.setSubject(mailDto.getSubjectEmail());
			mailMessage.setText(messageHtml, true);
			javaMailSender.send(mimeMessage);
			return true;
		} catch (MessagingException | MailException e) {
			return false;
		}
	}
}
