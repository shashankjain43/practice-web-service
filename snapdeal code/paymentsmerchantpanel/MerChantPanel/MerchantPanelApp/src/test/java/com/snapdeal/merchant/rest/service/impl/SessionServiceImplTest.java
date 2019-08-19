package com.snapdeal.merchant.rest.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.neo4j.cypher.internal.compiler.v2_1.functions.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantFlowDTO;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.response.GetMerchantStateResponse;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.test.util.RMSMapper;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.dto.OnBoardingStepDTO;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.AddUserRequest;
import com.snapdeal.mob.request.GetMerchantProfileStatusRequest;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.response.GetMerchantProfileStatusResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.payments.roleManagementClient.exceptions.HttpTransportException;
import com.snapdeal.payments.roleManagementModel.dto.Token;
import com.snapdeal.payments.roleManagementModel.exceptions.InvalidCodeException;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.LogoutUserRequest;
import com.snapdeal.payments.roleManagementModel.request.Role;
import com.snapdeal.payments.roleManagementModel.request.User;
import com.snapdeal.payments.roleManagementModel.request.VerifyCodeRequest;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;
import com.snapdeal.payments.roleManagementModel.response.LogoutUserResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:*/spring/application-context-mvc.xml")
public class SessionServiceImplTest extends AbstractTestNGSpringContextTests {

	@InjectMocks
	private SessionServiceImpl sessionService;

	@Spy
	private RMSUtil rmsUtil;

	@Spy
	private MOBUtil mobUtil;

	@Mock
	private IUserService userService;
	
	@Mock
	private IMerchantServices merchantService;

	@Mock
	private RoleMgmtService roleMgmtService;

	@Mock
	private MpanelConfig config;

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
	public void testLoginSuccess() throws MerchantException, ServiceException {

		MerchantLoginRequest loginRequest = getLoginRequest();

		MerchantLoginResponse expectedResponse = getLoginResponse();
		MerchantLoginResponse actualResponse=null;

		LoginUserRequest rmsRequest = new LoginUserRequest();
		rmsRequest.setPassword("password123");
		rmsRequest.setUserName("xyzabcd");
		LoginUserResponse rmsResponse = new LoginUserResponse();

		Token token = new Token();
		token.setToken("token123");
		token.setExpiry(null);
		rmsResponse.setToken(token);
		User user = new User();
		user = (User) RMSMapper.merchantAndRMSUserMapping(getUserDetails(), UserMappingDirection.MERCHANT_TO_RMS, true);
		rmsResponse.setUser(user);

		List<Role> rmsRoleList = new ArrayList<Role>();
		Role rmsRole = (Role) RMSMapper.merchantAndRMSRoleMapping(getRole(), UserMappingDirection.MERCHANT_TO_RMS, true,
				user);
		rmsRoleList.add(rmsRole);

		rmsResponse.setAllRoles(rmsRoleList);

		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.when(roleMgmtService.loginUser(rmsRequest)).thenReturn(rmsResponse);

		List<String> roles = new ArrayList<String>();
		roles.add("Account");
		Mockito.when(config.getRoles()).thenReturn(roles);

		GetUserMerchantResponse mobResponse = new GetUserMerchantResponse();
		MerchantDetails merchantDetails = getMerchantDetails();
		mobResponse.setMerchantDetails(merchantDetails);

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant((GetUserMerchantRequest) any(GetUserMerchantRequest.class)))
				.thenReturn(mobResponse);
	
		
		GetMerchantProfileStatusRequest mobStaterequest = new GetMerchantProfileStatusRequest();
		mobStaterequest.setMerchantId("asdfghjkl");
		mobStaterequest.setToken("token123");
		GetMerchantProfileStatusResponse mobProfileState = new GetMerchantProfileStatusResponse(); 
		List<OnBoardingStepDTO> stepList = new ArrayList<OnBoardingStepDTO>();
		OnBoardingStepDTO step = new OnBoardingStepDTO();
		step.setCompleted(true);
		step.setDescription("Basic Details");
		stepList.add(step);
		mobProfileState.setSteps(stepList);

		mobUtil.setMerchantService(merchantService);
		Mockito.when(merchantService.getMerchantProfileStatus(any(GetMerchantProfileStatusRequest.class))).thenReturn(mobProfileState);
		
		actualResponse = sessionService.login(loginRequest);

		Assert.assertEquals("Invalid response from Login API", expectedResponse, actualResponse);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void testLoginFailed() throws MerchantException, ServiceException {

		MerchantLoginRequest loginRequest = new MerchantLoginRequest();
		loginRequest.setPassword("password");

		LoginUserRequest rmsRequest = new LoginUserRequest();
		rmsRequest.setPassword("password");
		rmsUtil.setRoleMgmtService(roleMgmtService);
		RoleMgmtException ex = new RoleMgmtException("UserName is blank");
		Mockito.when(roleMgmtService.loginUser(rmsRequest)).thenThrow(ex);

		sessionService.login(loginRequest);

	}
	
	@Test(expectedExceptions = MerchantException.class)
	public void testLoginHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantLoginRequest loginRequest = new MerchantLoginRequest();

		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		LoginUserRequest rmsRequest = new LoginUserRequest();
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.doThrow(e).when(roleMgmtService).loginUser(rmsRequest);

		sessionService.login(loginRequest);

	}

	@Test
	public void testLogout() throws MerchantException {

		MerchantLogoutRequest logoutRequest = new MerchantLogoutRequest();
		logoutRequest.setMerchantId("asdfghjkl");
		logoutRequest.setToken("token123");

		MerchantLogoutResponse expectedResponse = new MerchantLogoutResponse();
		expectedResponse.setSuccess(true);

		LogoutUserResponse rmsResponse = new LogoutUserResponse();
		rmsResponse.setSuccess(true);

		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.when(roleMgmtService.logoutUser(any(LogoutUserRequest.class))).thenReturn(rmsResponse);

		MerchantLogoutResponse actualResponse = sessionService.logout(logoutRequest);
		Assert.assertEquals("Invalid response from Logout API", expectedResponse, actualResponse);
	}

	@Test
	public void testSetPassword() throws MerchantException {

		MerchantSetPasswordRequest request = new MerchantSetPasswordRequest();
		request.setPassword("Password@123");
		request.setUserIdentifier("vxhsvjs");

		MerchantSetPasswordResponse expectedResponse = new MerchantSetPasswordResponse();
		expectedResponse.setSuccess(true);

		VerifyCodeRequest rmsRequest = new VerifyCodeRequest();
		rmsRequest.setPassword(request.getPassword());
		rmsRequest.setVerificationCode(request.getUserIdentifier());

		rmsUtil.setRoleMgmtService(roleMgmtService);

		Mockito.doNothing().when(roleMgmtService).verifyCodeAndSetPassword(rmsRequest);

		MerchantSetPasswordResponse actualResponse = sessionService.setPassword(request);
		Assert.assertEquals("Invalid response from Logout API", expectedResponse, actualResponse);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void testSetPasswordFailed() throws MerchantException, ServiceException {

		MerchantSetPasswordRequest request = new MerchantSetPasswordRequest();
		request.setPassword("Password@123");
		/*request.setUserIdentifier("vxhsvjs");*/

		MerchantSetPasswordResponse expectedResponse = new MerchantSetPasswordResponse();
		expectedResponse.setSuccess(false);

		VerifyCodeRequest rmsRequest = new VerifyCodeRequest();
		rmsRequest.setPassword(request.getPassword());
		/*rmsRequest.setVerificationCode("dsvgefv");*/
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		RoleMgmtException ex = new RoleMgmtException("UserIdentifier is empty");

		Mockito.doThrow(ex).when(roleMgmtService).verifyCodeAndSetPassword(rmsRequest);
		
		sessionService.setPassword(request);

	}
	
	@Test(expectedExceptions = MerchantException.class)
	public void testSetPasswordHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantSetPasswordRequest request = new MerchantSetPasswordRequest();

		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		VerifyCodeRequest rmsRequest = new VerifyCodeRequest();
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.doThrow(e).when(roleMgmtService).verifyCodeAndSetPassword(rmsRequest);

		sessionService.setPassword(request);

	}


	
	private MerchantDetails getMerchantDetails() {

		MerchantDetails merchantDetail = new MerchantDetails();
		merchantDetail.setBusinessCategory("businessCategory");
		merchantDetail.setBusinessType("businessType");
		merchantDetail.setEmail("abc@snapdeal.com");
		merchantDetail.setMerchantId("asdfghjkl");
		merchantDetail.setMerchantName("merchantName");
		merchantDetail.setMerchantStatus("merchantStatus");
		merchantDetail.setMobileNumber("9876543210");
		merchantDetail.setTin("123456");
		return merchantDetail;
	}

	private MerchantLoginRequest getLoginRequest() {

		MerchantLoginRequest loginRequest = new MerchantLoginRequest();
		loginRequest.setLoginName("xyzabcd");
		loginRequest.setPassword("password123");
		return loginRequest;
	}

	private MerchantLoginResponse getLoginResponse() {

		MerchantLoginResponse loginResponse = new MerchantLoginResponse();
		loginResponse.setMerchantId("asdfghjkl");
		loginResponse.setToken("token123");
		loginResponse.setUserDTO(getUserDetails());
		
		GetMerchantStateResponse merchantStateresponse = new GetMerchantStateResponse();
		List<MerchantFlowDTO> merchantFlowDTOList =  new ArrayList<MerchantFlowDTO>();
		MerchantFlowDTO merchantFlowDTO =  new MerchantFlowDTO();
		merchantFlowDTO.setCompleted(true);
		merchantFlowDTO.setState("BASIC_INFO");
		merchantFlowDTOList.add(merchantFlowDTO);
		merchantStateresponse.setMerchantFlowDTO(merchantFlowDTOList);
		
		loginResponse.setMerchantState(merchantFlowDTOList);
		return loginResponse;
	}

	private MerchantUserDTO getUserDetails() {

		MerchantUserDTO userDetails = new MerchantUserDTO();

		userDetails.setEmailId("abc@snapdeal.com");
		userDetails.setLoginName("xyzabcd");
		userDetails.setUserId("userId123");
		userDetails.setUserName("ActualName");

		List<MerchantRoleDTO> roleList = new ArrayList<MerchantRoleDTO>();
		roleList.add(getRole());
		userDetails.setRoleList(roleList);

		return userDetails;
	}

	private MerchantRoleDTO getRole() {

		MerchantRoleDTO roleDTO = new MerchantRoleDTO();
		roleDTO.setId(1);
		roleDTO.setName("Account");

		List<MerchantPermissionDTO> permissionList = new ArrayList<MerchantPermissionDTO>();
		permissionList.add(getPermission());

		roleDTO.setPermissions(permissionList);

		return roleDTO;
	}

	private MerchantPermissionDTO getPermission() {
		MerchantPermissionDTO permissionDTO = new MerchantPermissionDTO();
		permissionDTO.setEnabled(true);
		permissionDTO.setId(1);
		permissionDTO.setName("Manage User");

		return permissionDTO;
	}

}
