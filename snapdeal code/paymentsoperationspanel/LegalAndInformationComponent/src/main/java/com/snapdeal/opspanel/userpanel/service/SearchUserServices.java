package com.snapdeal.opspanel.userpanel.service;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.snapdeal.opspanel.userpanel.response.GenericResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnByIdFlowResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnFlowResponse;
import com.snapdeal.opspanel.userpanel.response.SearchWithdrawalFlowResponse;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetVoucherBalanceDetailsResponse;

public interface SearchUserServices {
	
	public GetUserResponse searchUserByEmail(GetUserByEmailRequest request) throws InfoPanelException;
	
	public GetUserResponse searchUserById(GetUserByIdRequest request) throws InfoPanelException;
	
	public GetUserResponse searchUserByMobile(GetUserByMobileRequest request) throws InfoPanelException;
	
	public UserSearchResponse userSearch(UserSearchRequest request) throws InfoPanelException;
	
	public UserUpgradationResponse getUserMigrationStatus(UserUpgradeByEmailRequest request) throws InfoPanelException;
	
	public String getWalletAccountStatus(String userId) throws InfoPanelException;
	
	public BigDecimal getGeneralAccountBalance(String userId) throws InfoPanelException;
	
	public BigDecimal getVoucherAccountBalance(String userId) throws InfoPanelException;
	
	public GetVoucherBalanceDetailsResponse getAllVouchers(String userId) throws InfoPanelException;
	
	public byte[] getTransactionHistoryAsCSV(String emailId) throws InfoPanelException;
	
	public abstract GetActivityResponse getActivity(GetActivityRequest paramGetActivityRequest)
		    throws ServiceException, InfoPanelException;
	
	public SearchTxnFlowResponse searchTxnFlow(SearchTxnFlowRequest request) throws InfoPanelException;
	
	public SearchTxnByIdFlowResponse searchTxnByIdFlow(SearchTxnByIdFlowRequest request) throws InfoPanelException;
	
	public SearchWithdrawalFlowResponse searchWithdrawal(SearchWithdrawalFlowRequest request) throws InfoPanelException;
	
	public SearchTxnByIdFlowResponse searchTxnByReferenceFlow(SearchTxnByReferenceFlowRequest request) throws InfoPanelException;
	
	public SearchTxnByIdFlowResponse searchTxnByIdempotencyFlow(SearchTxnByIdempotencyIdFlowRequest request) throws InfoPanelException;

	public GetAccountBalanceDetailsResponse getAccBalanceDetails(GetAccountBalanceDetailsRequest request) throws InfoPanelException ;

}
