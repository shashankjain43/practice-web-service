package com.snapdeal.merchant.request;

import java.util.List;

import lombok.Data;

@Data
public class MerchantRoleRequest extends AbstractMerchantRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1528077648632960508L;
	
	private List<String> roles;

}
