package com.snapdeal.payments.view.request.commons.dto;

import lombok.Data;

public @Data class ActionTxnDetailsDTO {

	private String id;
	private String userId ;
	private String referenceId ;
	private String referenceType ;
	private String actionContext ;
}
