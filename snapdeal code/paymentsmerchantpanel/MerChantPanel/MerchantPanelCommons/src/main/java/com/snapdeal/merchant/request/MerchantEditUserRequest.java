package com.snapdeal.merchant.request;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;

@Data
public class MerchantEditUserRequest extends AbstractMerchantRequest {

   private static final long serialVersionUID = 1L;

   @NotBlank(message = ErrorConstants.USER_ID_IS_BLANK_CODE)
   private String userId;

   @NotBlank(message = ErrorConstants.USER_NAME_IS_BLANK_CODE)
   private String userName;

   @Email(mandatory = false)
   private String email;

   private List<MerchantRoleDTO> roleList;

}
