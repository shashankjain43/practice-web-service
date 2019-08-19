package com.snapdeal.opspanel.audit.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.opspanel.audit.entity.AuditEntity;
import com.snapdeal.opspanel.audit.response.AuditResponse;

@Transactional(isolation = Isolation.REPEATABLE_READ)
public interface GenericAuditDao {

	public void setAuditEntry(AuditEntity auditEntity) throws Exception;

	public void updateAuditException(AuditEntity auditEntity) throws Exception;

	public void updateAuditResponse(AuditEntity auditEntity) throws Exception;
	
	public List<AuditResponse> getResponseWithEmail(AuditEntity entity) throws Exception;
	
	public List<AuditResponse> getResponse(AuditEntity entity) throws Exception;
	
	public int getCount(AuditEntity entity) throws Exception;
	
	public List<AuditResponse> getLastSuccess(AuditEntity entity) throws Exception;

	// public String checkAuditRequestIdExistance(String requestId);
}