package com.snapdeal.opspanel.clientkeymanagement.request;

import lombok.Data;

import com.snapdeal.payments.authorize.core.model.UpdateClientStatusRequest;
@Data
public class GenericUpdateClientStatusRequest {
	
	private String targetApplication;
	private UpdateClientStatusRequest updateClientStatusRequest;

}
