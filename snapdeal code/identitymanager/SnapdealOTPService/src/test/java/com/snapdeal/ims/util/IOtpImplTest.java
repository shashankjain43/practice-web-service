package com.snapdeal.ims.util;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.dao.impl.OTPInfoDaoImpl;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.service.IOtp;
import com.snapdeal.ims.otp.service.impl.NotifierImpl;
import com.snapdeal.ims.otp.service.impl.OTPImpl;
import com.snapdeal.ims.otp.types.OTPStatus;

@RunWith(MockitoJUnitRunner.class)
public class IOtpImplTest {

	private UserOTPEntity otpInfo = getDefaultOTPInfo();
	@Mock
	OTPInfoDaoImpl otpInfoDao;
	@Mock
	NotifierImpl notifier;

	IOtp otp ;


	@Test
	@Ignore
	public void testPublish() {
		try {
			otp=new OTPImpl(otpInfo, otpInfoDao, notifier);
			otp.incrementSendCount();
			otp.getOtpInfo();
			//otp.publish();
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	private void fail(String message) {
		System.out.println(message);
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
}
