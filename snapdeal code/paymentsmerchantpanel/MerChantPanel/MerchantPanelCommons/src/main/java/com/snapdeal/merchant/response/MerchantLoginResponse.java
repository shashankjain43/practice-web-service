package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantFlowDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude="token")
public class MerchantLoginResponse extends AbstractResponse {

   private static final long serialVersionUID = 1L;

   private String token;

   private String merchantId;

   private MerchantUserDTO userDTO;
   
   private List<MerchantFlowDTO> merchantState ;
}
