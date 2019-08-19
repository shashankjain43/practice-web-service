package com.snapdeal.ims.otp.service.impl;

import javax.annotation.concurrent.NotThreadSafe;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.scheduling.annotation.Async;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.dao.OTPInfoDao;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.UpdateResendAttemptsRequest;
import com.snapdeal.ims.otp.service.INotifier;
import com.snapdeal.ims.otp.service.IOtp;

@AllArgsConstructor
@NotThreadSafe
public class OTPImpl implements IOtp {
	@Getter
	private UserOTPEntity otpInfo;

	private final OTPInfoDao otpInfoDao;
	private final INotifier notifier;

	public void incrementSendCount() {
		UpdateResendAttemptsRequest updateResendAttemptsRequest = new UpdateResendAttemptsRequest();
		updateResendAttemptsRequest.setOtpId(otpInfo.getOtpId());
		updateResendAttemptsRequest.setToken(otpInfo.getToken());
		updateResendAttemptsRequest.setMoobileNumber(otpInfo.getMobileNumber());
		updateResendAttemptsRequest.setEmailId(otpInfo.getEmail());
		updateResendAttemptsRequest.setClientId(otpInfo.getClientId());
		updateResendAttemptsRequest.setResendAttempts(otpInfo
				.getResendAttempts());
		otpInfoDao.incrementResendAttempts(updateResendAttemptsRequest);
	}

	@Override
	@Async
	public void sendSMS(Merchant merchant) {
		notifier.sendSMS(otpInfo,merchant);
	}

	@Override
	@Async
	public void callNumber(Merchant merchant) {
		notifier.callNumber(otpInfo,merchant);
	}

}
