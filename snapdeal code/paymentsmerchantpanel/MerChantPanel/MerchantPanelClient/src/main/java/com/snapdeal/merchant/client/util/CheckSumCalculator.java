package com.snapdeal.merchant.client.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSumCalculator {

	public static String generateSHA256CheckSum(String arg) {
		String returnValue = null;
		if ((arg == null) || (arg.trim().equals(""))) {
			return null;
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException nsae) {
			returnValue = null;
		}

		if (md == null)
			returnValue = null;
		else {
			try {
				byte[] bytesOfMessage = arg.getBytes("UTF-8");
				md.reset();
				md.update(bytesOfMessage);
				byte[] messageDigest = md.digest();

				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < messageDigest.length; i++) {
					String hex = Integer.toHexString(0xFF & messageDigest[i]);
					if (hex.length() == 1) {
						hexString.append("0");
					}
					hexString.append(hex);
				}
				returnValue = hexString.toString();
			} catch (UnsupportedEncodingException uee) {
				returnValue = null;
			}
		}
		return returnValue;
	}

}
