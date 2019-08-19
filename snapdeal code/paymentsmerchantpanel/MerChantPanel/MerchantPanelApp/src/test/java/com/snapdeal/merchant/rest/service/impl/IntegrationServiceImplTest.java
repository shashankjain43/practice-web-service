package com.snapdeal.merchant.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.response.MerchantKeyResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.dto.Key;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.GetMerchantKeysRequest;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.response.GetMerchantKeysResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:*/spring/application-context-mvc.xml")
public class IntegrationServiceImplTest extends AbstractTestNGSpringContextTests {

	@InjectMocks
	private IntegrationServiceImpl integrationService;

	@Spy
	private MOBUtil mobUtil;

	@Mock
	private MpanelConfig config;

	@Mock
	private IUserService userService;

	@Mock
	private IMerchantServices merchantService;

	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void testGetMerchantSandBoxKey() throws MerchantException, ServiceException {

		MerchantKeyRequest request = new MerchantKeyRequest();
		request.setToken("token1");
		request.setLoggedUserId("UserId123");
		request.setMerchantId("merchantId12345");
		request.setLoggedLoginName("LoginName");

		MerchantKeyResponse actualResponse;
		MerchantKeyResponse expectedResponse = new MerchantKeyResponse();
		expectedResponse.setKey("sandboxKey -- 123");
		expectedResponse.setUrl("sandboxUrl -- bsjdjsnxsmakmxk");

		GetUserMerchantRequest mobRequestForDetails = new GetUserMerchantRequest();
		mobRequestForDetails.setUserId("UserId123");
		mobRequestForDetails.setToken("token1");

		MerchantDetails merchantDetails = new MerchantDetails();
		merchantDetails.setIntegrationMode("online");
		merchantDetails.setMerchantId("merchantId12345");

		GetUserMerchantResponse mobResponseForDetails = new GetUserMerchantResponse();
		mobResponseForDetails.setMerchantDetails(merchantDetails);

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant(mobRequestForDetails)).thenReturn(mobResponseForDetails);

		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();
		mobRequest.setMerchantId("merchantId12345");
		mobRequest.setToken("token1");

		List<Key> keyList = new ArrayList<Key>();
		Key key = new Key();
		key.setId("1");
		key.setKey("sandboxKey -- 123");
		key.setActive(true);
		key.setClientId("clientId123");
		key.setDomainId("domainId123");

		keyList.add(key);

		GetMerchantKeysResponse mobResponse = new GetMerchantKeysResponse();
		mobResponse.setKeys(keyList);

		mobUtil.setMerchantService(merchantService);

		Mockito.when(merchantService.getMerchantKeysSandbox(mobRequest)).thenReturn(mobResponse);

		integrationService.setConfig(config);
		Mockito.when(config.getSandboxUrl()).thenReturn("sandboxUrl -- bsjdjsnxsmakmxk");

		actualResponse = integrationService.getMerchantSandBoxKey(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from GetMerchantSandbox API");

	}


	/*@Test(expectedExceptions = MerchantException.class)
	public void testGetMerchantSandBoxKeyHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantKeyRequest request = new MerchantKeyRequest();

		GetUserMerchantRequest mobRequestForDetails = new GetUserMerchantRequest();

		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant(mobRequestForDetails)).thenThrow(e);

		mobUtil.setMerchantService(merchantService);
		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();
		Mockito.when(merchantService.getMerchantKeysSandbox(mobRequest)).thenThrow(e);

		integrationService.getMerchantSandBoxKey(request);

	}*/
	
	@Test
	public void testGetMerchantProductionKey() throws MerchantException, ServiceException {

		MerchantKeyRequest request = new MerchantKeyRequest();
		request.setToken("token1");
		request.setLoggedUserId("UserId123");
		request.setMerchantId("merchantId12345");
		request.setLoggedLoginName("LoginName");

		MerchantKeyResponse actualResponse;
		MerchantKeyResponse expectedResponse = new MerchantKeyResponse();
		expectedResponse.setKey("productionKey -- 123");
		expectedResponse.setUrl("productionUrl -- bsjdjsnxsmakmxk");

		GetUserMerchantRequest mobRequestForDetails = new GetUserMerchantRequest();
		mobRequestForDetails.setUserId("UserId123");
		mobRequestForDetails.setToken("token1");

		MerchantDetails merchantDetails = new MerchantDetails();
		merchantDetails.setIntegrationMode("online");
		merchantDetails.setMerchantId("merchantId12345");
		merchantDetails.setMerchantStatus("ACTIVE");

		GetUserMerchantResponse mobResponseForDetails = new GetUserMerchantResponse();
		mobResponseForDetails.setMerchantDetails(merchantDetails);

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant(mobRequestForDetails)).thenReturn(mobResponseForDetails);

		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();
		mobRequest.setMerchantId("merchantId12345");
		mobRequest.setToken("token1");

		List<Key> keyList = new ArrayList<Key>();
		Key key = new Key();
		key.setId("1");
		key.setKey("productionKey -- 123");
		key.setActive(true);
		key.setClientId("clientId123");
		key.setDomainId("domainId123");

		keyList.add(key);

		GetMerchantKeysResponse mobResponse = new GetMerchantKeysResponse();
		mobResponse.setKeys(keyList);

		mobUtil.setMerchantService(merchantService);

		Mockito.when(merchantService.getMerchantKeysProduction(mobRequest)).thenReturn(mobResponse);

		integrationService.setConfig(config);
		Mockito.when(config.getProdUrl()).thenReturn("productionUrl -- bsjdjsnxsmakmxk");

		actualResponse = integrationService.getMerchantProductionKey(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from GetMerchantProduction API");

	}
	
	@Test(expectedExceptions = MerchantException.class)
	public void testGetMerchantProductionBoxKeyHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantKeyRequest request = new MerchantKeyRequest();

		GetUserMerchantRequest mobRequestForDetails = new GetUserMerchantRequest();

		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant(mobRequestForDetails)).thenThrow(e);

		mobUtil.setMerchantService(merchantService);
		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();
		Mockito.when(merchantService.getMerchantKeysProduction(mobRequest)).thenThrow(e);

		integrationService.getMerchantProductionKey(request);

	}

}
