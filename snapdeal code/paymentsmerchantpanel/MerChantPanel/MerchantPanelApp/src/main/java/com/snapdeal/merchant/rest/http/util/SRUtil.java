package com.snapdeal.merchant.rest.http.util;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantInvoiceDetailsRequest;
import com.snapdeal.merchant.request.MerchantSettlementReportRequest;
import com.snapdeal.payments.settlement.report.client.SettlementReportClient;
import com.snapdeal.payments.settlement.report.exceptions.SettlementReportException;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SRUtil {

	
	@Autowired
	private MpanelConfig config;
	
	@Autowired
	private SettlementReportClient srClient;
		
	public GetMerchantSettlementReportDetailsResponse getSettlementReport(MerchantSettlementReportRequest request) throws MerchantException {
		
		GetMerchantSettlementReportDetailsRequest srRequest = new GetMerchantSettlementReportDetailsRequest();
		srRequest.setMerchantId(request.getMerchantId());
		srRequest.setPageSize(request.getPageSize());
		
		if(request.getStartTime() != null)
		  srRequest.setStartTime(new Date(request.getStartTime()));
		
		if(request.getEndTime() != null)
		srRequest.setEndTime(new Date(request.getEndTime()));
		
		GetMerchantSettlementReportDetailsResponse srResponse = null;
		try{
			log.info("SR Request for getSettlementReport: {}",srRequest);
			srResponse = srClient.getMerchantSettlementReportDetails(srRequest);
			log.info("SR Response for getSettlementReport: {}",srResponse);
		}catch(SettlementReportException sre){
		
			log.error("Exception From SettlementReport while getting report url : {}  {}", sre.getMessage() , sre);
		throw new MerchantException(sre.getErrorCode().getErrorCode(), ErrorConstants.UNABLE_TO_RETRIEVE_INFO_MSG);
		}
		
		return srResponse;
		
	}

	public GetMerchantInvoiceDetailsResponse getInvoiceDetails(MerchantInvoiceDetailsRequest request) throws MerchantException {
		
		GetMerchantInvoiceDetailsRequest  srRequest = new GetMerchantInvoiceDetailsRequest();
		srRequest.setMerchantId(request.getMerchantId());
		srRequest.setPageSize(request.getPageSize());
		
		if(request.getStartTime() != null)
		  srRequest.setStartTime(new Date(request.getStartTime()));
		
		if(request.getEndTime() != null)
		srRequest.setEndTime(new Date(request.getEndTime()));
		
		GetMerchantInvoiceDetailsResponse srResponse = null;
		try{
			log.info("SR Request For getInvoiceDetails: {}",srRequest);
			srResponse = srClient.getMerchantInvoiceDetails(srRequest);
			log.info("SR Response For getInvoiceDetails: {}",srResponse);
			
		}catch(SettlementReportException sre){
		
			log.error("Exception From SettlementReport while getting report url : {}  {}", sre.getMessage() , sre);
		throw new MerchantException(sre.getErrorCode().getErrorCode(), ErrorConstants.UNABLE_TO_RETRIEVE_INFO_MSG);
		}
		
		return srResponse;
	}
	
	public void setSrClient(SettlementReportClient srClient){
		this.srClient=srClient;
	}
}
