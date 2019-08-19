package com.snapdeal.merchant.response;

import com.snapdeal.merchant.response.AbstractResponse;

import lombok.Data;

@Data
public class MerchantSetPasswordResponse extends AbstractResponse{
	
	
	private static final long serialVersionUID = 1480371140969748225L;
	
	private boolean success ;

}
