package com.snapdeal.payments.view.request.commons.dto;

import lombok.Data;

import com.snapdeal.payments.view.request.commons.enums.PartyType;

public @Data class PartyDetailsDto {

	
	private String partyName ;
	
	
	private String id;
	private String name;
	private String mobileNumber;
	private String emailId;
	private PartyType partyType;
	private String partyTag;
}
