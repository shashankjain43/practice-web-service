package com.snapdeal.ims.otp.util;

import java.util.Date;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.types.OTPState;
import com.snapdeal.ims.otp.types.OTPStatus;

public class OTPStateCalculator {

	public static OTPState get(Optional<UserOTPEntity> currentOtpInfo) {
		if (currentOtpInfo.isPresent()) {
			UserOTPEntity otpInfo = currentOtpInfo.get();
			if (!otpInfo.getOtpStatus().equals(OTPStatus.ACTIVE))
				return OTPState.VERIFIED;
			Date currentDate = new Date();
			if (currentDate.before(otpInfo.getExpiryTime()))
				return OTPState.IN_EXPIRY;
			else
				return OTPState.NOT_ACTIVE;
		} else
			return OTPState.DOES_NOT_EXIST;
	}

}
