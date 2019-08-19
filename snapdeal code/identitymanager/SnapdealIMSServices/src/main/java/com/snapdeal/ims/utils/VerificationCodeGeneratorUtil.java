package com.snapdeal.ims.utils;

import java.util.UUID;

import com.snapdeal.ims.constants.VerificationCodeConstants;

public class VerificationCodeGeneratorUtil {
	/**
	 * Utility method to generate verification code.
	 * 
	 * @return VerificationCode.
	 */
	public static String getVerificationCode() {
	
		String uuid = UUID.randomUUID().toString();
		uuid = VerificationCodeConstants.VersionOne + "#" + uuid;
		return uuid;
	}
}
