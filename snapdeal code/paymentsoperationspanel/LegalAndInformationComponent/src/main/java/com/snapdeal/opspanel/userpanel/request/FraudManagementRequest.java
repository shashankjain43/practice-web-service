package com.snapdeal.opspanel.userpanel.request;

import java.util.List;

import com.snapdeal.opspanel.userpanel.enums.FraudManagementReason;
import com.snapdeal.opspanel.userpanel.enums.FraudManagementTransactionEnum;

import lombok.Data;

@Data
public class FraudManagementRequest {

	private String fraudManagementActionkey;
	
	private String fraudManagementActionKeyType;
	
	private List<String> actions;
	
	private String amount;
	
	private String dateOfFraudTransaction;
	
	private String reason;
	
	private String otherReason;
	
}
