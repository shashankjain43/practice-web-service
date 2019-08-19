package com.snapdeal.ims.service.impl;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.notifierClient.FCNotifier;
import com.snapdeal.ims.service.INotifierEmailSenderServiceHelper;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.service.Notifier;

@Component
@Slf4j
public class NotifierServiceDelegater {

   final static Logger logger = LoggerFactory
            .getLogger(ServiceCommonConstants.EMAIL_LOGGER_NAME);

	@Autowired
	private NotifierServiceClient fcNotifier;

	@Autowired
	private Notifier notifier;
	@Autowired
	private INotifierEmailSenderServiceHelper notifierEmailSenderServiceHelper;
	
	@Autowired
	private AuthorizationContext context;

	public void sendEmail(EmailMessage emailMessage, boolean blocking) {
		setEmailIDAsTestEmailIDIfApplicable(emailMessage);
		String merchant = getMerchant();
		String replyToEmailId = getEmailReplyTo(emailMessage.getEmailSendBy(),
				ConfigurationConstants.SEND_EMAIL_REPLY_TO_EMAIL_ID);
		String subject = emailMessage.getSubject();
		if (StringUtils.isBlank(subject)) {
			subject = getEmailSubject(merchant,
					ConfigurationConstants.SEND_EMAIL_SUBJECT);
		}
		String fromEmailId = getEmailFromMail(emailMessage.getEmailSendBy(),
				ConfigurationConstants.SEND_EMAIL_FROM_EMAIL_ID);

		// Overriding from|subject|replyto Tag based on emailSendBy field.
		emailMessage.setFrom(fromEmailId);

		emailMessage.setSubject(subject);

		emailMessage.setReplyTo(replyToEmailId);
		log.debug("Final Email message: " + emailMessage);
		try {
			boolean isFCNotifierEnabled = Boolean
					.valueOf(Configuration
							.getGlobalProperty(ConfigurationConstants.FC_NOTIFIER_ENABLED));
			log.info(ServiceCommonConstants.FREECHARGE
					+ "    "
					+ emailMessage.getEmailSendBy()
					+ "    "
					+ ServiceCommonConstants.FREECHARGE.equals(emailMessage
							.getEmailSendBy()));
			if (ServiceCommonConstants.FREECHARGE.equalsIgnoreCase(emailMessage
					.getEmailSendBy())) {
				if (isFCNotifierEnabled) {
					log.info("sending sms through FCNotifier");
					EmailNotifierRequest fcNotifierEmailRequest = getFCNotifierRequest(
							emailMessage, blocking);
					notifierEmailSenderServiceHelper.createNotifierEmailSendingTask(fcNotifierEmailRequest);
				} else {
					log.error("FC notifier is not enabled, and all mails from FreeCharge will fail.");
				}
			} else {
				log.info("sending sms through Notifier");
				notifier.sendEmail(emailMessage, true);
			}
		} catch (Exception e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("\nEmail : ").append(emailMessage.getTo());
			errorMessage.append("\nEmail Template : ").append(
					emailMessage.getTemplateKey());
			errorMessage.append("\nMerchant : " + getMerchant());
         logger.error(errorMessage.toString());
			log.error("Sending email failed: " + errorMessage.toString());
			log.error("Sending email failed exception : " + e);
         if (blocking) {
            throw new IMSServiceException(
                     IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                     IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
         }
		}
	}

	private EmailNotifierRequest getFCNotifierRequest(
			EmailMessage emailMessage, boolean blocking)
			throws ValidationException {

		EmailNotifierRequest fcNotifierEmailRequest = new EmailNotifierRequest();
		fcNotifierEmailRequest.setBlocking(blocking);
		fcNotifierEmailRequest.setEmailMessage(FCNotifier
				.getFcNotifierEmailMessage(emailMessage));
		return fcNotifierEmailRequest;
	}

	private void setEmailIDAsTestEmailIDIfApplicable(EmailMessage emailMessage) {
		boolean sendTestEmail = Boolean
				.valueOf(Configuration
						.getGlobalProperty(ConfigurationConstants.NOTIFIER_SEND_TEST_EMAIL));
		if (sendTestEmail) {
			log.debug("Sending email to test email instead of : "
					+ emailMessage.getTo());
			emailMessage
					.setTo(Arrays.asList(Configuration
							.getGlobalProperty(ConfigurationConstants.NOTIFIER_TEST_EMAIL_ID)));
		}
	}

	protected String getMerchant() {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		return ClientConfiguration.getMerchantById(clientId).getMerchantName();
	}

	protected String getEmailReplyTo(String merchant,
			ConfigurationConstants configConstants) {
		String replyToEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(replyToEmailId)) {
			replyToEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return replyToEmailId;
	}

	protected String getEmailSubject(String merchant,
			ConfigurationConstants configConstants) {
		String subject = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(subject)) {
			subject = Configuration.getGlobalProperty(configConstants);
		}
		return subject;
	}

	protected String getEmailFromMail(String merchant,
			ConfigurationConstants configConstants) {
		String fromEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(fromEmailId)) {
			fromEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return fromEmailId;
	}

}
