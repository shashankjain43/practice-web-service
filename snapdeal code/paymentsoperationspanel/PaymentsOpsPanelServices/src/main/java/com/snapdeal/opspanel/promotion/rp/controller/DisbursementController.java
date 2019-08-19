package com.snapdeal.opspanel.promotion.rp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.mob.enums.MOBRequestHeaders;
import com.snapdeal.mob.request.GetAllMerchantsRequest;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.payments.disbursement.model.GetDisburseHistoryRequest;
import com.snapdeal.payments.disbursement.model.GetDisburseHistoryResponse;
import com.snapdeal.payments.disbursement.model.GetDisburseTransactionsRequest;
import com.snapdeal.payments.disbursement.model.GetDisburseTransactionsResponse;
import com.snapdeal.payments.disbursement.model.GetMerchantDisbursementDetailsRequest;
import com.snapdeal.payments.disbursement.model.GetMerchantDisbursementDetailsResponse;
import com.snapdeal.payments.disbursement.model.SetDisburseStatusRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.vanila.service.MerchantOnBoardService;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.clientintegrationscomponent.service.impl.DisbursementOPSService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("disbursement/")
@Slf4j
public class DisbursementController {

	@Autowired
	DisbursementOPSService dService;

	@Autowired
	MerchantOnBoardService mobService;

	@Audited(context = "DISBURSEMENT", searchId = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_DISBURSEMENT_view'))")
	@RequestMapping(value = "/getAllMerchant", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getAllMerchant(HttpServletRequest httpRequest) throws Exception {
		GetAllMerchantsRequest request = new GetAllMerchantsRequest();
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return OPSUtils.getGenericResponse(mobService.getAllMerchant(request));
	}

	@Audited(context = "DISBURSEMENT", searchId = "request.merchantDisbursementId", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_DISBURSEMENT_view'))")
	@RequestMapping(value = "/getDisbursementHistory", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getDisbursementHistory(@RequestBody GetDisburseHistoryRequest request)
			throws Exception {
		GetDisburseHistoryResponse response = dService.getDisburseHistory(request);

		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "Disbursement", searchId = "request.transactionReference", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_DISBURSEMENT_updater'))")
	@RequestMapping(value = "/setDisburseStatus", method = RequestMethod.POST)
	public @ResponseBody GenericResponse setDisburseStatus(@RequestBody SetDisburseStatusRequest request)
			throws Exception {
		String response = dService.setDisburseStatus(request);

		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "Disbursement", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_DISBURSEMENT_view'))")
	@RequestMapping(value = "/getDisbursementDetails", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getMerchantDetails(@RequestBody GetMerchantDisbursementDetailsRequest request)
			throws Exception {
		GetMerchantDisbursementDetailsResponse response = dService.getMerchantDisbursementDetails(request);

		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "Disbursement", searchId = "request.disbursementType", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_DISBURSEMENT_view'))")
	@RequestMapping(value = "/getAlldisbursements", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getAlldisbursements(@RequestBody GetDisburseTransactionsRequest request)
			throws Exception {
		GetDisburseTransactionsResponse response = dService.getDisburseAutomaticReconTransactionsResponse(request);

		return OPSUtils.getGenericResponse(response);
	}
}
