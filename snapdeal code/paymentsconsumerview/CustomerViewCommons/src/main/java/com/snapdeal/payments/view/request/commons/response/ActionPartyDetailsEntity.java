package com.snapdeal.payments.view.request.commons.response;

import lombok.Data;

public @Data class ActionPartyDetailsEntity {

	private String partyType;
	private String partyId;
	private String emailId;
	private String mobileNumber;
	private String partyTag ;
	private String jabberId ;
	private String partyName ;
}
