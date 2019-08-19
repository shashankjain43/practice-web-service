package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantReportDetails;

import lombok.Data;

@Data
public class MerchantSettlementReportResponse extends AbstractResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7148211171924932376L;
	
	private List<MerchantReportDetails> merchantReportDetails;

}
