package com.snapdeal.payments.view.merchant.commons.dto;

import lombok.Data;

@Data
public class MVTxnWithMetaDataDTO extends MVTransactionDTO {

	private static final long serialVersionUID = 2600763292943247586L;
	
	// meta data fields
	private String txnMetaData;
	private String payableMetaData;
	private String disbursementMetaData;
}
