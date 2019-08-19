package com.snapdeal.opspanel.clientkeymanagement.response;

import lombok.Data;

import com.snapdeal.payments.authorize.core.model.ClientSummary;

@Data
public class GenericUpdateClientStatusResponse {
	
	private String message;
	private ClientSummary clientDetails;

}
