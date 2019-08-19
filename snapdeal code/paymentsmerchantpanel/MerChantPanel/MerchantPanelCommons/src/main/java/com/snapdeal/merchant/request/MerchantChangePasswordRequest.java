package com.snapdeal.merchant.request;

import com.snapdeal.merchant.annotation.Password;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString( exclude = {"oldPassword","newPassword"})
public class MerchantChangePasswordRequest extends AbstractMerchantRequest {

   private static final long serialVersionUID = -1666320150332716000L;

   @Password(mandatory = true)
   private String oldPassword;
   
   @Password(mandatory = true)
   private String newPassword;

}
