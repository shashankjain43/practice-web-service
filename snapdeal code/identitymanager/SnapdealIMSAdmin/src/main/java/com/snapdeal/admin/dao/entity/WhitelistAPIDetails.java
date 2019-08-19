package com.snapdeal.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WhitelistAPIDetails {

   private String clientId;
   private long imsApiId;
   private boolean isAllowed;
   
}
