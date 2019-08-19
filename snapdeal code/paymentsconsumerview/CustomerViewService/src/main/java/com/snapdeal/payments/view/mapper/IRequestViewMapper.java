package com.snapdeal.payments.view.mapper;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.RequestViewEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionDto;
import com.snapdeal.payments.view.request.commons.dto.RVTransactionDto;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchCursorRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;

public interface IRequestViewMapper {

	public List<RequestViewEntity> getRequestViewTransactionDetails(
			Map<String,String> txnDetails);
	
	public int updateTxnDetailsOfP2PTxnDetails(Map<String,Object> updateRVTxnDetails);
	
	public void saveRequestViewEntity(List<RequestViewEntity> requestViewEntity) ;
	
	public List<RVTransactionDto> getRVTransactionDetails(GetRequestViewSearchRequest request) ;
	
	public ActionDto healthCheckForRequestView() ;

	public List<RVTransactionDto> getRVTxnsCursor(
			GetRequestViewSearchCursorRequest request);
}
 