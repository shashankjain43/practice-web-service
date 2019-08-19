package com.snapdeal.opspanel.promotion.constant;

public class BulkPromotionConstants {

	//configuration
	public static final String BULK_PROMOTION_ACTIVITY_ID = "BULK_PROMOTION_SYSTEM";
	public static final long chunkSize = 8000;

	//validation constants
	public static final int maxCSVRow = 100000;

	//executor constants
	public static final int userIdIndex = 0;
	public static final int amountIndex = 1;
	public static final int eventContextIndex = 2;
	public static final int dbDumpFrequency = 1000;
	public static final int logDumpFrequency = 1000;

	public static final String IDEMPOTENCY_ID = "idempotencyId";
	public static final String STEP_IDENTIFIER = "BULK_STEP:";
	public static final String LATENCY_IDENTIFIER = " LATENCY: ";
	public static final String MOBILE_ID = "MOBILE_ID";
	public static final String IMS_ID = "IMS_ID";
	public static final String ID_TYPE = "id_type";
	public static final String INSTRUMENT = "instrument";
	public static final String BUSINESS_ENTITY = "businessEntity";
	public static final String CORP_ID = "corpId";
	public static final String IS_WALLET_NOTIFICATION_SUPPRESSED = "isWalletNotificationSuppressed";
	public static final String IS_PARK = "isPark";
	public static final String IS_EMAIL_SUPPRESSED = "isEmailSuppressed";
	public static final String IS_SMS_SUPPRESSED = "isSMSSuppressed";
	public static final String SMS_TEMPLATE_ID = "smsTemplateId";
	public static final String EMAIL_TEMPLATE_ID = "emailTemplateId";
	public static final String CREDITED_AMOUNT = "creditedAmount";
	public static final String TOTAL_AMOUNT = "totalAmount";
	public static final String UPLOAD_TIMESTAMP = "uploadTimestamp";
	public static final String OPS_INTERNAL_SERVER_EXCEPTION = "OPS_INTERNAL_SERVER_EXCEPTION";
	public static final String FILE_NAME = "fileName";

	public static final String transactionAlreadyExistMessage = "Money was already dispensed to this user for this campaign.";
	public static final String moneySuccessfulMessage = "Money successfully dispensed.";
	public static final String parkingFailMessage = "could not park money as  park money is disabled";

}
