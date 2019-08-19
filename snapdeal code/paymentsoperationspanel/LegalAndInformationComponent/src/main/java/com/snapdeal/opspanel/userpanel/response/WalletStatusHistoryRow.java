package com.snapdeal.opspanel.userpanel.response;

import java.util.Date;

import lombok.Data;

@Data
public class WalletStatusHistoryRow {

   private Date statusChangeDate;
   private String agentName;
   private String reason;

}
