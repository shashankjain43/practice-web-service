package com.snapdeal.ims.otp.email.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.otp.email.ISnapdealEmailSender;
import com.snapdeal.ims.otp.internal.request.EmailInfo;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.mail.client.model.EmailRequest;
import com.snapdeal.mail.client.model.EmailResponse;
import com.snapdeal.mail.client.service.exceptions.InitializationException;
import com.snapdeal.mail.client.service.impl.EmailSender;

@Slf4j
@Service
public class SnapdealEMailSenderImpl implements ISnapdealEmailSender {

	private EmailSender sender;
	@Autowired
	private OTPUtility otpUtility;

	private void initialize() {
		if (EmailSender.getInstance() == null) {
			try {
				log.debug(otpUtility.getClientName() + 
						otpUtility.getUrl()) ;
				EmailSender.init(otpUtility.getClientName(),
						otpUtility.getUrl());
			} catch (InitializationException e) {
				log.debug(e.getMessage(), e);
				throw new InternalServerException(
						IMSServiceExceptionCodes.ERROR_CONNECTING_MAIL_CLIENT
								.errCode(),
						IMSServiceExceptionCodes.ERROR_CONNECTING_MAIL_CLIENT
								.errMsg());
			}
		}
	}

	private EmailRequest setEmailRequest(EmailInfo emailInfo) {
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setFromEmail(emailInfo.getFrom());

		// to do move them to configuration.
		emailRequest.setFromName(otpUtility.getClientName());
		emailRequest.setHtmlContent(otpUtility.getVerificationPin()
				+ emailInfo.getOTP());
		emailRequest.setReplyToEmail(emailInfo.getReplyTo());
		emailRequest.setSubject(emailInfo.getSubject());
		emailRequest.setTextContent(otpUtility.getTextContent());
		emailRequest.setTo(emailInfo.getTo().toString());
		emailRequest.setTriggerId(otpUtility.getTriggerId());
		return emailRequest;
	}

	@Override
	public EmailResponse send(EmailInfo emailInfo) {
		initialize();
		validateEmailInfo(emailInfo);
		EmailRequest request = setEmailRequest(emailInfo);

		try {
			log.debug(otpUtility.getClientName() + 
					otpUtility.getUrl()) ;
			sender = EmailSender.getInstance();
			EmailResponse response = sender.sendEmail(request);
			return response;

		} catch (Exception e) {
			log.error("error while sending email snapdeal email client", e);
			throw new InternalServerException(
					IMSServiceExceptionCodes.ERROR_SENDING_MAIL.errCode(),
					IMSServiceExceptionCodes.ERROR_SENDING_MAIL.errMsg());
		}
	}

	private boolean validateEmailInfo(EmailInfo emailInfo) {

		try {
			Preconditions.checkNotNull(emailInfo);
			Preconditions.checkArgument(!Strings.isNullOrEmpty(emailInfo
					.getFrom()));
			Preconditions.checkArgument(!Strings.isNullOrEmpty(emailInfo
					.getTo()));
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error("error while validating para for email");
			throw new RequestParameterException(
					IMSRequestExceptionCodes.EMAIL_FORMAT_INCORRECT.errCode(),
					IMSRequestExceptionCodes.EMAIL_FORMAT_INCORRECT.errMsg());
		}
		return true;
	}
}
