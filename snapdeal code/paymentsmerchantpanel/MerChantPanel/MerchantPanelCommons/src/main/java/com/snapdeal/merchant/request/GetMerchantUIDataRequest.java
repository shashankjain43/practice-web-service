package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class GetMerchantUIDataRequest extends AbstractRequest{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="TYPE_IS_BLANK")
	private String type;
	
	private String parent;
	
	@NotBlank(message="PARENT_IS_BLANK")
	private String integrationMode;
	
}