package com.snapdeal.opspanel.promotion.enums;

public enum SampleFileLocationMapping {

	ACTION_BULK_IMS_ID( "/samplefiles/actionbulk/IMS Sample Data.csv"),
	ACTION_BULK_EMAIL_ID( "/samplefiles/actionbulk/EMAIL Sample Data.csv"),
	ACTION_BULK_MOBILE_NUMBER( "/samplefiles/actionbulk/mobile_sample_data.csv"),
	MERCHANT_OPS_BULK_REFUND( "/samplefiles/merchant-ops/Bulk_Refud_Sample_Data.csv" ),
	
	BULK_FOS_MOBILE_SAMPLE_FILE("/samplefiles/bulkFOS/BULK_FOS_MOBILE_SAMPLE_FILE.csv"),
	BULK_FOS_EMAIL_SAMPLE_FILE("/samplefiles/bulkFOS/BULK_FOS_EMAIL_SAMPLE_FILE.csv");

	private String type;

	private SampleFileLocationMapping( String type ) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
