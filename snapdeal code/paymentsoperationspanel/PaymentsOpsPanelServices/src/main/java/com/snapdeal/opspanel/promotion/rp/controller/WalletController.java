package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceOnTimestampRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountsForEntityRequest;
import com.snapdeal.payments.sdmoney.service.model.GetPendingGeneralBalanceLimitsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetPendingGeneralBalanceLimitsForUserResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wallet")
@Slf4j
public class WalletController {

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Audited(context = "Wallet", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getCorpAccountsForEntity", method = RequestMethod.POST)
	public @ResponseBody com.snapdeal.opspanel.promotion.Response.GenericResponse getCorpAccountsForEntityCorpModule(
			@RequestBody GetCorpAccountsForEntityRequest request)
					throws WalletServiceException {
		List response;
		try {
			response = sdMoneyClient.getCorpAccountsForEntity(request);

		} catch (SDMoneyException sdme) {
		   log.info( "Exception while getting corp account list for entities " + sdme );
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode()
					.getErrorCode()), sdme.getMessage(), "SDMoneyClient");

		}
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "Wallet", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getPendingGeneralBalanceLimitsForUser", method = RequestMethod.GET)
	public @ResponseBody com.snapdeal.opspanel.promotion.Response.GenericResponse getPendingGeneralBalanceLimitsForUser(
			@RequestParam("email") String email)
					throws WalletServiceException {
		GetPendingGeneralBalanceLimitsForUserRequest request= new GetPendingGeneralBalanceLimitsForUserRequest();
		request.setEmailId(email);
		GetPendingGeneralBalanceLimitsForUserResponse response;
		try {
			response = sdMoneyClient.getPendingGeneralBalanceLimitsForUser(request);

		} catch (SDMoneyException sdme) {
		   log.info( "Exception while getting corp account list for entities " + sdme );
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode()
					.getErrorCode()), sdme.getMessage(), "SDMoneyClient");

		}
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}

	@Audited(context = "Wallet", searchId = "request.timestamp", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getCorpAccountBalanceOnTimestamp", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCorpAccountBalanceOnTimestamp(
			@RequestBody GetCorpAccountBalanceOnTimestampRequest request)
					throws WalletServiceException {
		GetCorpAccountBalanceResponse response;
		//TODO: HOT FIX for account balance
		GetCorpAccountBalanceRequest getCorpAccountBalanceRequest = new GetCorpAccountBalanceRequest();
		getCorpAccountBalanceRequest.setAccountId(request.getAccountId());
		try {
			response = sdMoneyClient.getCorpAccountBalance(getCorpAccountBalanceRequest);

		} catch (SDMoneyException sdme) {
		   log.info( "Exception while getting corp account balance on timestamp " + sdme );
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode()
					.getErrorCode()), sdme.getMessage(), "SDMoneyClient");

		}
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "Wallet", searchId = "request.idempotencyKey", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/gettransactionbyidempotencyid", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getTransactionByIdempotencyId(
			@RequestBody GetTransactionByIdempotencyIdRequest request)
					throws WalletServiceException {
		GetTransactionByIdempotencyIdResponse response;
		try {
			response = sdMoneyClient.getTransactionByIdempotencyId(request);

		} catch (SDMoneyException sdme) {
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode()
					.getErrorCode()), sdme.getMessage(), "SDMoneyClient");

		}
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
}