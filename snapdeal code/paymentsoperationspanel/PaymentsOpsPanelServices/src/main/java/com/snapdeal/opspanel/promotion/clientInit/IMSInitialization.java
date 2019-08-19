package com.snapdeal.opspanel.promotion.clientInit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.snapdeal.ims.utils.ClientDetails;


@Configuration
public class IMSInitialization{


	private String port;

	private String host;

	private String clientId;
	
	private String clientSecret;
	
	private int apiTimeOut;
	
	
	public int getApiTimeOut() {
		return apiTimeOut;
	}


	public void setApiTimeOut(int apiTimeOut) {
		this.apiTimeOut = apiTimeOut;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}



	public String getClientSecret() {
		return clientSecret;
	}


	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}


	@Scope
	public void initIMS() throws Exception {
		ClientDetails.init(host, port, clientSecret, clientId, apiTimeOut);
	}
	
}
