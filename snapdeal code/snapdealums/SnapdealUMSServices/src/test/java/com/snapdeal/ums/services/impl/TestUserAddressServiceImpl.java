package com.snapdeal.ums.services.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.oms.base.model.GetShippingHistoryRequest;
import com.snapdeal.oms.base.model.GetShippingHistoryResponse;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.userAddress.AddUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.server.services.IUserAddressServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;
import com.snapdeal.ums.server.services.impl.UserAddressServiceImpl;
import com.snapdeal.ums.utils.EncryptionUtils;

public class TestUserAddressServiceImpl {

	@InjectMocks
	UserAddressServiceImpl testUserAddressServiceImpl;

	@Mock
	IUserServiceInternal testUserServiceInternal;
	
	@Mock
	IUserAddressServiceInternal testUserAddressServiceInternal;
	
	@Mock
	IUMSConvertorService testUMSConvertorService;
	
	@Mock
	IOrderClientService testOrderClientService;
	
	@Mock
	IMSService testIMSService;
	
	@InjectMocks
	CacheManager testCacheManager = CacheManager.getInstance();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = UserAddressException.class)
	public void testGetUserAddressByUserId_Exception()
			throws UserAddressException {

		GetUserAddressesByUserIdRequest testExceptionRequest = new GetUserAddressesByUserIdRequest();
		testUserAddressServiceImpl
				.getUserAddressesByUserId(testExceptionRequest);
	}

	@Test
	public void testGetUserAddressByUserId_UserNull()
			throws UserAddressException {
		int testId = 3;
		GetUserAddressesByUserIdRequest testRequest = new GetUserAddressesByUserIdRequest(
				testId);
		Mockito.when(testUserServiceInternal.getUserById(testId)).thenReturn(
				null);
		GetUserAddressesByUserIdResponse testResponse = testUserAddressServiceImpl
				.getUserAddressesByUserId(testRequest);
		boolean expectedSuccessfull = true;
		boolean actualSuccessfull = testResponse.isSuccessful();
		Assert.assertEquals(actualSuccessfull, expectedSuccessfull);
	}

	@Test
	public void testGetUserAddressByUserId_NotUseOmsAddress()
			throws Exception {
		int testId = 3;
		GetUserAddressesByUserIdRequest testRequest = new GetUserAddressesByUserIdRequest(
				testId);
		User testUser = Mockito.mock(User.class);
		Mockito.when(testUserServiceInternal.getUserById(testId)).thenReturn(
				testUser);
		
		UMSPropertiesCache testProperties=new UMSPropertiesCache();
		testProperties.addProperty("use.oms.addresses", "false");
		CacheManager.getInstance().setCache(testProperties);
		
		List<UserAddressSRO> testResult=new ArrayList<UserAddressSRO>();
		Mockito.when(testUserAddressServiceInternal.getUserAddressById(any(Integer.class))).thenReturn(new UserAddress());
		Mockito.when(testUMSConvertorService.getUserAddressSROsFromEntities(any(List.class))).thenReturn(testResult);
		
		GetUserAddressesByUserIdResponse testResponse = testUserAddressServiceImpl
				.getUserAddressesByUserId(testRequest);
		Assert.assertEquals(testResponse.isSuccessful(), true);
		Assert.assertEquals(testResponse.getUserAddresses(), testResult);
	}
	
	@Test
	public void testGetUserAddressByUserId_UseOmsAddress() throws Exception
	{
		int testId = 3;
		GetUserAddressesByUserIdRequest testRequest = new GetUserAddressesByUserIdRequest(
				testId);
		User testUser = Mockito.mock(User.class);
		Mockito.when(testUserServiceInternal.getUserById(testId)).thenReturn(
				testUser);
		
		UMSPropertiesCache testProperties=new UMSPropertiesCache();
		testProperties.addProperty("use.oms.addresses", "true");
		CacheManager.getInstance().setCache(testProperties);
		
		GetShippingHistoryResponse testShippingResponse=new GetShippingHistoryResponse();
		testShippingResponse.setSuccessful(true);
		Mockito.when(testOrderClientService.getShippingHistoryByEmail(any(GetShippingHistoryRequest.class))).thenReturn(testShippingResponse);
		
		GetUserAddressesByUserIdResponse testResponse = testUserAddressServiceImpl
				.getUserAddressesByUserId(testRequest);
		Assert.assertEquals(testResponse.isSuccessful(), true);
	}
	
	@Test(expected=UserAddressException.class)
	public void testAddUserAddress_Exception() throws UserAddressException
	{
		AddUserAddressRequest testRequest=new AddUserAddressRequest();
		testUserAddressServiceImpl.addUserAddress(testRequest);
	}
	
	@Test(expected=UserAddressException.class)
	public void testAddUserAddress_Exception2() throws UserAddressException
	{
		int testId=3;
		UserAddressSRO testSRO=new UserAddressSRO();
		testSRO.setUserId(testId);
		AddUserAddressRequest testRequest=new AddUserAddressRequest(testSRO);
		testUserAddressServiceImpl.addUserAddress(testRequest);
	}
	
	@Test
	public void testAddUserAddress_Successfull() throws UserAddressException
	{
		int testId=3;
		boolean expected=true;
		String testEncryptedUserId=EncryptionUtils.encrypt(testId);
		UserAddressSRO testSRO=new UserAddressSRO();
		testSRO.setUserId(testId);
		UserOwner testOwner=Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class))).thenReturn(testOwner);
		Mockito.when(testUserServiceInternal.isUserExistsById(any(Integer.class))).thenReturn(expected);
		AddUserAddressRequest testRequest=new AddUserAddressRequest(testSRO,testEncryptedUserId);
		AddUserAddressResponse testResponse=testUserAddressServiceImpl.addUserAddress(testRequest);
		Assert.assertEquals(testResponse.isSuccessful(), expected);
	}
	
	@Test
	public void testAddUserAddress_NotSuccessfull() throws UserAddressException
	{
		int testId=3;
		boolean expected=false;
		String testEncryptedUserId=EncryptionUtils.encrypt(testId);
		UserAddressSRO testSRO=new UserAddressSRO();
		testSRO.setUserId(testId);
		UserOwner testOwner=Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class))).thenReturn(testOwner);
		Mockito.when(testUserServiceInternal.isUserExistsById(any(Integer.class))).thenReturn(expected);
		AddUserAddressRequest testRequest=new AddUserAddressRequest(testSRO,testEncryptedUserId);
		AddUserAddressResponse testResponse=testUserAddressServiceImpl.addUserAddress(testRequest);
		Assert.assertEquals(testResponse.isSuccessful(), expected);
	}
}
