package com.snapdeal.merchant.response;

import com.snapdeal.merchant.response.AbstractResponse;

import lombok.Data;

@Data
public class CreateMPMerchantResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	private String merchantId;
}
