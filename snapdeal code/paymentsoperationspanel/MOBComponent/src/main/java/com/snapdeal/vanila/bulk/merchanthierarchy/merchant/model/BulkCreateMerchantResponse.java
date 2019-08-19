package com.snapdeal.vanila.bulk.merchanthierarchy.merchant.model;

import lombok.Data;

@Data
public class BulkCreateMerchantResponse {

	private String createStatus;
	
	private String merchantId;
	
	private String createError;
}
