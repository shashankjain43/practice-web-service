package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.token.service.IActivityDataService;

public class JobSchedularServiceImplTest {
	
	@InjectMocks
	JobSchedularServiceImpl jobSchedularService;
	
	@Mock
	IActivityDataService activityDataService;
	
	@Mock
	private IMSServiceImpl imsService;

	@Mock
	private IUserMigrationService userMigrationService;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

	}
	@Test
	public void upgradeUserTest(){
		UpgradeUserByEmailRequest request=new UpgradeUserByEmailRequest();
		request.setEmailId("abc123@gmail.com");
		UpgradeUserByEmailResponse response=new UpgradeUserByEmailResponse();
		response.setStatus(true);
		User user=new User();
		user.setEmailId("abc123@gmail.com");
		user.setUserId("123");
		user.setStatus(UserStatus.UNVERIFIED);
		Mockito.when(imsService.updateUserStatus("abc123@gmail.com")).thenReturn(user);
		Mockito.when(userMigrationService.upgradeUserStatusViaResetPassword(any(String.class),any(UserDetailsDTO.class))).thenReturn(true);
		UpgradeUserByEmailResponse responseAct=jobSchedularService.upgradeUser(request);
		Assert.assertEquals(responseAct.isStatus(),response.isStatus());
	
}
	}