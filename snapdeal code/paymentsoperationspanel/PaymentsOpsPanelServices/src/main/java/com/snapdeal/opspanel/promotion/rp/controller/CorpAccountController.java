package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.clientintegrationscomponent.request.CorpToCorpMoneyTransferRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.LoadMerchantNodalRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.PGNodalRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.service.CorpAccountService;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.Response.CorpSettlementResponse;
import com.snapdeal.opspanel.promotion.constants.CorpAccountConstants;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.CorpAccount;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceOnTimestampRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountsForEntityAndTypeRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountsForEntityRequest;
import com.snapdeal.payments.sdmoney.service.model.MerchantCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.MerchantRiskReserveCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.PGSettleCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.SettleCorpAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.SettleCorpAccountResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/corpLoad")
@Slf4j
public class CorpAccountController {

	@Autowired
	CorpAccountService corpAccountService;

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Audited(context = "CORPACCOUNT", searchId = "corpToCorpMoneyTransferReqeust.transactionReference" )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")
	@RequestMapping(value = CorpAccountConstants.CORPTOCORPTRANSFER, method = RequestMethod.POST)
	public @ResponseBody GenericResponse corpToCorpMoneyTransfer(
			@RequestBody @Valid CorpToCorpMoneyTransferRequest corpToCorpMoneyTransferReqeust,
			BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "corpToCorpMoneyTransfer in CorpAccountController");
		log.info("Received corpToCorpMoneyTransferRequest: " + corpToCorpMoneyTransferReqeust);
		return GenericControllerUtils
				.getGenericResponse(corpAccountService.corpToCorpMoneyTranfer(corpToCorpMoneyTransferReqeust));
	}

	@Audited(context = "CORPACCOUNT", searchId = "getCorpAccountsForEntityRequest.merchantId",viewable=0 )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD') or hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")
	@RequestMapping(value = CorpAccountConstants.GETCORPACCOUNTFORENTITY, method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCorpAccountForEntity(
			@RequestBody @Valid GetCorpAccountsForEntityRequest getCorpAccountsForEntityRequest,
			BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "getCorpAccountEntity in CorpAccountController");
		log.info("Received getCorpAccountForEntity: " + getCorpAccountsForEntityRequest);
		return GenericControllerUtils
				.getGenericResponse(corpAccountService.getCorpAccountForEntity(getCorpAccountsForEntityRequest));
	}

	@Audited(context = "CORPACCOUNT", searchId = "merchantCorpLoadRequest.neftId" )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD'))")
	@RequestMapping(value = CorpAccountConstants.LOADMERCHANTCORPACCOUNT, method = RequestMethod.POST)
	public @ResponseBody GenericResponse loadMerchantCorpAccount(
			@RequestBody @Valid MerchantCorpLoadRequest merchantCorpLoadRequest, BindingResult bindingResult)
					throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "loadMerchantCorpAccount in CorpAccountController");
		log.info("Received loadMerchantCorpAccount : " + merchantCorpLoadRequest);
		return GenericControllerUtils
				.getGenericResponse(corpAccountService.loadMerchantCorpAccount(merchantCorpLoadRequest));
	}

	@Audited(context = "CORPACCOUNT", searchId = "pgSettleCorpLoadRequest.neftId" )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_PGSETTLE'))")
	@RequestMapping(value = CorpAccountConstants.LOADPGSETTLECORPACCOUNT, method = RequestMethod.POST)
	public @ResponseBody GenericResponse loadPGSettleCorpAccount(
			@RequestBody @Valid PGSettleCorpLoadRequest pgSettleCorpLoadRequest, BindingResult bindingResult)
					throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "loadPGSettleCorpAccount in CorpAccountController");
		log.info("Received loadPGSettleCorpAccount: " + pgSettleCorpLoadRequest);
		return GenericControllerUtils
				.getGenericResponse(corpAccountService.loadPGSettleCorpAccount(pgSettleCorpLoadRequest));
	}

	@Audited(context = "CORPACCOUNT", searchId = "merchantRiskReserveCorpLoadRequest.neftId" )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_RISKRESERVE'))")
	@RequestMapping(value = CorpAccountConstants.LOADMERCHANTRISKRESERVECORPACCOUNT, method = RequestMethod.POST)
	public @ResponseBody GenericResponse loadRiskReseveCorpAccount(
			@RequestBody @Valid MerchantRiskReserveCorpLoadRequest merchantRiskReserveCorpLoadRequest,
			BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "loadRiskReseveCorpAccount in CorpAccountController");
		log.info("Received loadRiskReserveCorpAccount: " + merchantRiskReserveCorpLoadRequest);
		return GenericControllerUtils
				.getGenericResponse(corpAccountService.loadRiskReseveCorpAccount(merchantRiskReserveCorpLoadRequest));
	}

	@Audited(context = "CORPACCOUNT", searchId = "pgNodalRequest.neftId" )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_PGNODAL'))")
	@RequestMapping(value = CorpAccountConstants.PGNODAL, method = RequestMethod.POST)
	public @ResponseBody GenericResponse loadPGNodal(@RequestBody @Valid PGNodalRequest pgNodalRequest,
			BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "loadPGNodal in CorpAccountController");
		log.info("Received loadPGNodal: " + pgNodalRequest);
		return GenericControllerUtils.getGenericResponse(corpAccountService.loadPGNodal(pgNodalRequest));
	}

	@Audited(context = "CORPACCOUNT", searchId = "getCorpAccountBalanceRequest.accountId",viewable=0 )
	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")
	@RequestMapping( value = CorpAccountConstants.GET_CORP_ACCOUNT_BALANCE, method = RequestMethod.POST )
	public @ResponseBody GenericResponse getCorpAccountBalance( @RequestBody @Valid GetCorpAccountBalanceRequest getCorpAccountBalanceRequest, BindingResult bindingResult ) throws Exception {
		GenericControllerUtils.checkBindingResult( bindingResult , "getCorpAccountBalanceRequest in CorpAccountController" );
		return GenericControllerUtils.getGenericResponse( corpAccountService.getCorpAccountBalance( getCorpAccountBalanceRequest ) );
	}

	@Audited(context = "CORPACCOUNT", searchId = "request.transactionReference" )
	@PreAuthorize("(hasPermission('OPS_CORP_SETTLEMENT'))")
	@RequestMapping(value = "/corpSettleBalance", method = RequestMethod.POST)
	public @ResponseBody GenericResponse corpSettleBalance(@RequestBody @Valid SettleCorpAccountRequest request,
			BindingResult bindingResult)
			throws WalletServiceException, OpsPanelException {
		GenericControllerUtils.checkBindingResult( bindingResult , "corpSettleBalance in CorpAccountController" );
		CorpSettlementResponse response = new CorpSettlementResponse();
		try {

			SettleCorpAccountResponse corpSettlementResponse = sdMoneyClient.settleCorpAccountBalance(request);
			response.setIdempotencyId( request.getIdempotencyId() );
			response.setTransactionRefrence( request.getTransactionReference() );
			response.setTransactionId(corpSettlementResponse.getTransactionId());
			response.setTransactionTimeStamp(corpSettlementResponse.getTransactionTimeStamp());

		} catch (SDMoneyException sdme) {
			log.info("SDMoneyException in corpsettlebalance " + sdme);
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode().getErrorCode()), sdme.getMessage());

		}
		return GenericControllerUtils.getGenericResponse(response);
	}

	@Audited(context = "CORPACCOUNT", searchId = "request.merchantId" )
	@PreAuthorize("(hasPermission('OPS_CORP_SETTLEMENT'))")
	@RequestMapping(value = "/getCorpsettleAccountsForEntityAndType", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCorpSettleAccountsForEntityAndType(
			@RequestBody GetCorpAccountsForEntityAndTypeRequest request)
					throws WalletServiceException, OpsPanelException {
		List<CorpAccount> response;

		try {
			List<String> corpIdList = sdMoneyClient.getCorpAccountsForEntityAndType(request);

			GetCorpAccountsForEntityRequest getCorpAccountsForEntityRequest = new GetCorpAccountsForEntityRequest();
			getCorpAccountsForEntityRequest.setBusinessEntity(request.getMerchantId());

			List<CorpAccount> corpAccounts = sdMoneyClient.getCorpAccountsForEntity(getCorpAccountsForEntityRequest);

			response = corpAccountService.mapCorpAccountsForEntity(corpAccounts, corpIdList);

		} catch (SDMoneyException sdme) {
			log.info("SDMoneyException in getCorpSettleAccountsForEntityAndType " + sdme);
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode().getErrorCode()), sdme.getMessage());

		}
		return GenericControllerUtils.getGenericResponse(response);
	}

	@Audited(context = "CORPACCOUNT", searchId = "request.accountId" )
	@PreAuthorize("(hasPermission('OPS_CORP_SETTLEMENT'))")
	@RequestMapping(value = "/getCorpSettleBalanceOnTimestamp", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCorpSettleBalanceOnTimestamp(
			@RequestBody GetCorpAccountBalanceOnTimestampRequest request) throws WalletServiceException {
		GetCorpAccountBalanceResponse response;
		try {
			response = sdMoneyClient.getCorpAccountBalanceOnTimestamp(request);
		} catch (SDMoneyException sdme) {
			log.info("SDMoneyException in getCorpSettleBalanceOnTimestamp " + sdme);
			throw new WalletServiceException(String.valueOf(sdme.getErrorCode().getErrorCode()), sdme.getMessage());

		}
		return GenericControllerUtils.getGenericResponse(response);
	}


	@Audited(context = "CORPACCOUNT", searchId = "loadMerchantNodalRequest.merchantId",viewable=0 )
	@PreAuthorize( "(hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD_NODAL'))" )
	@RequestMapping( value = "/loadMerchantNodal", method = RequestMethod.POST )
	public @ResponseBody GenericResponse loadMerchantNodal(
			@RequestBody LoadMerchantNodalRequest loadMerchantNodalRequest,
			BindingResult bindingResult ) throws OpsPanelException {
		 	GenericControllerUtils.checkBindingResult( bindingResult, "loadMerchantNodal in CorpAccountController" );
			return GenericControllerUtils.getGenericResponse( corpAccountService.loadMerchantNodal( loadMerchantNodalRequest ) );
	}
}
