package com.snapdeal.opspanel.userpanel.response;



import lombok.Data;

@Data
public class FraudOutputResponse {


	private String userId;
	
	private String userName;
	
	private String userEmail;
	
	private String userMobile;
	
	private String imsAccountStatus;
	
	private String imsWalletStatus;
	
	private String amountRefunded;
	
	private String originalAmount;
	
	private String generalAccountBalanceAfterRefund;
	
	private String generalVoucherBalanceAfterRefund;
		
}
