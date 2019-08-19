package com.snapdeal.ims.token.dao.aerospike.impl;

import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.service.impl.BaseTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class GlobalTokenDetailsCacheTest extends BaseTest{

	@Autowired
	private IGlobalTokenDetailsDAO globalTokenDetailsCacheImpl;
	
	@Autowired
   IIMSCacheServiceProvider imsCacheService;

	@Before
	public void insertTestData() {
	   
	   initConfig();
	   imsCacheService.connectToCacheCluster(null);
	   	   
		GlobalTokenDetailsEntity detailEntity = new GlobalTokenDetailsEntity();
		detailEntity.setExpiryTime(new Date());
		detailEntity.setCreatedTime(new Date());
		detailEntity.setGlobalToken("globalToken");
		detailEntity.setGlobalTokenId("123");
		detailEntity.setUserId("testId");
		globalTokenDetailsCacheImpl.save(detailEntity);

		detailEntity = new GlobalTokenDetailsEntity();
		detailEntity.setExpiryTime(new Date());
		detailEntity.setCreatedTime(new Date());
		detailEntity.setGlobalToken("globalToken2");
		detailEntity.setGlobalTokenId("124");
		detailEntity.setUserId("testId2");
		globalTokenDetailsCacheImpl.save(detailEntity);

	}

	@After
	public void cleanupTestData() {
	   try{
	      globalTokenDetailsCacheImpl.delete("123");
	      } catch(NullPointerException e){}
	   
	   try{
	      globalTokenDetailsCacheImpl.delete("124");
	      } catch(NullPointerException e){}
	}

	@Test
   @Ignore
	public void testSaveSuccess() {
		GlobalTokenDetailsEntity detailEntity = new GlobalTokenDetailsEntity();
		detailEntity.setExpiryTime(new Date());
		detailEntity.setCreatedTime(new Date());
		detailEntity.setGlobalToken("globalToken");
		detailEntity.setGlobalTokenId("125");
		detailEntity.setUserId("testId3");
		globalTokenDetailsCacheImpl.save(detailEntity);

		GlobalTokenDetailsEntity resultEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("125");
		Assert.assertEquals("Unable to save Global Token correctly", "testId3",
				resultEntity.getUserId());

	}

   @Ignore
	@Test(expected = IMSServiceException.class)
	public void testSaveFail() {
		GlobalTokenDetailsEntity detailEntity = new GlobalTokenDetailsEntity();
		detailEntity.setExpiryTime(new Date());
		detailEntity.setCreatedTime(new Date());
		detailEntity.setGlobalToken("globalToken");
		detailEntity.setGlobalTokenId("125");
		detailEntity.setUserId(null);
		globalTokenDetailsCacheImpl.save(detailEntity);
	}

	@Test
	public void testGetGlobalTokenById() {
		GlobalTokenDetailsEntity resultEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("123");
		Assert.assertEquals("Unable to save Global Token correctly", "testId",
				resultEntity.getUserId());

		resultEntity = globalTokenDetailsCacheImpl.getGlobalTokenById("124");
		Assert.assertEquals("Unable to get Global Token correctly", "testId2",
				resultEntity.getUserId());

	}

	@Test
   @Ignore
	public void testDeleteSuccess() {

		globalTokenDetailsCacheImpl.delete("123");
		globalTokenDetailsCacheImpl.delete("124");

		GlobalTokenDetailsEntity resultEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("123");
		Assert.assertNull("Unable to save Global Token correctly", resultEntity);

		resultEntity = globalTokenDetailsCacheImpl.getGlobalTokenById("124");
		Assert.assertNull("Unable to delete Global Token correctly",
				resultEntity);

	}

	@Test
	public void testDeleteAllTokenForUser() {

		GlobalTokenDetailsEntity initialEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("124");
		Assert.assertEquals("Unable to get Global Token correctly", "testId2",
				initialEntity.getUserId());
		globalTokenDetailsCacheImpl.deleteAllTokenForUser("testId2");

		GlobalTokenDetailsEntity finalEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("124");
		Assert.assertNull("Unable to get Global Token correctly", finalEntity);

	}

	@Test
   @Ignore
	public void testUpdateExpiryDateSuccess() {

		GlobalTokenDetailsEntity initialEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("123");
		Date initialDate = initialEntity.getExpiryTime();
		globalTokenDetailsCacheImpl.updateExpiryDate(initialEntity);
		Date updatedDate = initialEntity.getExpiryTime();

		Assert.assertTrue("Failed to update date correctly",
				initialDate.getTime() <= updatedDate.getTime());

	}

   @Ignore
	@Test(expected = IMSServiceException.class)
	public void testUpdateExpiryDateFail() {

		GlobalTokenDetailsEntity initialEntity = globalTokenDetailsCacheImpl
				.getGlobalTokenById("129");
		Assert.assertNull("Entity should not exist", initialEntity);
		globalTokenDetailsCacheImpl.updateExpiryDate(initialEntity);

	}

}
