package com.snapdeal.opspanel.clientkeymanagement.request;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.payments.authorize.core.model.OnBoardClientRequest;

@Data
public class GenericOnBoardClientRequest {
	
	private String clientName;
	private String sourceApplication;
	private String targetApplication;
	private String clientContext;
	private String remarks;
	private List<String> addUsers;
	private List<String> removeUsers;
	private OnBoardClientRequest onBoardClientRequest;
	@JsonProperty
	private boolean isNewClient;

}
