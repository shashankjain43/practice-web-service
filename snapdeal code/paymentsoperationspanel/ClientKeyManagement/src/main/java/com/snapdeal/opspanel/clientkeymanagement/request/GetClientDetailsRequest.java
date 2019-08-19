package com.snapdeal.opspanel.clientkeymanagement.request;

import lombok.Data;

@Data
public class GetClientDetailsRequest {
	String clientName;
	String targetApplication;

}
