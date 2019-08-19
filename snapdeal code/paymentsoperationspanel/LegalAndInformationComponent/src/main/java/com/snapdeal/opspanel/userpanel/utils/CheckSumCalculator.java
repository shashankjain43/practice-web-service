package com.snapdeal.opspanel.userpanel.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;

public class CheckSumCalculator {
	public static String generateSHA256CheckSum(String arg) throws InfoPanelException {
		String returnValue = null;
		if ((arg == null) || (arg.trim().equals(""))) {
			return null;
		}

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte[] bytesOfMessage = arg.getBytes("UTF-8");
			md.update(bytesOfMessage);
			byte[] messageDigest = md.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}

			returnValue = hexString.toString();

			/*
			 * ( if (log.isInfoEnabled()) log.info("Generated auth token {}",
			 * returnValue);
			 */
		} catch (NoSuchAlgorithmException ex) {
			/*
			 * throw new GenericOneCheckException( Error.OC_7.getErrCode(),
			 * Error.OC_7.getErrMessage());
			 */
			throw new InfoPanelException("MT-5506", ex.getMessage());
		} catch (UnsupportedEncodingException ex) {/*
													 * throw new
													 * GenericOneCheckException(
													 * Error.OC_7.getErrCode(),
													 * Error.OC_7.getErrMessage(
													 * ));
													 */
			throw new InfoPanelException("MT-5505", ex.getMessage());
		}
		return returnValue;
	}
}