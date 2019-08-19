package com.snapdeal.payments.view.merchant.commons.response;

import java.math.BigDecimal;

import com.snapdeal.payments.view.commons.response.AbstractResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author shashank
 * 
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetTotalRefundedAmountForTxnResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 1758036917210099698L;
	
	private BigDecimal totalRefundedAmount;
	
}
