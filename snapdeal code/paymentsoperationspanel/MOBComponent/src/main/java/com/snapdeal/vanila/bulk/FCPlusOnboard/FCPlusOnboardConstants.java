package com.snapdeal.vanila.bulk.FCPlusOnboard;

public class FCPlusOnboardConstants {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	
	public static final String TOKEN = "token";
	public static final String APPNAME = "appName";
	
	public static final String FC_PLUS = "FC+";
	
	public static final String FC_PLUS_BULK_ONBOARD_ACTIVITY_ID = "BulkFCPlusOnboard";
	
	public static final String[] sampleOnboard = {"Email Id","Shop Name","Merchant Name","Address","City","State","Pin Code","Primary Mobile (with which consumer account was created)","Business Category","Account Holder Name","Account Number","Bank Name","IFSC Code","Pay Tag","Latitude","Longitude"};
	
	public static final String COLUMNS_MISMATCH = "The columns in the uploaded CSV file do NOT match with those in the sample CSV file, Please use the sample file! ";

	public static final String MORE_ROWS = "This row has more fields than expected! This may be because the row contains a cell with comma inside!";
	
	public static final String LESS_ROWS = "This row has less fields than expected!";
	
	public static final String FILE_NOT_FOUND = "File not found";
}
