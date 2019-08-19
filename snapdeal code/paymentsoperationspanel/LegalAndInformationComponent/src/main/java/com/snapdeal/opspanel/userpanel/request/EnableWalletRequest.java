package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.opspanel.userpanel.enums.FraudManagementReason;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;

import lombok.Data;

@Data
public class EnableWalletRequest {
	
	   private UserPanelIdType userIdType;
	   private String userId;
	   private String reason;
	   private String otherReason;
	   private String actionPerformer;
	   private String requestId;
	   private String typeOfFraud;
}
