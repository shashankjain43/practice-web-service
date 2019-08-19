package com.snapdeal.ims.otp.util;

import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.utils.CipherServiceUtil;

public class OtpEncryptionUtility {

	public static String encryptOTP(String str) {
		String encryptedOTP = null;
		try {
			encryptedOTP = CipherServiceUtil.encrypt(str);
		} catch (CipherException e) {
			e.printStackTrace();
		}

		return encryptedOTP;
	}

	public static String decryptOTP(String str) {
		String decryptedOTP = null;
		try {
			decryptedOTP = CipherServiceUtil.decrypt(str);
		} catch (CipherException e) {
			e.printStackTrace();
		}

		return decryptedOTP;
	}

}
