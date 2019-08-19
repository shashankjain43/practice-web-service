package com.snapdeal.ums.server.subsidiary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.StringUtils;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.ums.cache.EmailTemplateCache;
import com.snapdeal.ums.core.entity.EmailTemplate;

@Service
public class SubsidiaryEmailService {
	 private static final Logger LOG = LoggerFactory
		        .getLogger(SubsidiaryEmailService.class);
	
	@Autowired
	SubsidiaryEmailTriggerService emailTriggerService;
	/**
	 * @author Shishir
	 * @category call this method after creating the new User for sending the confirmation email to User
	 * @param email
	 * @param contextPath
	 * @param contentPath
	 * @param confirmationLink
	 * @param pwd
	 */
	public void sendAccountCreationEmail(String email, String contextPath,
			String contentPath, String pwd, String emailTemplate) {
		LOG.info("inside sendAccountCreationAndVerificationEmail");
		// send confirmation email
		SubsidiaryEmailMessage emailMessage = new SubsidiaryEmailMessage(email, emailTemplate);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("loginID", email);
		emailMessage.addTemplateParam("password", pwd);
		send(emailMessage);
	}
	
	@AuditableMethod
	public void send(SubsidiaryEmailMessage message) {
		// EmailTemplateVO template =
		// CacheManager.getInstance().getCache(EmailTemplateCache.class).getTemplateByName(message.getTemplateName());
		// notificationService.sendEmail(message, template);

		// Getting the required template from cache
		LOG.info("inside send method");
		EmailTemplate template = CacheManager.getInstance()
				.getCache(EmailTemplateCache.class)
				.getTemplateByName(message.getTemplateName());

		try {
			// Calling the trigger service to send the email request
			emailTriggerService.triggerEmail(message, template);

		} catch (SendEmailException e) {
			LOG.error(
					"error while sending email request to emailTriggerService:"
							+ template.getName(), e);
		}
	}


}
