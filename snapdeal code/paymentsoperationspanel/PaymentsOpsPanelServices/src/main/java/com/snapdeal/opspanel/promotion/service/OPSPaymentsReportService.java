package com.snapdeal.opspanel.promotion.service;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGenerationResponse;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGetResponse;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGenerationRequest;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGetRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;

@Service("reportService")
public interface OPSPaymentsReportService {

	public OPSPaymentsReportGenerationResponse generateReport(OPSPaymentsReportGenerationRequest opsPaymentsReportGenerationRequest, String reportHead) throws Exception;
	public OPSPaymentsReportGetResponse getReport(OPSPaymentsReportGetRequest opsPaymentsReportGetRequest, String reportHead) throws Exception;
    public GetMerchantSettlementReportDetailsResponse getMerchantSettlementReport(GetMerchantSettlementReportDetailsRequest getMerchantsettlementReportDetailsRequest) throws WalletServiceException;
    public GetMerchantInvoiceDetailsResponse getMerchantInvoiceDetails(GetMerchantInvoiceDetailsRequest request) throws OpsPanelException;

}
