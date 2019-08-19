package com.snapdeal.ums.admin.sdCashBulkDebit;

import java.util.List;
import java.util.Map;

import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.constants.ErrorConstants;

public class SDCashBulkDebitUploadResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4394093867696755158L;
	
	@AuditableField
	Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap;
	
	public SDCashBulkDebitUploadResponse() {
		super();
	}

	public SDCashBulkDebitUploadResponse(
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap) {
		super();
		this.unProcessedSDUserEmailIDMap = unProcessedSDUserEmailIDMap;
	}

	public Map<ErrorConstants, List<String>> getUnProcessedSDUserEmailIDMap() {
		return unProcessedSDUserEmailIDMap;
	}

	public void setUnProcessedSDUserEmailIDMap(
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap) {
		this.unProcessedSDUserEmailIDMap = unProcessedSDUserEmailIDMap;
	}

	

	
	

}
