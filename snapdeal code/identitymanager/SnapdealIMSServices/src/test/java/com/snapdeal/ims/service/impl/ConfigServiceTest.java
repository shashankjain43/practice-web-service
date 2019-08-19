package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.dao.IConfigDetailsDao;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;
import com.snapdeal.ims.utility.IMSUtility;

import static org.mockito.Matchers.any;

public class ConfigServiceTest {
	
	@InjectMocks
	private ConfigServiceImpl configServiceImpl;
	
	@Mock
	private IMSUtility imsUtility;
	
	@Mock
	private IConfigDetailsDao configDetailsDao;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testgetAllConfigs() {
		
		//doesn't make sense
		List<ConfigDetails> configList = new ArrayList<ConfigDetails>();
		Mockito.when(configDetailsDao.getAllConfigs()).thenReturn(configList);
		System.out.println(configServiceImpl.getAllConfigs());
	}
	
	@Test
	public void testgetConfig() {
		
		//doesn't make sense
		Mockito.when(configDetailsDao.getConfig(any(String.class), any(String.class))).thenReturn(new ConfigDetails());
		System.out.println(configServiceImpl.getConfig("configKey", "configType"));
	}
	
	@Test
	public void testgetConfigsByKey() {
		
		//doesn't make sense
		List<ConfigDetails> configList = new ArrayList<ConfigDetails>();
		Mockito.when(configDetailsDao.getConfigsByKey(any(String.class))).thenReturn(configList);
		System.out.println(configServiceImpl.getConfigsByKey("configKey"));
	}
	
	@Test
	public void testgetConfigsByType() {
		
		//doesn't make sense
		List<ConfigDetails> configList = new ArrayList<ConfigDetails>();
		Mockito.when(configDetailsDao.getConfigsByType(any(String.class))).thenReturn(configList);
		System.out.println(configServiceImpl.getConfigsByType("configType"));
	}
	
}
