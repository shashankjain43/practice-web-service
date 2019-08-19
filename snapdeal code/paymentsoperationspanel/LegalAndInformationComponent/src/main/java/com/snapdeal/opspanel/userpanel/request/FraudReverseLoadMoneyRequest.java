package com.snapdeal.opspanel.userpanel.request;

import lombok.Data;

@Data
public class FraudReverseLoadMoneyRequest {

	private String transactionId;
	private String reason;
	
}
