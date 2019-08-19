package com.snapdeal.merchant.token;

import java.util.UUID;

public class TokenUtil {
	
	private TokenUtil(){
		
	}
	
	public static String getToken() {
		return UUID.randomUUID().toString();
	}

}
