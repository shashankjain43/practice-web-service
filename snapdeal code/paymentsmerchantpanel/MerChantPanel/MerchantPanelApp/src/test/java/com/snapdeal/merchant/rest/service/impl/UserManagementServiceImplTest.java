package com.snapdeal.merchant.rest.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.snapdeal.fcNotifier.client.INotifierServiceClient;
import com.snapdeal.fcNotifier.reponse.EmailResponse;
import com.snapdeal.fcNotifier.request.EmailMessage;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantContactUsRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantContactUsResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;
import com.snapdeal.merchant.rest.http.util.IMSUtil;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.test.util.MOBMapper;
import com.snapdeal.merchant.util.MOBMapperUtil;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.dto.Permission;
import com.snapdeal.mob.dto.Role;
import com.snapdeal.mob.dto.UserDetailsDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.AddUserRequest;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.request.GetUsersForMerchantRequest;
import com.snapdeal.mob.request.UpdateUserRequest;
import com.snapdeal.mob.response.AddUserResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.response.GetUsersforMerchantResponse;
import com.snapdeal.mob.response.UpdateUserResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.ChangePasswordRequest;
import com.snapdeal.payments.roleManagementModel.request.ForgotPasswordNotifyRequest;
import com.snapdeal.payments.roleManagementModel.request.GenerateOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.GetRolesByRoleNamesRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByUserNameRequest;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.ResendOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.User;
import com.snapdeal.payments.roleManagementModel.request.VerifyOTPRequest;
import com.snapdeal.payments.roleManagementModel.response.GenerateOTPResponse;
import com.snapdeal.payments.roleManagementModel.response.GetRolesByRoleNamesResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByUserNameResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:*/spring/application-context-mvc.xml")
public class UserManagementServiceImplTest extends AbstractTestNGSpringContextTests {

	@Mock
	@Value("${mpanel.roles}")
	private List<String> roles;
	
	@Mock
	private MpanelConfig config;

	@InjectMocks
	private UserManagementServiceImpl merchantUserService;

	@Spy
	private RMSUtil rmsUtil;

	@Spy
	private MOBUtil mobUtil;
	
	@Spy
	private IMSUtil imsUtil;

	@Mock
	private IUserService userService;

	@Mock
	private IMerchantServices merchantService;

	@Mock
	private RoleMgmtService roleMgmtService;
	
	@Mock
	 private INotifierServiceClient fcNotifierClient;
	
	@Spy
	private MOBMapperUtil mobMapperUtil;

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
	public void testAddUser() throws MerchantException, ServiceException {

		MerchantAddUserRequest request = getMerchantAddUserRequest();
		MerchantAddUserResponse expectedResponse = getMerchantAddUserResponse();
		MerchantAddUserResponse actualResponse;

		AddUserRequest mobRequest = new AddUserRequest();
		mobRequest.setPassword("password123");
		mobRequest.setUserDetails(getMOBUserDetails());

		AddUserResponse mobResponse = new AddUserResponse();
		UserDetailsDTO mobUserDetails = getMOBUserDetails();
		mobResponse.setUserDetails(mobUserDetails);
		
		mobUtil.setUserService(userService);
		Mockito.when(userService.addUser(any(AddUserRequest.class))).thenReturn(mobResponse);

		actualResponse = merchantUserService.addUser(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from addUser API");

	}

	@Test
	public void testEditUser() throws MerchantException, ServiceException {

		MerchantEditUserRequest request = getMerchantEditUserRequest();
		MerchantEditUserResponse expectedResponse = getMerchantEditUserResponse();
		MerchantEditUserResponse actualResponse;

		UpdateUserResponse mobResponse = new UpdateUserResponse();
		mobResponse.setSuccess(true);

		mobUtil.setUserService(userService);
		Mockito.when(userService.updateUser(any(UpdateUserRequest.class))).thenReturn(mobResponse);

		actualResponse = merchantUserService.editUser(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from editUser API");

	}

	@Test(expectedExceptions = MerchantException.class)
	public void testAddUserHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantAddUserRequest request = getMerchantAddUserRequest();

		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		mobUtil.setUserService(userService);
		Mockito.when(userService.addUser(any(AddUserRequest.class))).thenThrow(e);

		merchantUserService.addUser(request);

	}
	
	/*@Test(expectedExceptions = MerchantException.class)
	public void testAddUserFailed() throws MerchantException, ServiceException {

		MerchantAddUserRequest request = getMerchantAddUserRequest();
		

		merchantUserService.addUser(request);

	}*/

	@Test(expectedExceptions = MerchantException.class)
	public void testEditUserHTTPTransportFailTest() throws ServiceException, MerchantException {

		MerchantEditUserRequest request = getMerchantEditUserRequest();
		HttpTransportException e = new HttpTransportException("HttpTransportException", "Httpcode");

		mobUtil.setUserService(userService);
		Mockito.when(userService.updateUser(any(UpdateUserRequest.class))).thenThrow(e);

		merchantUserService.editUser(request);

	}

	@Test
	public void testGetAllUsersOfMerchant() throws ServiceException, MerchantException {

		MerchantAllUsersRequest request = new MerchantAllUsersRequest();
		request.setMerchantId("asdfghjkl");
		request.setToken("token123");

		List<String> roles = new ArrayList<String>();
		roles.add("Account");
		merchantUserService.setroles(roles);
		  
		MerchantAllUsersResponse expectedResponse = getAllUsersResponse();
		MerchantAllUsersResponse actualResponse;

		GetUsersforMerchantResponse mobResponse = getGetUsersforMerchantMOBResponse();

		mobUtil.setUserService(userService);
		Mockito.when(userService.getUsersForMerchant(any(GetUsersForMerchantRequest.class))).thenReturn(mobResponse);

		GetRolesByRoleNamesResponse rmsResponse = getRMSRolesResponse();

		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.when(roleMgmtService.getRolesByRoleNames(any(GetRolesByRoleNamesRequest.class)))
				.thenReturn(rmsResponse);

		actualResponse = merchantUserService.getAllUsersOfMerchant(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from GetAllUsersOfMerchant API");

	}

	@Test
	public void testForgotPassword() throws MerchantException, ServiceException {

		MerchantForgotPasswordRequest request = new MerchantForgotPasswordRequest();
		request.setLoginName("loginName");
		
		MerchantForgotPasswordResponse expectedResponse = new MerchantForgotPasswordResponse();
		expectedResponse.setSuccess(true);
		MerchantForgotPasswordResponse actualResponse;
		
		ForgotPasswordNotifyRequest rmsRequest = new ForgotPasswordNotifyRequest();
		rmsRequest.setUserName("loginName");
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.doNothing().when(roleMgmtService).forgotPasswordNotify(rmsRequest);

		actualResponse = merchantUserService.forgotPassword(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from forgot password API");

	}
	
	@Test
	public void testChangePassword() throws MerchantException, ServiceException {

		MerchantChangePasswordRequest request = new MerchantChangePasswordRequest();
		request.setNewPassword("newPassword");
		request.setOldPassword("oldPassword");
		
		MerchantChangePasswordResponse expectedResponse = new MerchantChangePasswordResponse();
		expectedResponse.setSuccess(true);
		
		MerchantChangePasswordResponse actualResponse;
		
		ChangePasswordRequest rmsRequest = new ChangePasswordRequest();
		rmsRequest.setNewPassword("newPassword");
		rmsRequest.setOldPassword("oldPassword");
		rmsRequest.setToken("token");
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.doNothing().when(roleMgmtService).changePassword(rmsRequest);

		actualResponse = merchantUserService.changePassword(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from change password API");

	}
	
	@Test
	public void testVerifyUser() throws MerchantException, ServiceException {

		MerchantVerifyUserRequest request = new MerchantVerifyUserRequest();
		request.setLoginName("loginName");
		
		MerchantVerifyUserResponse expectedResponse = new MerchantVerifyUserResponse();
		expectedResponse.setUserPresent(true);
		
		MerchantVerifyUserResponse actualResponse;
		
		GetUserByUserNameRequest rmsRequest = new GetUserByUserNameRequest();
		rmsRequest.setUserName("loginName");
		
		GetUserByUserNameResponse rmsResponse = new GetUserByUserNameResponse();
		User user = new User();
		user.setUserName("loginName");
		user.setId("5");
		user.setEmail("abc@gmail.com");
		user.setName("loginName");
		rmsResponse.setUser(user);
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.when(roleMgmtService.getUserByUserName(rmsRequest)).thenReturn(rmsResponse);

		actualResponse = merchantUserService.verifyUser(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from verify user API");

	}
	
	@Test
	public void testGenerateOTP() throws MerchantException,ServiceException{
		
		MerchantGenerateOTPRequest request = new MerchantGenerateOTPRequest();
		request.setLoginName("loginName");
		
		MerchantGenerateOTPResponse actualResponse;
		
		MerchantGenerateOTPResponse expectedResponse = new MerchantGenerateOTPResponse();
		expectedResponse.setOtpId("vdcjacjdned0e2");
		
		GenerateOTPRequest rmsRequest = new GenerateOTPRequest();
		rmsRequest.setUserName("loginName");
		
		GenerateOTPResponse rmsResponse = new GenerateOTPResponse();
		rmsResponse.setOtpId("vdcjacjdned0e2");
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.when(roleMgmtService.generateOTP(rmsRequest)).thenReturn(rmsResponse);

		actualResponse = merchantUserService.generateOTP(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from generate OTP API");

		
	}
	
	@Test
	public void testVerifyOTP() throws MerchantException,ServiceException{
		
		MerchantVerifyOTPRequest request = new MerchantVerifyOTPRequest();
		request.setOtp("3345");
		request.setOtpId("vdcjacjdned0e2");
		request.setPassword("password");
		
		MerchantVerifyOTPResponse actualResponse;
		
		MerchantVerifyOTPResponse expectedResponse = new MerchantVerifyOTPResponse();
		expectedResponse.setMessage("Password reset successfully");
		
		VerifyOTPRequest rmsRequest = new VerifyOTPRequest();
		rmsRequest.setNewPassword("password");
		rmsRequest.setOtp("3345");
		rmsRequest.setOtpId("vdcjacjdned0e2");
		
		rmsUtil.setRoleMgmtService(roleMgmtService);
		Mockito.doNothing().when(roleMgmtService).verifyOTP(rmsRequest);

		actualResponse = merchantUserService.verifyOTP(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from verify OTP API");

	}
	
	@Test
	public void testResendOTP() throws MerchantException,ServiceException{
		
		MerchantResendOTPRequest request = new MerchantResendOTPRequest();
		request.setOtpId("vdcjacjdned0e2");
		
		MerchantResendOTPResponse actualResponse;
		
		MerchantResendOTPResponse expectedResponse = new MerchantResendOTPResponse();
		expectedResponse.setOtpId("32teyd2idopsjj");
		
		ResendOTPRequest rmsRequest = new ResendOTPRequest();
		rmsRequest.setOtpId("vdcjacjdned0e2");
		
		GenerateOTPResponse rmsResponse = new GenerateOTPResponse();
		rmsResponse.setOtpId("32teyd2idopsjj");
		
		rmsUtil.setRoleMgmtService(roleMgmtService);

		Mockito.when(roleMgmtService.resendOTP(rmsRequest)).thenReturn(rmsResponse);

		actualResponse = merchantUserService.resendOTP(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from resend OTP API");	
	}
	
	@Test
	public void testContactUs() throws MerchantException, ServiceException, com.snapdeal.fcNotifier.exception.HttpTransportException, com.snapdeal.fcNotifier.exception.ServiceException{
		
		MerchantContactUsRequest request = new MerchantContactUsRequest();
		request.setEmailContent("this is email content");
		request.setEmailId("emailId123");
		request.setIssueType("Adding User");
		request.setMerchantName("merchantName");
		request.setLoggedUserId("userId123456");
		
		
		GetUserMerchantRequest mobRequest = new GetUserMerchantRequest();
		mobRequest.setUserId("userId123456");
		
		
		MerchantDetails merchantDetails = new MerchantDetails();
		merchantDetails.setMerchantId("merchantId123");
		merchantDetails.setMerchantName("merchantName");
		
		GetUserMerchantResponse mobResponse = new GetUserMerchantResponse();
		mobResponse.setMerchantDetails(merchantDetails);
		
		mobUtil.setUserService(userService);
		Mockito.when(userService.getUserMerchant(mobRequest)).thenReturn(mobResponse);
		
		MerchantContactUsResponse actualResponse;
		
		MerchantContactUsResponse expectedResponse = new MerchantContactUsResponse();
		expectedResponse.setMessage("The request has been successfully submitted, We will get in touch with you shortly");
		
		imsUtil.setMpanelConfig(config);
		Mockito.when(config.getFromEmail()).thenReturn("FreeCharge<noreply@freechargemail.in>");
		Mockito.when(config.getReplyToEmail()).thenReturn("noreply@freecharge.in");
		Mockito.when(config.getMerchantSupportEmailId()).thenReturn("lovey.anand@snapdeal.com");
		//Mockito.when(config.getContactusTmplKey()).thenReturn("global.mpanel.contactus.emailkey");
		
		EmailMessage emailMessage = new EmailMessage();
		
		List<String> to =  new ArrayList<String>();
		to.add("lovey.anand@snapdeal.com");
	
		Map<String, String> tags = new HashMap<String, String>();
		
		tags.put("Merchant_name", "merchantName");
		tags.put("Mid", "merchantId123");
		tags.put("Issue_Subject", "Adding User");
		tags.put("Description","this is email content");
		tags.put("email_id", "lovey.anand@snapdeal.com");
		
		emailMessage.setTo(to);
		emailMessage.setSubject("Adding User");
		emailMessage.setTemplateKey("global.mpanel.contactus.emailkey");
		emailMessage.setRequestId("12473r9fccssnxcko");
		emailMessage.setTags(tags);
		emailMessage.setFrom("FreeCharge<noreply@freechargemail.in>");
		emailMessage.setReplyTo("noreply@freecharge.in");
		emailMessage.setTaskId("bdwhsjdw93767e00q");
		
		imsUtil.setFCNotifierService(fcNotifierClient);
		EmailNotifierRequest message = new EmailNotifierRequest();
		message.setEmailMessage(emailMessage);
		
		EmailResponse fcResponse = new EmailResponse();
		fcResponse.setRequestId("dbwsh788");
		
		Mockito.when(fcNotifierClient.sendEmail(message)).thenReturn(fcResponse);
		
		actualResponse = null;//merchantUserService.contactUs(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from contact Us API");	
	}
	
	
	private GetRolesByRoleNamesResponse getRMSRolesResponse() {

		GetRolesByRoleNamesResponse rmsRoles = new GetRolesByRoleNamesResponse();
		
		List<com.snapdeal.payments.roleManagementModel.request.Role> roleList = new ArrayList<com.snapdeal.payments.roleManagementModel.request.Role>();
		
		com.snapdeal.payments.roleManagementModel.request.Role role = new com.snapdeal.payments.roleManagementModel.request.Role();
		role.setId(1);
		role.setName("Account");

		List<com.snapdeal.payments.roleManagementModel.request.Permission> permissionList = new ArrayList<com.snapdeal.payments.roleManagementModel.request.Permission>();
		com.snapdeal.payments.roleManagementModel.request.Permission permission = new com.snapdeal.payments.roleManagementModel.request.Permission();
		permission.setId(1);
		permission.setName("Manage User");
		permissionList.add(permission);
		
		role.setPermissions(permissionList);
		roleList.add(role);
		
		rmsRoles.setRoles(roleList);
		
		return rmsRoles;
	}

	private GetUsersforMerchantResponse getGetUsersforMerchantMOBResponse() {

		GetUsersforMerchantResponse response = new GetUsersforMerchantResponse();
		List<UserDetailsDTO> userDetails = new ArrayList<UserDetailsDTO>();
		UserDetailsDTO mobUser = getMOBUserDetails();

		userDetails.add(mobUser);
		response.setUserDetails(userDetails);
		return response;
	}

	private UserDetailsDTO getMOBUserDetails() {

		UserDetailsDTO mobUser = new UserDetailsDTO();
		mobUser.setEmailId("abc@snapdeal.com");
		mobUser.setLoginName("xyzabcd");
		mobUser.setName("xyzabcd");
		mobUser.setUserId("userId123");
		List<Permission> permissionList = new ArrayList<Permission>();
		Permission mobPermission = new Permission();
		mobPermission.setId(1);
		mobPermission.setName("Manage User");
		permissionList.add(mobPermission);
		mobUser.setPermissionList(permissionList);

		List<Role> roleList = new ArrayList<Role>();
		MerchantRoleDTO merchantRole = getRole();
		Role mobRole = (Role) MOBMapper.merchantAndMobRoleMapping(merchantRole, UserMappingDirection.MERCHANT_TO_MOB);
		roleList.add(mobRole);
		mobUser.setRoleList(roleList);
		return mobUser;

	}

	private MerchantAllUsersResponse getAllUsersResponse() {

		MerchantAllUsersResponse response = new MerchantAllUsersResponse();
		List<MerchantUserDTO> users = new ArrayList<MerchantUserDTO>();

		MerchantUserDTO userDetails = new MerchantUserDTO();
		userDetails.setEmailId("abc@snapdeal.com");
		userDetails.setLoginName("xyzabcd");
		userDetails.setUserName("xyzabcd");
		userDetails.setUserId("userId123");

		List<MerchantRoleDTO> roleList = new ArrayList<MerchantRoleDTO>();
		MerchantRoleDTO merchantRole = getRole();
		roleList.add(merchantRole);
		userDetails.setRoleList(roleList);

		users.add(userDetails);

		response.setUsers(users);

		return response;
	}

	private MerchantEditUserRequest getMerchantEditUserRequest() {

		MerchantEditUserRequest request = new MerchantEditUserRequest();

		request.setEmail("abc@snapdeal.com");
		request.setMerchantId("asdfghjkl");
		request.setToken("token123");
		request.setUserName("xyzabcd");
		request.setUserId("userId123");
		List<MerchantRoleDTO> roleList = new ArrayList<MerchantRoleDTO>();
		MerchantRoleDTO merchantRole = getRole();
		roleList.add(merchantRole);

		request.setRoleList(roleList);
		return request;
	}

	private MerchantEditUserResponse getMerchantEditUserResponse() {

		MerchantEditUserResponse response = new MerchantEditUserResponse();
		response.setSuccess(true);
		return response;
	}

	private MerchantAddUserResponse getMerchantAddUserResponse() {

		MerchantAddUserResponse response = new MerchantAddUserResponse();
		response.setSuccess(true);
		return response;
	}

	private MerchantAddUserRequest getMerchantAddUserRequest() {

		MerchantAddUserRequest request = new MerchantAddUserRequest();

		request.setEmail("abc@snapdeal.com");
		request.setLoginName("xyzabcd");
		request.setMerchantId("asdfghjkl");
		request.setPassword("password123");
		request.setToken("token123");
		request.setUserName("xyzabcd");

		List<MerchantRoleDTO> roleList = new ArrayList<MerchantRoleDTO>();
		MerchantRoleDTO merchantRole = getRole();
		roleList.add(merchantRole);

		request.setRoleList(roleList);
		return request;
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
