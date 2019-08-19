package com.snapdeal.notifier.sms.util;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SMSUtility {

	@Value("${snapdeal.sms.clientName}")
	private String smsClientName;

	@Value("${snapdeal.sms.clientUrl}")
	private String smsClientUrl;

	@Value("${snapdeal.notifier.email.octaneTriggerId}")
	private String triggerId;
}
