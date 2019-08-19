package com.snapdeal.opspanel.userpanel.bulkFraud;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;

public class BulkFraudBlacklistConstatnts {
	
	public static final String BULK_FRAUD_ACTIVITY_ID = "BULK_FRAUD";
	
	public static final String ENTITY_TYPE_NOT_FOUND = "Entity Type Not Found!";
	
	public static final String ACTION_TYPE_NOT_FOUND = "Action Type Not Found!";
	
	public static final String TXN_TYPE_NOT_FOUND = "TxnType Not Found!";
	
	public static final String STATUS_NOT_FOUND = "Status Not Found!";
	
	public static final String UPDATE_CODE_NOT_FOUND = "Update Code Not Found!";
	
	public static final String ENTITY_NOT_FOUND = "Entity Not Found!";
	
	public static final String REASON_NOT_FOUND = "Reason For Update Not Found!";
	
	public static final String JIRA_ID_NOT_FOUND = "Jira ID For Update Not Found!";
	
	public static final String ENTITY_STATUS_INVALID = "EntityStatus Enum Not Matched!";
	
	public static final String TAG_STATUS_INVALID = "TagStatus Enum Not Matched!";
	
	public static final String USER_ID_INVALID = "UserId not found or invalid!";
	
	public static final String FAILURE = "FAILURE";

	public static final String SUCCESS = "SUCCESS";
	
	public static final String SKIPPED = "SKIPPED";
	
	public static final String NOT_APPLICABLE = "N/A";
	
	public static final String TOKEN = "token";
	
	public static final String EMAIL_ID = "emailId";

	public static final String OPS_LEGAL_BULK_FRAUD_UPDATER = "OPS_LEGAL_BULK_FRAUD_UPDATER";

	public static final String OPS_LEGALPANEL_SUPERUSER = "OPS_LEGALPANEL_SUPERUSER";
	
	public static final String ENTITY_TYPE = "entityType";
	
	public static final String TXN_TYPE = "txnType";
	
	public static final String STATUS = "status";
	
	public static final String UPDATE_REASON = "updateReason";
	
	public static final String JIRA_ID = "jiraID";
	
	public static final String UPDATE_CODE = "updateCode";
	
	public static final String PARTIAL_BLOCKING = "PARTIAL BLOCKING";
	
	public static final String FULL_BLOCKING = "FULL BLOCKING";
	
	public static final String ACTION_TYPE = "actionType";
	
	public static String getPermissionForAction(String action){
		if(action.equals(BulkProcessEnum.UPLOAD.name())){
			return OPS_LEGAL_BULK_FRAUD_UPDATER;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE.name())){
			return OPS_LEGAL_BULK_FRAUD_UPDATER;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER.name())){
			return OPS_LEGALPANEL_SUPERUSER;
		}
		return null;
	}
	
	
}
