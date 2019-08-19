package com.snapdeal.notifier.sms.service;

import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.sms.client.model.SmsSenderResponse;

public interface ISMSSender {

	public SmsSenderResponse send(String mobile, String message) throws InternalServerException;

}