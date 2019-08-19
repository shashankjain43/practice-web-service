package com.snapdeal.ims.otp.service;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.otp.entity.UserOTPEntity;

/**
 * Interface to handle OTP
 * 
 * @author varun
 *
 */
public interface IOtp {

	public void incrementSendCount();

	/**
	 * @return {@link UserOTPEntity} associated with the OTP
	 */
	public UserOTPEntity getOtpInfo();

	
	public void sendSMS(Merchant merchant);

	public void callNumber(Merchant merchant);

}
