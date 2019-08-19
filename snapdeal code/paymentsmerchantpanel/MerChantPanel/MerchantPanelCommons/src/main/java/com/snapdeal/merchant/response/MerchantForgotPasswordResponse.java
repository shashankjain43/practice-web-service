package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantForgotPasswordResponse extends AbstractResponse {

   private static final long serialVersionUID = 4181966055257793886L;

   private boolean success;

}
