package com.snapdeal.opspanel.clientkeymanagement.response;
import java.util.List;

import lombok.Data;

import com.snapdeal.payments.authorize.core.model.ClientDetails;
import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;
import com.snapdeal.payments.authorize.core.model.ClientSummary;

@Data
public class ShowClientsResponse {
	
	WalletClients walletClients;
	}
