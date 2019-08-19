package com.snapdeal.opspanel.promotion.rp.controller;

import lombok.extern.slf4j.Slf4j;

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
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.GetBlockedTxnsResponse;
import com.snapdeal.opspanel.userpanel.service.BlockedTransactionHistory;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;


@Slf4j
@Controller
@RequestMapping("/blocked")
public class BlockedTransactionHistoryController {
	
	@Autowired
	BlockedTransactionHistory bth;
	
	@Audited(context = "blockedTxnHistory", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_FPS_BLOCKED_TXN'))")
	@RequestMapping(value = "/getBlockedTxns", method = RequestMethod.POST )
	public @ResponseBody GenericResponse getBlockedTxns(@RequestBody GetUserTransactionDetailsRequest request) throws OpsPanelException, InfoPanelException {
		GetBlockedTxnsResponse response = new GetBlockedTxnsResponse();
			response = bth.getUserTransactionDetails(request);
			return GenericControllerUtils.getGenericResponse(response);
	}

}
