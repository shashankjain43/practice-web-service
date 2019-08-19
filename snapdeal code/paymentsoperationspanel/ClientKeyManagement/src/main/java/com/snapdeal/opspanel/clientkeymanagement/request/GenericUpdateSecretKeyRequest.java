package com.snapdeal.opspanel.clientkeymanagement.request;

import lombok.Data;

import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyRequest;

@Data
public class GenericUpdateSecretKeyRequest {
	
	private String targetApplication;
	private UpdateSecretKeyRequest updateSecretKeyRequest;

}
