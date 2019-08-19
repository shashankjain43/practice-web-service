package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class WhitelistApiUpdateStatusRequest {

   @NotEmpty
   private String clientId;
   
   @NotEmpty
   private long imsApiId;
   
}
