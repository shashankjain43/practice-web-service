package com.snapdeal.vanila.bulk.BulkTID;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;

public class BulkTIDConstants {

	public static final String BULK_TID_ACTIVITY_ID = "BULK_TID";
	
	public static final String FAILURE = "FAILURE";

	public static final String SUCCESS = "SUCCESS";
	
	public static final String SKIPPED = "SKIPPED";
	
	public static final String NOT_APPLICABLE = "N/A";
	
	public static final String WHILE_CREATING_TERMINAL_ID_CACHE = " While creating terminalId Cache ";
	
	public static final String TERMINAL_ID_CACHE_UNAVAILABLE = "TerminalId Cache is Unavailable!";
	
	public static final String TOKEN = "token";
	
	public static final String APPNAME = "appName";
	
	public static final String STATUS = "status";
	
	public static final String EMAIL_ID = "emailId";
	
	public static final String ACTION_TYPE = "actionType";
	
	public static final String ADD_UPDATE = "ADD_UPDATE";
	
	public static final String DELETE = "DELETE";
	
	public static final String MERCHANT_ID = "merchantId";
	
	public static final String PROVIDER_MERCHANT_ID = "providerMerchantId";
	
	public static final String PLATFORM_ID = "platformId";
	
	public static final String ACTION_TYPE_INVALID = "ActionType Not Found or Invalid!";
	
	public static final String MERCHANT_ID_INVALID = "MerchantId Not Found or Invalid!";
	
	public static final String PROVIDER_MERCHANT_ID_INVALID = "Provider MerchantId Not Found or Invalid!";
	
	public static final String PLATFORM_ID_INVALID = "PlatformId Not Found or Invalid!";
	
	public static final String ADD = "ADD";
	
	public static final String UPDATE = "UPDATE";
	
	public static final String FAILED_AT_OPS = "Failure at Offline Payments System End";
	
	public static final String OPS_MERCHANT_TERMINAL_MAPPING = "OPS_MERCHANT_TERMINAL_MAPPING";

	
	public static String getPermissionForAction(String action){
		if(action.equals(BulkProcessEnum.UPLOAD.name())){
			return OPS_MERCHANT_TERMINAL_MAPPING;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE.name())){
			return OPS_MERCHANT_TERMINAL_MAPPING;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER.name())){
			return OPS_MERCHANT_TERMINAL_MAPPING;
		}
		return null;
	}
}
