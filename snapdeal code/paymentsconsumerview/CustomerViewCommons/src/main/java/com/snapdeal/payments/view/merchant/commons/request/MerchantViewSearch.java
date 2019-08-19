package com.snapdeal.payments.view.merchant.commons.request;

import lombok.Data;

public @Data class MerchantViewSearch {
	
	private String transactionId;
	private String merchantTxnId;
	private String settlementId;
	private String customerId;
	private String orderId;
	private String productId;
	private String terminalId;
	private String storeId;
	
	private String merchantTag ;
}