package com.snapdeal.merchant.entity.response;


import lombok.Data;

@Data
public class GenericMerchantResponse<T> {
	
	private GenericMerchantError error;
	
	private T data;
	
}
