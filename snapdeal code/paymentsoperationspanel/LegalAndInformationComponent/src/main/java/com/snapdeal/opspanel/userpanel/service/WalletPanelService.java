package com.snapdeal.opspanel.userpanel.service;

import java.util.List;

import org.json.JSONObject;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.SearchTxnResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;

public interface WalletPanelService {


   public SearchTxnResponse getTransactionsForUser( SearchTransactionRequest request ) throws InfoPanelException;
   public List<TransactionDetails> getTransactionsById( SearchTransactionRequest request ) throws InfoPanelException;
   public List<TransactionDetails> getTransactionByReference( SearchTransactionRequest request ) throws InfoPanelException;
   public void suspendSDMoneyAccount( SuspendWalletRequest request ) throws InfoPanelException;
   public void enableSDMoneyAccount(EnableWalletRequest enableWalletRequest)throws InfoPanelException;
   public JSONObject makeHttpCallOnFcLoadingTransaction(String transactionId) throws InfoPanelException;
}
