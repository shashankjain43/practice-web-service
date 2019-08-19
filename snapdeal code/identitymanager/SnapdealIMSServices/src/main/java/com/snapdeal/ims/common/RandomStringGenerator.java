package com.snapdeal.ims.common;

import java.util.Random;
import java.util.UUID;

/**
 * This class is used to generate random string 
 * @author subhash
 *
 */
public class RandomStringGenerator {
	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		//Including numeric 
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);

		//Including alphabets 
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);

		//Including special characters
		tmp.append("!@#$%^&");
		symbols = tmp.toString().toCharArray();
	}   

	public static String nextString(int len) {
		char[] buf = new char[len];
		Random random = new Random();
		for (int idx = 0; idx < len; ++idx) 
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}

	public static String getRandomKeyUsingUUID() {
		final Long hashKey  = UUID.randomUUID().getLeastSignificantBits();
		final String key = Long.toHexString(hashKey).toUpperCase();
		return key;
	}
}
