package com.snapdeal.opspanel.promotion.enums;

public enum OPSPaymentsReportType {

	ALL_CORP_ACCOUNT_ACTIVITY_REPORT,        /*-- +Corp Account Reports for various Merchants+ --*/
	SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT,   /*-- -Corp Account Reports for various Merchants- --*/
		WALLET_LOAD_REPORT,			         /*---------   +ERP Reports for Finance+    ---------*/
		WALLET_USAGE_REPORT,
		WALLET_USAGE_CANCEL_REPORT,
		WALLET_WITHDRAW_REPORT,
		ALL_CREDITS_REPORT,
		EXPIRY_REPORT,
		WALLET_CREDIT_REPORT,		     	 /*---------    -ERP Reports for Finance-   ---------*/
	MIRROR_ACCOUNT_REPORT,          		 /*---------     +Mirror Account Report-    ---------*/
	IMPS_REPORT,         		  		     /*------ +IMPS Out Manual Recon Txns Report- -------*/
		WORKFLOW_COUNT_REPORT,      		 /*------ +Internal Report for System Recon+ --------*/
		ACCOUNTS_REPORT,					 /*------ -Internal Report for System Recon- --------*/
	RBI_REPORT								 /*------             +RBI Report-           --------*/
}
