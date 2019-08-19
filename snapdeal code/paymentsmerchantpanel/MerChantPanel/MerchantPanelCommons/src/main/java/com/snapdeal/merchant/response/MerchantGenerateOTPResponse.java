package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantGenerateOTPResponse extends AbstractResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3365629886682688729L;

	private String otpId;
	
}
