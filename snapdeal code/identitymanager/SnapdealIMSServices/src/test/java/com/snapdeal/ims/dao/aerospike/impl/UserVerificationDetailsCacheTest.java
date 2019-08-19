package com.snapdeal.ims.dao.aerospike.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.token.service.impl.BaseTest;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class UserVerificationDetailsCacheTest extends BaseTest{

	@Autowired
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Autowired
	IIMSCacheServiceProvider imsCacheService;

	@Before
	public void setup(){
	     initConfig();
	     imsCacheService.connectToCacheCluster(null);
	}
	
	@Test
	public void testCreateSuccess() {
	   
		UserVerification userVerificationEntity = userVerificationDetailsDao
				.getUserVerificationDetailsByCode("Code2");
		Assert.assertNull("UserVerificationEntity for 'Code2' should be null",
				userVerificationEntity);

		userVerificationEntity = getTestUserVerificationEntity("Code2",
				"TestUser2");
		userVerificationDetailsDao.create(userVerificationEntity);

		UserVerification createdVerificationEntity = userVerificationDetailsDao
				.getUserVerificationDetailsByCode("Code2");

		Assert.assertEquals(
				"Unable to fetch user verification entity correctly",
				"TestUser2", createdVerificationEntity.getUserId());
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateFail() {

		UserVerification userVerificationEntity = getTestUserVerificationEntity(
				"Code3", "TestUser3");
		userVerificationDetailsDao.create(userVerificationEntity);

		UserVerification createdVerificationEntity = userVerificationDetailsDao
				.getUserVerificationDetailsByCode("Code3");

		Assert.assertEquals(
				"Unable to fetch user verification entity correctly",
				"TestUser3", createdVerificationEntity.getUserId());

		userVerificationDetailsDao.create(userVerificationEntity);

	}

	@Test
	public void testGetUserVerificationDetailsByCode() {
		UserVerification userVerificationEntity = getTestUserVerificationEntity(
				"Code1", "TestUser1");
		userVerificationDetailsDao.create(userVerificationEntity);

		UserVerification createdVerificationEntity = userVerificationDetailsDao
				.getUserVerificationDetailsByCode("Code1");

		Assert.assertEquals(
				"Unable to fetch user verification entity correctly",
				"TestUser1", createdVerificationEntity.getUserId());
	}

	private UserVerification getTestUserVerificationEntity(String code,
			String userId) {

		UserVerification userVerificationEntity = new UserVerification();
		userVerificationEntity.setCode(code);
		userVerificationEntity.setCodeExpiryTime(new Timestamp(new Date()
				.getTime()));
		userVerificationEntity.setCreatedTime(new Timestamp(new Date()
				.getTime()));
		userVerificationEntity.setUserId(userId);
		return userVerificationEntity;
	}
}
