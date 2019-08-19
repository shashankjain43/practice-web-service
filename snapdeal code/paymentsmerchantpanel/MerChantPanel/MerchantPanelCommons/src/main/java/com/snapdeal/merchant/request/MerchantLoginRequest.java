package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Password;
import com.snapdeal.merchant.annotation.UserName;
import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude="password")
public class MerchantLoginRequest extends AbstractRequest {

   private static final long serialVersionUID = 1L;

   @NotBlank(message=ErrorConstants.LOGIN_NAME_IS_BLANK_CODE)
   private String loginName;

   @NotBlank(message=ErrorConstants.PASSWORD_IS_BLANK_CODE)
   private String password;
}
