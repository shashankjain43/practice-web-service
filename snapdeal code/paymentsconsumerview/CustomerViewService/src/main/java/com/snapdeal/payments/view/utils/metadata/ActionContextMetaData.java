package com.snapdeal.payments.view.utils.metadata;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.snapdeal.payments.view.entity.ActionPartyDetailsEntity;

public @Data class ActionContextMetaData<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
	private T txnMetaData ;
	
	private ActionPartyDetailsEntity otherPartyDTO ;
	
	private ActionPartyDetailsEntity userDisplayInfo ;
	
	private BigDecimal txnAmount ;
	
	private String otherPrtyId ;
	
	private String referenceId ;
	
	private String referenceType ;
}
