package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.admin.dao.entity.IMSAPIDetails;

@Getter
@Setter
public class WhiteListAPI {

   private String clientId;
   
   private IMSAPIDetails apiDetails;

   private boolean isAllowed;
   
}
