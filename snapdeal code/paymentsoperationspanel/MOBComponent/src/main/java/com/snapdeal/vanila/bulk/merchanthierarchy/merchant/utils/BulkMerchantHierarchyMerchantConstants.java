package com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.enums.AssociationOperationType;

public class BulkMerchantHierarchyMerchantConstants {
	
	public static final String MERCHANT_ONBOARD = "MERCHANT_ONBOARD";
	
	public static final String MERCHANT = "MERCHANT";
	
	public static final String ONBOARD = "ONBOARD";
	
	public static final String UPDATE  =  "UPDATE";
	
	public static final String OPERATION = "operation_type";
	
	public static final String TOKEN = "token";
	
	public static final String APP_NAME = "appName";
	
	public static final String EMAIL = "emailId";
	
	public static final ImmutableList<String> action = ImmutableList.of(ONBOARD, UPDATE);

	public static ImmutableList<String> getAction() {
		return action;
	}

	public static final String[] sampleOnboard = {"*Email","*Business Name","*Merchant Name","*Mobile","*Address","*City","*State","*Pin Code","STD Code","Landline Number","*Business Category","SubCategory","*Business Type","*Date Of Formation","*TIN","*Account Holder Name","*Account Number","*Bank Name","*IFSC Code","*Integration Mode","*Integration Mode Subtype","Velocity Limits","Merchant Reserves","Shop Name","Campaign Text","Campaign Link","Notes","Settlement email id 1","Settlement email id 2","Settlement email id 3","Disburse Difference Period","Secondary Email","Secondary Mobile","Website","App Name","Logo URL","Business Description"};
	
	public static final String[] sampleUpdate = {"*Email","*Business Name","*Merchant Name","*Mobile","*Address","*City","*State","*Pin Code","STD Code","Landline Number","*Business Category","SubCategory","*Business Type","*Date Of Formation","*TIN","*Bank Name","*IFSC Code","*Account Holder Name","*Account Number","Campaign Text","Campaign Link","Notes","Settlement email id 1","Settlement email id 2","Settlement email id 3","Disburse Difference Period","Secondary Email","Secondary Mobile","Website","App Name","Logo URL","Business Description"};
	
	public static final String TOKEN_OR_APPNAME_NOT_FOUND = "Either token or appName is empty/invalid!";
	
	public static final String COLUMNS_MISMATCH = "The columns in the uploaded CSV file do NOT match with those in the sample CSV file, Please use the sample file! ";
	
	public static final String INVALID_OPERATION_TYPE = "Invalid operation type!";
	
	public static final String MORE_ROWS = "This row has more fields than expected! This may be because the row contains a cell with comma inside!";
	
	public static final String LESS_ROWS = "This row has less fields than expected!";
	
	public static final String FILE_NOT_FOUND = "File not found";
	
	
	
	

	public static final String SUCCESS = "SUCCESS";
	
	public static final String FAILURE = "FAILURE";
	
	public static final String SKIPPED = "SKIPPED";
	
	public static final String OPS_MERCHANT_HIERARCHY_BULK_UPDATER = "OPS_MERCHANT_HIERARCHY_BULK_UPDATER";
	
	public static final String OPS_MERCHANT_HIERARCHY_SUPERUSER ="OPS_MERCHANT_HIERARCHY_SUPERUSER";
	
	
	public static String getPermissionForAction(String action){
		if(action.equals(BulkProcessEnum.UPLOAD.name())){
			return OPS_MERCHANT_HIERARCHY_BULK_UPDATER;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE.name())){
			return OPS_MERCHANT_HIERARCHY_BULK_UPDATER;
		}
		if(action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER.name())){
			return OPS_MERCHANT_HIERARCHY_SUPERUSER;
		}
		if(action.equalsIgnoreCase(AssociationOperationType.MERCHANT_DEALER.toString()) || action.equalsIgnoreCase(AssociationOperationType.MERCHANT_PARTNER.toString()) || action.equalsIgnoreCase(AssociationOperationType.MERCHANT_PLATFORM.toString()) || action.equalsIgnoreCase(AssociationOperationType.PARTNER_PLATFORM.toString())){
			return OPS_MERCHANT_HIERARCHY_BULK_UPDATER;
		}
		return null;
	}
}
