package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.exception.OneCheckServiceException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.service.LegalService;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.ImsIdTypes;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.P2PReverseTxnTypes;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/legal")
@Slf4j
public class LegalController {

	@Autowired
	LegalService legalService;

	@Audited(context = "Legal", searchId = "reverseTransactionRequest.prevTransactionId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGAL_REVERSE_TRANSACTION'))")
	@RequestMapping(value = "/reverseTransaction", method = RequestMethod.POST)
	public @ResponseBody GenericResponse reverseTransaction(
			@RequestBody ReverseTransactionRequest reverseTransactionRequest, BindingResult bindingResult)
					throws WalletServiceException, OpsPanelException {

		GenericControllerUtils.checkBindingResult(bindingResult, "reverseTransaction in LegalController");
		log.info("Received reverseTransactionRequest: " + reverseTransactionRequest);
		return GenericControllerUtils.getGenericResponse(legalService.reverseTransaction(reverseTransactionRequest));

	}

	@Audited(context = "Legal", searchId = "reverseLoadMoneyRequest.prevTransactionId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGAL_REVERSE_LOAD_MONEY'))")
	@RequestMapping(value = "/reverseLoadMoney", method = RequestMethod.POST)
	public @ResponseBody GenericResponse reverseLoadMoney(@RequestBody ReverseLoadMoneyRequest reverseLoadMoneyRequest,
			BindingResult bindingResult) throws WalletServiceException, OpsPanelException {
		GenericControllerUtils.checkBindingResult(bindingResult, "reverseLoadMoney in LegalController");
		log.info("Received reverseLoadMoneyRequest: " + reverseLoadMoneyRequest);
		return GenericControllerUtils.getGenericResponse(legalService.reverseLoadMoney(reverseLoadMoneyRequest));

	}
	
	@Audited(context = "Legal", searchId = "reverseLoadMoneyRequest.transactionId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGAL_REVERSE_LOAD_MONEY'))")
	@RequestMapping(value = "/reverseLoadMoneyOneCheck", method = RequestMethod.POST)
	public @ResponseBody GenericResponse reverseLoadMoneyViaOneCheck(
			@RequestBody com.snapdeal.opspanel.promotion.request.ReverseLoadMoneyRequest
			reverseLoadMoneyRequest,
			BindingResult bindingResult) throws OpsPanelException, OneCheckServiceException {
		GenericControllerUtils.checkBindingResult(bindingResult, "reverseLoadMoney in LegalController");
		log.info("Received reverseLoadMoneyRequest: " + reverseLoadMoneyRequest);
		return GenericControllerUtils.getGenericResponse(legalService.reverseLoadMoneyViaOneCheck(reverseLoadMoneyRequest));

	}

	@PreAuthorize("(hasPermission('OPS_LEGAL_PARTIAL_P2P_REVERSE_TRANSACTION') )")
	@RequestMapping(value = "/getIMSIDForP2PReversal", method = RequestMethod.GET)
	public @ResponseBody com.snapdeal.opspanel.promotion.Response.GenericResponse getActionsForBulkActivity()
			throws Exception {
		List<String> actionList = new ArrayList<>();
		for (ImsIdTypes type : ImsIdTypes.values()) {
			actionList.add(type.toString());
		}

		return OPSUtils.getGenericResponse(actionList);

	}

	@Audited(context = "Legal", searchId = "reverseRefundTransactionRequest.prevTransactionId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGAL_REFUND_REVERSE_TRANSACTION'))")
	@RequestMapping(value = "/reverseRefundTransaction", method = RequestMethod.POST)
	public @ResponseBody GenericResponse reverseRefundTransaction(
			@RequestBody ReverseRefundTransactionRequest reverseRefundTransactionRequest, BindingResult bindingResult)
					throws WalletServiceException, OpsPanelException {
		GenericControllerUtils.checkBindingResult(bindingResult, "reverseRefundTransaction in LegalController");
		log.info("Received reverseRefundTransactionRequest: " + reverseRefundTransactionRequest);
		return GenericControllerUtils
				.getGenericResponse(legalService.reverseRefundTransaction(reverseRefundTransactionRequest));

	}

	@PreAuthorize("(hasPermission('OPS_LEGAL_PARTIAL_P2P_REVERSE_TRANSACTION') )")
	@RequestMapping(value = "/getTxnTypesForP2P", method = RequestMethod.GET)
	public @ResponseBody com.snapdeal.opspanel.promotion.Response.GenericResponse getTxnTypesForP2PReversal()
			throws Exception {
		List<String> actionList = new ArrayList<>();

		for (P2PReverseTxnTypes type : P2PReverseTxnTypes.values()) {
			actionList.add(type.toString());
		}

		return OPSUtils.getGenericResponse(actionList);

	}

}
