package com.snapdeal.merchant.exception;

import lombok.Data;

@Data
public class MerchantException extends Exception {

   private static final long serialVersionUID = -773081330719468371L;

   private String errCode;
   private String errMsg;

   public MerchantException(String errCode, String errMsg) {
      super(errMsg);
      this.errCode = errCode;
      this.errMsg = errMsg;

   }

}
