package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class MerchantRefundAmountForTxnRequest extends AbstractMerchantRequest{


	private static final long serialVersionUID = -8267778501807168448L;

	private String txnRefId;
	
	private String txnRefType;
	
	private String orderId;
}
