package com.snapdeal.ims.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;

@Ignore
public class UserMigrationServiceTest {

	@InjectMocks
	private UserMigrationServiceImpl umigs;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = IMSServiceException.class)
	public void testupgradeUser() {
		System.out.println(umigs.upgradeUser(new UserUpgradeRequest()));
	}
	
	@Test
	public void testgetUserUpgradeStatus() {
		System.out.println(umigs.getUserUpgradeStatus(new UserUpgradeByEmailRequest(), false));
	}
}
