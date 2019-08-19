package com.snapdeal.ims.test.authorization.impl;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.authorization.impl.ImsRequestAuthorizationImpl;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.client.service.IClientService;

public class ImsRequestAuthorizationImplTest {

	@InjectMocks
	ImsRequestAuthorizationImpl imsRequestAuthorization;

	@Mock
	private AuthorizationContext context;

	@Mock
	private IClientService clientService;

	private String CLIENT_SECRET_ID = "CLIENT_SECRET";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
}
