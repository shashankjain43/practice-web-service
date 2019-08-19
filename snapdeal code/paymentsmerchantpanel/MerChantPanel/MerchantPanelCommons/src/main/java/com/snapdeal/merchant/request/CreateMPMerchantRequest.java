package com.snapdeal.merchant.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="password")
public class CreateMPMerchantRequest extends AbstractRequest{

	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private String password;
	
	private String integrationMode ; 
	
	private String integrationModeSubtype ;
	
}
