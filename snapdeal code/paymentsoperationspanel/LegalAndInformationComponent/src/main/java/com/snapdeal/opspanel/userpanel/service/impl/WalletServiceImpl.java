package com.snapdeal.opspanel.userpanel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.FraudReverseLoadMoneyRequest;
import com.snapdeal.opspanel.userpanel.service.WalletService;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.EnableSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusResponse;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.SuspendSDMoneyAccountRequest;

import lombok.extern.slf4j.Slf4j;

@Service("WalletService")
@Slf4j
public class WalletServiceImpl implements WalletService {

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Override
	public GetTransactionByIdResponse getTransactionById(GetTransactionByIdRequest getTransactionByIdRequest)
			throws InfoPanelException {
		try {
			return sdMoneyClient.getTransactionById(getTransactionByIdRequest);
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting transaction by id " + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting transaction by Id","SDMoneyClient");
		}
	}

	@Override
	public GetTransactionsForUserResponse getTransactionsForUser(
			GetTransactionsForUserRequest getTransactionsForUserRequest) throws InfoPanelException {
		try {
			return sdMoneyClient.getTransactionsForUser(getTransactionsForUserRequest);
		} catch (SDMoneyException sdMoneyException) {
			log.info("Exception from SDMoneyClient while getting transactions for user" + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting transaction for user","SDMoneyClient");
		}
	}

	@Override
	public void suspendSDMoneyAccount(SuspendSDMoneyAccountRequest suspendSDMoneyAccountRequest)
			throws InfoPanelException {
		try {
			sdMoneyClient.suspendSDMoneyAccount(suspendSDMoneyAccountRequest);
		} catch (SDMoneyException sdMoneyException) {
			log.info("Exception from SDMoneyClient while suspending wallet " + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),"SDMoneyClient");
		}
	}

	@Override
	public GetSDMoneyAccountResponse getSDMoneyAccount(GetSDMoneyAccountRequest getSDMoneyAccountRequest)
			throws InfoPanelException {
		try {
			return sdMoneyClient.getSDMoneyAccount(getSDMoneyAccountRequest);
		} catch (SDMoneyException sdMoneyException) {
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting SD Money Account","SDMoneyClient");
		}
	}

	@Override
	public GetMoneyOutStatusResponse getMoneyOutStatus(GetMoneyOutStatusRequest getMoneyOutStatusRequest)
			throws InfoPanelException {
		try {
			return sdMoneyClient.getMoneyOutStatus(getMoneyOutStatusRequest);
		} catch (SDMoneyException sdMoneyException) {
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting SD Money Out Status","SDMoneyClient");
		}
	}

	@Override
	public GetTransactionsByReferenceResponse getTransactionsByReference(
			GetTransactionsByReferenceRequest getTransactionsByReferenceRequest) throws InfoPanelException {
		try {
			return sdMoneyClient.getTransactionByReference(getTransactionsByReferenceRequest);
		} catch (SDMoneyException sdMoneyException) {
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting Transactions by reference","SDMoneyClient");
		}
	}

	@Override
	public ReverseLoadMoneyResponse reverseLoadMoney(FraudReverseLoadMoneyRequest fraudReverseLoadMoneyRequest)
			throws InfoPanelException {

		ReverseLoadMoneyResponse reverseLoadMoneyResponse = null;

		ReverseLoadMoneyRequest reverseLoadMoneyRequest = new ReverseLoadMoneyRequest();
		reverseLoadMoneyRequest.setPrevTransactionId(fraudReverseLoadMoneyRequest.getTransactionId());
		reverseLoadMoneyRequest.setReverseReason(fraudReverseLoadMoneyRequest.getReason());

		try {

			log.info("reversing loadmoney for transactionId=" + fraudReverseLoadMoneyRequest.getTransactionId());

			reverseLoadMoneyResponse = sdMoneyClient.reverseLoadMoney(reverseLoadMoneyRequest);

		} catch (SDMoneyException e) {

			log.error("Exception while reversing loadmoney " + e.getMessage());

			throw new InfoPanelException("ET-11028", "Exception while reversing loadmoney :" +e.getErrorCode()+" "+e.getMessage(),"SDMoneyClient");

		}

		log.info("loadmoney reversed for transactionId=" + fraudReverseLoadMoneyRequest.getTransactionId()
				+ " with amount " + reverseLoadMoneyResponse.getReversedAmount());

		return reverseLoadMoneyResponse;
	}

	@Override
	public void enableWallet(EnableSDMoneyAccountRequest enableSDMoneyAccountRequest) throws InfoPanelException {

		try {

			sdMoneyClient.enableSDMoneyAccount(enableSDMoneyAccountRequest);

		} catch (SDMoneyException e) {

			log.error("exception occured while enabling wallet :"+e.getMessage() + enableSDMoneyAccountRequest.getSdIdentity());

			throw new InfoPanelException("ET-110044", e.getMessage(),"SDMoneyClient");

		}
	}

}
