package com.snapdeal.opspanel.clientkeymanagement.response;

import java.util.List;

import lombok.Data;

import com.snapdeal.opspanel.clientkeymanagement.enums.TargetApplications;
import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;

@Data
public class WalletClients {
	
	String targetApplication;
	List<ClientDetailsSummary> clients;
	
	
	public WalletClients() {
		this.targetApplication = TargetApplications.WALLET.toString();
	}
	

}


