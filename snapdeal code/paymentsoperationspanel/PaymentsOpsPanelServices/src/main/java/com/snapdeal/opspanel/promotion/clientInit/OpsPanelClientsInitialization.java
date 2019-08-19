package com.snapdeal.opspanel.promotion.clientInit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.snapdeal.opspanel.restclient.BaseClient;
import com.snapdeal.opspanel.restclient.ClientConfig;

@Configuration
public class OpsPanelClientsInitialization {

	private final String CONTENT_TYPE = "Content-Type";
	private final String ACCEPT = "Accept";
	private final String applicationJson = "application/json";
	
	@Value("${id.client.url}")
	private String url;
	
	@Value("${id.client.accessKey}")
	private String access_key;
	
	@Value("${id.client.secretKey}")
	private String secret_key;

	@Value( "${cstool.client.url}" )
	private String cstoolClientUrl;

	@Value( "${cstool.client.timeout}" )
	private String cstoolClientTimeout;


	@Value( "${campaign.client.url}" )
	private String campaignClientUrl;

	@Value( "${campaign.client.timeout}" )
	private String campaignClientTimeout;

	@Bean
	@Qualifier( "InvalidDenominationClient" )
	@Scope
	public BaseClient initInvalidDenominationClient() throws Exception {

		Map<String, String> headers = new HashMap<String,String>();
		
		headers.put(CONTENT_TYPE, applicationJson);
		headers.put(ACCEPT, applicationJson);
		
		headers.put("accessKey", access_key);
		headers.put("secretKey", secret_key);

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setApiTimeout( 10000 );
		clientConfig.setBaseUrl( url );
		clientConfig.setCommonHeaders( headers );
		
		return new BaseClient( clientConfig );
	}

	@Bean
	@Qualifier( "CSToolClient" )
	@Scope
	public BaseClient initCSToolClient() throws Exception {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig.setApiTimeout( Integer.parseInt( cstoolClientTimeout ) );
		clientConfig.setBaseUrl( cstoolClientUrl );

		return new BaseClient( clientConfig );
	}

	@Bean
	@Qualifier( "CampaignClient" )
	@Scope
	public BaseClient initCampaignClient() throws Exception {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig.setApiTimeout( Integer.parseInt( campaignClientTimeout ) );
		clientConfig.setBaseUrl( campaignClientUrl );

		return new BaseClient( clientConfig );
	}
}
