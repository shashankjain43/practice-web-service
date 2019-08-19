package com.snapdeal.ims.otp.service;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.otp.entity.UserOTPEntity;

public interface INotifier {
	public void sendSMS(UserOTPEntity otpInfo,Merchant merchant);

	public void callNumber(UserOTPEntity otpInfo, Merchant merchant);

}
