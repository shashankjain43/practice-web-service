package com.snapdeal.vanila.bulkFOS;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;

import lombok.Data;

@Data
public class BulkFOSConstants {
	
	public static final String BULK_FOS_ACTIVITY_ID = "BulkFOS";
	public static final String MOBILE ="mobile";
	public static final String EMAIL = "email";
	
	public static final String OPS_FC_PLUS_FOS_BULK = "OPS_FC_PLUS_FOS_BULK";
	public static final String OPS_FC_PLUS_SUPERUSER = "OPS_FC_PLUS_SUPERUSER";
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String INVALID_FOS = "Unable to get FOS Value for the given Acquisition Type";
	public static final String ID_TYPE = "idType";
	public static final String ACQUISITION_TYPE = "acquisitionType";
	
	public static final String TOKEN = "token";
	public static final String APPNAME = "appName";
	
	public static final String UI_DATA_MISSING = "idType or acquisitionType not found!";
	
	
	public static String getPermissionForAction(String action){
		if(action.equals(BulkProcessEnum.UPLOAD.name())){
			return OPS_FC_PLUS_FOS_BULK;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE.name())){
			return OPS_FC_PLUS_FOS_BULK;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER.name())){
			return OPS_FC_PLUS_SUPERUSER;
		}
		return null;
	}
}
