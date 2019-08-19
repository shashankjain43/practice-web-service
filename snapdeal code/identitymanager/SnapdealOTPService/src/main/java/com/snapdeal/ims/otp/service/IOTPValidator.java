package com.snapdeal.ims.otp.service;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;

public interface IOTPValidator {

	public int verify(Optional<UserOTPEntity> currentOtpInfo,
			VerifyOTPServiceRequest request,boolean validOTPCall);
}
