package com.snapdeal.payments.view.entity;

import lombok.Data;

public @Data class PartyDetailsEntity {
	private String partyType;
	private String partyId;
	private String emailId;
	private String mobileNumber;
	private String partyTag ;
	private String jabberId ;
}