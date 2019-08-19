package com.snapdeal.payments.view.mapper;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;

public interface MerchantViewAuditMapper {

	public void saveMerchantViewAuditEntity(MerchantViewAuditEntity entity) ;
	
	public int updateMerchantViewAuditEntity(MerchantViewAuditEntity entity) ;
	
	public MerchantViewAuditEntity getMerchantViewAuditEntity(Map<String,Object> txnDetails) ;
	
	public List<PaymentsViewAuditEntity> getMerchantViewAuditDetailsInChunks(RetryPaymentsViewAuditRequest request) ;
	
	public int updateMerchantViewAuditStatus(Map<String,Object> txnDetails) ;
}
