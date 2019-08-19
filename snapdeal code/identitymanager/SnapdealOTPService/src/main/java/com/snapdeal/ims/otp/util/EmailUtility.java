package com.snapdeal.ims.otp.util;

public class EmailUtility {

		public static String toLowerCaseEmail(String email) {
			if (email == null) {
				return null;
			}
			return email.trim().toLowerCase();

		}


}
