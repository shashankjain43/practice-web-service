package com.snapdeal.opspanel.userpanel.stolenInstrument.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.opspanel.userpanel.stolenInstrument.enums.EntityType;

@Data
public class BlockResponse {
	
	private String entityValue;
	
	private EntityType entityType;
	
	@JsonProperty
	private boolean userDisabledStatus;
	
	@JsonProperty
	private boolean walletDisabledStatus;
	
	@JsonProperty
	private boolean bankAccountDisabledStatus;

}
