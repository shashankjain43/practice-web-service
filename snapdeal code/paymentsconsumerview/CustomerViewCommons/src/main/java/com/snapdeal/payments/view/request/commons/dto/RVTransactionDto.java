
package com.snapdeal.payments.view.request.commons.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

public @Data class RVTransactionDto {

	private String txnId;
	// Transaction type in TSM.
    private String txnType;
    // Defines the view type.
	private String requestType;
	private String txnStatus;
	private Date txnDate;
	private BigDecimal txnAmount;
	
	private PartyDetailsDto srcParty;
	private PartyDetailsDto destParty;



	}