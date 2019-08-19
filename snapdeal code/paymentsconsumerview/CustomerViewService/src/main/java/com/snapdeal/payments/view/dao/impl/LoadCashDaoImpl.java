package com.snapdeal.payments.view.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.view.dao.ILoadCashDao;
import com.snapdeal.payments.view.load.dto.LoadCashTxnDTO;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.mapper.ILoadCashMapper;
import com.snapdeal.payments.view.service.request.GetLoadCashTxnsMapperRequest;

@Repository
public class LoadCashDaoImpl implements ILoadCashDao{
	
	@Autowired
	private ILoadCashMapper loadCashMapper;

	@Override
	public List<LoadCashTxnDTO> getLoadCashTransactions(
			GetLoadCashTxnsRequest request) {
		
		GetLoadCashTxnsMapperRequest mapperReq = new GetLoadCashTxnsMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setFilters(request.getFilters());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		return loadCashMapper.getLoadCashTransactions(mapperReq);
	}

	@Override
	public List<LoadCashTxnDTO> getLoadCashTxnsByUserId(
			GetLoadCashTxnsByUserIdRequest request) {
		GetLoadCashTxnsMapperRequest mapperReq = new GetLoadCashTxnsMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		mapperReq.setUserId(request.getUserId());
		return loadCashMapper.getLoadCashTxnsByUserId(mapperReq);
	}

	@Override
	public List<LoadCashTxnDTO> getLoadCashTxnsByTxnId(
			GetLoadCashTxnsByTxnIdRequest request) {
		GetLoadCashTxnsMapperRequest mapperReq = new GetLoadCashTxnsMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		mapperReq.setTxnId(request.getTxnId());
		return loadCashMapper.getLoadCashTxnsByTxnId(mapperReq);
	}

}
