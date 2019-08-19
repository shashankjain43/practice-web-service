package com.snapdeal.opspanel.userpanel.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.UserTransaction;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.GetBlockedTxnsResponse;
import com.snapdeal.opspanel.userpanel.service.BlockedTransactionHistory;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsResponse;
import com.snapdeal.payments.fps.aht.model.UserTransactionDetails;
import com.snapdeal.payments.fps.aht.model.UserTransactionEntityType;
import com.snapdeal.payments.fps.client.FPSClient;
import com.snapdeal.payments.fps.exception.FPSException;

@Slf4j
@Service
public class BlockedTransactionHistoryImpl implements BlockedTransactionHistory{

	@Autowired
	FPSClient fps;
	
	@Autowired
	SearchUserServices searchUserServices;
	
	
	@Override
	public GetBlockedTxnsResponse getUserTransactionDetails(
			GetUserTransactionDetailsRequest request) throws OpsPanelException, InfoPanelException {
		GetUserTransactionDetailsResponse response =  new GetUserTransactionDetailsResponse();
		
		String query = request.getEntityId();
		
		String userId = query;
		GetUserResponse getUserResponse = new GetUserResponse();
		
		if(request.getEntityType().equals(UserTransactionEntityType.USER_ID)){
			if(StringUtils.isNumeric(query)){
				GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
				getUserByMobileRequest.setMobileNumber(query);
				getUserResponse = searchUserServices.searchUserByMobile(getUserByMobileRequest);
				if(getUserResponse != null && getUserResponse.getUserDetails() != null){
					userId = getUserResponse.getUserDetails().getUserId();
					request.setEntityId(userId);
				} else {
					log.info("getUserResponse = NULL OR getUserResponse.getUserDetails() = NULL... \n");
				}
			} else if (Pattern.compile(".+@.+\\.[a-z]+").matcher(query).matches()){
				GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
				getUserByEmailRequest.setEmailId(query);
				getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);
				if(getUserResponse != null && getUserResponse.getUserDetails() != null){
					userId = getUserResponse.getUserDetails().getUserId();
					request.setEntityId(userId);
				} else {
					log.info("getUserResponse = NULL OR getUserResponse.getUserDetails() = NULL... \n");
				}
			} else {
				request.setEntityId(userId);
			}
		}
		
		try {
			response = fps.getUserTransactionDetails(request);
		} catch (FPSException e) {
			log.info("FPSException while calling getUserTransactionDetails ...");
			log.info("FPSException : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(),  "FPS");
		} catch (Exception e) {
			log.info("OtherException while calling getUserTransactionDetails ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUNTIME", e.getMessage(),  "FPS");
			
		}
		GetBlockedTxnsResponse getBlockedTxnsResponse =  new GetBlockedTxnsResponse();
		List<UserTransactionDetails> list = new ArrayList<UserTransactionDetails>();
		String lastEvaluatedKey =  null;
		String startDate = null;
		String endDate = null;
		DateFormat dateFormatter = new SimpleDateFormat("EEE dd/MM/yyyy hh:mm:ss a z");
		if(response != null){
			try {
				lastEvaluatedKey = response.getLastEvaluatedKey();
				getBlockedTxnsResponse.setLastEvaluatedKey(lastEvaluatedKey);
				List<UserTransactionDetails> fullList = new ArrayList<UserTransactionDetails>();
				fullList = response.getListOfTransactions();
				if(fullList != null && fullList.size() != 0){
					int size = fullList.size();
					
					UserTransactionDetails ut = fullList.get(0);
					Date date;
					if (ut != null) {
						date = ut.getCreatedOn();
						endDate = dateFormatter.format(date);
					}
					ut = fullList.get(size-1);
					if (ut != null) {
						date = ut.getCreatedOn();
						startDate = dateFormatter.format(date);
					}
					getBlockedTxnsResponse.setList(fullList);
					getBlockedTxnsResponse.setStartDate(startDate);
					getBlockedTxnsResponse.setEndDate(endDate);
				} else{
					log.info("listOfTransactions either empty or NULL! ...");
				}
			} catch (Exception e) {
				log.info("Exception Occured while filling CHECK/DENY transactions ...");
				log.info("Exception = [" + e.getMessage() + "]");
			}
		}
		return getBlockedTxnsResponse;
		
	}

}
