package com.snapdeal.payments.view.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.view.dao.IMerchantTransactionDetailsDao;
import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.entity.TransactionDetailsForDisbursment;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.mapper.IMerchantViewMapper;
import com.snapdeal.payments.view.mapper.ITransactionDetailsMapper;
import com.snapdeal.payments.view.mapper.ITransactionStateDetailsMapper;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionPaybleDto;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterCursorRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.service.request.GetMVSettledTxnsMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantTransactionWithCursor;
import com.snapdeal.payments.view.service.request.GetMerchantTxnsSearchFilterWithMetaDataMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewFilterMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchWithFilterCursorMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchWithFilterMapperRequest;

@Slf4j
@Repository
public class MerchantTransactionDetailsDaoImpl implements
		IMerchantTransactionDetailsDao {

	@Autowired
	private ITransactionDetailsMapper txnDetailsMapper;

	@Autowired
	private ITransactionStateDetailsMapper txnStateMapper;


	@Autowired
	private IMerchantViewMapper merchantViewMapper;

	@Override
	public void saveTransactionDetails(TransactionDetailsEntity entity) {
		txnDetailsMapper.saveTransactionDetails(entity);
	}

	@Override
	public void saveTransactionStateDetails(TransactionStateDetailsEntity entity) {
		txnStateMapper.saveTransactionStateDetails(entity);
	}

	@Override
	public TransactionDetailsDTO getTransactionDetails(
			Map<String, String> txnDetails) {
		try {
			return txnDetailsMapper.getTransactionDetails(txnDetails);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public TransactionDetailsDTO getTransactionDetails(String txnId) {
		return txnDetailsMapper.getTransactionDetails(txnId);
	}

	

	@Override
	public List<MVTransactionDTO> getMerchantViewFilter(
			GetMerchantViewFilterRequest request) {
		GetMerchantViewFilterMapperRequest mapperReq = new GetMerchantViewFilterMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setFilters(request.getFilters());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		log.info(mapperReq.toString());
		return merchantViewMapper.getTxnsForFilters(mapperReq);
	}

	@Override
	public List<MVTransactionDTO> getMerchantViewSearch(
			GetMerchantViewSearchRequest request) {
		GetMerchantViewSearchMapperRequest mapperReq = new GetMerchantViewSearchMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setSearchCriteria(request.getSearchCriteria());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		return merchantViewMapper.getTxnsForSearchCriteria(mapperReq);
	}
	
	@Override
	public List<MVTxnWithMetaDataDTO> getTxnsMetaDataForSearch(
			GetMerchantViewSearchRequest request) {
		GetMerchantViewSearchMapperRequest mapperReq = new GetMerchantViewSearchMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setSearchCriteria(request.getSearchCriteria());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		return merchantViewMapper.getTxnsMetaDataForSearchCriteria(mapperReq);
	}

	@Override
	public int updateTxnDetailsOFDirectSystem(TransactionDetailsEntity entity) {
		return txnDetailsMapper.updateTxnDetailsOFDirectSystem(entity);
	}

	@Override
	public int updateTxnDeatilsPayableSystem(TransactionPaybleDto dto) {
		return txnDetailsMapper.updateTxnDeatilsPayableSystem(dto);
	}

	@Override
	public boolean verifyForTxnStatusValid(TransactionStateDetailsEntity entity) {
		TransactionStateDetailsEntity stateEntity = txnStateMapper
				.verifyForTxnStatusValid(entity);
		if (stateEntity != null)
			return true;
		return false;
	}

	@Override
	public void updateForAlreadyExistState(TransactionStateDetailsEntity entity) {
		txnStateMapper.updateForAlreadyExistState(entity);

	}

	@Override
	public int updateTxnDeatilsDisbursmentSystem(
			TransactionDetailsForDisbursment deEntity) {
		return txnDetailsMapper.updateTxnDeatilsDisbursmentSystem(deEntity);
	}

	@Override
	public String getTransactionByTxnBtsRef(String txnRef) {
		return txnDetailsMapper.getTransactionByTxnBtsRef(txnRef);
	}

	public void healthCheckForMerchant() {
		txnDetailsMapper.healthCheckForMerchant();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapdeal.payments.view.dao.IMerchantTransactionDetailsDao#
	 * getMerchantViewSearchWithFilter
	 * (com.snapdeal.payments.view.merchant.commons
	 * .request.GetMerchantViewSearchWithFilterRequest)
	 */
	@Override
	public List<MVTransactionDTO> getMerchantViewSearchWithFilter(
			GetMerchantViewSearchWithFilterRequest request) {
		GetMerchantViewSearchWithFilterMapperRequest mapperReq = new GetMerchantViewSearchWithFilterMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setSearchCriteria(request.getSearchCriteria());
		mapperReq.setFilters(request.getFilters());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		return merchantViewMapper.getTxnsForSearchWithFilter(mapperReq);
	}

	@Override
	public BigDecimal getTotalRefundedAmountForTxn(
			GetTotalRefundedAmountForTxnRequest request) {
		return merchantViewMapper.getTotalRefundedAmountForTxn(request);
	}

	@Override
	public List<MVTransactionDTO> getMerchantViewSearchWithFilterCursor(
			GetMerchantViewSearchWithFilterCursorRequest request) {
		GetMerchantViewSearchWithFilterCursorMapperRequest newReq=new 
				GetMerchantViewSearchWithFilterCursorMapperRequest();
		try {
			BeanUtils.copyProperties(newReq, request);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			log.error("Error while copying object props",e);
		}
		newReq.setCursorKey(new Date(request.getLastEvaluatedkey()));
		return merchantViewMapper.getTxnsForSearchWithFilterCursor(newReq);
	}

	@Override
	public List<MVTransactionDTO> getMerchantVewTransactionsWithCursor(
			GetMerchantTransactionWithCursor request) {
		
		return merchantViewMapper.getMerchantVewTransactionsWithCursor(request);
	}

	@Override
	public  List<MVTxnStatusDTO> getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) {
		return merchantViewMapper.getMerchantTxnStatusHistoryByTxnId(request);
	}

	@Override
	public List<MVTxnDTO> getMerchantTxnsSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request) {
		GetMerchantTxnsSearchFilterWithMetaDataMapperRequest mapperReq = new GetMerchantTxnsSearchFilterWithMetaDataMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setSearchCriteria(request.getSearchCriteria());
		mapperReq.setFilters(request.getFilters());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		return merchantViewMapper.getTxnsForSearchFilterWithMetaData(mapperReq);
	}

	@Override
	public List<MVTxnDTO> getMVSettledTxns(
			GetMerchantSettledTransactionsRequest request) {
		GetMVSettledTxnsMapperRequest mapperReq = new GetMVSettledTxnsMapperRequest();
		mapperReq.setOffset((request.getPage() - 1) * request.getLimit());
		mapperReq.setLimit(request.getLimit());
		mapperReq.setOrderby(request.getOrderby());
		mapperReq.setMerchantId(request.getMerchantId());
		mapperReq.setStartDate(request.getStartDate());
		mapperReq.setEndDate(request.getEndDate());
		mapperReq.setTxnStatus(MVTransactionStatus.SETTLED);
		return merchantViewMapper.getMVSettledTxns(mapperReq);
	}
	
	
}
