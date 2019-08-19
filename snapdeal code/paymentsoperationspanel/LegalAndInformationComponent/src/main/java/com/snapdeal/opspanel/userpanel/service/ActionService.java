package com.snapdeal.opspanel.userpanel.service;

import java.util.List;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountResponse;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.request.ViewUserAccountHistoryRequest;
import com.snapdeal.opspanel.userpanel.response.BlackListWhiteListUserResponse;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;
import com.snapdeal.opspanel.userpanel.response.ViewUserAccountResponse;

public interface ActionService {

   public SearchTxnResponse searchTransaction( SearchTransactionRequest request ) throws InfoPanelException;

	public EnableDisableUserResponse enableDisableUser(EnableDisableUserRequest enableDisableUserRequest) throws InfoPanelException;

	public void suspendWallet( SuspendWalletRequest suspendWalletRequest ) throws InfoPanelException;
	
	public void enableWallet(EnableWalletRequest enableWalletRequest) throws InfoPanelException;

	public BlackListWhiteListUserResponse blackListWhiteListUser(BlackListWhiteListUserRequest blackListWhiteListUserRequest) throws InfoPanelException;
	
	public CloseUserAccountResponse closeUserAccount(CloseUserAccountRequest closeUserAccountRequest) throws InfoPanelException;
	
	public List<ViewUserAccountResponse> viewUserHistoryAccount(ViewUserAccountHistoryRequest viewUserAccountRequest) throws InfoPanelException;

}
