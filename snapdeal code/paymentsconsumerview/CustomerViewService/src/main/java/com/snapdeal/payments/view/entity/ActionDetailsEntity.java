package com.snapdeal.payments.view.entity;

import java.util.Date;

import lombok.Data;

public @Data class ActionDetailsEntity {
	private String id ;
	private String actionId ;
	private String actionType;
	private String actionState;
	private String actionViewState;
	private String actionTaken;
	private String validActionCommands;
	private String userId;
	private String userType;
	private String otherPartyId ;
	private String referenceId ;
	private String referenceType ;
	private String actionContext ;
	private Date actionInitiationTimestamp;
	private Date actionLastIpdateTimestamp;
	private Date actionNextScheduleTimestamp ;
	private Date actionExpiryTime ;
}
