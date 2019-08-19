package com.snapdeal.merchant.request.coverter;

import org.springframework.core.convert.converter.Converter;

import com.snapdeal.merchant.request.MerchantVerifyUserRequest;

public class StringToVerifyUserRequestConverter implements Converter<String, MerchantVerifyUserRequest> {

	@Override
	public MerchantVerifyUserRequest convert(String source) {
		MerchantVerifyUserRequest request = new MerchantVerifyUserRequest();
		request.setLoginName(source);
		return request;
	}
	
}
