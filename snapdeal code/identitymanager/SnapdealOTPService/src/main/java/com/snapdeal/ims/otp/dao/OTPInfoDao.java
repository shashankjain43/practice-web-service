package com.snapdeal.ims.otp.dao;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.internal.request.IsOTPVerifiedRequest;
import com.snapdeal.ims.otp.internal.request.UpdateInvalidAttemptsRequest;
import com.snapdeal.ims.otp.internal.request.UpdateOTPStatusRequest;
import com.snapdeal.ims.otp.internal.request.UpdateResendAttemptsRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;

/**
 * 
 * @author shagun
 * @usage To store OTP information for a user and perform various fetch and
 *        update operations
 *
 */
public interface OTPInfoDao {

	public void saveOTP(UserOTPEntity otpDaoInfo);

	public Optional<UserOTPEntity> getOtpId(UserOTPEntity otpDaoInfo);

	public Optional<UserOTPEntity> getOTPFromId(
			FetchLatestOTPRequest latestOtpInfo);

	public void incrementInvalidAttempts(
			UpdateInvalidAttemptsRequest invalidAttemptsInfo);

	public void incrementResendAttempts(
			UpdateResendAttemptsRequest resendAttemptsInfo);

	public void updateCurrentOTPStatus(UpdateOTPStatusRequest otpStatusInfo);

	public Optional<UserOTPEntity> getLatestOTP(GenerateOTPServiceRequest request);
	
	public Optional<UserOTPEntity> isOTPVerified(IsOTPVerifiedRequest request) ;

}