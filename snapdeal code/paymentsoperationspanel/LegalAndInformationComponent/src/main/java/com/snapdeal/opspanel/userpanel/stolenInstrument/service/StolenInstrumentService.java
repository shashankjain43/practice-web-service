package com.snapdeal.opspanel.userpanel.stolenInstrument.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.stolenInstrument.model.CategoryWiseTxnsInfo;
import com.snapdeal.opspanel.userpanel.stolenInstrument.request.SubmitRequest;
import com.snapdeal.opspanel.userpanel.stolenInstrument.response.SubmitResponse;
import com.snapdeal.opspanel.userpanel.stolenInstrument.service.impl.UserDeviceTransactionResponse;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsForUserResponse;

@Service
public interface StolenInstrumentService {
	
	
	public UserDeviceTransactionResponse getUserAndDeviceDetailsForTransaction(String transactionId) throws InfoPanelException, OpsPanelException;

	public CategoryWiseTxnsInfo getCategorizedTransactions(String transactionId) throws InfoPanelException;
	
	public SubmitResponse submit(SubmitRequest submitRequest);
}
