package com.snapdeal.ims.utility;

public class EmailUtils {

	public static String toLowerCaseEmail(String email) {
		if (email == null) {
			return null;
		}
		return email.trim().toLowerCase();

	}

	public static String getDomainFromEmail(String email) {
		String[] parts = email.split("@");
		int lastIndex = parts.length - 1;
		return parts[lastIndex];
	}

	public static String maskEmail(String email) {
		int total = email.length();
		int startlen = 2;
		int endlen = total - (email.indexOf('@') - 2);
		int masklen = total - (startlen + endlen);
		StringBuffer maskedbuf = new StringBuffer(email.substring(0, startlen));
		for (int i = 0; i < masklen; i++) {
			maskedbuf.append('*');
		}
		maskedbuf.append(email.substring(startlen + masklen, total));
		String masked = maskedbuf.toString();
		return masked;
	}
}
