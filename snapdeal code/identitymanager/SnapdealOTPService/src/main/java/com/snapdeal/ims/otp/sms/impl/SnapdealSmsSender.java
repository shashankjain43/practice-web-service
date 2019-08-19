package com.snapdeal.ims.otp.sms.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.otp.sms.ISMSSender;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.sms.client.model.SmsSenderRequest;
import com.snapdeal.sms.client.model.SmsSenderResponse;
import com.snapdeal.sms.client.service.exceptions.InitializationException;
import com.snapdeal.sms.client.service.exceptions.SendSmsException;
import com.snapdeal.sms.client.service.impl.SmsSender;

@Slf4j
@Service
@Deprecated
public class SnapdealSmsSender implements ISMSSender {

	@Autowired
	OTPUtility utility;

	private void init() {
		if (SmsSender.getInstance() == null) {
			try {
				log.debug(utility.getSmsClientName() + utility.getSmsClientUrl());
				SmsSender.init(utility.getSmsClientName(),utility.getSmsClientUrl());
			} catch (InitializationException e) {
				log.debug(e.getMessage(), e);
				throw new InternalServerException(
                        IMSServiceExceptionCodes.ERROR_SENDING_SMS.errCode(),
                        IMSServiceExceptionCodes.ERROR_SENDING_SMS.errMsg());
			}
		}
	}

	@Override
	public void send(String mobile, String message) {
		init();
		SmsSenderRequest request = createSMSRequest(mobile, message);
		SmsSenderResponse response = null;
		try {
			log.debug(utility.getSmsClientName() + utility.getSmsClientUrl());
			response = SmsSender.getInstance().sendSMS(request);
			log.debug(response.getSmsRequesId());
		} catch (SendSmsException e) {
			log.debug(e.getMessage());
			throw new InternalServerException(
					IMSServiceExceptionCodes.ERROR_SENDING_MAIL.errCode(),
					IMSServiceExceptionCodes.ERROR_SENDING_MAIL.errMsg());
		}
	}

	private SmsSenderRequest createSMSRequest(String mobile, String message) {
		String triggerid = utility.getTriggerId();
		SmsSenderRequest smsRequest = new SmsSenderRequest(mobile, message,
				triggerid);
		return smsRequest;

	}
}
