package com.snapdeal.merchant.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="token")
public abstract class AbstractMerchantRequest extends AbstractRequest {

   private static final long serialVersionUID = -4802665972405660859L;

   private String merchantId;

   private String token;
   
   private String loggedUserId;
   
   private String loggedLoginName;

}
