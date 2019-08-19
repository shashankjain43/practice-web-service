package com.snapdeal.merchant.aero.test.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.service.IMerchantCacheServiceProvider;
import com.snapdeal.merchant.config.constants.AeroConfigConstants;

@Component
public class AeroInitializer {
	
	@Autowired
	IMerchantCacheServiceProvider provider;
	
	@Autowired
	AeroConfigConstants config;
	
	//@PostConstruct
	public void init() throws MPAerospikeException {
		
		provider.connectToCacheCluster(config.getClusterInfo());
		
	}

}
