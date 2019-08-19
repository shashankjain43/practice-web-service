package com.snapdeal.payments.view.dao;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.RequestViewEntity;
import com.snapdeal.payments.view.request.commons.dto.RVTransactionDto;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchCursorRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;

public interface IRequestViewDao {

	public List<RequestViewEntity> getRequestViewTransactionDetails(
			Map<String,String> txnDetails);
	
	public void updateTxnDetailsOfP2PTxnDetails(List<Map<String,Object>> updateRVTxnDetails);
	
	public void saveRequestViewEntity(List<RequestViewEntity> requestViewEntity) ;
	
	
	public List<RVTransactionDto> getRVTransactionDetails(GetRequestViewSearchRequest request) ;
	
	public List<RVTransactionDto> getRVTxnsCursor(GetRequestViewSearchCursorRequest request) ;
	
	public void healthCheckForRequestView() ;
	
}
