package com.snapdeal.ims.service.impl;

import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.dao.IBlackWhiteEntityDao;
import com.snapdeal.ims.dao.IConfigDetailsDao;
import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;
import com.snapdeal.ims.otp.sms.utility.SMSTemplateInfo;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.notifier.service.Notifier;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CacheUpdaterTest {

	@InjectMocks
	private CacheUpdater cacheUpdater;

	@Mock
	private IConfigDetailsDao configDetailsMapper;

	@Mock
	private IClientDetailsDao clientDetailsMapper;

	@Mock
	private IIMSCacheServiceProvider imsCacheService;
	
	@Mock
	private OTPUtility otpUtility ;
	
	@Mock
	private SMSTemplateInfo smsTemplateInfo ;
	
	@Mock
   private Notifier notifier;
	
	@Mock
	private IBlackWhiteEntityDao blackEntityDao;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

   @Ignore
	@Test
	public void testloadAll() {
		List<ConfigDetails> configList = new ArrayList<ConfigDetails>();
		ConfigDetails config = new ConfigDetails();
		config.setConfigKey("configKey");
		config.setConfigType("configType");
		config.setConfigValue("configValue");
		config.setDescription("description");

		configList.add(config);

		// aerospike details
		ConfigDetails aerospikeConfig = new ConfigDetails();
		aerospikeConfig.setConfigKey("aerospike.cluster");
		aerospikeConfig.setConfigType("global");
		aerospikeConfig.setConfigValue("hostname:port");

		configList.add(aerospikeConfig);
		Mockito.when(configDetailsMapper.getAllConfigs())
				.thenReturn(configList);

		List<Client> clients = new ArrayList<Client>();
		Client client = new Client();
		client.setClientId("clientId");

		clients.add(client);

		Mockito.when(clientDetailsMapper.getAllClient()).thenReturn(clients);
		Mockito.doNothing().when(notifier).registerOrRefreshEmailTemplate(Mockito.any(Map.class));
		
		List<BlackList> blackList = new ArrayList<BlackList>();
		Mockito.when(blackEntityDao.getAllEntities()).thenReturn(blackList);

		System.out.println(cacheUpdater.loadAll());
	}
}
