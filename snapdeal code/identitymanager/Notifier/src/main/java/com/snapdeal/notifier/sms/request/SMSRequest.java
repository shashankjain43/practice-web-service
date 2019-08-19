package com.snapdeal.notifier.sms.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data

public class SMSRequest {
	private String mobileNumber;
	private String templateV0;
	private int SMSChannelId;
	private String SMSChannelName;
	private Map<String, String> templateFiller = new HashMap<String, String>();
	private String otpType;
	private String templateBody;
	private String sendOtpBy;
	public SMSRequest() {

	}

}
