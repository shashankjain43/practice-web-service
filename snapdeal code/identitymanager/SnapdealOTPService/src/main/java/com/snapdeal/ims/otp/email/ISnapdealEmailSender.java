package com.snapdeal.ims.otp.email;

import com.snapdeal.ims.otp.internal.request.EmailInfo;
import com.snapdeal.mail.client.model.EmailResponse;

public interface ISnapdealEmailSender {

	public EmailResponse send(EmailInfo emailInfo);
}
