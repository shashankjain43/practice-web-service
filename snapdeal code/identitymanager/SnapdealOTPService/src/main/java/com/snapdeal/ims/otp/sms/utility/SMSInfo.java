package com.snapdeal.ims.otp.sms.utility;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
@Deprecated
public class SMSInfo {
	private String mobileNumber;
	private String templateV0;
	private int SMSChannelId;
	private String SMSChannelName;
	private String otpType;
	private Map<String, String> templateFiller = new HashMap<String, String>();

	public SMSInfo() {

	}

}
