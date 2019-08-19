package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewInternalServerExceptionCodes {

	GENERIC_INTERNAL_SERVER("ER-3101","Error while processing request. If the issue persists, please contact customer support."),
	CLIENT_CONFIGURATION_PARAMETER_MISSING("ER-3111","Client configuration parameter are mandatory."), 
	CLIENT_CONFIGURATION_NOT_PRESENT("ER-3112", "Client configuration not present for client name."),
	MERCHANT_CONFIGURATION_NOT_PRESENT("ER-3113","Merchnat Configuration not present for merchnatId");

   private String errCode;
   private String errMsg;

   private PaymentsViewInternalServerExceptionCodes(String errCode, String errMsg) {
      this.errCode = errCode;
      this.errMsg = errMsg;
   }

   public String errCode() {
      return this.errCode;
   }

   public String errMsg() {
      return this.errMsg;
   }

}
