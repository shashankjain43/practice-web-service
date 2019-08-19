package com.snapdeal.payments.view.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.payments.view.dao.IRequestViewDao;
import com.snapdeal.payments.view.entity.RequestViewEntity;
import com.snapdeal.payments.view.mapper.IRequestViewMapper;
import com.snapdeal.payments.view.request.commons.dto.RVTransactionDto;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchCursorRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;

@Repository("RequestViewDaoImpl")
public class RequestViewDaoImpl implements IRequestViewDao{

	@Autowired
	private IRequestViewMapper requestViewMapper;
	
	public List<RequestViewEntity> getRequestViewTransactionDetails(
			Map<String,String> txnDetails){
		
		return requestViewMapper.getRequestViewTransactionDetails(txnDetails);
	}
	@Transactional
	public void updateTxnDetailsOfP2PTxnDetails(List<Map<String,Object>> updateRVTxnDetails){
		for(Map<String,Object> updateDetails  : updateRVTxnDetails)
		requestViewMapper.updateTxnDetailsOfP2PTxnDetails(updateDetails);
	}
	
	@Transactional
	public void saveRequestViewEntity(List<RequestViewEntity> requestViewEntity) {
		requestViewMapper.saveRequestViewEntity(requestViewEntity);
	}
	
	public List<RVTransactionDto> getRVTransactionDetails(GetRequestViewSearchRequest request){
		request.setPage((request.getPage()-1)*request.getLimit());
		return requestViewMapper.getRVTransactionDetails(request) ;
	}
	
	public void healthCheckForRequestView() {
		requestViewMapper.healthCheckForRequestView();
	}
	@Override
	public List<RVTransactionDto> getRVTxnsCursor(
			GetRequestViewSearchCursorRequest request) {
		return requestViewMapper.getRVTxnsCursor(request) ;
	}
}
