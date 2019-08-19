package com.snapdeal.notifier.email.task;

import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.mail.client.model.EmailResponse;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;

import java.util.Map;

public interface IEmailSender {

	public EmailResponse send(EmailMessage message,
			String templateHTMLStr, 
			String subject, 
			Map<String, String> tags) 
					throws ValidationException,
					InternalServerException;

	public EmailResponse send(EmailMessage message,
			String templateName,
			String templateLocation,
			String subject, 
			Map<String, String> tags) 
					throws ValidationException,
					InternalServerException;

	public void initialize();
}
