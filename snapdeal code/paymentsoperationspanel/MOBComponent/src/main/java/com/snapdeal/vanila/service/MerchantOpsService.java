package com.snapdeal.vanila.service;

import java.util.List;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.vanila.dto.MerchantCallHistoryEntity;
import com.snapdeal.vanila.request.GetAttemptsRequest;
import com.snapdeal.vanila.request.GetMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.InsertMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.MerchantPointOfContactRequest;
import com.snapdeal.vanila.response.MerchantPointOfContactResponse;


public interface MerchantOpsService {

	public List<MerchantCallHistoryEntity> getCallsHistoryByMerchantId(String merchantId) throws OpsPanelException;

	public List<MerchantCallHistoryEntity> getMerchantContactHistory(
			GetMerchantCallHistoryRequest getMerchantCallHistoryRequest) throws OpsPanelException;

	public StringBuffer downloadMerchantContactHistory(
			GetMerchantCallHistoryRequest getMerchantCallHistoryRequest) throws OpsPanelException;

	 public void insertMerchantContactHistory(InsertMerchantCallHistoryRequest insertMerchantCallHistoryRequest)
			throws OpsPanelException;
	 
	 public int getAttemptsCountForMerchantAndCallType(GetAttemptsRequest getAttemptsRequest) throws OpsPanelException;

	public void insertMerchantPointOfContact(MerchantPointOfContactRequest merchantPointOfContactRequest)
			throws OpsPanelException;

	public List<MerchantPointOfContactResponse> getMerchantPointOfContact(
			MerchantPointOfContactRequest merchantPointOfContactRequest) throws OpsPanelException;

	public void updateMerchantPointOfContact(MerchantPointOfContactRequest merchantPointOfContactRequest)
			throws OpsPanelException;

}
