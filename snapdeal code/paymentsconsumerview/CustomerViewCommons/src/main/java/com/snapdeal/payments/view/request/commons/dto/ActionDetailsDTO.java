package com.snapdeal.payments.view.request.commons.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

public @Data class ActionDetailsDTO {

	private String actionId;
	private String actionType;
	private String actionViewState;
	private BigDecimal txnAmount;
	private String userId;
	private String userType;
	private String otherPartyId;
	private String userInfo;
	private String referenceId;
	private String referenceType;
	private Date txnTime;
	private String actionState;
	private String actionTaken;
	private String validActionCommands;
	private String userDisplayInfo;
}
