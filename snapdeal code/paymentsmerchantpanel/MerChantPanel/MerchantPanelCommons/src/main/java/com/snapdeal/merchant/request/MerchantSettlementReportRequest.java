package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantSettlementReportRequest extends AbstractMerchantRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9101151235420727098L;
	
	public Integer pageSize;
	public Long startTime;
	public Long endTime;

}
