package com.snapdeal.opspanel.userpanel.service;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.GetBlockedTxnsResponse;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsResponse;

@Service
public interface BlockedTransactionHistory {
	
	public GetBlockedTxnsResponse getUserTransactionDetails(GetUserTransactionDetailsRequest request) throws OpsPanelException, InfoPanelException;

}
