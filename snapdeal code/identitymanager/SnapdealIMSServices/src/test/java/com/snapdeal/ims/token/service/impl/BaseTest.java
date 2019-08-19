package com.snapdeal.ims.token.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;

public abstract class BaseTest {

   protected final AuthorizationContext authContext = new AuthorizationContext();

	protected void initConfig() {

		Map<String, String> globalMap = new HashMap<String, String>();
		globalMap.put("default.tokengeneration.service.version", "V01");
		globalMap.put("default.tokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.TokenGenerationServiceV01Impl");
		globalMap.put("default.globaltokengeneration.service.version", "V01");
		globalMap.put("default.globaltokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV01Impl");
		globalMap.put("cipher.unique.key", "U25hcGRlYWxVbmlxdWVLZXk=");
		globalMap.put("days.to.renew.token", "5");
		globalMap.put("dummy.migration.enabled", "true");
      globalMap.put("upgrade.percentage", "100");
      globalMap.put("link.upgrade.globaltokengeneration.service.version", "V02");
      globalMap.put("link.upgrade.tokengeneration.service.version", "V02");
      globalMap.put("default.tokengeneration.service.V02",
               "com.snapdeal.ims.token.service.impl.TokenGenerationServiceV02Impl");
      globalMap.put("default.globaltokengeneration.service.V02",
               "com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV02Impl");
      globalMap.put("aerospike.userIdByGTokenIds.update.maxRetries","5");
      globalMap.put("aerospike.userIdByGTokenIds.retry.sleep.in.milliseconds","200");

		final ConfigCache configCache = new ConfigCache();
		configCache.put("global", globalMap);
		// map.clear();
		Map<String, String> clientMap = new HashMap<String, String>();
		clientMap.put("global.token.expiry.time", "30");
		clientMap.put("token.expiry.time", "30");
		configCache.put("WEB", clientMap);
		
		Map<String, String> clientConfigMap = new HashMap<String, String>();
		clientConfigMap.put("upgrade.enabled", "true");
      configCache.put("1", clientConfigMap);
      
		CacheManager.getInstance().setCache(configCache);

		ClientCache clientConfig = new ClientCache();
		Client client = getClient();
		clientConfig.put(client.getClientId(), client);
		CacheManager.getInstance().setCache(clientConfig);
		
		setAuthorizationContext();
	}

	private Client getClient() {
		Client cli = new Client();
		cli.setClientId("1");
		cli.setClientKey("snapdeal");
		cli.setClientName("test client");
		cli.setMerchant(Merchant.SNAPDEAL);
		cli.setClientType(ClientType.USER_FACING);
		cli.setClientPlatform(ClientPlatform.WEB);
		cli.setImsInternalAlias("TESTALIAS");
		return cli;
	}
	
	private void setAuthorizationContext(){
      authContext.set(IMSRequestHeaders.USER_AGENT.toString(), "web");
      authContext.set(IMSRequestHeaders.CLIENT_ID.toString(), "1");
      authContext.set(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString(), "blah");
   }
	
	public void changeClientMerchant(Merchant merchant){
      CacheManager.getInstance().getCache(ClientCache.class).get("1").setMerchant(merchant);
	}
}
