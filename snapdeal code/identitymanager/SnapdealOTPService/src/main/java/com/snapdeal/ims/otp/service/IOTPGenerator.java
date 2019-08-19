package com.snapdeal.ims.otp.service;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.response.FrozenAccountResponse;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;

public interface IOTPGenerator {

	public IOtp generate(GenerateOTPServiceRequest request);

	public IOtp reSendOTP(Optional<UserOTPEntity> otpInfo);

	// public void blockUser(OTPInfo otpRequest);

	public void validateUserFreeze(GenerateOTPServiceRequest request);

	public FrozenAccountResponse getFreezdUser (UserOTPEntity otp);

	public void blockUser(UserOTPEntity otp, String reason);
}
