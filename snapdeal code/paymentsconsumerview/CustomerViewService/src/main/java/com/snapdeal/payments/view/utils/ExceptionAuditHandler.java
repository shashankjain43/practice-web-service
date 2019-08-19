package com.snapdeal.payments.view.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.dao.IPaymentsViewAuditDao;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.entity.RequestViewAuditEntity;

@Slf4j
@Component
public class ExceptionAuditHandler<T extends PaymentsViewAuditEntity> {

	@Autowired
	@Qualifier("MerchantViewAuditDaoImpl")
	private IPaymentsViewAuditDao<T> merchantAuditPersistanceManager;

	@Autowired
	@Qualifier("RequestViewAuditDaoImpl")
	private IPaymentsViewAuditDao<T> requestAuditPersistanceManager;

	public void handleExceptionOfMerchantView(Throwable e, final T request) {

		try {
			if (e instanceof JsonParseException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.JSON_PARSE_EXCEPTION
								.errCode(),
						e.getMessage(), ExceptionType.JSON_PARSE_EXCEPTION);

				throw ex;
			} else if (e instanceof JsonMappingException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.JSON_MAPPING_EXCEPTION
								.errCode(), e.getMessage(),
						ExceptionType.JSON_MAPPING_EXCEPTION);
				throw ex;
			} else if (e instanceof IOException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.IO_EXCETPION
								.errCode(),
						e.getMessage(), ExceptionType.IO_EXCEPTION);
				throw ex;
			} else if (e instanceof NullPointerException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.NULL_POINTER_EXCEPPTION
								.errCode(), "Meta Data  is null",
						ExceptionType.NULL_POINTER_EXCEPTION);

				throw ex;
			} else if (e instanceof ClassCastException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.CLASS_CAST_EXCEPTION
								.errCode(),
						e.getMessage(), ExceptionType.CLASS_CAST_EXCEPTION);
				throw ex;
			} else if (e instanceof MySQLIntegrityConstraintViolationException) {

				updateExceptionAuditStatus(request,
						RetryTaskStatus.BLOCKED.getName());

				log.error("exception occured while processing notification + "
						+ e.getMessage());
			} else if (e instanceof DataIntegrityViolationException) {

				updateExceptionAuditStatus(request,
						RetryTaskStatus.BLOCKED.getName());

				log.error("exception occured while processing notification + "
						+ e.getMessage());
			} else if (e instanceof SQLException) {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.SQL_EXCEPTION
								.errCode(),
						e.getMessage(), ExceptionType.SQL_EXCEPTION);
				throw ex;
			} else if (e instanceof PaymentsViewGenericException) {
				throw (PaymentsViewGenericException) e;

			} else {
				PaymentsViewGenericException ex = new PaymentsViewGenericException(
						PaymentsViewServiceExceptionCodes.UNKNOWN_EXCEPTION
								.errCode(),
						e.getMessage(), ExceptionType.UNKNOWN_EXCEPTION);
				log.info("Exception occured while processing message : "
						+ ex.getErrMsg());
				throw ex;
			}
		} catch (PaymentsViewGenericException ex) {

			log.info("EXCEPTION :" + ex.getMessage());

			saveExceptionAudit(request, ex);
		}
	}

	/**
	 * @param request
	 */
	private void saveExceptionAudit(T request, PaymentsViewGenericException ex) {

		Map<String, Object> txnDetails = new HashMap<String, Object>();
		txnDetails.put(PaymentsViewConstants.FC_TXN_ID, request.getFcTxnId());
		txnDetails.put(PaymentsViewConstants.FC_TXN_TYPE, request.getTxnType());
		T merchanEntity = getAuditManager(request).getPaymentsViewAuditEntity(
				txnDetails);
		AddExceptionData(request, ex);
		if (merchanEntity != null) {
			if (merchanEntity.getRetryCount() >= PaymentsViewConstants.RETRY_COUNT) {
				request.setStatus(RetryTaskStatus.BLOCKED);
			} else {
				request.setStatus(RetryTaskStatus.PENDING);
			}
			getAuditManager(request).updatePaymentsViewAuditEntity(request);
		} else {
			getAuditManager(request).savePaymentsViewAuditEntity(request);
		}

	}

	/**
	 * @param request
	 * @param status
	 */
	public void updateExceptionAuditStatus(PaymentsViewAuditEntity request, String status) {
		Map<String, Object> txnDetails = new HashMap<String, Object>();
		txnDetails.put(PaymentsViewConstants.FC_TXN_ID, request.getFcTxnId());
		txnDetails.put(PaymentsViewConstants.FC_TXN_TYPE, request.getTxnType());
		txnDetails.put(PaymentsViewConstants.STATUS, status);

		T merchanEntity = getAuditManager(request).getPaymentsViewAuditEntity(
				txnDetails);
		if (merchanEntity != null) {
			getAuditManager(request).updatePaymentsViewAuditStatus(txnDetails);
		}

	}

	private IPaymentsViewAuditDao<T> getAuditManager(PaymentsViewAuditEntity request) {

		if (request instanceof MerchantViewAuditEntity)
			return merchantAuditPersistanceManager;

		if (request instanceof RequestViewAuditEntity)
			return requestAuditPersistanceManager;

		throw new PaymentsViewGenericException("Exception audit not suppoetred for given type");

	}

	private void AddExceptionData(T entity, PaymentsViewGenericException ex) {
		entity.setExceptionType(ex.getExceptionType().name());
		entity.setExceptionCode(ex.getErrCode());
		if (StringUtils.isBlank(ex.getMessage())) {
			entity.setExceptionMsg("UNKOWN ERROR");
		} else {
			entity.setExceptionMsg(ex.getMessage());
		}
	}

}
