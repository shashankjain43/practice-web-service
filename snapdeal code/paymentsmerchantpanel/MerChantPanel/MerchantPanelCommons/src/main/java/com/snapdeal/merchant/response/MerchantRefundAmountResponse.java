package com.snapdeal.merchant.response;

import lombok.Data;

@Data
public class MerchantRefundAmountResponse extends AbstractResponse {
	
	private static final long serialVersionUID = -8223148850927500076L;
	
	private boolean status;
	
	private String refundTxnId;

}
