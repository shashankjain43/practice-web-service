package com.snapdeal.ims.otp.sms;

import com.snapdeal.ims.otp.sms.utility.SMSInfo;

public interface ISMSChannel {

	public void send(SMSInfo smsInfo);

}
