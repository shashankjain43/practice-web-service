package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.opspanel.userpanel.enums.FraudManagementReason;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;

import lombok.Data;

@Data
public class EnableDisableUserRequest {

	private String userId;
	private UserPanelAction action;
	private String reason;
    private String otherReason;
    private String actionPerformer;
    private String requestId;
    private String typeOfFraud;

}
