package com.snapdeal.payments.view.utils.clients;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.client.utils.ClientDetails;
import com.snapdeal.payments.sdmoney.exceptions.NoSuchMerchantException;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementAccountTransactionsActivityRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementAccountTransactionsActivityResponse;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementCorpAccountBalanceResponse;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantUnsettledAmount;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalUnsettledAmountRespnse;

@Slf4j
@Component
public class SDmoneyClientUtil {
	private SDMoneyClient sdmoneyClient;

	@Value("${com.snapdeal.payments.sdmoney.url}")
	private String serviceHostUrl;

	@Value("${com.snapdeal.payments.sdmoney.clientkey}")
	private String clientKey;

	@Value("${com.snapdeal.payments.sdmoney.clientname}")
	private String clientName;

	@Value("${com.snapdeal.payments.sdmoney.xAuthToken}")
	private String xAuthToken;

	@Value("${com.snapdeal.payments.sdmoney.requestTimeOut}")
	private int requestTimeOut;

	@PostConstruct
	public void setup() throws Exception {
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setClientKey(clientKey);
		clientDetails.setClientName(clientName);
		clientDetails.setUrl(serviceHostUrl);
		clientDetails.setXAuthToken(xAuthToken);
		clientDetails.setConnectTimeout(requestTimeOut);
		sdmoneyClient = new SDMoneyClient(clientDetails);
	}

	public GetMerchantSettlementAccountTransactionsActivityResponse getTransactionFromWallet(
			GetMerchantSettlementAccountTransactionsActivityRequest request) {

		GetMerchantSettlementAccountTransactionsActivityResponse response = null;
		try {
			response = sdmoneyClient
					.getMerchantSettlementAccountTransactions(request);
		} catch (Throwable th) {
			throw new PaymentsViewServiceException(
					PaymentsViewServiceExceptionCodes.SDMONEY_EXCEPTION
							.errCode(),
					th.getMessage(), ExceptionType.SDMONEY_EXCEPTION);
		}
		return response;
	}

	public GetTotalUnsettledAmountRespnse getUnsettleAmount(
			GetMerchantUnsettledAmount request) {
		GetMerchantSettlementCorpAccountBalanceResponse response = null;
		try {
			GetMerchantSettlementCorpAccountBalanceRequest sdRequest = new GetMerchantSettlementCorpAccountBalanceRequest();
			sdRequest.setMerchantId(request.getMerchantId());
			response = sdmoneyClient
					.getMerchantSettlementCorpAccountBalance(sdRequest);
		} catch(NoSuchMerchantException ex){
			log.error("Exception when called sdmoney: " + ex.getMessage());
			throw new PaymentsViewServiceException(
					PaymentsViewServiceExceptionCodes.SDMONEY_EXCEPTION
							.errCode(),
							ex.getMessage(), ExceptionType.SDMONEY_EXCEPTION);
		}catch (Throwable th) {
			log.error("Exception when called sdmoney: " + th.getMessage());
			throw new PaymentsViewServiceException(
					PaymentsViewServiceExceptionCodes.SDMONEY_EXCEPTION
							.errCode(),
							PaymentsViewServiceExceptionCodes.SDMONEY_EXCEPTION.errMsg(), ExceptionType.SDMONEY_EXCEPTION);
		}
		return new GetTotalUnsettledAmountRespnse(response.getBalance());
	}
}
