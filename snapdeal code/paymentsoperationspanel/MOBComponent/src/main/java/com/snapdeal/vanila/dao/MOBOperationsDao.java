package com.snapdeal.vanila.dao;

import java.util.List;

import com.snapdeal.vanila.dto.MerchantCallHistoryEntity;
import com.snapdeal.vanila.request.GetAttemptsRequest;
import com.snapdeal.vanila.request.GetMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.MerchantPointOfContactRequest;
import com.snapdeal.vanila.response.MerchantPointOfContactResponse;

public interface MOBOperationsDao {

//	public List<MerchantOpsCallEntity> getMerchantContactsData(String merchantIds);

	public List<MerchantCallHistoryEntity> getMerchantCallHistory(
			GetMerchantCallHistoryRequest getMerchantCallHistoryRequest);

	public void insertMerchantCallHistory(MerchantCallHistoryEntity merchantCallHistoryEntity);
	
	public int getAttemptsForMerchantandCallType(GetAttemptsRequest request);

	public void insertMerchantPointOfContact(MerchantPointOfContactRequest request);

	public List<MerchantPointOfContactResponse> getMerchantPointOfContact(MerchantPointOfContactRequest request);

	public void updateMerchantPointOfContact(MerchantPointOfContactRequest request);
	
/*	public MerchantOpsEntity getMerchantOpsDetailsById(String merchantId);

	public void insertOPSMerchantData(MerchantOpsEntity merchantOpsEntity);

	public void updateMerchantOpsData(MerchantOpsEntity merchantOpsEntity);*/

}
