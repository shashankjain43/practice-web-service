package com.snapdeal.merchant.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MerchantRefundAmountForTxnResponse extends AbstractResponse{

	
	private static final long serialVersionUID = -2164621669849196636L;
	
	private BigDecimal totalRefundAmount;

}
