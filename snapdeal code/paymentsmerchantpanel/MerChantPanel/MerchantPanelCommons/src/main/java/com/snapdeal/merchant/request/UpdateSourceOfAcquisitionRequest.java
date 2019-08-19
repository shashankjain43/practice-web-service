package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class UpdateSourceOfAcquisitionRequest extends AbstractMerchantRequest{

	private static final long serialVersionUID = 1L;
	
	private String sourceOfAcquisition ;

}
