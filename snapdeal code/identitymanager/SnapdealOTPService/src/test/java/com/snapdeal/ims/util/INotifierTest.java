package com.snapdeal.ims.util;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.service.INotifier;
import com.snapdeal.ims.otp.service.impl.NotifierImpl;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OTPUtility;

@RunWith(MockitoJUnitRunner.class)
public class INotifierTest {
	@Mock
	OTPUtility otpUtility;
	@InjectMocks
	INotifier notifierImpl = new NotifierImpl();

	@Before
	public void initMocks() {
		try {
			MockitoAnnotations.initMocks(notifierImpl);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testSend() {
		try {
			when(otpUtility.getChannelId()).thenReturn(2);
			when(otpUtility.getChannelName()).thenReturn("valuefirst");

			when(otpUtility.getTemplateName()).thenReturn("otp");
			//notifierImpl.sendSMS(getDefaultOTPInfo());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	private UserOTPEntity getDefaultOTPInfo() {
		UserOTPEntity otpInfo = new UserOTPEntity();
		otpInfo.setCreatedOn(new Date());
		otpInfo.setExpiryTime(new Date());
		otpInfo.setOtp("1234");
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setClientId("1");
		otpInfo.setEmail("abhishek.garg@snapdeal.com");
		otpInfo.setMobileNumber("7206303105");
		otpInfo.setOtpId("122");
		otpInfo.setOtpType(OTPPurpose.FORGOT_PASSWORD.toString());
		return otpInfo;
	}

	private void fail(String message) {
		System.out.println(message);
	}
}
