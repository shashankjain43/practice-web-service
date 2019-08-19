package com.snapdeal.vanila.request;

import lombok.Data;



@Data
public abstract class AbstractMerchantRequest extends AbstractRequest {

   private static final long serialVersionUID = -4802665972405660859L;

   private String merchantId;

   private String token;
   
   private String loggedUserId;
   
   private String loggedUserName;

}
