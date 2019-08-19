package com.snapdeal.opspanel.userpanel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.amazonaws.util.Base64;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.dao.FraudAuditDao;
import com.snapdeal.opspanel.userpanel.entity.FraudManagementEntity;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.FraudManagementRequest;
import com.snapdeal.opspanel.userpanel.request.FraudReverseLoadMoneyRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.response.FraudManagementResponse;
import com.snapdeal.opspanel.userpanel.response.FraudOutputResponse;
import com.snapdeal.opspanel.userpanel.response.FraudTransactionDetails;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.FraudManagementService;
import com.snapdeal.opspanel.userpanel.service.WalletPanelService;
import com.snapdeal.opspanel.userpanel.service.WalletService;
import com.snapdeal.opspanel.userpanel.utils.FraudUtils;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.opspanel.userpanel.utils.SuperCSVWriter;
import com.snapdeal.payments.sdmoney.client.BankDetailsStoreClient;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.service.model.BankAccountDetails;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusResponse;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.Transaction;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
import com.snapdeal.payments.sdmoney.service.model.type.TransactionType;

import lombok.extern.slf4j.Slf4j;

@Service("FraudManagement")
@Slf4j
public class FraudManagementServiceImpl implements FraudManagementService {

	@Autowired
	ActionService actionService;

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Autowired
	WalletPanelService WalletPanelService;

	@Autowired
	WalletService walletService;

	@Autowired
	FraudAuditDao fraudAuditDao;

	@Autowired
	HttpServletRequest servRequest;

	@Autowired
	SuperCSVWriter SuperCSVWriter;

	@Autowired
	HttpServletResponse response;

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	private TokenService tokenService;

	@Autowired
	BankDetailsStoreClient bankDetailStoreClient;

	@Override
	public GenericResponse performFraudActions(FraudManagementResponse fraudManagementResponse,
			FraudManagementRequest fraudManagementRequest) throws InfoPanelException, OpsPanelException {

		List<String> actionsList = fraudManagementRequest.getActions();

		FraudOutputResponse fraudOutputResponse = new FraudOutputResponse();

		String userAndWalletDetailsHeader = null, transactionDetailsHeader = null;

		List<FraudTransactionDetails> fraudTransactionDetailsList = null;

		for (String fraudAction : actionsList) {

			switch (fraudAction) {

			case "DISABLE_IMS_USER_ACCOUNT":

				disableUser(getUserId(fraudManagementResponse), fraudManagementRequest, fraudOutputResponse);
				break;

			case "DISABLE_WALLET":

				suspendWallet(getUserId(fraudManagementResponse), fraudManagementRequest, fraudOutputResponse);
				break;

			case "REVERSE_TRANSACTION_AND_REFUND_MONEY":

				reverseLoadMoney(fraudManagementResponse, fraudManagementRequest, fraudOutputResponse);
				break;

			case "USER_DETAILS":

				fillUserDetails(fraudManagementResponse, fraudOutputResponse);
				break;

			case "TRANSACTION_HISTORY":

				fraudTransactionDetailsList = writeTransactionHistory(getTransaction(fraudManagementResponse));
				transactionDetailsHeader = FraudUtils.responseUserTransactionDetails;
				break;

			}

		}

		userAndWalletDetailsHeader = FraudUtils.responseUserDetailsHeader + FraudUtils.responseCommaHeader
				+ FraudUtils.responseWalletDetailsHeader;

		HashMap<String, Object> response = new HashMap<>();

		response.put("file", outPutResponseFile(userAndWalletDetailsHeader, transactionDetailsHeader,
				fraudOutputResponse, fraudTransactionDetailsList));
		response.put("status", "SUCCESS");

		return GenericUtils.getGenericResponse(response);

	}

	private String outPutResponseFile(String userAndWalletDetailsHeader, String transactionDetailsHeader,
			FraudOutputResponse fraudOutputResponse, List<FraudTransactionDetails> fraudTransactionDetails) {

		String[] userDetailsHeaderArray = userAndWalletDetailsHeader.split(",");

		String[] transactionDetailsHeaderArray = new String[0];

		if (transactionDetailsHeader != null)
			transactionDetailsHeaderArray = transactionDetailsHeader.split(",");

		CellProcessor[] userDetailsCellProcessor = getCellProcessor(userDetailsHeaderArray.length, true);

		CellProcessor[] transactionDetailsCellProcessor = getCellProcessor(transactionDetailsHeaderArray.length, false);

		try {

			byte[] out = SuperCSVWriter.createCSVForFraud(userDetailsHeaderArray, transactionDetailsHeaderArray,
					userDetailsCellProcessor, transactionDetailsCellProcessor, fraudOutputResponse,
					fraudTransactionDetails);

			return Base64.encodeAsString(out);

		} catch (InfoPanelException e) {
			e.printStackTrace();
		}

		return null;

	}

	private CellProcessor[] getCellProcessor(int cellProcessorLength, boolean actionNotSelected) {

		CellProcessor[] cellProcessor = null;

		cellProcessor = new CellProcessor[cellProcessorLength];

		for (int i = 0; i < cellProcessor.length; i++) {

			cellProcessor[i] = actionNotSelected == true ? new ConvertNullTo("")
					: new ConvertNullTo("");
		}

		return cellProcessor;

	}

	// user details
	private void fillUserDetails(FraudManagementResponse fraudManagementResponse,
			FraudOutputResponse fraudOutputResponse) {

		TransactionDetails transactionDetails = getTransaction(fraudManagementResponse);

		fraudOutputResponse.setUserId(transactionDetails.getUserId());
		fraudOutputResponse.setUserEmail(transactionDetails.getEmailId());

	}

	private List<FraudTransactionDetails> writeTransactionHistory(TransactionDetails transactionDetails) {

		return getTransactionsForUser(transactionDetails.getEmailId());

	}

	// reverse Load Money
	private void reverseLoadMoney(FraudManagementResponse fraudManagementResponse,
			FraudManagementRequest fraudManagementRequest, FraudOutputResponse fraudOutputResponse)
					throws InfoPanelException, OpsPanelException {

		TransactionDetails transactionDetails = getTransaction(fraudManagementResponse);

		FraudReverseLoadMoneyRequest fraudReverseLoadMoneyRequest = new FraudReverseLoadMoneyRequest();
		fraudReverseLoadMoneyRequest.setTransactionId(transactionDetails.getWalletTransactionId());

		ReverseLoadMoneyResponse reverseLoadMoneyResponse;

		try {
			log.info("reverse load money " + fraudReverseLoadMoneyRequest.getTransactionId());

			reverseLoadMoneyResponse = walletService.reverseLoadMoney(fraudReverseLoadMoneyRequest);

			log.info("load money reversed sucessfully for " + fraudReverseLoadMoneyRequest.getTransactionId());

			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("IST"));

			FraudManagementEntity fraudManagementEntity = new FraudManagementEntity();
			fraudManagementEntity.setActionTime(cal.getTime());
			fraudManagementEntity.setRequestId(getRequestId());
			fraudManagementEntity.setClientId(getClientId());
			fraudManagementEntity.setAction("DISABLE_IMS_USER_ACCOUNT");
			fraudManagementEntity.setReason(fraudManagementRequest.getReason());
			fraudManagementEntity.setUserIdInvolved(transactionDetails.getUserId());
			fraudManagementEntity.setAmountRefunded(reverseLoadMoneyResponse.getReversedAmount().toString());
			fraudManagementEntity.setOriginalAmount(transactionDetails.getTransactionAmount().toString());

			fraudOutputResponse.setAmountRefunded(reverseLoadMoneyResponse.getReversedAmount().toString());
			fraudOutputResponse.setOriginalAmount(transactionDetails.getTransactionAmount().toString());

			fraudAuditDao.auditFraud(fraudManagementEntity);

		} catch (InfoPanelException e) {
			log.error("Exception occured while reversing load money");
		}

	}

	private TransactionDetails getTransaction(FraudManagementResponse fraudManagementResponse) {
		return fraudManagementResponse.getTransactions().get(0);
	}

	// actions for fraud
	private void disableUser(String userId, FraudManagementRequest fraudManagementRequest,
			FraudOutputResponse fraudOutputResponse) throws OpsPanelException {

		EnableDisableUserRequest enableDisableUserRequest = new EnableDisableUserRequest();
		enableDisableUserRequest.setAction(UserPanelAction.DISABLE_USER);
		enableDisableUserRequest.setUserId(userId);
		enableDisableUserRequest.setReason(fraudManagementRequest.getReason());
		EnableDisableUserResponse enableDisableUserResponse;

		try {
			log.info("disabling user " + enableDisableUserRequest.getUserId());
			enableDisableUserResponse = actionService.enableDisableUser(enableDisableUserRequest);
			log.info("user disabled " + enableDisableUserRequest.getUserId());

			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("IST"));

			FraudManagementEntity fraudManagementEntity = new FraudManagementEntity();
			fraudManagementEntity.setActionTime(cal.getTime());
			fraudManagementEntity.setRequestId(getRequestId());
			fraudManagementEntity.setClientId(getClientId());
			fraudManagementEntity.setAction("DISABLE_IMS_USER_ACCOUNT");
			fraudManagementEntity.setReason(fraudManagementRequest.getReason());
			fraudManagementEntity.setUserIdInvolved(userId);
			fraudAuditDao.auditFraud(fraudManagementEntity);

			fraudOutputResponse.setImsAccountStatus("DISABLED");

		} catch (InfoPanelException e) {
			log.error("Exception occurred while disabling user " + e);
		}

	}

	private void suspendWallet(String userId, FraudManagementRequest fraudManagementRequest,
			FraudOutputResponse fraudOutputResponse) throws OpsPanelException {

		SuspendWalletRequest suspenWalletRequest = new SuspendWalletRequest();
		suspenWalletRequest.setUserId(userId);
		suspenWalletRequest.setUserIdType(UserPanelIdType.IMS_ID);

		try {
			log.info("suspending wallet " + userId);
			WalletPanelService.suspendSDMoneyAccount(suspenWalletRequest);
			log.info("wallet supended " + userId);

			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("IST"));

			FraudManagementEntity fraudManagementEntity = new FraudManagementEntity();
			fraudManagementEntity.setActionTime(cal.getTime());
			fraudManagementEntity.setRequestId(getRequestId());
			fraudManagementEntity.setClientId(getClientId());
			fraudManagementEntity.setAction("DISABLE_WALLET");
			fraudManagementEntity.setReason(fraudManagementRequest.getReason());
			fraudManagementEntity.setUserIdInvolved(userId);
			fraudAuditDao.auditFraud(fraudManagementEntity);

		} catch (InfoPanelException e) {

			if (e.getErrMessage().trim().equals("Account is suspended")) {

				log.info("wallet supended " + userId);

				Calendar cal = Calendar.getInstance();
				cal.setTimeZone(TimeZone.getTimeZone("IST"));

				FraudManagementEntity fraudManagementEntity = new FraudManagementEntity();
				fraudManagementEntity.setActionTime(cal.getTime());
				fraudManagementEntity.setRequestId(getRequestId());
				fraudManagementEntity.setClientId(getClientId());
				fraudManagementEntity.setAction("DISABLE_WALLET");
				fraudManagementEntity.setReason(fraudManagementRequest.getReason());
				fraudManagementEntity.setUserIdInvolved(userId);

				try {
					fraudAuditDao.auditFraud(fraudManagementEntity);
				} catch (InfoPanelException e1) {
					e1.printStackTrace();
				}

			} else
				log.error("Exception occurred while suspending wallet " + e);

		}

	}

	private String getRequestId() {
		return ( String ) servRequest.getAttribute( "requestId" );
	}

	private String getClientId() throws OpsPanelException {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return emailId;
	}

	private String getTransactionId(FraudManagementResponse fraudManagementResponse) {

		TransactionDetails transactionDetails = fraudManagementResponse.getTransactions().get(0);

		return transactionDetails.getWalletTransactionId();

	}

	private String getUserId(FraudManagementResponse fraudManagementResponse) {

		return fraudManagementResponse.getTransactions().get(0).getUserId();
	}

	@Override
	public FraudManagementResponse interceptFraudActions(FraudManagementRequest fraudManagementRequest)
			throws InfoPanelException {

		SearchTransactionRequest searchTransactionRequest = new SearchTransactionRequest();

		FraudManagementResponse fraudManagementResponse = null;

		if(fraudManagementRequest.getFraudManagementActionKeyType().equalsIgnoreCase("FREECHARGE_LOAD_MONEY")) { 


			searchTransactionRequest.setFcLoadingTransactionId(fraudManagementRequest.getFraudManagementActionkey());
			fraudManagementResponse = (FraudManagementResponse) getTransactionDetails(searchTransactionRequest);
		} else if(fraudManagementRequest.getFraudManagementActionKeyType().equalsIgnoreCase("FREECHARGE_PAYMENT")) {

			searchTransactionRequest.setFcPaymentTransactionId(fraudManagementRequest.getFraudManagementActionkey());
			fraudManagementResponse = (FraudManagementResponse) getTransactionDetails(searchTransactionRequest);
		} else if(fraudManagementRequest.getFraudManagementActionKeyType().equalsIgnoreCase("FREECHARGE_WITHDRAWAL")) {

			searchTransactionRequest.setFcWithdrawlTransactionId(fraudManagementRequest.getFraudManagementActionkey());
			fraudManagementResponse = (FraudManagementResponse) getTransactionDetails(searchTransactionRequest);
		} else if (fraudManagementRequest.getFraudManagementActionKeyType().equalsIgnoreCase("SNAPDEAL_PAYMENT_ORDER")) {

			searchTransactionRequest.setSdPaymentOrderId(fraudManagementRequest.getFraudManagementActionkey());
			fraudManagementResponse = (FraudManagementResponse) getTransactionDetails(searchTransactionRequest);
		} else if (fraudManagementRequest.getFraudManagementActionKeyType().equalsIgnoreCase("WALLET_TRANSACTION_ID")){

			searchTransactionRequest.setWalletTransactionId(fraudManagementRequest.getFraudManagementActionkey());
			fraudManagementResponse = (FraudManagementResponse) getTransactionDetails(searchTransactionRequest);


		}

		return fraudManagementResponse;
	}

	private Object getTransactionDetails(SearchTransactionRequest searchTransactionRequest) throws InfoPanelException {

		List<TransactionDetails> transactions = actionService.searchTransaction(searchTransactionRequest).getTransactionDetailsList()	;

		if (transactions == null)
			throw new InfoPanelException("ET-11032", "No Transactions Found on the basis of transactionReference");

		else if (transactions.size() == 0)
			throw new InfoPanelException("ET-11032", "No Transactions Found on the basis of transactionReference");

		ArrayList<String> transactionIds = new ArrayList<>();

		for (TransactionDetails transactionDetails : transactions) {

			if (!transactionIds.contains(transactionDetails.getWalletTransactionId())) {

				transactionIds.add(transactionDetails.getWalletTransactionId());

			}

		}

		FraudManagementResponse fraudManagementResponse = new FraudManagementResponse();
		fraudManagementResponse.setTransactions(transactions);

		if (transactionIds.size() > 1)
			fraudManagementResponse.setStatus("CONFLICTING");

		else
			fraudManagementResponse.setStatus("SUCCESS");

		return (Object) fraudManagementResponse;

	}

	public List<FraudTransactionDetails> getTransactionsForUser(String userEmailId) {

		GetTransactionsForUserRequest getTransactionsForUserRequest = new GetTransactionsForUserRequest();
		getTransactionsForUserRequest.setPageSize(50);
		getTransactionsForUserRequest.setUserId(userEmailId);

		GetTransactionsForUserResponse getTransactionsForUserResponse = sdMoneyClient
				.getTransactionsForUser(getTransactionsForUserRequest);

		return getDetailedTransactions(getTransactionsForUserResponse.getListTransaction());
	}

	private List<FraudTransactionDetails> getDetailedTransactions(List<TransactionSummary> transactionSummaryList) {

		List<FraudTransactionDetails> transactionDetailsList = new ArrayList<>();

		Map<String, Object> uniqueTransactionIdList = new HashMap<>();

		for (TransactionSummary transactionSummary : transactionSummaryList) {

			if (uniqueTransactionIdList.get(transactionSummary.getTransactionId()) == null)
				uniqueTransactionIdList.put(transactionSummary.getTransactionId(), transactionSummary);
		}

		for (String transactionId : uniqueTransactionIdList.keySet()) {

			List<FraudTransactionDetails> transactionDetailsById = getTransactionsById(transactionId);

			transactionDetailsList.addAll(transactionDetailsById);

		}

		return transactionDetailsList;
	}

	private List<FraudTransactionDetails> getTransactionsById(String transactionId) {

		GetTransactionByIdRequest getTransactionByIdRequest = new GetTransactionByIdRequest();
		getTransactionByIdRequest.setTransactionId(transactionId);

		GetTransactionByIdResponse getTransactionByIdResponse;

		getTransactionByIdResponse = sdMoneyClient.getTransactionById(getTransactionByIdRequest);

		List<Transaction> transactionList = getTransactionByIdResponse.getTransactions();

		List<FraudTransactionDetails> fraudTransactionDetailsList = new ArrayList<>();

		GetSDMoneyAccountRequest getSDMoneyAccountRequest = new GetSDMoneyAccountRequest();

		for (Transaction transaction : transactionList) {

			FraudTransactionDetails fraudTransactionDetails = new FraudTransactionDetails();

			getSDMoneyAccountRequest.setSdIdentity(transaction.getUserId());

			GetSDMoneyAccountResponse getSDMoneyAccountResponse = null;
			try {
				getSDMoneyAccountResponse = walletService.getSDMoneyAccount(getSDMoneyAccountRequest);
			} catch (InfoPanelException e) {
				e.printStackTrace();
			}

			if (getSDMoneyAccountResponse == null)
				continue;

			String walletAccountStatus = getSDMoneyAccountResponse.getSdMoneyAccountStatus().toString();

			fraudTransactionDetails.setRunningBalance(transaction.getRunningBalance().getTotalBalance().toString());
			fraudTransactionDetails.setTransactionType(transaction.getBusinessTransactionType().name());
			fraudTransactionDetails.setTransactionRefrence(transaction.getTransactionReference());
			fraudTransactionDetails.setVoucherBalance(transaction.getRunningBalance().getVoucherBalance().toString());
			fraudTransactionDetails.setGeneralBalance(transaction.getRunningBalance().getGeneralBalance().toString());
			fraudTransactionDetails.setMerchantName(transaction.getBussinessEntity());

			if (transaction.getType() == TransactionType.DEBIT_WITHDRAW) {

				try {
					GetMoneyOutStatusRequest getMoneyOutStatusRequest = new GetMoneyOutStatusRequest();
					getSDMoneyAccountRequest.setSdIdentity(transaction.getUserId());
					getMoneyOutStatusRequest.setTransactionRef(transaction.getTransactionReference());

					GetMoneyOutStatusResponse getMoneyOutStatusResponse = sdMoneyClient
							.getMoneyOutStatus(getMoneyOutStatusRequest);
					getMoneyOutStatusResponse.getStatus();

					GetBankDetailsRequest getBankDetailsRequest = new GetBankDetailsRequest();
					getBankDetailsRequest.setAccountToken(getMoneyOutStatusResponse.getAccountToken());

					GetBankDetailsResponse getBankDetailsResponse;
					getBankDetailsResponse = bankDetailStoreClient.getBankDetails(getBankDetailsRequest);

					BankAccountDetails bankAccountDetails = getBankDetailsResponse.getAccountDetails();
					fraudTransactionDetails.setBankAccountName(bankAccountDetails.getBankName());
					fraudTransactionDetails.setBankIFSCCode(bankAccountDetails.getIfsc());
					fraudTransactionDetails.setBankAccountNumber(bankAccountDetails.getAccountNumber());
				} catch (Exception e) {

					fraudTransactionDetails.setBankAccountName("Unable to get bank account name");
					fraudTransactionDetails.setBankIFSCCode("Unable to get bank account name");
					fraudTransactionDetails.setBankAccountNumber("Unable to get bank account name");
				}

			}

			fraudTransactionDetailsList.add(fraudTransactionDetails);

		}

		return fraudTransactionDetailsList;
	}

}
