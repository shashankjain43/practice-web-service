package com.snapdeal.ums.admin.sdCashBulkCredit;

import java.util.List;
import java.util.Map;

import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.constants.ErrorConstants;

@AuditableClass
public class SDCashBulkCreditUploadResponse extends ServiceResponse {

	/**
     *  
     */
	private static final long serialVersionUID = 9140749487793156202L;

	@AuditableField
	Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap;

	public SDCashBulkCreditUploadResponse() {

		super();
	}

	public Map<ErrorConstants, List<String>> getUnProcessedSDUserEmailIDMap() {

		return unProcessedSDUserEmailIDMap;
	}

	public void setUnProcessedSDUserEmailIDMap(
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap) {

		this.unProcessedSDUserEmailIDMap = unProcessedSDUserEmailIDMap;
	}

	public SDCashBulkCreditUploadResponse(boolean reason, boolean activityId,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap) {

		super();
		this.unProcessedSDUserEmailIDMap = unProcessedSDUserEmailIDMap;
	}

}
