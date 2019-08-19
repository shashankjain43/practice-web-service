package com.snapdeal.ims.util;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.email.IEmailSender;
import com.snapdeal.ims.otp.email.impl.EmailSenderImpl;
import com.snapdeal.ims.otp.email.impl.SnapdealEMailSenderImpl;
import com.snapdeal.ims.otp.internal.request.EmailInfo;


@RunWith(MockitoJUnitRunner.class)
public class IEmailSenderTest {
	@Mock
	SnapdealEMailSenderImpl emailSender;
	@InjectMocks
	IEmailSender sender=new EmailSenderImpl();
	
	@Test
	@Ignore
	public void testSend(){
		try{
		//sender.send(getDefaultEmailInfo());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	private EmailInfo getDefaultEmailInfo() {
		String email = "abhishek.garg@snapdeal.com";
		// to do move to configurations;
		EmailInfo emailInfo = EmailInfo.builder().from("noreply@snapdeals.co.in")
				.to(email).build();
		emailInfo.setSubject(OtpConstants.OTP_EMAIL);
		emailInfo.setReplyTo("snapdeal@snapdeal.com");
		emailInfo.setOTP("4554");
		return emailInfo;
	}
	private void fail(String message) {
		System.out.println(message);
	}

}
