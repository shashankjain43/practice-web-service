package com.snapdeal.opspanel.userpanel.request;

import javax.validation.constraints.NotNull;

import com.snapdeal.opspanel.userpanel.enums.FraudManagementReason;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;

import lombok.Data;

@Data
public class ActionBulkRequest {

	private String uploadFilePath;

	@NotNull( message = "Please enter some action.")
	private UserPanelAction action;

	@NotNull( message = "Please enter some reason." )
	private String reason;
    private String otherReason;
    private UserPanelIdType idType;
    private String typeOfFraud;
    private String actionPerformer;
    private String requestId;
}
