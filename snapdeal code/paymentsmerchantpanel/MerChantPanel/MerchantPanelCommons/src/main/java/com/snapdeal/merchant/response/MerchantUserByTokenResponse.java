package com.snapdeal.merchant.response;

import com.snapdeal.merchant.dto.MerchantUserDTO;

import lombok.Data;

@Data
public class MerchantUserByTokenResponse extends AbstractResponse{

	
	private static final long serialVersionUID = -2041830159644587786L;
	
	private MerchantUserDTO userDetails;

}
