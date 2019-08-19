package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantReportDetails;

import lombok.Data;

@Data
public class MerchantInvoiceDetailsResponse extends AbstractResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2564490974274145727L;
	
	private List<MerchantReportDetails> merchantInvoiceDetails;
}
