package com.snapdeal.opspanel.userpanel.service;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.FraudReverseLoadMoneyRequest;
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
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.SuspendSDMoneyAccountRequest;

public interface WalletService {


   public GetTransactionsForUserResponse getTransactionsForUser(GetTransactionsForUserRequest getTransactionsForUserRequest) throws InfoPanelException;
   public GetTransactionByIdResponse getTransactionById(GetTransactionByIdRequest getTransactionByIdRequest) throws InfoPanelException;
   public void suspendSDMoneyAccount( SuspendSDMoneyAccountRequest suspendSDMoneyAccountRequest ) throws InfoPanelException;
   public GetSDMoneyAccountResponse getSDMoneyAccount( GetSDMoneyAccountRequest getSDMoneyAccountRequest ) throws InfoPanelException;
   public GetMoneyOutStatusResponse getMoneyOutStatus( GetMoneyOutStatusRequest getMoneyOutStatusRequest ) throws InfoPanelException;
   public GetTransactionsByReferenceResponse getTransactionsByReference( GetTransactionsByReferenceRequest getTransactionsByReferenceRequest ) throws InfoPanelException;	
   public ReverseLoadMoneyResponse reverseLoadMoney(FraudReverseLoadMoneyRequest fraudReverseLoadMoneyRequest) throws InfoPanelException;
   public void enableWallet(EnableSDMoneyAccountRequest enableSDMoneyAccountRequest ) throws InfoPanelException;

}
