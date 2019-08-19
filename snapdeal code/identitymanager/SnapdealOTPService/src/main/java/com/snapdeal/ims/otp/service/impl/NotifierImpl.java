package com.snapdeal.ims.otp.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.service.INotifier;
import com.snapdeal.ims.otp.service.provider.CallNotifierServiceDelegetor;
import com.snapdeal.ims.otp.service.provider.SMSNotifierServiceDelegater;

@Slf4j
@Service
public class NotifierImpl implements INotifier {

	@Autowired
	private SMSNotifierServiceDelegater SMSNotifierService;

	@Autowired
	private CallNotifierServiceDelegetor callNotifierService;

	@Override
	public void sendSMS(UserOTPEntity otpInfo,Merchant merchant) {
		SMSNotifierService.sendSms(otpInfo,merchant);
	}

	@Override
	public void callNumber(UserOTPEntity otpInfo,Merchant merchant) {
		callNotifierService.callNumber(otpInfo,merchant);
	}
}