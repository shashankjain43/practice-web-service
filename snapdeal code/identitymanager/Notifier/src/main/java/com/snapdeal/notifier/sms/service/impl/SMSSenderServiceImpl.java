package com.snapdeal.notifier.sms.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.sms.client.service.exceptions.InitializationException;
import com.snapdeal.notifier.errorcodes.ExceptionCodes;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.sms.service.ISMSSender;
import com.snapdeal.notifier.sms.util.SMSUtility;
import com.snapdeal.sms.client.model.SmsSenderRequest;
import com.snapdeal.sms.client.model.SmsSenderResponse;
import com.snapdeal.sms.client.service.exceptions.SendSmsException;
import com.snapdeal.sms.client.service.impl.SmsSender;

@Slf4j
@Service
public class SMSSenderServiceImpl implements ISMSSender {
	@Autowired
	SMSUtility utility;

	@Override
	public SmsSenderResponse send(String mobile, String message) throws InternalServerException {
		init();
		SmsSenderRequest request = createSMSRequest(mobile, message);
		SmsSenderResponse response = null;
		try {
			log.debug(utility.getSmsClientName() + utility.getSmsClientUrl());
			response = SmsSender.getInstance().sendSMS(request);
			log.debug(response.getSmsRequesId());
			return response;
		} catch (SendSmsException e) {
			log.debug(e.getMessage());
			throw new InternalServerException(
					ExceptionCodes.ERROR_SENDING_SMS.errCode(),
					ExceptionCodes.ERROR_SENDING_SMS.errMsg());
		}
	}

	private SmsSenderRequest createSMSRequest(String mobile, String message) {
		String triggerid = utility.getTriggerId();
		SmsSenderRequest smsRequest = new SmsSenderRequest(mobile, message,
				triggerid);
		return smsRequest;

	}

	private void init() throws InternalServerException {
		if (SmsSender.getInstance() == null) {
			try {
				log.debug(utility.getSmsClientName()
						+ utility.getSmsClientUrl());
				SmsSender.init(utility.getSmsClientName(),
						utility.getSmsClientUrl());
			} catch (InitializationException e) {
				log.debug(e.getMessage(), e);
				throw new InternalServerException(
						ExceptionCodes.ERROR_SENDING_SMS.errCode(),
						ExceptionCodes.ERROR_SENDING_SMS.errMsg());
			}
		}
	}

}
