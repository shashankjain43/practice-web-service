package com.snapdeal.ims.token.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.ITokenService;

public class ActivityDataServiceImplTest {

	@InjectMocks
	ActivityDataServiceImpl activityDataService;

	@Mock
	AuthorizationContext context;

	@Mock
	ITokenService tokenService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}
	@Test
	public void validateToken() {
		activityDataService.validateToken("");
	}
	
	@Test
	public void setActivityDataByUserId() {
		activityDataService.setActivityDataByUserId("");
	}

	@Test
	public void setActivityDataByToken() {
		activityDataService.setActivityDataByToken("");
	}

	@Test
	public void setActivityDataByEmailId() {
		activityDataService.setActivityDataByEmailId("");
	}



}
