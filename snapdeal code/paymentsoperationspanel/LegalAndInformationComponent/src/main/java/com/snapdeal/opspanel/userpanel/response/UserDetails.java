package com.snapdeal.opspanel.userpanel.response;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

import lombok.Data;

@Data
public class UserDetails {

   private String name;
   private String email;
   private String mobileNumber;
   private String userId;
   private String accountCreationDate;
   private String blacklistingStatus; //TODO ask for enums
   private String walletAccountStatus; //TODO ask for enums
   private String imsAccountStatus; //TODO ask for enums
   private String migrationStatus; //TODO ask for enums
   private BigDecimal generalAccountBalance;
   private BigDecimal generalVoucherBalance;
   private URL transactionHistoryDownlaodUrl;
   private long voucherCount;
   private Date expirationDate;

}
