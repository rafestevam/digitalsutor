package br.com.intelliapps.digitalsutor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private MailSender mailSender;
	
	@Async
	public void sendMail(SimpleMailMessage email) {
		mailSender.send(email);
	}
	
}
