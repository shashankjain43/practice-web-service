package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewAerospikeExceptionCodes {
   
   AEROSPIKE_CLIENT_NOT_INITIALIZED("ER-10001", "Aerospike client not initialized"),
   AEROSPIKE_CLIENT_CONFIG_NOT_PRESENT("ER-10002", "Aerospike client configuration not provided to create aerospike client");

   private String errCode;
   private String errMsg;

   private PaymentsViewAerospikeExceptionCodes(String errCode, String errMsg) {
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