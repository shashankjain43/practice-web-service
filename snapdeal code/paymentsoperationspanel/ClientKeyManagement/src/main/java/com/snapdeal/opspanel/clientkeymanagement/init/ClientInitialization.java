/*package com.snapdeal.opspanel.clientkeymanagement.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.snapdeal.payments.sdmoney.admin.client.SDMoneyAdminClient;
import com.snapdeal.payments.sdmoney.admin.client.utils.ClientAuthDetails;

@Configuration
public class ClientInitialization<Context extends Enum<Context>>{
	@Value("${admin.clientKey}")
	private String admin_clientKey;
	
	@Value("${admin.clientName}")
	private String admin_clientName;
	
	@Value("${admin.url}")
	private String admin_url;
	
	@Value("${admin.connectionRequestTimeout}")
	private String admin_connectionRequest;
	
	@Value("${admin.connectionTimeout}")
	private String admin_connectionTimeout;
	
	@Bean
	@Scope
	public SDMoneyAdminClient initAdminClient () throws Exception {
		ClientAuthDetails clientDetails = new ClientAuthDetails();
		clientDetails.setClientKey(admin_clientKey);
		clientDetails.setClientName(admin_clientName);
		clientDetails.setUrl(admin_url);
		clientDetails.setConnectTimeout(Integer.parseInt(admin_connectionTimeout));
		clientDetails.setConnectionRequestTimeout(Integer.parseInt(admin_connectionRequest));
		SDMoneyAdminClient client = new SDMoneyAdminClient(clientDetails);
		return client;
		
	}

}
*/