package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantVerifyOTPResponse extends AbstractResponse
{
	
	private static final long serialVersionUID = -5158864351860288510L;

	private String message;
}
