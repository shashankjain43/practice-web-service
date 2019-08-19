package com.snapdeal.payments.view.commons.constant;

public class RestURIConstants {

	public static final String APPLICATION_JSON = "application/json" ;
	
	public static final String VIEW = "/api/v1/view" ;
	
	public static final String MERCHANT_VIEW = "/merchant"  ;
	public static final String MERCHANT_VIEW_SEARCH = "/search"  ;
	public static final String ORDER_ID = "/orderId" ;
	public static final String SEARCH_STATS_HISTORY = "/status/history" ; 
	
	public static final String MERCHANT_VIEW_SEARCH_WITH_FILTER = "/searchfilter"  ;
	public static final String MV_SEARCH_WITH_FILTER_CURSOR = "/searchfilter/cursor"  ;
	public static final String MV_TXN_WITH_FILTERS = "/merchantTxnsWithFilter"  ;
	public static final String MV_SETTLED_TXNS = "/merchantSettledTransactions"  ;
	public static final String MV_TXN_HISTORY = "/merchantTxns"  ;
	public static final String MV_SEARCH_FILTER_WITH_META_DATA = "/searchfilter/metadata";
	public static final String TXN_ID = "/txnId"  ;
	public static final String MERCHANT_VIEW_FETCH_TOTAL_REFUNDED_AMOUNT = "/refundedamount"  ;
	public static final String UNSETTLED_AMOUNT =  "/unsettledAmount" ;
	
	public static final String REQUEST_VIEW = "/request" ;
	public static final  String SEARCH = "/search";
	public static final String PENDING_ACTIONS_BW_PARTY = "/pendingActionsBetweenParties" ;
	public static final String PENDING_ACTIONS_FOR_PARTY = "/userPendingActions" ;
	public static final String USER_ACTION_HISTORY = "/userActionsHistory" ;
	public static final String TSM_SYNC_NOTIFICATION = "/tsm/syncNotification" ;
	public static final String SPLIT_TRANSACTIONS = "/split/transaction" ;
	
	public static final String USER_ID = "{userId}" ;
	public static final String START = "?start={start}" ;
	public static final String LIMIT = "&limit={limit}" ;
	public static final String MERCHANT_NAME= "&merchantName={merchantName}" ;
	public static final String AMOUNT = "&amount={amount}" ;
	public static final String START_DATE = "&startDate={startDate}" ;
	public static final String END_DATE = "&endDate={endDate}" ;
	
	public static final String CACHE_RELOAD = "/cache/reload" ;
	
	public static final String CREATE_CLIENT = "/create" ;
	public static final String UPDATE_CLIENT = "/update" ;
	public static final String GET_ALL_CLIENT = "/getAll" ;
	
	
	public static final String LIMIT_RANGE = "/start/{start}/limit/{limit}" ;
	
	public static final String GET_BY_USER_ID = "/{userId}" ;
	public static final String GET_BY_MERCHANT_NAME = "/{userId}/merchantName" ;
	public static final String GET_BY_AMOUNT = "/{userId}/amount" ;
	public static final String GET_BY_DATE_RNAGE = "/{userId}/date" ;
	
	public static final String GET_ALL_TXN_BY_MERCHANT_ID = "/{merchantId}" ;
	public static final String GET_TXNS_BY_MERCHANT_ID_FILTERS = "/{merchantId}/filter" ;
	public static final String GET_TXNS_FOR_REFUND = "/{merchantId}/refund" ;
	public static final String GET_TXNS_FOR_SETTELED = "/{merchantId}/settled" ;
	
	public static final String GET_BY_MERCHANT_ID_DATE_RANGE = "/{merchantId}/dateRange";
	public static final String MERCHANT_ID = "/merchantId/" ;
	public static final String MERCHANT_AUDIT = "/merchant/audit" ;
	public static final String TRANSACTION_LIST = "/trransaction/list" ;
	
	public static final String LOAD_CASH = "/loadCash";
	public static final String LOAD_CASH_WITH_FILTER = "/filter";
	public static final String LOAD_CASH_BY_USER_ID = "/byUserId";
	public static final String LOAD_CASH_BY_TXN_ID = "/byTxnId";
	public static final int DEFAULT_SECURE_KEY_LEN = 12;
	public static final String CLIENT = "/client" ;
	
}
