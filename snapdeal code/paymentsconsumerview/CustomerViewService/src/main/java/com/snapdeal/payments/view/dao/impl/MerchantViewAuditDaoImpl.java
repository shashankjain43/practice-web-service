package com.snapdeal.payments.view.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.view.dao.IMerchantViewAuditDao;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.mapper.MerchantViewAuditMapper;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;

@Repository("MerchantViewAuditDaoImpl")
public class MerchantViewAuditDaoImpl implements IMerchantViewAuditDao {

	@Autowired
	private MerchantViewAuditMapper merchantViewAuditMapper;

	@Override
	public void savePaymentsViewAuditEntity(MerchantViewAuditEntity entity) {
		merchantViewAuditMapper.saveMerchantViewAuditEntity(entity);

	}

	@Override
	public int updatePaymentsViewAuditEntity(MerchantViewAuditEntity entity) {

		return merchantViewAuditMapper.updateMerchantViewAuditEntity(entity);

	}

	@Override
	public MerchantViewAuditEntity getPaymentsViewAuditEntity(
			Map<String, Object> txnDetails) {
		return merchantViewAuditMapper.getMerchantViewAuditEntity(txnDetails);
	}

	@Override
	public List<PaymentsViewAuditEntity> getPaymentsViewAuditDetailsInChunks(
			RetryPaymentsViewAuditRequest request) {
		return merchantViewAuditMapper
				.getMerchantViewAuditDetailsInChunks(request);
	}

	@Override
	public int updatePaymentsViewAuditStatus(Map<String, Object> txnDetails) {
		return merchantViewAuditMapper
				.updateMerchantViewAuditStatus(txnDetails);
	}

}
