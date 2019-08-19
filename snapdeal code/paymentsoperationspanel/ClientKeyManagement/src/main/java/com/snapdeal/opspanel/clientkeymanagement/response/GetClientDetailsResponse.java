package com.snapdeal.opspanel.clientkeymanagement.response;

import java.util.List;

import lombok.Data;

import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;
import com.snapdeal.payments.authorize.core.model.ClientSummary;

@Data
public class GetClientDetailsResponse {
	
	ClientSummary clientSummary;
	List<String> users;

}
