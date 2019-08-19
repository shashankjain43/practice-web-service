package com.snapdeal.merchant.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantInvoiceDetailsRequest extends AbstractMerchantRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8165209690292221960L;
	public Integer pageSize;
	public Long startTime;
	public Long endTime;

}

