package com.snapdeal.ims.client.dao.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.client.dbmapper.entity.Activity;
import com.snapdeal.ims.client.dbmapper.entity.info.ActivityStatus;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityDetailsDaoTest {

	@Autowired
	ActivityDetailsDao activityDetailsDao;

	@Test
	public void testCreateActivitySuccess() {

		Activity testActivity = new Activity();
		testActivity.setActivityType("testActivity");
		testActivity.setActivitySubtype("testActivitySubType");
		testActivity.setClientId("AA123");
		testActivity.setCreatedDate(new Date());
		testActivity.setEntityId("testEntityId");
		testActivity.setId(230000);
		testActivity.setIpAddress("10.22.33.44");
		testActivity.setMacAddress("00-14-22-01-23-45");
		testActivity.setStatus(ActivityStatus.SUCCESS);

		long returnValue = activityDetailsDao.createActivity(testActivity);
		Assert.assertTrue("Unable to create activity", returnValue > 0);
	}

	@Test
	public void testCreateActivityException() {

	   Assert.assertEquals(-1, activityDetailsDao.createActivity(null));
	}

}
