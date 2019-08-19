package com.snapdeal.payments.view.commons.constant;



public class PaymentsViewConstants {

	public static final String ALGO = "SHA-256";
	public static final String ENCODING = "UTF-8";
	public static final String CHECKSUM = "checksum";
	
	public static final int CORE_POOL_SIZE = 50 ;
	public static final int MAX_POOL_SIZE =  80 ;
	public static final int KEEP_ALIVE_TIME = 500 ;
	
	public static final int DEFAULT_ERROR_STATUS_CODE = 500;
	
	public static final int REQUEST_EXPIRY_TIME = 600 ; 
	
	public static final String CLIENT_CACHE = "clientCache" ;
	public static final String MERCHANT_ON_BOADRDING_CACHE = "merchantCache" ;
	
	public static final String RESPONSE_200 = "200" ;
	
	public static final String DATE_FORMAT = "dd-mm-yyyy" ;
	
	public static final String TXN_ID = "txnId" ;
	public static final String TXN_TYPE = "txnType" ;
	
	public static final String FC_TXN_ID = "fcTxnId" ;
	public static final String FC_TXN_TYPE = "txnType" ;
	public static final String GLOBAL_TXN_ID = "globalTxnId" ;
	public static final String GLOBAL_TXN_TYPE = "globaTxnType" ;
	public static final String STATUS = "status" ;
	public static final String  TSM_TIME_STAMP = "tsmTimeStamp" ;
	public static final int RETRY_COUNT = 3;
	
	public static final String TXN_REF = "TransactionReference" ;
	public static final String SETTLEMENT_START_TIME = "SettlementStartTime" ;
	public static final String SETTLEMENT_END_TIME = "SettlementEndTime" ;
	public static final String DOMAIN = "Domain" ;
	
	public static final String RV_TXN_STATE = "rvTxnState" ;
	public static final String RECEIVER_ID = "receiverId" ;
	public static final String SENDER_ID = "senderId" ;
	
	public static final String TSM_TXN_STATE = "tsmTxnState";
	public static final String TSM_COMMIT_TIME = "tsmCommitTime" ;
	public static final String P2P_TXN_STATE = "p2pTxnState" ;
	public static final String META_DATA = "metaData" ;
	
	public static final String MERCHANT_NOT_PRESENT_IN_MOB = "unkonwn" ;
	public static final String KEY_DOMAIN = "Merchant" ;
	public static final String ENV_TYPE = "PRODUCTION" ;
	
	public static final String SRC_PATY_INFO = "sourceDisplayInfo";
	public static final String DEST_PATY_INFO = "destDisplayInfo";
	
	public static final String MOBILE = "mobile";
	public static final String NAME =  "profileName";
	public static final String TAG = "tag" ;
	public static final String CONTACT_NAME = "contactName" ;
	
	
	public static final String ACTION_VIEW_STATE = "actionViewState" ;
	public static final String ACTION_TXN_STATE = "actionTxnState";
	public static final String ACTION_LAST_UPDATED_TIME_STAMP = "actionLastIpdateTimestamp" ;
	public static final String ACTION_CONEXT = "actionContext" ;
	public static final String ID = "id" ;
	public static final String CUSTOMER_NAME = "Name" ;
	public static final String EMAIL = "email" ;
	public static final String USER_ID = "userId";
	
	public static final String REQUEST_TTL = "300";
	public static final String CLIENT_NAME_IS_BLANK = "CLIENT_NAME_IS_BLANK" ;
	public static final String CLIENT_NAME_MAX_LENGTH = "CLIENT_NAME_MAX_LENGTH" ;
	public static final String CLIENT_STATUS_IS_NULL = "CLIENT_STATUS_IS_NULL" ;
	public static final int DEFAULT_SECURE_KEY_LEN = 12;
	public static final long CACHE_UPDATE_DELAY = 1800000 ;
 	
}
