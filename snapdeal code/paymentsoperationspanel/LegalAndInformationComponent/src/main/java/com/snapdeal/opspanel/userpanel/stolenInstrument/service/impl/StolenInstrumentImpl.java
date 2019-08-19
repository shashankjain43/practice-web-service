package com.snapdeal.opspanel.userpanel.stolenInstrument.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.bulkFraud.BulkFraudBlacklistConstatnts;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.SearchWithdrawalFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.BlackListWhiteListUserResponse;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.response.SearchWithdrawalFlowResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.opspanel.userpanel.service.impl.BlockedTransactionHistoryImpl;
import com.snapdeal.opspanel.userpanel.stolenInstrument.enums.EntityType;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.BlockRequest;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.CategorizedTxnSum;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.CategoryWiseTxnsInfo;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.DeviceDetailsInfo;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.FreechargedUser;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.MerchantTxn;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.SentMoneyRecipient;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.TransactionDetailsInfo;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.UserDetailsInfo;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.VirtualCardsTxn;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.WithdrawToBankTxn;
import com.snapdeal.opspanel.userpanel.stolenInstrument.request.SubmitRequest;
import com.snapdeal.opspanel.userpanel.stolenInstrument.response.BlockResponse;
import com.snapdeal.opspanel.userpanel.stolenInstrument.response.SubmitResponse;
import com.snapdeal.opspanel.userpanel.stolenInstrument.service.StolenInstrumentService;
import com.snapdeal.opspanel.userpanel.stolenInstrument.utils.StolenInstrumentsConstants;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.payments.fps.aht.exception.AHTException;
import com.snapdeal.payments.fps.aht.model.GetRequestDetailsRequest;
import com.snapdeal.payments.fps.aht.model.GetRequestDetailsResponse;
import com.snapdeal.payments.fps.aht.model.GetTransactionsRequest;
import com.snapdeal.payments.fps.aht.model.GetTransactionsResponse;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsResponse;
import com.snapdeal.payments.fps.aht.model.RegisterRequest;
import com.snapdeal.payments.fps.aht.model.UserTransactionDetails;
import com.snapdeal.payments.fps.aht.model.UserTransactionEntityType;
import com.snapdeal.payments.fps.client.FPSClient;
import com.snapdeal.payments.fps.entity.Device;
import com.snapdeal.payments.fps.exception.FPSException;
import com.snapdeal.payments.pms.client.ProfileManagementClient;
import com.snapdeal.payments.pms.entity.EntityStatus;
import com.snapdeal.payments.pms.exceptions.InternalClientException;
import com.snapdeal.payments.pms.exceptions.ProfileManagementException;
import com.snapdeal.payments.pms.service.model.EntityMetaInfo;
import com.snapdeal.payments.pms.service.model.GetEntityRequest;
import com.snapdeal.payments.pms.service.model.GetEntityResponse;
import com.snapdeal.payments.pms.service.model.UpdateEntityRequest;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.Balance;
import com.snapdeal.payments.sdmoney.service.model.BankAccountDetails;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserResponse;
import com.snapdeal.payments.sdmoney.service.model.Transaction;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
import com.snapdeal.payments.sdmoney.service.model.type.BusinessTransactionType;
import com.snapdeal.payments.sdmoney.service.model.type.TransactionType;

@Slf4j
@Service
public class StolenInstrumentImpl implements StolenInstrumentService {

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Autowired
	SearchUserServices searchUserServices;

	@Autowired
	FPSClient fps;

	@Autowired
	ActionService actionService;

	@Autowired
	ProfileManagementClient pms;

	public GetTransactionByIdResponse getTransactionById(GetTransactionByIdRequest getTransactionByIdRequest) throws InfoPanelException{

		GetTransactionByIdResponse getTransactionByIdResponse = new GetTransactionByIdResponse();
		try {
			getTransactionByIdResponse = sdMoneyClient.getTransactionById(getTransactionByIdRequest);
			return getTransactionByIdResponse;
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getTransactionById for txnId = " + getTransactionByIdRequest.getTransactionId() + " " + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting getTransactionById","SDMoneyClient");
		}catch (Exception e) {
			log.info("OtherException while getTransactionById for txnId = " + getTransactionByIdRequest.getTransactionId());
			log.info("Exception : [" + e.getMessage() + "]");
			throw new InfoPanelException(e.getMessage(),
					"Exception while getting getTransactionById","SDMoneyClient");

		}

	}

	public GetTransactionsByReferenceResponse getTransactionByReference(GetTransactionsByReferenceRequest getTransactionByReferenceRequest) throws InfoPanelException{

		GetTransactionsByReferenceResponse getTransactionsByReferenceResponse = new GetTransactionsByReferenceResponse();
		try {
			getTransactionsByReferenceResponse = sdMoneyClient.getTransactionByReference(getTransactionByReferenceRequest);
			return getTransactionsByReferenceResponse;
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getTransactionByReference for txnReference = " + getTransactionByReferenceRequest.getTransactionReference() + " " + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting getTransactionByReference","SDMoneyClient");
		}catch (Exception e) {
			log.info("OtherException while getTransactionByReference for txnReference = " + getTransactionByReferenceRequest.getTransactionReference());
			log.info("Exception : [" + e.getMessage() + "]");
			throw new InfoPanelException(e.getMessage(),
					"Exception while getting getTransactionByReference","SDMoneyClient");

		}

	}


	public com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse getTransactions(com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest getTransactionsRequest) {

		com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse getTransactionsResponse = new com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse();
		try {
			getTransactionsResponse = sdMoneyClient.getTransactions(getTransactionsRequest);
			return getTransactionsResponse;
		} catch (SDMoneyException sde) {
			log.info(" Exception from sdmoneyclient while getting getTransactions for userId = " + getTransactionsRequest.getSdIdentity() + " " + sde);
			return null;
		}catch (Exception e) {
			log.info("OtherException while getting getTransactions for userId = " + getTransactionsRequest.getSdIdentity());
			log.info("Exception : [" + e.getMessage() + "]");
			return null;
		}

	}

	public GetTransactionsForUserResponse getTransactionsForUser(GetTransactionsForUserRequest getTransactionsForUserRequest) throws InfoPanelException{

		GetTransactionsForUserResponse getTransactionsForUserResponse = new GetTransactionsForUserResponse();
		try {
			getTransactionsForUserResponse = sdMoneyClient.getTransactionsForUser(getTransactionsForUserRequest);
			return getTransactionsForUserResponse;
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting getTransactionsForUser for userId = " + getTransactionsForUserRequest.getUserId() + " "  + sdMoneyException);
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(),
					"Exception while getting getTransactionsForUser","SDMoneyClient");
		}catch (Exception e) {
			log.info("OtherException while getting getTransactionsForUser for userId = " + getTransactionsForUserRequest.getUserId());
			log.info("Exception : [" + e.getMessage() + "]");
			throw new InfoPanelException(e.getMessage(),
					"Exception while getting getTransactionsForUser","SDMoneyClient");

		}

	}

	public GetUserTransactionDetailsResponse getTransactionsDetails(GetUserTransactionDetailsRequest request) throws InfoPanelException{

		GetUserTransactionDetailsResponse response = new GetUserTransactionDetailsResponse();
		try {
			response = fps.getUserTransactionDetails(request);
			return response;
		} catch (AHTException ahtException) {
			log.info(" AHTException from FPS while getTransactionsDetails for userId = " + request.getEntityId() + ahtException);
			throw new InfoPanelException(ahtException.getErrorCode().toString(),
					"Exception while getTransactionsDetails","FPSClient");
		}catch (Exception e) {
			log.info("OtherException while getTransactionsDetails for userId = " + request.getEntityId());
			log.info("Exception : [" + e.getMessage() + "]");
			throw new InfoPanelException(e.getMessage(),
					"Exception while getting getTransactionsDetails","SDMoneyClient");
		}
	}

	public GetUserResponse getUser(GetUserByIdRequest request) throws InfoPanelException{
		return searchUserServices.searchUserById(request);		
	}

	public GetUserResponse getUserByMobile(GetUserByMobileRequest request) throws InfoPanelException{
		return searchUserServices.searchUserByMobile(request);	
	}
	
	
	public void getDeviceDetails(String fpsTxnId){
		
		GetRequestDetailsRequest getRequestDetailsRequest = new GetRequestDetailsRequest();
		getRequestDetailsRequest.setTransactionId(fpsTxnId);
		
		GetRequestDetailsResponse getRequestDetailsResponse = new GetRequestDetailsResponse();
		
		try {
			getRequestDetailsResponse = fps.getRegisteredRequestDetails(getRequestDetailsRequest);
		} catch (AHTException e) {
			log.info("AHTException while calling getRegisteredRequestDetails ...");
			log.info("AHTException : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
		} catch (Exception e) {
			log.info("OtherException while calling getRegisteredRequestDetails ...");
			log.info("Exception : [" + e.getMessage() + "]");
		}
		
		if(getRequestDetailsResponse != null){
			if(getRequestDetailsResponse.getListOfRegisteredRequest() != null && getRequestDetailsResponse.getListOfRegisteredRequest().size() != 0){
				RegisterRequest registerRequest = new RegisterRequest();
				registerRequest = getRequestDetailsResponse.getListOfRegisteredRequest().get(0);
				
				if(registerRequest != null){
					String requestParam = registerRequest.getRequestParam();
					
				}else {
					log.info("registerRequest[0] = NULL inside getDeviceDetails!");
				}
			}else {
				log.info("getRequestDetailsResponse.getListOfRegisteredRequest() = NULL OR empty inside getDeviceDetails!");
			}
		} else {
			log.info("getRequestDetailsResponse = NULL inside getDeviceDetails!");
		}
	}

	@Override
	public UserDeviceTransactionResponse getUserAndDeviceDetailsForTransaction(String transactionId) throws InfoPanelException, OpsPanelException{

		UserDeviceTransactionResponse userDeviceTransactionResponse = new UserDeviceTransactionResponse();

		GetTransactionByIdRequest getTransactionByIdRequest = new GetTransactionByIdRequest();
		getTransactionByIdRequest.setTransactionId(transactionId);

		GetTransactionByIdResponse getTransactionByIdResponse = new GetTransactionByIdResponse();

		TransactionDetailsInfo transactionDetailsInfo =  new TransactionDetailsInfo();

		try {
			getTransactionByIdResponse = getTransactionById(getTransactionByIdRequest);
			transactionDetailsInfo.setError(null);
			transactionDetailsInfo.setTransactionDetails(getTransactionByIdResponse);
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getTransactionById for txnId = " + getTransactionByIdRequest.getTransactionId());
			transactionDetailsInfo.setError(e.getErrCode() + " : " + e.getErrMessage());
			transactionDetailsInfo.setTransactionDetails(null);
		} catch (Exception e) {
			log.info("OtherException while getTransactionById for txnId = " + getTransactionByIdRequest.getTransactionId());
			log.info("Exception : [" + e.getMessage() + "]");
			transactionDetailsInfo.setError(e.getMessage());
			transactionDetailsInfo.setTransactionDetails(null);

		}

		userDeviceTransactionResponse.setTransactionDetailsInfo(transactionDetailsInfo);

		String userId = null;

		Date createdDate = null;

		String idempotancyId = null;

		if(getTransactionByIdResponse != null){
			List<Transaction> allTxns = new ArrayList<Transaction>();
			allTxns  = getTransactionByIdResponse.getTransactions();
			if(allTxns != null && allTxns.size() != 0){
				if(allTxns.get(0) != null){
					userId = allTxns.get(0).getUserId();
					createdDate = allTxns.get(0).getTimestamp();
					idempotancyId = allTxns.get(0).getIdempotencyId();
				}else {
					log.info("allTxns.get(0) inside getUserAndDeviceDetailsForTransaction!");
				}
			}else {
				log.info("allTxns = NULL or EMPTY inside getUserAndDeviceDetailsForTransaction!");
			}
		} else {
			log.info("getTransactionByIdResponse = NULL inside getUserAndDeviceDetailsForTransaction!");
		}

		GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
		getUserByIdRequest.setUserId(userId);

		GetUserResponse getUserResponse = new GetUserResponse();

		UserDetailsInfo userDetailsInfo = new UserDetailsInfo();

		try {
			getUserResponse = getUser(getUserByIdRequest);
			userDetailsInfo.setUserDetails(getUserResponse);
			userDetailsInfo.setError(null);
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getUser for userId = " + userId);
			userDetailsInfo.setUserDetails(null);
			userDetailsInfo.setError(e.getErrCode() + " : " + e.getErrMessage());	
		} catch (Exception e) {
			log.info("OtherException while calling getUser ...");
			log.info("Exception : [" + e.getMessage() + "]");
			userDetailsInfo.setUserDetails(null);
			userDetailsInfo.setError(e.getMessage());

		}

		userDeviceTransactionResponse.setUserDetailsInfo(userDetailsInfo);

		GetUserTransactionDetailsRequest getUserTransactionDetailsRequest = new GetUserTransactionDetailsRequest();
		getUserTransactionDetailsRequest.setEntityId(userId);
		getUserTransactionDetailsRequest.setEntityType(UserTransactionEntityType.USER_ID);
		/*getUserTransactionDetailsRequest.setStartDate(createdDate);
		getUserTransactionDetailsRequest.setEndDate(createdDate);*/

		boolean userTxnFromFPSFound = false;
		GetUserTransactionDetailsResponse getUserTransactionDetailsResponse =  new GetUserTransactionDetailsResponse();

		DeviceDetailsInfo deviceDetailsInfo =  new DeviceDetailsInfo();



		try {
			getUserTransactionDetailsResponse = getTransactionsDetails(getUserTransactionDetailsRequest);
			userTxnFromFPSFound = true;
		} catch (AHTException e) {
			log.info("AHTException while calling getUserTransactionDetails ...");
			log.info("AHTException : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			deviceDetailsInfo.setDeviceInfo(null);
			deviceDetailsInfo.setError(e.getErrorCode().getErrorCode() + e.getMessage());
		} catch (Exception e) {
			log.info("OtherException while calling getUserTransactionDetails ...");
			log.info("Exception : [" + e.getMessage() + "]");
			deviceDetailsInfo.setDeviceInfo(null);
			deviceDetailsInfo.setError(e.getMessage());
		}

		Device deviceInfo = null;
		if (userTxnFromFPSFound) {
			deviceInfo = new Device();
			boolean idempotancyFound = false;
			if (getUserTransactionDetailsResponse != null) {
				if (getUserTransactionDetailsResponse.getListOfTransactions() != null
						&& getUserTransactionDetailsResponse
						.getListOfTransactions().size() != 0) {
					for (UserTransactionDetails userTransactionDetails : getUserTransactionDetailsResponse
							.getListOfTransactions()) {
						if (userTransactionDetails.getRequestParam() != null
								&& userTransactionDetails.getRequestParam()
								.getTransactionId() != null) {
							if (userTransactionDetails.getRequestParam()
									.getTransactionId()
									.equals(idempotancyId)) {
								idempotancyFound = true;
								deviceInfo = userTransactionDetails
										.getRequestParam().getDevice();
								deviceDetailsInfo.setDeviceInfo(deviceInfo);
								deviceDetailsInfo.setError(null);
								break;
							}
						}
					}
					if(idempotancyFound){
						log.info("Device details found for txnId = " + transactionId);
					} else {
						log.info("Device details NOT found for txnId = " + transactionId);
					}
				}

				/*UserTransactionDetails userTransactionDetails = new UserTransactionDetails();
					userTransactionDetails = getUserTransactionDetailsResponse.getListOfTransactions().get(0);
					if (userTransactionDetails != null  && userTransactionDetails.getRequestParam() != null) {
						deviceInfo = userTransactionDetails.getRequestParam()
								.getDevice();*/
			} else {
				log.info("getUserTransactionDetailsResponse.getListOfTransactions() = NULL or EMPTY inside getUserAndDeviceDetailsForTransaction!");
			}
		} else {
			log.info("getUserTransactionDetailsResponse = NULL inside getUserAndDeviceDetailsForTransaction!");
		}
		
		if(deviceInfo == null){
			
		}

		userDeviceTransactionResponse.setDeviceDetailsInfo(deviceDetailsInfo);

		return userDeviceTransactionResponse;

	}


	public boolean isSendMonyTxn(TransactionSummary txnSummary){
		if(txnSummary.getBusinessTransactionType() != null && txnSummary.getBusinessTransactionType().equals(BusinessTransactionType.WALLET_TRANSFER) && txnSummary.getTransactionType() != null && txnSummary.getTransactionType().equals(TransactionType.DEBIT_TRANSFER)){
			return true;
		}
		return false;
	}

	public boolean isRechargeTxn(TransactionSummary txnSummary){
		if(txnSummary.getMerchantId() != null && txnSummary.getMerchantId().equals("FREECHARGE") && txnSummary.getBusinessTransactionType() != null && txnSummary.getBusinessTransactionType().equals(BusinessTransactionType.PAYMENT) && txnSummary.getTransactionType() != null && txnSummary.getTransactionType().equals(TransactionType.DEBIT_DEFAULT)){
			return true;
		}
		return false;
	}


	public boolean isWithdrawTxn(TransactionSummary txnSummary){
		if(txnSummary.getBusinessTransactionType() != null && txnSummary.getBusinessTransactionType().equals(BusinessTransactionType.WALLET_WITHDRAW) && txnSummary.getTransactionType() != null && txnSummary.getTransactionType().equals(TransactionType.DEBIT_WITHDRAW)){
			return true;
		}
		return false;
	}


	public boolean isOnlineOfflineMerchantsTxn(TransactionSummary txnSummary){
		if(txnSummary.getBusinessTransactionType() != null && txnSummary.getBusinessTransactionType().equals(BusinessTransactionType.PAYMENT) && txnSummary.getTransactionType() != null && txnSummary.getTransactionType().equals(TransactionType.DEBIT_DEFAULT) && txnSummary.getMerchantId() != null && !txnSummary.getMerchantId().equals("FREECHARGE") && !txnSummary.getMerchantId().equals("SNAPDEAL") && !txnSummary.getMerchantId().equals("SHOPO") && !txnSummary.getMerchantId().equals("EXCLUSIVELY")){
			return true;
		}
		return false;
	}

	public boolean isVirtualCardTxn(TransactionSummary txnSummary){
		if(txnSummary.getBusinessTransactionType() != null && txnSummary.getBusinessTransactionType().equals(BusinessTransactionType.PAYMENT) && txnSummary.getTransactionType() != null && txnSummary.getTransactionType().equals(TransactionType.DEBIT_DEFAULT) && txnSummary.getMerchantId() != null && txnSummary.getMerchantId().equals("MasterCard")){
			return true;
		}
		return false;
	}

	public CategoryWiseTxnsInfo getCategorizedTransactions(String transactionId) throws InfoPanelException{

		String userId = null;

		Date createdTime = null;

		BigDecimal balance = null;

		CategorizedTxnSum categorizedTxnSum = new CategorizedTxnSum();

		categorizedTxnSum.setOnlineOfflineMerchantsTxnsSum(BigDecimal.ZERO);
		categorizedTxnSum.setOtherTxnsSum(BigDecimal.ZERO);
		categorizedTxnSum.setRechargeTxnsSum(BigDecimal.ZERO);
		categorizedTxnSum.setSendMoneyTxnsSum(BigDecimal.ZERO);
		categorizedTxnSum.setVirtualCardTxnsSum(BigDecimal.ZERO);
		categorizedTxnSum.setWithdrawTxnsSum(BigDecimal.ZERO);

		GetTransactionByIdRequest getTransactionByIdRequest = new GetTransactionByIdRequest();
		getTransactionByIdRequest
		.setTransactionId(transactionId);
		GetTransactionByIdResponse getTransactionByIdResponse = new GetTransactionByIdResponse();
		try {
			getTransactionByIdResponse = getTransactionById(getTransactionByIdRequest);
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getTransactionById for  txnId = " +getTransactionByIdRequest.getTransactionId());
		}
		userId = null;
		createdTime = null;
		if (getTransactionByIdResponse != null) {
			List<Transaction> allTxns = new ArrayList<Transaction>();
			allTxns = getTransactionByIdResponse.getTransactions();
			if (allTxns != null && allTxns.size() != 0) {
				if (allTxns.get(0) != null) {
					userId = allTxns.get(0).getUserId();
					createdTime = allTxns.get(0).getTimestamp();
					categorizedTxnSum.setFraudAmount(allTxns.get(0).getTransactionAmount());
					Balance sdMoneyBalance = new Balance();
					sdMoneyBalance = allTxns.get(0).getRunningBalance();
					if(sdMoneyBalance !=  null){
						categorizedTxnSum.setGeneralBalance(sdMoneyBalance.getGeneralBalance());
						categorizedTxnSum.setVoucherBalance(sdMoneyBalance.getVoucherBalance());
					} else {
						log.info("allTxns.get(0).getRunningBalance() = NULL inside getCategorizedTransactions!");
					}

				} else {
					log.info("allTxns.get(0) inside getCategorizedTransactions!");
				}
			} else {
				log.info("allTxns = NULL or EMPTY inside getCategorizedTransactions!");
			}
		} else {
			log.info("getTransactionByIdResponse = NULL inside getCategorizedTransactions!");
		}
		com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse getTransactionsResponse = new com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse();
		String lastEvaluatedKey = null;
		int getTxnsCallNumber = 1;
		com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest getTransactionsRequest = new com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest();
		getTransactionsRequest.setSdIdentity(userId);
		getTransactionsRequest.setStartTime(createdTime);
		getTransactionsRequest.setPageSize(50);


		List<TransactionSummary> txnsList = new ArrayList<TransactionSummary>();

		try {
			getTransactionsResponse = getTransactions(getTransactionsRequest);
		} catch (SDMoneyException sde) {
			log.info(" Exception from sdmoneyclient while getting getTransactions for userId = " + userId + " at call number " + getTxnsCallNumber + " " + sde);
		}catch (Exception e) {
			log.info("OtherException while getting getTransactions for userId = " + userId + " at call number " + getTxnsCallNumber);
			log.info("Exception : [" + e.getMessage() + "]");
		}

		if(getTransactionsResponse != null){
			if(getTransactionsResponse.getTransactions() != null && getTransactionsResponse.getTransactions().size() !=0){
				txnsList.addAll(getTransactionsResponse.getTransactions());	
				lastEvaluatedKey = getTransactionsResponse.getLastEvaluatedKey();
				getTxnsCallNumber++;
			}else {
				log.info("getTransactionsResponse.getTransactions() = NULL OR getTransactionsResponse.getTransactions().size() = 0 inside getCategorizedTransactions\n");
			}

		}else{
			log.info("getTransactionsResponse = NULL inside getCategorizedTransactions\n");
		}

		while(lastEvaluatedKey != null){
			com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest req = new com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest();
			req.setSdIdentity(userId);
			req.setStartTime(createdTime);
			req.setPageSize(50);
			req.setLastEvaluatedKey(lastEvaluatedKey);

			try {
				getTransactionsResponse = getTransactions(getTransactionsRequest);
				if(getTransactionsResponse != null){
					if(getTransactionsResponse.getTransactions() != null && getTransactionsResponse.getTransactions().size() !=0){
						txnsList.addAll(getTransactionsResponse.getTransactions());	
						lastEvaluatedKey = getTransactionsResponse.getLastEvaluatedKey();
						getTxnsCallNumber++;
					}else {
						log.info("getTransactionsResponse.getTransactions() = NULL OR getTransactionsResponse.getTransactions().size() = 0 inside getCategorizedTransactions\n");
					}

				}else{
					log.info("getTransactionsResponse = NULL inside getCategorizedTransactions\n");
				}
			} catch (SDMoneyException sde) {
				log.info(" Exception from sdmoneyclient while getting getTransactions for userId = " + userId + " at call number " + getTxnsCallNumber + " " + sde);
			}catch (Exception e) {
				log.info("OtherException while getting getTransactions for userId = " + userId + " at call number " + getTxnsCallNumber);
				log.info("Exception : [" + e.getMessage() + "]");
			}
		}


		/*CATEGORIZE TRANSACTIONS*/


		CategoryWiseTxnsInfo categoryWiseTxnsInfo = new CategoryWiseTxnsInfo();

		List<SentMoneyRecipient> sendMoneyTxns = new ArrayList<SentMoneyRecipient>();

		List<FreechargedUser> rechargeTxns = new ArrayList<FreechargedUser>();

		List<WithdrawToBankTxn> withdrawTxns = new ArrayList<WithdrawToBankTxn>();

		List<MerchantTxn> onlineOfflineMerchantsTxns = new ArrayList<MerchantTxn>();

		List<VirtualCardsTxn> virtualCardTxns = new ArrayList<VirtualCardsTxn>();

		List<TransactionSummary> otherTxns = new ArrayList<TransactionSummary>();

		int size = txnsList.size();
		for(int i=0; i<size; i++){
			TransactionSummary txnSummary = new TransactionSummary();
			txnSummary = txnsList.get(i);

			if(isSendMonyTxn(txnSummary)){

				SentMoneyRecipient sentMoneyRecipient = new SentMoneyRecipient();
				if(txnSummary.getEventContext() != null){
					sentMoneyRecipient = getSendMoneyTransactionDetails(txnSummary.getEventContext(), txnSummary.getTransactionAmount());
					sentMoneyRecipient.setCreationDate(txnSummary.getTimestamp());
					categorizedTxnSum.setSendMoneyTxnsSum(categorizedTxnSum.getSendMoneyTxnsSum().add(sentMoneyRecipient.getAmount()));


				}else{
					log.info("txnSummary.getEventContext() = NULL for sendMoneyTxn with txnId = " + txnSummary.getTransactionId());
				}
				sendMoneyTxns.add(sentMoneyRecipient);

			} else if(isRechargeTxn(txnSummary)){

				FreechargedUser freechargedUser = new FreechargedUser();
				if(txnSummary.getEventContext() != null){
					freechargedUser = getRechargeDetails(txnSummary.getEventContext(), txnSummary.getTransactionAmount());
					freechargedUser.setCreationDate(txnSummary.getTimestamp());
					categorizedTxnSum.setRechargeTxnsSum(categorizedTxnSum.getRechargeTxnsSum().add(freechargedUser.getAmount()));

				}else{
					log.info("txnSummary.getEventContext() = NULL for rechargeTxns with txnId = " + txnSummary.getTransactionId());
				}
				rechargeTxns.add(freechargedUser);

			} else if(isWithdrawTxn(txnSummary)){
				String transactionReference = txnSummary.getTransactionReference();

				SearchWithdrawalFlowRequest flowRequest = new SearchWithdrawalFlowRequest();

				GetMoneyOutStatusRequest getMoneyOutStatusRequest = new GetMoneyOutStatusRequest();
				getMoneyOutStatusRequest.setSdIdentity(userId);
				getMoneyOutStatusRequest.setTransactionRef(transactionReference);

				flowRequest.setMoneyOutStatusRequest(getMoneyOutStatusRequest);

				SearchWithdrawalFlowResponse flowResponse = new SearchWithdrawalFlowResponse();

				WithdrawToBankTxn withdrawToBankTxn = new WithdrawToBankTxn();
				withdrawToBankTxn.setCreationDate(txnSummary.getTimestamp());
				withdrawToBankTxn.setAmount(txnSummary.getTransactionAmount());

				categorizedTxnSum.setWithdrawTxnsSum(categorizedTxnSum.getWithdrawTxnsSum().add(withdrawToBankTxn.getAmount()));

				try {
					flowResponse = searchUserServices.searchWithdrawal(flowRequest);
					if(flowResponse != null){
						if(flowResponse.getBankDetailsResponse() != null){
							GetBankDetailsResponse bankDetailsResponse = new GetBankDetailsResponse();
							bankDetailsResponse = flowResponse.getBankDetailsResponse();
							if(bankDetailsResponse.getAccountDetails() != null){
								BankAccountDetails bankAccountDetails = new BankAccountDetails();
								bankAccountDetails = bankDetailsResponse.getAccountDetails();

								withdrawToBankTxn.setAccountNumber(bankAccountDetails.getAccountNumber());
								withdrawToBankTxn.setBankName(bankAccountDetails.getBankName());
								withdrawToBankTxn.setIfsc(bankAccountDetails.getIfsc());

							}else {
								log.info("flowResponse.getBankDetailsResponse().getAccountDetails() = NULL for userId = " + userId + " and txnId = " + txnSummary.getTransactionId());
							}
						}else {
							log.info("flowResponse.getBankDetailsResponse() = NULL for userId = " + userId + " and txnId = " + txnSummary.getTransactionId());
						}
					} else {
						log.info("flowResponse = NULL for userId = " + userId + " and txnId = " + txnSummary.getTransactionId());
					}
				} catch (InfoPanelException e) {
					log.info("InfoPanelException while searchWithdrawal for userId = " + userId + " and txnId = " + txnSummary.getTransactionId());
				} catch (Exception e) {
					log.info("OtherException while searchWithdrawal for userId = " + userId + " and txnId = " + txnSummary.getTransactionId());
					log.info("Exception : [" + e.getMessage() + "]");
				}
				withdrawTxns.add(withdrawToBankTxn);

			} else if(isOnlineOfflineMerchantsTxn(txnSummary)){

				MerchantTxn merchantTxn = new MerchantTxn();
				merchantTxn = getMerchantTxnDetails(txnSummary);
				merchantTxn.setCreationDate(txnSummary.getTimestamp());
				onlineOfflineMerchantsTxns.add(merchantTxn);

				categorizedTxnSum.setOnlineOfflineMerchantsTxnsSum(categorizedTxnSum.getOnlineOfflineMerchantsTxnsSum().add(merchantTxn.getAmount()));

			} else if(isVirtualCardTxn(txnSummary)){

				VirtualCardsTxn virtualCardsTxn = new VirtualCardsTxn();
				if(txnSummary.getEventContext() != null){
					virtualCardsTxn = getVirtualCardsTxnDetails(txnSummary.getEventContext(), txnSummary.getTransactionAmount());
					virtualCardsTxn.setCreationDate(txnSummary.getTimestamp());
					categorizedTxnSum.setVirtualCardTxnsSum(categorizedTxnSum.getVirtualCardTxnsSum().add(virtualCardsTxn.getAmount()));

				}else{
					log.info("txnSummary.getEventContext() = NULL for rechargeTxns with txnId = " + txnSummary.getTransactionId());
				}
				virtualCardTxns.add(virtualCardsTxn);

			} else {

				otherTxns.add(txnSummary);
				categorizedTxnSum.setOtherTxnsSum(categorizedTxnSum.getOtherTxnsSum().add(txnSummary.getTransactionAmount()));

			}
		}

		categoryWiseTxnsInfo.setOnlineOfflineMerchantsTxns(onlineOfflineMerchantsTxns);
		categoryWiseTxnsInfo.setOtherTxns(otherTxns);
		categoryWiseTxnsInfo.setRechargeTxns(rechargeTxns);
		categoryWiseTxnsInfo.setSendMoneyTxns(sendMoneyTxns);
		categoryWiseTxnsInfo.setVirtualCardTxns(virtualCardTxns);
		categoryWiseTxnsInfo.setWithdrawTxns(withdrawTxns);
		categoryWiseTxnsInfo.setCategorizedTxnSum(categorizedTxnSum);

		return categoryWiseTxnsInfo;

	}

	/*public JSONObject getJSONObject(String json){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(json);
			return jsonObject;
		} catch (JSONException jse) {
			log.info("JSONException while converting string to JSONObject " + jse.getMessage() + jse.getStackTrace().toString());
			return null;
		}
	}*/


	public SentMoneyRecipient getSendMoneyTransactionDetails(String eventContext, BigDecimal amount){

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap = new Gson().fromJson(eventContext, new TypeToken<HashMap<String, Object>>() {}.getType());
		} catch (JsonSyntaxException jse) {
			log.info("JsonSyntaxException while converting string to JSON Map" + jse.getMessage() + jse.getStackTrace().toString());
		}

		String userId = (String) retMap.get("receiverId");

		GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
		getUserByIdRequest.setUserId(userId);

		GetUserResponse getUserResponse = new GetUserResponse();

		SentMoneyRecipient sentMoneyRecipient = new SentMoneyRecipient();
		sentMoneyRecipient.setAmount(amount);

		try {
			getUserResponse = getUser(getUserByIdRequest);
			if(getUserResponse != null && getUserResponse.getUserDetails() != null){
				UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
				userDetailsDTO  = getUserResponse.getUserDetails();

				sentMoneyRecipient.setCreationDate(userDetailsDTO.getCreatedTime());
				sentMoneyRecipient.setEmail(userDetailsDTO.getEmailId());
				sentMoneyRecipient.setMobile(userDetailsDTO.getMobileNumber());
				sentMoneyRecipient.setName(userDetailsDTO.getFirstName() + " " + userDetailsDTO.getMiddleName() + " " + userDetailsDTO.getLastName());
				sentMoneyRecipient.setUserId(userDetailsDTO.getUserId());
				return sentMoneyRecipient;
			}
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getUser for userId = " + userId);	
		} catch (Exception e) {
			log.info("OtherException while calling getUser for userId = " + userId);
			log.info("Exception : [" + e.getMessage() + "]");
		}
		return sentMoneyRecipient;

	}

	public FreechargedUser getRechargeDetails(String eventContext, BigDecimal amount){

		String target = new String("serviceNumber:");

		String mobile = null;
		int position = eventContext.indexOf(target);
		if(position != -1){
			mobile = eventContext.substring(position + 14, position + 24);
		}else {
			log.info("String serviceNumber: not found inside eventContext for Recharge Txn ..\n");
		}

		GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
		getUserByMobileRequest.setMobileNumber(mobile);

		GetUserResponse getUserResponse = new GetUserResponse();

		FreechargedUser freechargedUser = new FreechargedUser();
		freechargedUser.setAmount(amount);
		freechargedUser.setMobile(mobile);

		try {
			getUserResponse = getUserByMobile(getUserByMobileRequest);
			if(getUserResponse != null && getUserResponse.getUserDetails() != null){
				UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
				userDetailsDTO  = getUserResponse.getUserDetails();

				freechargedUser.setCreationDate(userDetailsDTO.getCreatedTime());
				freechargedUser.setEmail(userDetailsDTO.getEmailId());

				freechargedUser.setName(userDetailsDTO.getFirstName() + " " + userDetailsDTO.getMiddleName() + " " + userDetailsDTO.getLastName());
				freechargedUser.setUserId(userDetailsDTO.getUserId());
				return freechargedUser;
			}
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getUser for mobile = " + mobile);	
		} catch (Exception e) {
			log.info("OtherException while calling getUser for mobile = " + mobile);
			log.info("Exception : [" + e.getMessage() + "]");
		}
		return freechargedUser;

	}

	public VirtualCardsTxn getVirtualCardsTxnDetails(String eventContext, BigDecimal amount){

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap = new Gson().fromJson(eventContext, new TypeToken<HashMap<String, Object>>() {}.getType());
		} catch (JsonSyntaxException jse) {
			log.info("JsonSyntaxException while converting string to JSON Map" + jse.getMessage() + jse.getStackTrace().toString());
		}
		VirtualCardsTxn virtualCardsTxn = new VirtualCardsTxn();
		virtualCardsTxn.setAmount(amount);
		virtualCardsTxn.setCardHashId((String) retMap.get("cardHashId"));
		virtualCardsTxn.setMerchantCategoryCode((String) retMap.get("merchantCategoryCode"));
		virtualCardsTxn.setMerchantId((String) retMap.get("merchantId"));
		virtualCardsTxn.setMerchantName((String) retMap.get("merchantName"));
		virtualCardsTxn.setTraceNumber((String) retMap.get("traceNumber"));
		return virtualCardsTxn;
	}


	public MerchantTxn getMerchantTxnDetails(TransactionSummary txnSummary){

		MerchantTxn merchantTxn = new MerchantTxn();
		merchantTxn.setAmount(txnSummary.getTransactionAmount());
		merchantTxn.setMerchantId(txnSummary.getMerchantId());
		merchantTxn.setMerchantName(txnSummary.getDisplayName());
		return merchantTxn;
	}

	public boolean disableUser(String userId, String actionPerformer, String reason, String comment ){

		boolean userDisabled = false;
		EnableDisableUserRequest enableDisableUserRequest = new EnableDisableUserRequest();
		enableDisableUserRequest.setAction(UserPanelAction.DISABLE_USER);
		enableDisableUserRequest.setActionPerformer(actionPerformer);
		enableDisableUserRequest.setOtherReason(comment);
		enableDisableUserRequest.setReason(reason);
		enableDisableUserRequest.setRequestId("");
		enableDisableUserRequest.setTypeOfFraud(StolenInstrumentsConstants.STOLEN_INSTRUMENT);
		enableDisableUserRequest.setUserId(userId);

		EnableDisableUserResponse enableDisableUserResponse = new EnableDisableUserResponse();

		try {
			enableDisableUserResponse = actionService.enableDisableUser(enableDisableUserRequest);
			if(enableDisableUserResponse != null && enableDisableUserResponse.getMessage().equals("user has been disabled successfully")){
				userDisabled = true;
			}
		} catch (InfoPanelException e) {
			userDisabled = false;
			log.info("InfoPanelException while disableUser for userId = " + userId + e.getErrCode() + e.getErrMessage());
		}
		return userDisabled;

	}

	public void blackListUser(String userId, String actionPerformer, String reason, String comment ){

		BlackListWhiteListUserRequest blackListWhiteListUserRequest = new BlackListWhiteListUserRequest();
		blackListWhiteListUserRequest.setAction(UserPanelAction.BLACK_LIST_USER);
		blackListWhiteListUserRequest.setActionPerformer(actionPerformer);
		blackListWhiteListUserRequest.setEmailId("");
		blackListWhiteListUserRequest.setOtherReason(comment);
		blackListWhiteListUserRequest.setReason(reason);
		blackListWhiteListUserRequest.setRequestId("");
		blackListWhiteListUserRequest.setTypeOfFraud(StolenInstrumentsConstants.STOLEN_INSTRUMENT);

		BlackListWhiteListUserResponse blackListWhiteListUserResponse = new BlackListWhiteListUserResponse();

		try {
			blackListWhiteListUserResponse = actionService.blackListWhiteListUser(blackListWhiteListUserRequest);
		} catch (InfoPanelException e) {
			log.info("InfoPanelException blackListUser user for userId = " +userId + e.getErrCode() + e.getErrMessage());
		}
	}

	public boolean disableWallet(String userId, String actionPerformer, String reason, String comment ){

		boolean walletDisabled = false;
		SuspendWalletRequest suspendWalletRequest = new SuspendWalletRequest();
		suspendWalletRequest.setActionPerformer(actionPerformer);
		suspendWalletRequest.setOtherReason(comment);
		suspendWalletRequest.setReason(reason);
		suspendWalletRequest.setRequestId("");
		suspendWalletRequest.setTypeOfFraud(StolenInstrumentsConstants.STOLEN_INSTRUMENT);
		suspendWalletRequest.setUserId(userId);
		suspendWalletRequest.setUserIdType(UserPanelIdType.IMS_ID);

		try {
			actionService.suspendWallet(suspendWalletRequest);
			walletDisabled = true;
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while suspendWallet for userId = " + userId + e.getErrCode() + e.getErrMessage());
			walletDisabled = false;
		}
		return walletDisabled;
	}


	public BlockResponse blockUser(BlockRequest blockRequest, SubmitRequest submitRequest){

		BlockResponse blockResponse = new BlockResponse();
		blockResponse.setEntityType(blockRequest.getEntityType());
		blockResponse.setEntityValue(blockRequest.getEntityValue());
		blockResponse.setBankAccountDisabledStatus(false);
		blockResponse.setUserDisabledStatus(false);
		blockResponse.setWalletDisabledStatus(false);

		String mobile = blockRequest.getEntityValue();
		String userId = null;
		GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
		getUserByMobileRequest.setMobileNumber(mobile);

		GetUserResponse getUserResponse = new GetUserResponse();

		try {
			getUserResponse = getUserByMobile(getUserByMobileRequest);
			if(getUserResponse != null && getUserResponse.getUserDetails() != null){
				UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
				userDetailsDTO  = getUserResponse.getUserDetails();
				userId = userDetailsDTO.getUserId();
			}
		} catch (InfoPanelException e) {
			log.info("InfoPanelException while getUser for mobile = " + mobile);	
		} catch (Exception e) {
			log.info("OtherException while calling getUser for mobile = " + mobile);
			log.info("Exception : [" + e.getMessage() + "]");
		}

		boolean walletDisabled = false;
		boolean userDisabled = false;
		/*LETS DISABLE WALLET*/
		walletDisabled = disableWallet(userId, submitRequest.getActionPerformer(), submitRequest.getReportingEntity(),submitRequest.getFraudDescription());
		if(walletDisabled){
			blockResponse.setWalletDisabledStatus(true);
		}
		/*LETS DISABLE USER*/
		userDisabled = disableUser(userId, submitRequest.getActionPerformer(), submitRequest.getReportingEntity(),submitRequest.getFraudDescription());
		if(userDisabled){
			blockResponse.setUserDisabledStatus(true);
		}
		return blockResponse;
	}

	public BlockResponse disableBankAccount(BlockRequest blockRequest, SubmitRequest submitRequest){

		BlockResponse blockResponse = new BlockResponse();
		blockResponse.setEntityType(blockRequest.getEntityType());
		blockResponse.setEntityValue(blockRequest.getEntityValue());
		blockResponse.setBankAccountDisabledStatus(false);
		blockResponse.setUserDisabledStatus(false);
		blockResponse.setWalletDisabledStatus(false);

		GetEntityRequest getEntityRequest = new GetEntityRequest();

		String entityId = blockRequest.getEntityValue();
		String entityType = blockRequest.getEntityType().name();

		getEntityRequest.setEntityId(entityId);
		getEntityRequest.setEntityType(entityType);

		GetEntityResponse getEntityResponse = new GetEntityResponse();
		getEntityResponse = null;
		boolean entityFound = false;

		try {
			getEntityResponse = pms.getEntity(getEntityRequest);
			log.info("Found getEntityResponse = " + getEntityResponse.toString());
		} catch(InternalClientException ice){
			log.info("InternalClientException while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + ice.getMessage() + " : WILL BE RETRIED \n");
			try {
				getEntityResponse = pms.getEntity(getEntityRequest);
				log.info("Found on retry , getEntityResponse = " + getEntityResponse.toString());
			} catch (InternalClientException ice2) {
				log.info("InternalClientException on retry while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + ice.getMessage() + " : FAILURE");
			} catch (ProfileManagementException pme) {
				log.info("ProfileManagementException on retry while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + pme.getMessage() + " : FAILURE");
			}
		} catch (ProfileManagementException pme) {
			log.info("ProfileManagementException while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + pme.getMessage() + " : FAILURE");
		}

		Integer entityVersion = null;



		if(getEntityResponse != null && getEntityResponse.getEntityId() != null){
			log.info("EntityType found and its value is not NULL! \n");
			entityFound = true;
		} else {
			log.info("EntityType found = NULL! \n");
		}

		if(getEntityResponse != null && !getEntityResponse.getEntityType().equalsIgnoreCase(entityType)){
			log.info("entityType is not same as entityType of already existing entity! \n");
			entityFound = false;
		}

		if(entityFound == true){
			entityVersion = getEntityResponse.getEntityVersion();
		} else {
			entityVersion = Integer.valueOf(0);
		}

		boolean entityCreated = true;

		entityCreated = false;
		UpdateEntityRequest updateEntityRequest = new UpdateEntityRequest();
		updateEntityRequest.setEntityId(entityId);
		updateEntityRequest.setEntityType(entityType);
		updateEntityRequest.setEntityVersion(entityVersion);

		updateEntityRequest.setEntityStatus(EntityStatus.DENY);


		EntityMetaInfo entityMetaInfo = new EntityMetaInfo();
		entityMetaInfo.setJiraId(StolenInstrumentsConstants.STOLEN_INSTRUMENT);
		entityMetaInfo.setUpdateCode("Reporting Entity : " + submitRequest.getReportingEntity() + ", Fraud Description : " + submitRequest.getFraudDescription());
		entityMetaInfo.setUpdatedBy(submitRequest.getActionPerformer());

		updateEntityRequest.setEntityMetaInfo(entityMetaInfo);
		updateEntityRequest.setUpdateReason(StolenInstrumentsConstants.STOLEN_INSTRUMENT);
		try {
			pms.updateEntity(updateEntityRequest);
			log.info("Created Entity = ["
					+ entityType
					+ " , "
					+ entityId
					+ "] ");
			entityCreated = true;
			entityVersion = Integer.valueOf(1);
			blockResponse.setBankAccountDisabledStatus(true);
			log.info("FULL_BLOCKING the item Entity = ["
					+ entityType
					+ " , "
					+ entityId
					+ "] and Done!");
		} catch (InternalClientException ice) {
			log.info("InternalClientException while updating Entity! Entity = ["
					+ entityType
					+ " , "
					+ entityId
					+ "] "
					+ " : "
					+ ice.getMessage() + " : WILL BE RETRIED \n");
			try {
				pms.updateEntity(updateEntityRequest);
				log.info("Created on retry , Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] ");
				entityCreated = true;
				entityVersion = Integer.valueOf(1);
				blockResponse.setBankAccountDisabledStatus(true);
				log.info("FULL_BLOCKING the item Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] and Done!");
			} catch (InternalClientException ice2) {
				log.info("InternalClientException on retry while updating Entity! Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] "
						+ " : "
						+ ice.getMessage() + " : FAILURE");
			} catch (ProfileManagementException pme) {
				log.info("ProfileManagementException on retry while updating Entity! Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] "
						+ " : "
						+ pme.getMessage() + " : FAILURE");
			}
		} catch (ProfileManagementException pme) {
			log.info("ProfileManagementException while updating Entity! Entity = ["
					+ entityType
					+ " , "
					+ entityId
					+ "] "
					+ " : "
					+ pme.getMessage() + " : FAILURE");
		}
		return blockResponse;

	}


	public SubmitResponse submit(SubmitRequest submitRequest){
		SubmitResponse submitResponse = new SubmitResponse();
		if(submitRequest != null){
			if(submitRequest.getBlockThese() !=  null){
				List<BlockRequest> listToBlock = new ArrayList<BlockRequest>();
				listToBlock = submitRequest.getBlockThese();

				List<BlockResponse> listBlockedResponse = new ArrayList<BlockResponse>();

				int size = listToBlock.size();
				if(size != 0){
					for(int i=0;  i<size ; i++){
						BlockRequest blockRequest = new BlockRequest();
						blockRequest = listToBlock.get(i);

						BlockResponse blockResponse = new BlockResponse();

						if(blockRequest.getEntityType().equals(EntityType.MOBILE)){

							blockResponse = blockUser(blockRequest, submitRequest);

						} else if(blockRequest.getEntityType().equals(EntityType.BANK_ACCOUNT)){

							blockResponse = disableBankAccount(blockRequest, submitRequest);

						}
						listBlockedResponse.add(blockResponse);
					}
					submitResponse.setListBlockedResponse(listBlockedResponse);
				}else {
					log.info("listToBlock size = 0...\n");
				}

			}else {
				log.info("submitRequest.getBlockThese() = NULL...\n");
			}

		}else {
			log.info("submitRequest = NULL...\n");
		}
		return submitResponse;

	}


}
