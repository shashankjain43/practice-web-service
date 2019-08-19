/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snapdeal.ums.server.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.email.intrnl.email.SendUMSNotificationRequest;
import com.snapdeal.ums.server.services.IEmailTriggerService;
import com.snapdeal.ums.cache.EmailTemplateCache;

/**
 * @version 1.0, 12-Jan-2015
 * @author jain.shashank@snapdeal.com
 * 
 *         This class will provide the functionality to send the notification
 *         email to ums dev, qa and prod teams.
 */
@Service
@Transactional
public class UMSNotificationService {

	private static final Logger LOG = LoggerFactory
			.getLogger(UMSNotificationService.class);

	@Autowired
	private IEmailTriggerService emailTriggerService;

	private static final String UMS_CHECKPOINT_BREACH_EMAIL_TEMPLATE = "UMSCheckpointBreachNotification";
	private static final String TECH_BREACH_EMAIL_TEMPLATE = "techBreachNotification";

	/**
	 * Intention of this service is to notify product team and stakeholders in
	 * case of functional flow checkpoint breach.
	 * 
	 * @param sendUMSNotificationRequest
	 */
	public void sendUMSFunctionalNotification(
			SendUMSNotificationRequest sendUMSNotificationRequest) {

		// Getting the required template from cache
		EmailTemplate template = CacheManager.getInstance()
				.getCache(EmailTemplateCache.class)
				.getTemplateByName(UMS_CHECKPOINT_BREACH_EMAIL_TEMPLATE);

		EmailMessage emailMessage = new EmailMessage(
				UMS_CHECKPOINT_BREACH_EMAIL_TEMPLATE);
		emailMessage.addTemplateParam("msg",
				sendUMSNotificationRequest.getMsg());
		emailMessage.addTemplateParam("subject",
				sendUMSNotificationRequest.getSubject());
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));

		try {
			// Calling the trigger service to send the email request
			emailTriggerService.triggerEmail(emailMessage, template);

		} catch (SendEmailException e) {
			LOG.error(
					"error while sending email request to emailTriggerService:"
							+ template.getName(), e);
		}

	}

	/**
	 * Intention of this service is to notify internally only to tech team in
	 * case of any technical breakdown.
	 * 
	 * @param sendUMSNotificationRequest
	 */
	public void sendTechBreachNotification(
			SendUMSNotificationRequest sendUMSNotificationRequest) {

		// Getting the required template from cache
		EmailTemplate template = CacheManager.getInstance()
				.getCache(EmailTemplateCache.class)
				.getTemplateByName(TECH_BREACH_EMAIL_TEMPLATE);

		EmailMessage emailMessage = new EmailMessage(TECH_BREACH_EMAIL_TEMPLATE);
		emailMessage.addTemplateParam("msg",
				sendUMSNotificationRequest.getMsg());
		emailMessage.addTemplateParam("subject",
				sendUMSNotificationRequest.getSubject());
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));

		try {
			// Calling the trigger service to send the email request
			emailTriggerService.triggerEmail(emailMessage, template);

		} catch (SendEmailException e) {
			LOG.error(
					"error while sending email request to emailTriggerService:"
							+ template.getName(), e);
		}

	}

}
