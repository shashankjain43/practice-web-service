package com.snapdeal.opspanel.promotion.rp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.service.OPSPaymentsReportService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;

@Controller
@RequestMapping("settlementReports")
public class SettlementReportsController {

	@Autowired
	OPSPaymentsReportService OPSPaymentsReportService;
	
	@Audited(context = "Settlement", searchId = "getMerchantsettlementReportDetailsRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_SETTLEMENT_REPORT'))")
	@RequestMapping(value = "/getSettlementReportsByMerchantId", method = RequestMethod.POST)
	public @ResponseBody GetMerchantSettlementReportDetailsResponse getSettlementReportByMerchantId(
			@RequestBody GetMerchantSettlementReportDetailsRequest getMerchantsettlementReportDetailsRequest)
					throws WalletServiceException {

		try {
			return OPSPaymentsReportService.getMerchantSettlementReport(getMerchantsettlementReportDetailsRequest);
		} catch (WalletServiceException e) {
			e.printStackTrace();
			throw new WalletServiceException("MT-9002", e.getMessage());
		}

	}

	@Audited(context = "Settlement", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_SETTLEMENT_REPORT'))")
	@RequestMapping(value = "/getMerchantInvoiceDetails", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getMerchantInvoiceDetails( @RequestBody GetMerchantInvoiceDetailsRequest request) throws OpsPanelException {
			return GenericControllerUtils.getGenericResponse( OPSPaymentsReportService.getMerchantInvoiceDetails( request ) );
	}

}
