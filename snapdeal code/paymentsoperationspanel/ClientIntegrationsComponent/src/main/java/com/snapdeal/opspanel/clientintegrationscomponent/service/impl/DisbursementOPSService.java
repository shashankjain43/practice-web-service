package com.snapdeal.opspanel.clientintegrationscomponent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.clientintegrationscomponent.exception.ClientIntegrationException;
import com.snapdeal.payments.disbursement.client.DisbursementClient;
import com.snapdeal.payments.disbursement.exceptions.DisbursementEngineException;
import com.snapdeal.payments.disbursement.model.GetBankDetailsRequest;
import com.snapdeal.payments.disbursement.model.GetBankDetailsResponse;
import com.snapdeal.payments.disbursement.model.GetBankTransactionStatusResponse;
import com.snapdeal.payments.disbursement.model.GetDisburseAutomaticReconTransactionsResponse;
import com.snapdeal.payments.disbursement.model.GetDisburseHistoryRequest;
import com.snapdeal.payments.disbursement.model.GetDisburseHistoryResponse;
import com.snapdeal.payments.disbursement.model.GetDisburseTransactionsRequest;
import com.snapdeal.payments.disbursement.model.GetDisburseTransactionsResponse;
import com.snapdeal.payments.disbursement.model.GetMerchantDisbursementDetailsRequest;
import com.snapdeal.payments.disbursement.model.GetMerchantDisbursementDetailsResponse;
import com.snapdeal.payments.disbursement.model.SetDisburseStatusRequest;

import lombok.extern.slf4j.Slf4j;

@Service("disbursementopsservice")
@Slf4j
public class DisbursementOPSService {

	@Autowired
	DisbursementClient client;

	public GetBankDetailsResponse getBankDetails(GetBankDetailsRequest request) throws ClientIntegrationException {
		GetBankDetailsResponse response;
		try {
			response = client.getBankDetails(request);
		} catch (DisbursementEngineException de) {
			log.info("Exception in get Bank Details : " + de.getStackTrace());
			throw new ClientIntegrationException(de.getErrorCode(), de.getMessage() + " Source : Disbursement");
		}
		return response;
	}

	public String setDisburseStatus(SetDisburseStatusRequest request) throws ClientIntegrationException {
		GetBankTransactionStatusResponse response;
		try {
			client.setDisburseStatus(request);
			;
		} catch (DisbursementEngineException de) {
			log.info("Exception in set disburse status response : " + de.getStackTrace());
			throw new ClientIntegrationException(de.getErrorCode(), de.getMessage() + " Source : Disbursement");
		}
		return "SUCCESS";
	}

	public GetDisburseHistoryResponse getDisburseHistory(GetDisburseHistoryRequest request)
			throws ClientIntegrationException {
		GetDisburseHistoryResponse response;
		try {
			response = client.getDisburseHistory(request);
		} catch (DisbursementEngineException de) {
			log.info("Exception in get Disburse History : " + de.getStackTrace());
			throw new ClientIntegrationException(de.getErrorCode(), de.getMessage() + " Source : Disbursement");
		}
		return response;
	}

	public GetMerchantDisbursementDetailsResponse getMerchantDisbursementDetails(
			GetMerchantDisbursementDetailsRequest request) throws ClientIntegrationException {
		GetMerchantDisbursementDetailsResponse response;
		try {
			response = client.getMerchantDisbursementDetails(request);

		} catch (DisbursementEngineException de) {
			log.info("Exception in get Disburse History : " + de.getStackTrace());
			throw new ClientIntegrationException(de.getErrorCode(), de.getMessage() + " Source : Disbursement");
		}
		return response;
	}

	public GetDisburseTransactionsResponse getDisburseAutomaticReconTransactionsResponse(
			GetDisburseTransactionsRequest request) throws ClientIntegrationException {

		GetDisburseTransactionsResponse getDisburseTransactionsResponse;

		try {

			getDisburseTransactionsResponse = client.getDisburseTransactions(request);

		} catch (DisbursementEngineException de) {
			log.info("Exception in get Disburse History : " + de.getStackTrace());
			throw new ClientIntegrationException(de.getErrorCode(), de.getMessage() + " Source : Disbursement");
		}
		return getDisburseTransactionsResponse;

	}

}
