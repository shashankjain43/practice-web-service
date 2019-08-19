package com.snapdeal.opspanel.userpanel.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EnableUserRequest {
   String userIdType;
   private String userId;
   private MultipartFile file;
   //TODO ask for agent name
}
