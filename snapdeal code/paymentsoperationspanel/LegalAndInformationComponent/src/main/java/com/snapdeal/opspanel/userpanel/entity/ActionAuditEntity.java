package com.snapdeal.opspanel.userpanel.entity;

import java.util.Date;

import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;

import lombok.Data;

@Data
public class ActionAuditEntity {

	private String clientName;
	private String clientRole;
	private UserPanelAction action;
	private String actionkeyType;
	private String actionKey;
	private String reason;
	private Date actionTime;
	private String comments;
	private String typeOfFraud;

}
