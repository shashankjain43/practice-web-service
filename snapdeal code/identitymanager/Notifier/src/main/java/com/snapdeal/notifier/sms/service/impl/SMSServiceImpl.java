package com.snapdeal.notifier.sms.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.snapdeal.notifier.errorcodes.ExceptionCodes;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.sms.request.SMSChannelInfo;
import com.snapdeal.notifier.sms.request.SMSRequest;
import com.snapdeal.notifier.sms.service.ISMSSender;
import com.snapdeal.notifier.sms.service.ISMSService;
import com.snapdeal.notifier.sms.util.TokenReplacer;
import com.snapdeal.sms.client.model.SmsSenderResponse;

@Slf4j
@Component
public class SMSServiceImpl implements ISMSService {

	@Autowired
	private SMSChannelInfo smsChannelInfo;

	@Autowired
	ISMSSender smsSender;

	@Override
	public SmsSenderResponse sendSMS(SMSRequest smsRequest)
			throws ValidationException, InternalServerException {

		validateSMSInfo(smsRequest);

		return smsSender.send(smsRequest.getMobileNumber(), TokenReplacer
				.evaluate(smsRequest.getTemplateFiller(),
						smsRequest.getTemplateBody()));
	}

	private void validateSMSInfo(SMSRequest smsRequest)
			throws InternalServerException {

		try {
			Preconditions.checkNotNull(smsRequest);
			Preconditions.checkArgument(!Strings.isNullOrEmpty(smsRequest
					.getMobileNumber()));
			/*Preconditions.checkArgument(!Strings.isNullOrEmpty(smsRequest
					.getTemplateV0()));
			Preconditions.checkArgument(smsChannelInfo.getChannelId().equals(
					smsRequest.getSMSChannelId()));*/

		} catch (IllegalArgumentException e) {
			log.error("Wrong smsChannelInfo {}", smsChannelInfo.toString());
			throw new InternalServerException(
					ExceptionCodes.WRONG_SMS_CHANNEL_INFO.errCode(),
					ExceptionCodes.WRONG_SMS_CHANNEL_INFO.errMsg());

		}
	}
}
