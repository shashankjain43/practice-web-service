package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantGetKYCDocListRequest extends AbstractMerchantRequest{

	private static final long serialVersionUID = 1L;
	
	String businessType;

}
