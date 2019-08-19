package com.snapdeal.payments.view.merchant.commons.dto;

import lombok.Data;

@Data
public class RetryTransactionDTO {

	public String txnId;
	public String txnType ;
}
