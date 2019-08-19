package com.snapdeal.merchant.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="bankAccount")
public class MerchantBankInfo {

	private String bankAccount;
	
	private String bankName;
	
	private String accountHolderName;
   
	private String ifscCode;
	   
	private Integer disburseDifferencePeriod;

	private String bankStatus;
	
}
