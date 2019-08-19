package com.snapdeal.merchant.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;

@Data
public class MerchantRefundAmountRequest extends AbstractMerchantRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -491077987342376580L;

	@NotBlank(message=ErrorConstants.ORDER_ID_IS_BLANK_CODE)
	private String orderId;
	
	private String refId;
	
	@NotNull(message=ErrorConstants.AMOUNT_IS_BLANK_CODE)
	private BigDecimal amount;

	private String comments;
}
