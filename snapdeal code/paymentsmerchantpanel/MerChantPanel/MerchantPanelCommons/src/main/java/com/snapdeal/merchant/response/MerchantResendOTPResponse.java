package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantResendOTPResponse extends AbstractResponse{

	private static final long serialVersionUID = -896697479695256407L;
	
	private String otpId;
	
}
