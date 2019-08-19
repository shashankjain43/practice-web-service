package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantAddUserResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	
	private boolean success;

}
