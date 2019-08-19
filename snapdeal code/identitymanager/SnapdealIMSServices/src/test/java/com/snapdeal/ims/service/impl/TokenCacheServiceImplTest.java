package com.snapdeal.ims.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.cache.service.impl.TokenCacheServiceImpl;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.service.impl.BaseTest;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TokenCacheServiceImplTest extends BaseTest {

	@Autowired
	TokenCacheServiceImpl tokenCacheService;
	
	@Autowired
	@Qualifier("IMSAerospikeServiceProviderMock")
	private IIMSCacheServiceProvider cacheServiceProvider;
	
	@Before
    public void setup(){
		initConfig();
		cacheServiceProvider.connectToCacheCluster(null);
    }
	
	public GlobalTokenDetailsEntity getGlobalToken(String userId,String GlobalToken,String globalTokenId){
		GlobalTokenDetailsEntity gToken = new GlobalTokenDetailsEntity();
		gToken.setUserId(userId);
		gToken.setGlobalToken(GlobalToken);
		gToken.setGlobalTokenId(globalTokenId);
		gToken.setExpiryTime(new Date(2015-12-01));
		return gToken;
	}
	@Test
	public void getGTokenIDSetSizeByUserId(){
		GlobalTokenDetailsEntity gToken1 = getGlobalToken("100","GToken1","200");
		tokenCacheService.putGTokenById(gToken1);
		GlobalTokenDetailsEntity gToken2 = getGlobalToken("100","GToken2","201");
		tokenCacheService.putGTokenById(gToken2);
		GlobalTokenDetailsEntity gToken3 = getGlobalToken("100","Gtoken3","202");
		tokenCacheService.putGTokenById(gToken3);
		
		int size = tokenCacheService.getGTokenIDSetSizeByUserId("100");
		Assert.assertTrue(size==3);
	}
}
