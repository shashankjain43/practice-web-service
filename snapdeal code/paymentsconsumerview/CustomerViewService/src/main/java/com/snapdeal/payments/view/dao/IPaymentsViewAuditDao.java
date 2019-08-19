package com.snapdeal.payments.view.dao;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;

public interface IPaymentsViewAuditDao<T>{

	public T getPaymentsViewAuditEntity(Map<String,Object> txnDetails) ;
	
	public List<PaymentsViewAuditEntity> getPaymentsViewAuditDetailsInChunks(RetryPaymentsViewAuditRequest request) ;
	
	public int updatePaymentsViewAuditStatus(Map<String,Object> txnDetails) ;
	
	public void savePaymentsViewAuditEntity(T entity) ;
	
	public int updatePaymentsViewAuditEntity(T entity) ;
	
}
