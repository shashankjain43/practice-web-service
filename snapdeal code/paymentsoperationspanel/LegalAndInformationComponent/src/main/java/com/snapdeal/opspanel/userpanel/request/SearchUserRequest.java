package com.snapdeal.opspanel.userpanel.request;

import lombok.Data;

@Data
public class SearchUserRequest {
   private String name;
   private String email;
   private String mobileNumber;
   private String userId;
   private String accountCreationDate;
   private String blacklistingStatus;
   private String searchId;
   private String fromDate;
   private String toDate;
}
