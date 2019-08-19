package com.snapdeal.ums.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class HashProvider {

	private static final String HASH_TYPE = "SHA-1";

	public String generateHash(byte[] excelFileContent)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance(HASH_TYPE);
		byte[] sha1hash = new byte[40];
		md.update(excelFileContent);
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

}
