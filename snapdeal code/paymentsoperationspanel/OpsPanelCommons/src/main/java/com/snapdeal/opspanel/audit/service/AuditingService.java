package com.snapdeal.opspanel.audit.service;

import java.util.List;

import com.snapdeal.opspanel.audit.request.AuditRequest;
import com.snapdeal.opspanel.audit.response.AuditResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

public interface AuditingService {
	
	public int getCount(AuditRequest auditRequest) throws OpsPanelException;
	
	public List<AuditResponse> getResponse(AuditRequest auditRequest) throws OpsPanelException;

}
