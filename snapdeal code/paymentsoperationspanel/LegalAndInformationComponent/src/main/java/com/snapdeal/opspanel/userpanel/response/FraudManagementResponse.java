package com.snapdeal.opspanel.userpanel.response;

import java.util.List;

import lombok.Data;

@Data
public class FraudManagementResponse {
	
	private List<TransactionDetails>  transactions;
	private String status;
	
}
