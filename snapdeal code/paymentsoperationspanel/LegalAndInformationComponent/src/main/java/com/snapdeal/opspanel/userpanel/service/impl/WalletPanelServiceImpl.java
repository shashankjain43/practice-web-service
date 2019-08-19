package com.snapdeal.opspanel.userpanel.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.snapdeal.opspanel.userpanel.constants.OneCheckConstants;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.SearchTxnResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;
import com.snapdeal.opspanel.userpanel.service.WalletPanelService;
import com.snapdeal.opspanel.userpanel.service.WalletService;
import com.snapdeal.opspanel.userpanel.utils.CheckSumCalculator;
import com.snapdeal.payments.sdmoney.client.BankDetailsStoreClient;
import com.snapdeal.payments.sdmoney.client.utils.ValidResponseEnum;
import com.snapdeal.payments.sdmoney.service.model.Balance;
import com.snapdeal.payments.sdmoney.service.model.EnableSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsResponse;
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
import com.snapdeal.payments.sdmoney.service.model.SuspendSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.Transaction;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
import com.snapdeal.payments.sdmoney.service.model.type.TransactionType;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("WalletPanelService")
@Slf4j
public class WalletPanelServiceImpl implements WalletPanelService {

	@Autowired
	WalletService walletService;

	@Autowired
	BankDetailsStoreClient bankDetailsStoreClient;

	// ---------------------------
	// For checksum calculator
	@Value("${checksum.clientid}")
	String checksumClientId;

	@Value("${checksum.clientkey}")
	String checksumClientKey;
	// ---------------------------

	// ---------------------------
	// hitting url for fcloadingtransaction
	@Value("${fcloadingtransaction.url}")
	String fcLoadingTransactionUrl;
	// ---------------------------

	@Override
	public SearchTxnResponse getTransactionsForUser(SearchTransactionRequest request) throws InfoPanelException {
		if (request.getEmailId() == null) {
			return null;
		}
		// TODO: Implement pagination
		GetTransactionsForUserRequest getTransactionsForUserRequest = new GetTransactionsForUserRequest();
		getTransactionsForUserRequest.setPageSize(10);
		getTransactionsForUserRequest.setUserId(request.getEmailId());
		if (request.getTransactionStartDate() != null) {
			getTransactionsForUserRequest.setStartTime(request.getTransactionStartDate());
		}
		if (request.getTransactionEndDate() != null) {
			getTransactionsForUserRequest.setEndTime(request.getTransactionEndDate());
		}
		if(request.getLastEvaluatedKey() !=  null){
			getTransactionsForUserRequest.setLastEvaluatedKey(request.getLastEvaluatedKey());
		}
		GetTransactionsForUserResponse getTransactionsForUserResponse = walletService
				.getTransactionsForUser(getTransactionsForUserRequest);
		List<TransactionDetails> transactionDetailsList = getTransactionsForUserResponseToTransactionDetailsList(
				getTransactionsForUserResponse);
		for (TransactionDetails transactionDetails : transactionDetailsList) {
			transactionDetails.setEmailId(request.getEmailId());
		}
		SearchTxnResponse searchTxnResponse = new SearchTxnResponse();
		searchTxnResponse.setLastEvaluatedKey(getTransactionsForUserResponse.getLastEvaluatedKey());
		searchTxnResponse.setTransactionDetailsList(transactionDetailsList);
		return searchTxnResponse;
	}

	@Override
	public List<TransactionDetails> getTransactionsById(SearchTransactionRequest request) throws InfoPanelException {
		if (request.getWalletTransactionId() == null) {
			return null;
		}
		GetTransactionByIdRequest getTransactionByIdRequest = new GetTransactionByIdRequest();
		getTransactionByIdRequest.setTransactionId(request.getWalletTransactionId());
		GetTransactionByIdResponse getTransactionByIdResponse = walletService
				.getTransactionById(getTransactionByIdRequest);
		return getTransactionByIdResponseToTransactionDetailsList(getTransactionByIdResponse);
	}

	@Override
	public void suspendSDMoneyAccount(SuspendWalletRequest request) throws InfoPanelException {

		if (request.getUserId() == null || request.getUserIdType() == null
				|| !request.getUserIdType().equals(UserPanelIdType.IMS_ID)) {
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		SuspendSDMoneyAccountRequest suspendSDMoneyAccountRequest = new SuspendSDMoneyAccountRequest();
		suspendSDMoneyAccountRequest.setSdIdentity(request.getUserId());
		walletService.suspendSDMoneyAccount(suspendSDMoneyAccountRequest);

	}

	@Override
	public JSONObject makeHttpCallOnFcLoadingTransaction(String transactionId) throws InfoPanelException {

		// --------------------------
		HttpClient httpClient = HttpClients.createDefault();
		// --------------------------

		// --------------------------
		// JSON response
		JSONObject response = null;
		// --------------------------

		// ---------------------------
		String parameters = "?transactionid=" + transactionId;
		String completeUrl = fcLoadingTransactionUrl + parameters;
		// --------------------------

		// --------------------------
		InetAddress ip;
		String hostName = null;
		String hostIpAddress = null;
		String hostLoggedUserName = null;
		String requestId = null;
		long timeStamp = System.currentTimeMillis();

		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.info("Unknown Host Exception while getting local host " + e);
			throw new InfoPanelException("MT-5504", e.getMessage());
		}

		hostName = ip.getHostName();
		hostIpAddress = ip.getHostAddress();
		hostLoggedUserName = System.getenv().get("LOGNAME");
		requestId = UUID.randomUUID().toString();

		// --------------------------

		// --------------------------
		// Making a Http Post Request
		HttpGet httpGet = new HttpGet(completeUrl);

		httpGet.addHeader("content-type", "application/json");
		httpGet.addHeader("accept", "application/json");
		httpGet.addHeader("AppRequestId", requestId);
		httpGet.addHeader("checksum",
				CheckSumCalculator.generateSHA256CheckSum(checksumClientId + checksumClientKey + transactionId));
		httpGet.addHeader("clientid", checksumClientId);
		// --------------------------

		// --------------------------
		// Getting the results
		HttpResponse result = null;
		try {
			result = httpClient.execute(httpGet);
			if ((result != null) && (result.getStatusLine() != null)) {
				Integer statusCode = result.getStatusLine().getStatusCode();
				String json = EntityUtils.toString(result.getEntity());
				com.fasterxml.jackson.databind.ObjectMapper mapper2 = new com.fasterxml.jackson.databind.ObjectMapper();
				mapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				if ((statusCode == ValidResponseEnum.OK.getValue())
						|| (statusCode == ValidResponseEnum.CREATED.getValue())) {
					try {
						response = new JSONObject(json);
					} catch (JSONException e) {
						log.info("JSON Exception while getting response from one check core" + e);
						throw new InfoPanelException("MT-5503", e.getMessage());
					}
				}
			}
		} catch (ClientProtocolException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.info("Client protocol exception while communicating with one check core " + sw.toString());
			throw new InfoPanelException("MT-5502", e.getMessage());
		} catch (IOException e) {
			log.info("IO Exception while communicating with one check core " + e);
			throw new InfoPanelException("MT-5501", e.getMessage());
		}
		// --------------------------

		return response;
	}

	private List<TransactionDetails> getTransactionOnFailedFcLoadingTransaction(String transactionId)
			throws InfoPanelException {

		// --------------------------
		// Getting response on freecharge loading
		JSONObject response = makeHttpCallOnFcLoadingTransaction(transactionId);
		// --------------------------

		// --------------------------
		// Extracting all the data from json response
		List<TransactionDetails> listOfTransactionDetails = null;

		try {
			// ----------------------
			// On Transaction not found
			if (!response.isNull("error"))
				return null;
			// ----------------------

			JSONObject data = response.getJSONObject("data");

			// ----------------------
			// On Transaction Failed or Vaild
			JSONArray details = data.getJSONArray("details");
			String status = details.getJSONObject(0).getString("status");
			String failedMessage = "";

			if ( !status.equalsIgnoreCase( OneCheckConstants.LOAD_MONEY_DONE ) &&
					!status.equalsIgnoreCase( OneCheckConstants.MONEY_ALREADY_LOADED ) ) {
				// On Transaction Failed
				failedMessage = status;
			} else {
				return null; // On Successfully Load
			}
			// ----------------------

			listOfTransactionDetails = new ArrayList<TransactionDetails>();
			TransactionDetails transactionDetail = new TransactionDetails();
			transactionDetail.setTransactionReference(transactionId);
			transactionDetail.setTransactionStatus(failedMessage);
			listOfTransactionDetails.add(transactionDetail);

		} catch (JSONException e) {
			log.info("JSON exception while reading response from one check core " + e);
			throw new InfoPanelException("MT-5500", e.getMessage());
		}
		// --------------------------

		return listOfTransactionDetails;
	}

	@Override
	public List<TransactionDetails> getTransactionByReference(SearchTransactionRequest request)
			throws InfoPanelException {

		GetTransactionsByReferenceRequest getTransactionsByReferenceRequest = new GetTransactionsByReferenceRequest();

		String transactionId;
		if ((transactionId = request.getFcLoadingTransactionId()) != null) {
			List<TransactionDetails> listoftransactiondetails = getTransactionOnFailedFcLoadingTransaction(
					transactionId);
			if (listoftransactiondetails != null)
				return listoftransactiondetails;
		}

		String transactionReference = null;
		if (request.getFcLoadingTransactionId() != null) {
			transactionReference = request.getFcLoadingTransactionId();
		} else if (request.getFcPaymentTransactionId() != null) {
			if (transactionReference == null) {
				transactionReference = request.getFcPaymentTransactionId();
			} else if (!transactionReference.equals(request.getFcPaymentTransactionId())) {
				return null;
			}
		} else if (request.getFcWithdrawlTransactionId() != null) {
			if (transactionReference == null) {
				transactionReference = request.getFcWithdrawlTransactionId();
			} else if (!transactionReference.equals(request.getFcWithdrawlTransactionId())) {
				return null;
			}
		} else if (request.getSdPaymentOrderId() != null) {
			if (transactionReference == null) {
				transactionReference = request.getSdPaymentOrderId();
			} else if (!transactionReference.equals(request.getSdPaymentOrderId())) {
				return null;
			}
		}
		getTransactionsByReferenceRequest.setTransactionReference(transactionReference);

		GetTransactionsByReferenceResponse getTransactionByReferenceResponse = walletService
				.getTransactionsByReference(getTransactionsByReferenceRequest);

		return getTransactionsByReferenceResponseToTransactionDetailsList(getTransactionByReferenceResponse);

	}

	private List<TransactionDetails> getTransactionsForUserResponseToTransactionDetailsList(
			GetTransactionsForUserResponse getTransactionsForUserResponse) throws InfoPanelException {
		return transactionSummaryListToTransactionDetailsList(getTransactionsForUserResponse.getListTransaction());
	}

	private List<TransactionDetails> getTransactionByIdResponseToTransactionDetailsList(
			GetTransactionByIdResponse getTransactionByIdResponse) throws InfoPanelException {

		List<Transaction> transactionList = getTransactionByIdResponse.getTransactions();
		List<TransactionDetails> transactionDetailsList = new ArrayList<TransactionDetails>();

		GetSDMoneyAccountRequest getSDMoneyAccountRequest = new GetSDMoneyAccountRequest();

		for (Transaction transaction : transactionList) {

			TransactionDetails transactionDetails = new TransactionDetails();

			String walletAccountStatus;

			getSDMoneyAccountRequest.setSdIdentity(transaction.getUserId());
			try {
				GetSDMoneyAccountResponse getSDMoneyAccountResponse = walletService
						.getSDMoneyAccount(getSDMoneyAccountRequest);
				walletAccountStatus = getSDMoneyAccountResponse.getSdMoneyAccountStatus().toString();
			} catch( Exception e ) {
				log.info( "Exception while fetching sd money account status for sdId " + transaction.getUserId() + " : " + ExceptionUtils.getFullStackTrace( e ) );
				walletAccountStatus = "Could not fetch wallet account status";
			}

			transactionDetails.setUserId(transaction.getUserId());
			transactionDetails.setWalletTransactionId(transaction.getTransactionId());
			transactionDetails.setTransactionAmount(transaction.getBalanceChange());
			transactionDetails.setCumulativeBalance(transaction.getRunningBalance());
			transactionDetails.setTransactionReference(transaction.getTransactionReference());
			transactionDetails.setMetaData(transaction.getEventContext());
			transactionDetails.setTransactionDate(transaction.getTimestamp());
			transactionDetails.setWalletTransactionType(transaction.getType().toString());
			transactionDetails.setMerchantName(transaction.getBussinessEntity());
			transactionDetails.setWalletAccountStatus(walletAccountStatus);

			if (transaction.getBalanceChange().getGeneralBalance().compareTo(BigDecimal.ZERO) == 0) {
				transactionDetails.setInstrumentType("GiftVoucher");
			} else if (transaction.getBalanceChange().getVoucherBalance().compareTo(BigDecimal.ZERO) == 0) {
				transactionDetails.setInstrumentType("WalletBalance");
			} else {
				transactionDetails.setInstrumentType("GiftVoucher & WalletBalance");
			}

			if( transaction.getType() == TransactionType.CREDIT_LOAD ) {
				HashMap<String,String> map = new Gson().fromJson( transaction.getEventContext(), new TypeToken<HashMap<String, String>>(){}.getType());
				transactionDetails.setCardHash( map.get( "cardHash" ) );
			}

			if (transaction.getType() == TransactionType.DEBIT_WITHDRAW) {
				try {
					GetMoneyOutStatusRequest getMoneyOutStatusRequest = new GetMoneyOutStatusRequest();
	
					getSDMoneyAccountRequest.setSdIdentity(transactionDetails.getUserId());
					getMoneyOutStatusRequest.setTransactionRef(transaction.getTransactionReference());
					GetMoneyOutStatusResponse getMoneyOutStatusResponse = walletService
							.getMoneyOutStatus(getMoneyOutStatusRequest);
					transactionDetails.setTransactionStatus(getMoneyOutStatusResponse.getStatus().toString());
					transactionDetails.setReason( getMoneyOutStatusResponse.getReason() );
					String bankAccountToken = getMoneyOutStatusResponse.getAccountToken();

					try {
						GetBankDetailsRequest getBankDetailsRequest = new GetBankDetailsRequest();
						getBankDetailsRequest.setAccountToken( bankAccountToken );
						GetBankDetailsResponse getBankDetailsResponse = bankDetailsStoreClient.getBankDetails(getBankDetailsRequest);
						transactionDetails.setShortName( getBankDetailsResponse.getAccountDetails().getShortName() );
						transactionDetails.setAccountNumber( getBankDetailsResponse.getAccountDetails().getAccountNumber() );
						transactionDetails.setBankName( getBankDetailsResponse.getAccountDetails().getBankName() );
						transactionDetails.setIfsc( getBankDetailsResponse.getAccountDetails().getIfsc() );
					} catch( Exception e ) {
						log.info( "Exception while getting bank details: " + ExceptionUtils.getFullStackTrace( e ) );
						transactionDetails.setShortName( "Could Not Fetch" );
						transactionDetails.setAccountNumber( "Could Not Fetch");
						transactionDetails.setBankName( "Could Not Fetch" );
						transactionDetails.setIfsc( "Could Not Fetch" );
					}
				} catch( Exception e ) {
					log.info( "Exception while getting sdmoney account status: " + ExceptionUtils.getFullStackTrace( e ) );
					transactionDetails.setTransactionStatus( "Could not fetch details. " );
				}
			}

			transactionDetailsList.add(transactionDetails);

		}

		return transactionDetailsList;

	}

	private List<TransactionDetails> getTransactionsByReferenceResponseToTransactionDetailsList(
			GetTransactionsByReferenceResponse getTransactionsByReferenceResponse) throws InfoPanelException {
		return transactionSummaryListToTransactionDetailsList(getTransactionsByReferenceResponse.getListTransaction());
	}

	private List<TransactionDetails> transactionSummaryListToTransactionDetailsList(
			List<TransactionSummary> transactionSummaryList) throws InfoPanelException {
		List<TransactionDetails> transactionDetailsList = new ArrayList<TransactionDetails>();

		//List<String> iterationList= new ArrayList<String>();
		Set<String> uniqueTransactionIdList = new LinkedHashSet<String>();

		HashMap<String,TransactionSummary> transactionMap= new HashMap<String,TransactionSummary>();
		
		for (Iterator<TransactionSummary> txnSummaryIterator = transactionSummaryList.iterator(); txnSummaryIterator.hasNext();) {
			TransactionSummary transactionSummary = txnSummaryIterator.next();
//			if(!uniqueTransactionIdList.contains(transactionSummary.getTransactionId()))
//			{
//				iterationList.add(transactionSummary.getTransactionId());
//			}

			// keeping only unique transaction summary objects.
			if( uniqueTransactionIdList.contains( transactionSummary.getTransactionId() ) ) {
				txnSummaryIterator.remove();
			}
			transactionMap.put(transactionSummary.getTransactionId(), transactionSummary);
			uniqueTransactionIdList.add(transactionSummary.getTransactionId());
		}


		// Since Cumulative balance or running balance is not given separate for wallet and
		// voucher. Therefore, calling getTransactionById for each transaction summary.

//		for( Iterator<String> iterator = iterationList.iterator(); iterator.hasNext(); ) {
		for( Iterator<String> iterator = uniqueTransactionIdList.iterator(); iterator.hasNext(); ) {

			String transactionId = iterator.next();
			SearchTransactionRequest request = new SearchTransactionRequest();
			if (transactionId == null) {
				continue;
			}
			try {
				request.setWalletTransactionId(transactionId);
				List<TransactionDetails> transactionDetailsById = getTransactionsById(request);
				for (TransactionDetails transactionDetails : transactionDetailsById) {
					transactionDetails.setWalletTransactionId(transactionId);
					Balance balanceObject=transactionDetails.getCumulativeBalance();
					balanceObject.setTotalBalance(transactionMap.get(transactionId).getRunningBalance());
					transactionDetails.setCumulativeBalance(balanceObject);
				}
				transactionDetailsList.addAll(transactionDetailsById);
				iterator.remove();
			} catch( Exception e ) {
				log.info( "Exception occurred while getting transaction by id " + transactionId + " : " + ExceptionUtils.getFullStackTrace(e));
			}

		}

		// Now store the results where getTransactionById failed

		for( TransactionSummary transactionSummary : transactionSummaryList ) {
			if( uniqueTransactionIdList.contains( transactionSummary.getTransactionId() ) ) {

				TransactionDetails transactionDetails = new TransactionDetails();
				transactionDetails.setWalletTransactionId( transactionSummary.getTransactionId() );
				transactionDetails.setTransactionReference( transactionSummary.getTransactionReference() );
				transactionDetails.setTransactionDate( transactionSummary.getTimestamp() );
				transactionDetails.setWalletTransactionType( transactionSummary.getTransactionType().toString() );
				transactionDetails.setInstrumentType( "Could not fetch" );
				transactionDetails.setMerchantName(transactionSummary.getBussinessEntity() );
				transactionDetails.setWalletAccountStatus( "Could not fetch" );
				transactionDetails.setTransactionStatus( "Could not fetch" );
				transactionDetails.setUserId( "Could not fetch" );
				Balance balance = new Balance();
				balance.setTotalBalance(transactionSummary.getRunningBalance());
				transactionDetails.setCumulativeBalance(balance);
				if( transactionSummary.getBusinessTransactionType().equals(TransactionType.CREDIT_LOAD) ) {
					HashMap<String,String> map = new Gson().fromJson( transactionSummary.getEventContext(), new TypeToken<HashMap<String, String>>(){}.getType());
					transactionDetails.setCardHash( map.get( "cardHash" ) );
				}
				transactionDetailsList.add( transactionDetails );

				transactionDetailsList.add( transactionDetails );
			}
		}

		return transactionDetailsList;
	}

	@Override
	public void enableSDMoneyAccount(EnableWalletRequest request) throws InfoPanelException {

		if (request.getUserId() == null || request.getUserIdType() == null
				|| !request.getUserIdType().equals(UserPanelIdType.IMS_ID)) {
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		EnableSDMoneyAccountRequest enableSDMoneyAccountRequest = new EnableSDMoneyAccountRequest();
		enableSDMoneyAccountRequest.setSdIdentity(request.getUserId());
		walletService.enableWallet(enableSDMoneyAccountRequest);

	}

}
