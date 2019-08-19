package com.snapdeal.opspanel.userpanel.request;

import java.util.List;

import lombok.Data;

@Data
public class BlacklistingRequest {
   private List<String> emailIdList;
}
