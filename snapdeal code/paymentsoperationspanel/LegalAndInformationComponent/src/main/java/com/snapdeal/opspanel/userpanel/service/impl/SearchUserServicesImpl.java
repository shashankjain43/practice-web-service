package com.snapdeal.opspanel.userpanel.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.snapdeal.ims.client.IActivityServiceClient;
import com.snapdeal.ims.client.IDashBoardServiceClient;
import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetActivityRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.UserSearchRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.response.GetActivityResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchTxnByIdFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnByIdempotencyIdFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnByReferenceFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchWithdrawalFlowRequest;
import com.snapdeal.opspanel.userpanel.response.SearchTxnByIdFlowResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnFlowResponse;
import com.snapdeal.opspanel.userpanel.response.SearchWithdrawalFlowResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionSummaryRow;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.opspanel.userpanel.service.WalletPanelService;
import com.snapdeal.opspanel.userpanel.service.WalletService;
import com.snapdeal.opspanel.userpanel.utils.SuperCSVWriter;
import com.snapdeal.payments.sdmoney.client.BankDetailsStoreClient;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.Balance;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusResponse;
import com.snapdeal.payments.sdmoney.service.model.GetPendingGeneralBalanceLimitsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetPendingGeneralBalanceLimitsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetSDMoneyAccountResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetUserBankDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetUserBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetVoucherBalanceDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetVoucherBalanceDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetVouchersForTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.GetVouchersForTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.Transaction;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
import com.snapdeal.payments.sdmoney.service.model.type.TransactionType;
import com.snapdeal.payments.sdmoney.service.model.type.VoucherTransaction;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("SearchUserServicesImpl")
@Slf4j
public class SearchUserServicesImpl implements SearchUserServices {

	@Autowired
	IUserServiceClient iUserService;

	@Autowired
	IUserMigrationServiceClient iUserMigrationService;

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Autowired
	WalletService walletService;

	@Autowired
	WalletPanelService walletPanelService;

	@Autowired
	IDashBoardServiceClient dashboardClientService;

	@Autowired
	BankDetailsStoreClient bankDetailsStoreClient;

	@Autowired
	IActivityServiceClient iActivityServiceClient;

	public GetUserResponse searchUserByEmail(GetUserByEmailRequest request) throws InfoPanelException {

		GetUserResponse response;
		try {
			response = iUserService.getUserByEmail(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getUserByEmail : WILL BE RETRIED...");
			try {
				response = iUserService.getUserByEmail(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getUserByEmail : FAILURE ...");
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "IUserServiceClient");
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getUserByEmail : FAILURE ...");
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
			}
		} catch (ServiceException se) {
			log.info("Exception from IUserServiceClient while searching for user by email " + se);
			// throw new InfoPanelException(se.getErrCode(), "Exception while
			// while searching for user by email", "IUserServiceClient");
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response;
	}

	public GetUserResponse searchUserById(GetUserByIdRequest request) throws InfoPanelException {

		GetUserResponse response;
		try {
			response = iUserService.getUserById(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getUserById : WILL BE RETRIED...");
			try {
				response = iUserService.getUserById(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getUserById : FAILURE ...");
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "IUserServiceClient");
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getUserById : FAILURE ...");
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
			}
		} catch (ServiceException se) {
			log.info("Exception from IUserServiceClient while searching for user by userId " + se);
			// throw new InfoPanelException(se.getErrCode(), "Exception while
			// while searching for user by userId", "IUserServiceClient");
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response;
	}

	public GetUserResponse searchUserByMobile(GetUserByMobileRequest request) throws InfoPanelException {

		GetUserResponse response;
		try {
			response = iUserService.getUserByVerifiedMobile(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getUserByVerifiedMobile : WILL BE RETRIED...");
			try {
				response = iUserService.getUserByVerifiedMobile(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getUserByVerifiedMobile : FAILURE ...");
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "IUserServiceClient");
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getUserByVerifiedMobile : FAILURE");
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
			}
		} catch (ServiceException se) {
			log.info("Exception from IUserServiceClient while searching for user by mobile " + se);
			// throw new InfoPanelException(se.getErrCode(), "Exception while
			// while searching for user by mobile", "IUserServiceClient");
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response;
	}

	public UserUpgradationResponse getUserMigrationStatus(UserUpgradeByEmailRequest request) throws InfoPanelException {
		UserUpgradationResponse response;
		try {
			response = iUserMigrationService.userUpgradeStatus(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getting userUpgradeStatus : WILL BE RETRIED...");
			try {
				response = iUserMigrationService.userUpgradeStatus(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getting userUpgradeStatus : FAILURE ...");
				return null;
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getting userUpgradeStatus: FAILURE ...");
				return null;
			}
		} catch (ServiceException se) {
			log.info("Exception from IUserMigrationServiceClient while fetching userUpgradeStatus " + se);
			return null;
		}
		return response;
	}

	public UserSearchResponse userSearch(UserSearchRequest request) throws InfoPanelException {
		UserSearchResponse response = new UserSearchResponse();
		try {
			response = dashboardClientService.UserSearch(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while UserSearch : WILL BE RETRIED...");
			try {
				response = dashboardClientService.UserSearch(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while UserSearch : FAILURE ...");
				return null;
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getting userUpgradeStatus: FAILURE ...");
				return null;
			}
		} catch (ServiceException se) {
			log.info("Exception from IDashBoardServiceClient while searching for user " + se);
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");

		}
		return response;
	}

	public GetUserBankDetailsResponse getUserBankDetails(String imsId) throws SDMoneyException, InfoPanelException {
		GetUserBankDetailsRequest request = new GetUserBankDetailsRequest();
		request.setSdIdentity(imsId);
		try {
			return bankDetailsStoreClient.getUserBankDetails(request);
		} catch (SDMoneyException e) {
			log.info(" RETRY : SDMoneyException from sdmoneyclient while getuserbankDetails "
					+ ExceptionUtils.getFullStackTrace(e));
			throw new InfoPanelException(e.getErrorCode().toString(), e.getMessage(), "BankDetailsStoreClient");
		}
	}

	public String getWalletAccountStatus(String userId) throws InfoPanelException {
		String walletAccountStatus = "Could not fetch";
		GetSDMoneyAccountRequest request = new GetSDMoneyAccountRequest();
		GetSDMoneyAccountResponse response = new GetSDMoneyAccountResponse();
		request.setSdIdentity(userId);
		try {
			response = sdMoneyClient.getSDMoneyAccount(request);
		} catch (InternalClientException ice) {
			log.info("InternalClientException while getSDMoneyAccount : WILL BE RETRIED ...");
			try {
				response = sdMoneyClient.getSDMoneyAccount(request);
			} catch (InternalClientException ice2) {
				log.info("InternalClientException while getSDMoneyAccount : FAILURE ...");
				throw new InfoPanelException(ice2.getErrorCode().toString(), ice2.getMessage(), "IUserServiceClient");
			} catch (SDMoneyException sdMoneyException) {
				log.info(" RETRY : SDMoneyException from sdmoneyclient while getting VoucherBalanceDetails "
						+ sdMoneyException);
				throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
						"IUserServiceClient");
			}
		} catch (SDMoneyException sdMoneyException) {
			log.info(" SDMoneyException from sdmoneyclient while getting SDMoneyAccount Details " + sdMoneyException);
			// throw new
			// InfoPanelException(sdMoneyException.getErrorCode().toString(),
			// "Exception while getting SDMoneyAccount Details",
			// "IUserServiceClient");
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
					"IUserServiceClient");
		}
		if (response != null) {
			walletAccountStatus = response.getSdMoneyAccountStatus().toString();
		}
		return walletAccountStatus;
	}

	public String getPendingLimit(String userId) {
		GetPendingGeneralBalanceLimitsRequest request = new GetPendingGeneralBalanceLimitsRequest();
		request.setSdIdentity(userId);
		GetPendingGeneralBalanceLimitsResponse response = null;
		try {
			response = sdMoneyClient.getPendingGeneralBalanceLimits(request);
		} catch (Exception sdme) {
			try {
				response = sdMoneyClient.getPendingGeneralBalanceLimits(request);
			} catch (Exception e) {
				log.info("error while trying to get pending limit " + e.getMessage());
				return "could not fetch";
			}
		}
		BigDecimal finalBalance = response.getBalanceLimit();
		finalBalance = finalBalance.setScale(3, RoundingMode.HALF_DOWN);
		return finalBalance.toPlainString();
	}

	public BigDecimal getGeneralAccountBalance(String userId) throws InfoPanelException {
		BigDecimal generalAccountBalance = null;
		GetAccountBalanceRequest request = new GetAccountBalanceRequest();
		GetAccountBalanceResponse response = new GetAccountBalanceResponse();
		request.setSdIdentity(userId);
		try {
			response = sdMoneyClient.getAccountBalance(request);
		} catch (InternalClientException ice) {
			log.info("InternalClientException while getAccountBalance : WILL BE RETRIED ...");
			try {
				response = sdMoneyClient.getAccountBalance(request);
			} catch (InternalClientException ice2) {
				log.info("InternalClientException while getAccountBalance : FAILURE ...");
				throw new InfoPanelException(ice2.getErrorCode().toString(), ice2.getMessage(), "IUserServiceClient");
			} catch (SDMoneyException sdMoneyException) {
				log.info(" RETRY : SDMoneyException from sdmoneyclient while getAccountBalance " + sdMoneyException);
				throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
						"IUserServiceClient");
			}
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting AccountBalance " + sdMoneyException);
			// throw new
			// InfoPanelException(sdMoneyException.getErrorCode().toString(),
			// "Exception while getting AccountBalance", "IUserServiceClient");
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
					"IUserServiceClient");

		}
		if (response != null) {
			Balance bal = response.getBalance();
			if (bal != null) {
				generalAccountBalance = bal.getGeneralBalance();
			} else {
				log.info("Balance for " + userId + " is NULL ...");
			}
		} else {
			log.info("GetAccountBalanceResponse for " + userId + " is NULL ...");
		}
		return generalAccountBalance;
	}

	public BigDecimal getVoucherAccountBalance(String userId) throws InfoPanelException {
		BigDecimal voucherAccountBalance = null;
		GetAccountBalanceRequest request = new GetAccountBalanceRequest();
		GetAccountBalanceResponse response = new GetAccountBalanceResponse();
		request.setSdIdentity(userId);
		try {
			response = sdMoneyClient.getAccountBalance(request);
		} catch (InternalClientException ice) {
			log.info("InternalClientException while getAccountBalance : WILL BE RETRIED ...");
			try {
				response = sdMoneyClient.getAccountBalance(request);
			} catch (InternalClientException ice2) {
				log.info("InternalClientException while getAccountBalance : FAILURE ...");
				throw new InfoPanelException(ice2.getErrorCode().toString(), ice2.getMessage(), "IUserServiceClient");
			} catch (SDMoneyException sdMoneyException) {
				log.info(" RETRY : SDMoneyException from sdmoneyclient while getAccountBalance " + sdMoneyException);
				throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
						"IUserServiceClient");
			}
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting AccountBalance " + sdMoneyException);
			// throw new
			// InfoPanelException(sdMoneyException.getErrorCode().toString(),
			// "Exception while getting AccountBalance", "IUserServiceClient");
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
					"IUserServiceClient");
		}
		if (response != null) {
			Balance bal = response.getBalance();
			if (bal != null) {
				voucherAccountBalance = bal.getVoucherBalance();
			} else {
				log.info("Balance for " + userId + " is NULL ...");
			}
		} else {
			log.info("GetAccountBalanceResponse for " + userId + " is NULL ...");
		}
		return voucherAccountBalance;
	}

	public GetVoucherBalanceDetailsResponse getAllVouchers(String userId) throws InfoPanelException {
		GetVoucherBalanceDetailsRequest request = new GetVoucherBalanceDetailsRequest();
		GetVoucherBalanceDetailsResponse response = new GetVoucherBalanceDetailsResponse();
		request.setSdIdentity(userId);
		try {
			response = sdMoneyClient.getVoucherBalanceDetails(request);
		} catch (InternalClientException ice) {
			log.info("InternalClientException while getVoucherBalanceDetails : WILL BE RETRIED ...");
			try {
				response = sdMoneyClient.getVoucherBalanceDetails(request);
			} catch (InternalClientException ice2) {
				log.info("InternalClientException while getVoucherBalanceDetails : FAILURE ...");
			} catch (SDMoneyException sdMoneyException) {
				log.info(" RETRY : Exception from sdmoneyclient while getting VoucherBalanceDetails "
						+ sdMoneyException);
				throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
						"IUserServiceClient");
			}
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting VoucherBalanceDetails " + sdMoneyException);
			// throw new
			// InfoPanelException(sdMoneyException.getErrorCode().toString(),
			// "Exception while getting VoucherBalanceDetails",
			// "IUserServiceClient");
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
					"IUserServiceClient");
		}
		return response;
	}

	public byte[] getTransactionHistoryAsCSV(String emailId) throws InfoPanelException {
		GetTransactionsForUserRequest request = new GetTransactionsForUserRequest();
		request.setUserId(emailId);
		request.setPageSize(50);
		GetTransactionsForUserResponse response = new GetTransactionsForUserResponse();
		try {
			response = sdMoneyClient.getTransactionsForUser(request);
		} catch (InternalClientException ice) {
			log.info("InternalClientException while getTransactionsForUser : WILL BE RETRIED ...");
			try {
				response = sdMoneyClient.getTransactionsForUser(request);
			} catch (InternalClientException ice2) {
				log.info("InternalClientException while getTransactionsForUser : FAILURE ...");
				throw new InfoPanelException(ice2.getErrorCode().toString(), ice2.getMessage(), "IUserServiceClient");
			} catch (SDMoneyException sdMoneyException) {
				log.info(" RETRY : SDMoneyException from sdmoneyclient while getTransactionsForUser "
						+ sdMoneyException);
				throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
						"IUserServiceClient");
			}
		} catch (SDMoneyException sdMoneyException) {
			log.info(" Exception from sdmoneyclient while getting TransactionsForUser " + sdMoneyException);
			// throw new
			// InfoPanelException(sdMoneyException.getErrorCode().toString(),
			// "Exception while getting
			// TransactionsForUser","IUserServiceClient");
			throw new InfoPanelException(sdMoneyException.getErrorCode().toString(), sdMoneyException.getMessage(),
					"IUserServiceClient");
		}

		List<TransactionSummary> txnList = response.getListTransaction();
		List<TransactionSummaryRow> tRows = new ArrayList<TransactionSummaryRow>();

		for (TransactionSummary ts : txnList) {

			TransactionSummaryRow tsr = new TransactionSummaryRow();
			tsr.setDate(ts.getTimestamp());
			tsr.setTransactionAmount(ts.getTransactionAmount().toString());
			tsr.setType(ts.getTransactionType().toString());
			tsr.setTxnRef(ts.getTransactionReference());
			tsr.setPostTxnBalance(ts.getRunningBalance().toString());

			GetVouchersForTransactionResponse resp = new GetVouchersForTransactionResponse();
			GetVouchersForTransactionRequest req = new GetVouchersForTransactionRequest();
			req.setTransactionId(ts.getTransactionId());
			try {
				resp = sdMoneyClient.getVouchersForTransaction(req);
			} catch (InternalClientException ice) {
				log.info("InternalClientException while getVouchersForTransaction : WILL BE RETRIED ...");
				try {
					resp = sdMoneyClient.getVouchersForTransaction(req);
				} catch (InternalClientException ice2) {
					log.info("InternalClientException while getVouchersForTransaction : FAILURE ...");
					tsr.setGeneralBalanceTxnAmount("Could not fetch.");
					tsr.setGiftVoucherTxnAmount("Could not fetch.");
				} catch (SDMoneyException sdMoneyException) {
					log.info(" RETRY : SDMoneyException from sdmoneyclient while getVouchersForTransaction "
							+ sdMoneyException);
					tsr.setGeneralBalanceTxnAmount("Could not fetch.");
					tsr.setGiftVoucherTxnAmount("Could not fetch.");
				}
			} catch (SDMoneyException e) {

				log.info("Exception while getting voucher list for transaction id " + ts.getTransactionId());
				tsr.setGeneralBalanceTxnAmount("Could not fetch.");
				tsr.setGiftVoucherTxnAmount("Could not fetch.");

			}

			BigDecimal voucherTxnAmount = BigDecimal.ZERO;
			if (resp != null) {
				List<VoucherTransaction> vouchers = new ArrayList<VoucherTransaction>();
				vouchers = resp.getVoucherTransactionList();
				if (vouchers != null) {
					for (VoucherTransaction vt : vouchers) {
						voucherTxnAmount = voucherTxnAmount.add(vt.getTransactionAmount());
					}
				}
			}
			tsr.setGeneralBalanceTxnAmount(resp.getGeneralBalanceTransactionAmount().toString());
			tsr.setGiftVoucherTxnAmount(voucherTxnAmount.toString());
			tRows.add(tsr);

		}

		CellProcessor[] processors = new CellProcessor[] { new FmtDate("EEE, dd/MM/yyyy hh:mm:ss a z"), // Date
				new NotNull(), // TransactionAmount
				new NotNull(), // GeneralBalanceTxnAmount
				new NotNull(), // GiftVoucherTxnAmount
				new NotNull(), // Type
				new NotNull(), // TxnRef
				new NotNull() // PostTxnBalance
		};

		String[] header = { "date", "transactionAmount", "generalBalanceTxnAmount", "giftVoucherTxnAmount", "type",
				"txnRef", "postTxnBalance" };

		SuperCSVWriter csvWriter = new SuperCSVWriter(processors, header);
		byte[] userHistory = csvWriter.createCSV(tRows);

		return userHistory;

	}

	@Override
	public GetActivityResponse getActivity(GetActivityRequest paramGetActivityRequest)
			throws ServiceException, InfoPanelException {
		GetActivityResponse response = new GetActivityResponse();
		try {
			response = iActivityServiceClient.getActivity(paramGetActivityRequest);
		} catch (HttpTransportException e) {
			log.info("HttpTransportException while getActivity Logs : FAILURE ...");
			log.info("Going to retry For getActivityLogs ");
			try {
				response = iActivityServiceClient.getActivity(paramGetActivityRequest);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getActivity Logs : FAILURE ...");
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "iActivityServiceClient");
			} catch (ServiceException se) {
				log.info("RETRY : ServiceException while getActivity Logs : FAILURE ...");
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "iActivityServiceClient");
			}
		} catch (ServiceException se) {
			log.info("Exception from iActivityServiceClient while getActivity Logs for user" + se);
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response;

	}

	@Override
	public SearchTxnFlowResponse searchTxnFlow(SearchTxnFlowRequest request) throws InfoPanelException {
		GetUserResponse getUserResponse = null;
		String userId;
		SearchTxnFlowResponse flowResponse = new SearchTxnFlowResponse();
		try {

			if (StringUtils.isNumeric(request.getSearchKey())) {

				GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
				getUserByMobileRequest.setMobileNumber(request.getSearchKey());
				getUserResponse = searchUserByMobile(getUserByMobileRequest);
				userId=getUserResponse.getUserDetails().getUserId();
			} else if (Pattern.compile(".+@.+\\.[a-z]+").matcher(request.getSearchKey()).matches()) {

				GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
				getUserByEmailRequest.setEmailId(request.getSearchKey());
				getUserResponse = searchUserByEmail(getUserByEmailRequest);
				userId=getUserResponse.getUserDetails().getUserId();
			} else {

				GetUserByIdRequest getUserByIdrequest = new GetUserByIdRequest();
				userId=request.getSearchKey();

			}

			GetTransactionsRequest walletRequest = request.getSearchTxnRequest();
			walletRequest.setSdIdentity(userId);
			walletRequest.setPageSize(50);

			GetTransactionsResponse walletResponse = sdMoneyClient.getTransactions(walletRequest);

			flowResponse.setTxnSummaryList(walletResponse.getTransactions());
			flowResponse.setUserId(userId);
			flowResponse.setLastEvaluatedKey(walletResponse.getLastEvaluatedKey());
			Collections.sort(flowResponse.getTxnSummaryList(), new Comparator<TransactionSummary>() {
				public int compare(TransactionSummary o1, TransactionSummary o2) {
					return o2.getTimestamp().compareTo(o1.getTimestamp());
				}
			});

		} catch (InfoPanelException ipe) {
			throw ipe;
		} catch (SDMoneyException sdme) {
			throw sdme;
		} catch (Exception e) {
			throw e;
		}
		return flowResponse;
	}

	@Override
	public SearchTxnByIdFlowResponse searchTxnByIdFlow(SearchTxnByIdFlowRequest request) throws InfoPanelException {
		GetTransactionByIdResponse walletResponse = null;
		SearchTxnByIdFlowResponse response = new SearchTxnByIdFlowResponse();

		try {

			walletResponse = sdMoneyClient.getTransactionById(request.getRequest());

			response.setTransactionByIdResponse(walletResponse);
		} catch (SDMoneyException sdme) {
			throw sdme;
		} catch (Exception e) {
			throw e;
		}

		return response;
	}

	@Override
	public SearchWithdrawalFlowResponse searchWithdrawal(SearchWithdrawalFlowRequest request)
			throws InfoPanelException {
		SearchWithdrawalFlowResponse searchWithdrawalFlowResponse = new SearchWithdrawalFlowResponse();

		try {
			GetMoneyOutStatusResponse moneyOutStatusResponse = walletService
					.getMoneyOutStatus(request.getMoneyOutStatusRequest());

			String bankAccountToken = moneyOutStatusResponse.getAccountToken();

			GetBankDetailsRequest getBankDetailsRequest = new GetBankDetailsRequest();
			getBankDetailsRequest.setAccountToken(bankAccountToken);

			GetBankDetailsResponse bankDetailsResponse = bankDetailsStoreClient
					.getBankDetails(getBankDetailsRequest);

			searchWithdrawalFlowResponse.setBankDetailsResponse(bankDetailsResponse);
			searchWithdrawalFlowResponse.setError( moneyOutStatusResponse.getReason() );
			searchWithdrawalFlowResponse.setTransactionStatus( moneyOutStatusResponse.getStatus().toString() );

		} catch (SDMoneyException sdme) {
			throw sdme;
		} catch (Exception e) {
			log.info("Exception while getting sdmoney account status: " + ExceptionUtils.getFullStackTrace(e));
			throw e;
		}

		return searchWithdrawalFlowResponse;
	}

	@Override
	public SearchTxnByIdFlowResponse searchTxnByReferenceFlow(SearchTxnByReferenceFlowRequest request)
			throws InfoPanelException {
		SearchTxnByIdFlowResponse response = new SearchTxnByIdFlowResponse();


		List<Transaction> transactionsList = new ArrayList<Transaction>();
		try {
			String transactionReference = request.getTransactionsByReferenceRequest().getTransactionReference();

			GetTransactionsByReferenceRequest getTransactionsByReferenceRequest = new GetTransactionsByReferenceRequest();
			getTransactionsByReferenceRequest.setTransactionReference(transactionReference);

			GetTransactionsByReferenceResponse getTransactionByReferenceResponse = walletService
					.getTransactionsByReference(getTransactionsByReferenceRequest);

			List<TransactionSummary> transactionSummaryList = getTransactionByReferenceResponse.getListTransaction();

			Set<String> uniqueTransactionId = new HashSet<String>();

			for (TransactionSummary transactionSummary : transactionSummaryList) {

				uniqueTransactionId.add(transactionSummary.getTransactionId());
			}

			try {
				JSONObject addCashJson = walletPanelService
						.makeHttpCallOnFcLoadingTransaction(transactionReference);
				response.setAddCashJson(addCashJson.toString());
			} catch (Exception e) {
				log.info("Exception while getting add cash json " + e.getMessage());

			}

			for (String transactionId : uniqueTransactionId) {

				try {
					SearchTxnByIdFlowRequest searchTxnByIdFlowRequest = new SearchTxnByIdFlowRequest();

					GetTransactionByIdRequest getTransactionByIdRequest = new GetTransactionByIdRequest();
					getTransactionByIdRequest.setTransactionId(transactionId);

					searchTxnByIdFlowRequest.setRequest(getTransactionByIdRequest);

					SearchTxnByIdFlowResponse searchTxnByIdFlowResponse = searchTxnByIdFlow(searchTxnByIdFlowRequest);
					List<Transaction> transactions = searchTxnByIdFlowResponse.getTransactionByIdResponse()
							.getTransactions();

					transactionsList.addAll(searchTxnByIdFlowResponse.getTransactionByIdResponse().getTransactions());

				} catch (InfoPanelException ipe) {
					log.info("InfoPanelException "+ipe.getErrCode()+" : "+ipe.getErrMessage());
				} catch (SDMoneyException sdme) {
					log.info("InfoPanelException "+sdme.getErrorCode()+" : "+sdme.getMessage());
				} catch (Exception e) {
					log.info("Exception occured: "+e.getMessage());
				}
			}

			GetTransactionByIdResponse transactionByIdResponse = new GetTransactionByIdResponse();
			transactionByIdResponse.setTransactions(transactionsList);

			response.setTransactionByIdResponse(transactionByIdResponse);

			Collections.sort(response.getTransactionByIdResponse().getTransactions(), new Comparator<Transaction>() {
				public int compare(Transaction o1, Transaction o2) {
					return o2.getTimestamp().compareTo(o1.getTimestamp());
				}
			});

		} catch (InfoPanelException ipe) {
			log.info("InfoPanelException "+ipe.getErrCode()+" : "+ipe.getErrMessage());
			throw ipe;
		} catch (SDMoneyException sdme) {
			log.info("InfoPanelException "+sdme.getErrorCode()+" : "+sdme.getMessage());
			throw sdme;
		} catch (Exception e) {
			log.info("Exception occured: "+e.getMessage());
			throw e;
		}

		return response;
	}

	@Override
	public SearchTxnByIdFlowResponse searchTxnByIdempotencyFlow(SearchTxnByIdempotencyIdFlowRequest request)
			throws InfoPanelException {
		SearchTxnByIdFlowResponse finalResponse= null;
		try {
			GetTransactionByIdempotencyIdResponse response=sdMoneyClient.getTransactionByIdempotencyId(request.getRequest());
			SearchTxnByIdFlowRequest searchTxnByIdFlowRequest= new SearchTxnByIdFlowRequest();
			finalResponse=searchTxnByIdFlow(searchTxnByIdFlowRequest);
		} catch (InfoPanelException ipe) {
			throw ipe;
		} catch (SDMoneyException sdme) {
			throw sdme;
		} catch (Exception e) {
			throw e;
		}

		return finalResponse;
	}
	
	@Override
	public GetAccountBalanceDetailsResponse getAccBalanceDetails(GetAccountBalanceDetailsRequest request) throws InfoPanelException {
		GetAccountBalanceDetailsResponse getAccountBalanceDetailsResponse = sdMoneyClient.getAccountBalanceDetails(request);	
		return getAccountBalanceDetailsResponse;
	}
}
