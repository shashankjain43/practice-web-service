package com.snapdeal.merchant.client.util;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;

public interface RestURIConstants {

   public static final String BASE_URI = "/api";
   public static final String MERCHANT = "/merchant";
   public static final String GET_PROFILE = "/v1/get/profile";
   public static final String GET_ROLES = "/v1/get/roles";
   public static final String SESSION = "/session";
   public static final String LOGIN = "/v1/login";
   public static final String LOGOUT = "/v1/logout";
   public static final String USER =  "/v1/user";
   public static final String ADD_USER = "/v1/adduser";
   public static final String EDIT_USER = "/v1/edituser";
   public static final String CHANGE_PASSWORD = "/v1/updatepwd";
   public static final String FORGOT_PASSWORD = "/v1/genpwd";
   public static final String GET_ALL_USERS = "/v1/getall";
   public static final String VERIFY_USER = "/v1/verify/{loginName}";
   public static final String GET_LOGIN_NAME_PARAM = "{loginName}";
   public static final String VERIFY = "/verify/";
   public static final String TRANSACTION = "/txn";
   public static final String VIEW_TRANSACTION = "/v1/view";
   public static final String SEARCH_TRANSACTION = "/v1/search";
   public static final String REFUND_TRANSACTION = "/v1/refund";
   public static final String GENOTP = "/v2/genotp";
   public static final String RESEND_OTP = "/v2/resendotp";
   public static final String VERIFY_OTP = "/v1/verifyotp" ;
   public static final String SET_PASSWORD = "/v1/setpassword";
   public static final String GET_TXNS = "/v1/gettxns";
   public static final String REFUND_AMOUNT = "/v1/refundamount";
   
   public static final String startDate = "startDate=";
   public static final String endDate = "endDate=";
   public static final String orderBy = "orderBy=";
   public static final String fromAmount = "fromAmount=";
   public static final String toAmount = "toAmount=";
   public static final String txnTypeList = "txnTypeList=";
   public static final String txnStatusList = "txnStatusList=";
   public static final String page = "page=";
   public static final String limit = "limit=";
   public static final String and = "&";
   
   public static final String customerId = "customerId=";
   public static final String merchantTxnId = "merchantTxnId=";
   public static final String settlementId = "settlementId=";
   public static final String transactionId = "transactionId=";
   public static final String orderId = "orderId=";
   public static final String productId = "productId=";
   public static final String storeId = "storeId=";
   public static final String terminalId = "terminalId=";
   
   public static final String txnRefId = "txnRefId=";
   public static final String txnRefType = "txnRefType=";
     
   public static final String APPLICATION_JSON = "application/json";
}
