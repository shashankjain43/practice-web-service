package com.snapdeal.opspanel.promotion.model;

import java.util.Map;

import lombok.Data;

import com.snapdeal.payments.notification.utility.ChannelType;

@Data
public class NotificationRequestModel {

	private String userId;
	
	private String templateId;
	
	private ChannelType channelType;
	
	private Map<String,Object> templateParams;
		
	
}
