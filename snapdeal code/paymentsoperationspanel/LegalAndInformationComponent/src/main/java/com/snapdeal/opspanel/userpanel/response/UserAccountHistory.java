package com.snapdeal.opspanel.userpanel.response;

import java.util.List;

import lombok.Data;

@Data
public class UserAccountHistory {

   private String accountStatus; //TODO find enum for this if any
   private List<AccountStatusHistoryRow> accountStatusHistory;
   private List<WalletStatusHistoryRow> walletStatusHistory;

}
