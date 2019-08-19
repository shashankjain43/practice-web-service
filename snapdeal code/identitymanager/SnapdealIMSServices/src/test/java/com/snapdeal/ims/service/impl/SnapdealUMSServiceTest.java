/**
 * 
 */
package com.snapdeal.ims.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.securitymanager.connection.KeyManagerConnectionException;
import com.snapdeal.base.utils.MD5ChecksumUtils;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.NewConfigurationConstant;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utility.IMSUtility;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.ims.vault.Vault;
import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.ums.client.services.IUserClientService;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsRequest;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.user.UpdateUserResponse;
import com.snapdeal.ums.services.facebook.client.services.IFacebookUserClientService;

/**
 * @author shachi
 *
 */
public class SnapdealUMSServiceTest {

   @InjectMocks
   private SnapdealUMSServiceImpl sdUmsService;
   
   @Autowired
   private IOTPService otpService;
   
   @Rule 
   public ExpectedException thrown= ExpectedException.none();

	@Mock
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Mock
	ILoginUserService loginUserService;

	@Mock
	private IUserClientService umsUserClientService;

	@Mock
	private IUserMigrationService userMigrationService;
	
	@Mock
	private AuthorizationContext authorizationContext;

	@Mock
	private IGlobalTokenService globalTokenService;

	@Mock
	private ITokenService tokenService;

	@Mock
	private IMSUtility imsUtility;

	@Mock
	private Notifier notifier;

   @Mock
   private IActivityDataService activityDataService;
   
   @Mock
   private IUserService userService;

	@Mock
	private IFacebookUserClientService fbUserClientService;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(imsUtility.getAndValidateDOB(any(String.class))).thenReturn(new Date());

		CacheManager cacheManager = CacheManager.getInstance();
		final ConfigCache configCache = new ConfigCache();
		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();
		map1.put("create.user.email.verification.url", "www.snapdeal.com?vc={0}");
		map1.put("guest.email.verification.url", "www.snapdeal.com?vc={0}");
		map1.put("forget.password.email.verification.url", "www.snapdeal.com?vc={0}");
		map1.put("request.expiry.time", "600");
		map1.put("upgrade.enabled", "false");
		map2.put("upgrade.skip", "true");
		map2.put("upgrade.percentage", "100");
		configCache.put("SNAPDEAL", map1);
		configCache.put("global", map2);
		cacheManager.setCache(configCache);
		final ClientCache clientCache = new ClientCache();	
		Client client = new Client();	
		client.setClientId("1");
		client.setMerchant(Merchant.SNAPDEAL);
		client.setClientKey("@312asdb!#");
		client.setClientName("snapdeal");
		clientCache.put("1", client);
		cacheManager.setCache(clientCache);

		Mockito.when(userMigrationService.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class))).thenReturn(new UserUpgradationResponse());
		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString())).thenReturn("SNAPDEAL");
	}

	@Test
	public void testIsMobileVerified() {
      boolean thrown = false;
      try {
         MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
         sdUmsService.isMobileVerified(request);
      } catch (IMSServiceException e) {
        thrown = true;
      }
      assertTrue(thrown);
   }

	@Test
	public void testSignout() {

		//this test doesn't make sense. It's not testing anything.

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");		
		Mockito.doNothing().when(activityDataService).setActivityDataByToken(any(String.class));
		Mockito.when(tokenService.signOut(any(SignoutRequest.class))).thenReturn(new SignoutResponse());

		sdUmsService.signOut(new SignoutRequest());

	}

	@Test
	public void testgetUserByEmail() {
		GetUserByEmailResponse res = new GetUserByEmailResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());
		res.setGetUserByEmail(user);

		GetUserByEmailRequest imsReq = new GetUserByEmailRequest();
		imsReq.setEmailId("abc@abc.com");

		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		GetUserResponse response = sdUmsService.getUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testgetUserByEmail_nullUser() {

		GetUserByEmailResponse res = new GetUserByEmailResponse();

		UserSRO user = null;
		res.setGetUserByEmail(user);

		GetUserByEmailRequest imsReq = new GetUserByEmailRequest();
		imsReq.setEmailId("abc@abc.com");

		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		GetUserResponse response = sdUmsService.getUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test
	public void testGetUser() {

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		GetUserByIdRequest imsRequest = new GetUserByIdRequest();
		imsRequest.setUserId("1");

		GetUserResponse response = sdUmsService.getUser(imsRequest);
		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testGetUser_nullUser() {

		UserSRO user = null;

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		GetUserByIdRequest imsRequest = new GetUserByIdRequest();
		imsRequest.setUserId("1");

		GetUserResponse response = sdUmsService.getUser(imsRequest);
		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testGetUser_failedResponse() {

		UserSRO user = null;

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(false);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		GetUserByIdRequest imsRequest = new GetUserByIdRequest();
		imsRequest.setUserId("1");

		GetUserResponse response = sdUmsService.getUser(imsRequest);
		System.out.println(response);

	}

	@Test
	public void testUpdateUser() {

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		UpdateUserByIdRequest imsReq = new UpdateUserByIdRequest();
		UserDetailsRequestDto userdto = new UserDetailsRequestDto();
		userdto.setDob("1995-06-01");
		imsReq.setUserId("1");
		imsReq.setUserDetailsRequestDto(userdto);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		GetUserResponse response = sdUmsService.updateUser(imsReq);
		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testUpdateUser_nullUser() {

		UserSRO user = null;

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		UpdateUserByIdRequest imsReq = new UpdateUserByIdRequest();
		UserDetailsRequestDto userdto = new UserDetailsRequestDto();
		userdto.setDob("1995-06-01");
		imsReq.setUserId("1");
		imsReq.setUserDetailsRequestDto(userdto);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		GetUserResponse response = sdUmsService.updateUser(imsReq);
		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testUpdateUser_failedUmsUpdate() {

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		UpdateUserByIdRequest imsReq = new UpdateUserByIdRequest();
		UserDetailsRequestDto userdto = new UserDetailsRequestDto();
		userdto.setDob("1995-06-01");
		imsReq.setUserId("1");
		imsReq.setUserDetailsRequestDto(userdto);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(false);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		GetUserResponse response = sdUmsService.updateUser(imsReq);
		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testUpdateUser_invalidDate() {
		Mockito.when(imsUtility.getAndValidateDOB(any(String.class))).thenThrow(
				RequestParameterException.class);
		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		UpdateUserByIdRequest imsReq = new UpdateUserByIdRequest();
		UserDetailsRequestDto userdto = new UserDetailsRequestDto();
		userdto.setDob("1995-06-01");
		imsReq.setUserId("1");
		imsReq.setUserDetailsRequestDto(userdto);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(false);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		GetUserResponse response = sdUmsService.updateUser(imsReq);
		System.out.println(response);
	}

	@Test
	@Ignore
	public void testCreateUserByEmail() throws com.snapdeal.notifier.exception.ValidationException,
	InternalServerException {


		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");

		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(true);
		umsRes.setCreateUser(user);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);

		GetUserByEmailResponse getRes = new GetUserByEmailResponse();
		UserSRO userSro = new UserSRO();
		userSro.setId(1);
		userSro.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		userSro.setEmail("abc@abc.com");
		userSro.setCreated(new Date());
		
		getRes.setGetUserByEmail(userSro);

		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(getRes);
		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		Mockito.when(notifier.sendEmail(any(EmailMessage.class), any(Boolean.class))).thenReturn(
				new EmailResponse());

		// Mockito.doNothing().when(userVerificationDetailsDao.create(any(UserVerification.class)));

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test(expected = RequestParameterException.class)
	@Ignore
	public void testCreateUserByEmail_invalidEmail() {

		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abcbc.com");
		userdto.setPassword("pass");

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");

		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(true);
		umsRes.setCreateUser(user);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	@Ignore
	public void testCreateUserByEmail_nullUser() {

		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");

		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(true);
		umsRes.setCreateUser(null);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByEmail_umsCreationfail() {
		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");
		userdto.setGender(Gender.MALE);
		
		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());
		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
		CreateUserWithDetailsResponse createUserWithDetailsResponse = new CreateUserWithDetailsResponse() ;
		createUserWithDetailsResponse.setCode("213");
		createUserWithDetailsResponse.setMessage("success");
		createUserWithDetailsResponse.setSavedUser(user);
		createUserWithDetailsResponse.setSuccessful(false);
		Mockito.when(umsUserClientService.createUserWithDetails(any(CreateUserWithDetailsRequest.class)))
		.thenReturn(createUserWithDetailsResponse) ;
		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(false);
		umsRes.setCreateUser(user);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	// TO DO .. Looks Duplicate
	@Test
	@Ignore
	public void testCreateUserByEmail_umsUpdationfail() {
		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");

		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(true);
		umsRes.setCreateUser(user);

		GetUserByEmailResponse getRes = new GetUserByEmailResponse();
		UserSRO userSro = new UserSRO();
		userSro.setId(1);
		userSro.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		userSro.setEmail("abc@abc.com");
		userSro.setCreated(new Date());
		
		getRes.setGetUserByEmail(userSro);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(false);
		updateRes.setUpdateUser(user);

		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(getRes);

		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByEmail_userNull() {

		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");
		userdto.setGender(Gender.MALE);

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");

		CreateUserEmailRequest imsReq = new CreateUserEmailRequest();
		imsReq.setUserDetailsByEmailDto(userdto);

		UserSRO user = null;

		com.snapdeal.ums.ext.user.CreateUserResponse umsRes = new com.snapdeal.ums.ext.user.CreateUserResponse();
		umsRes.setSuccessful(true);
		umsRes.setCreateUser(user);
		
		  
		   CreateUserWithDetailsResponse createUserWithDetailsResponse = new CreateUserWithDetailsResponse() ;
			createUserWithDetailsResponse.setCode("213");
			createUserWithDetailsResponse.setMessage("success");
			//createUserWithDetailsResponse.setSavedUser(socialdto);
			createUserWithDetailsResponse.setSuccessful(false);
			Mockito.when(umsUserClientService.createUserWithDetails(any(CreateUserWithDetailsRequest.class)))
			.thenReturn(createUserWithDetailsResponse) ;

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(false);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		Mockito.when(
				umsUserClientService
				.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class)))
				.thenReturn(umsRes);

		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());

		CreateUserResponse response = sdUmsService.createUserByEmail(imsReq);

		System.out.println(response);

	}

	@Test
	@Ignore
	public void testCreateGuestUserByEmail() {

		CreateGuestUserResponse response = sdUmsService.createGuestUserByEmail(null);
		System.out.println(response);
	}

	@Test
	@Ignore
	public void testVerifyGuestUser() {
		VerifyUserResponse response = sdUmsService.verifyGuestUser(new VerifyUserRequest());
		System.out.println(response);
	}

	@Test
	public void testGetUserByToken() {
		GetUserByTokenRequest req = new GetUserByTokenRequest();
		req.setToken("token");

		Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		GetUserResponse response = sdUmsService.getUserByToken(req);
		System.out.println(response);
	}

	@Test
	public void testUpdateUserByToken() {
		GetUserByTokenRequest req = new GetUserByTokenRequest();
		req.setToken("token");

		Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");

		UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
		userdto.setDob("1995-06-01");
		userdto.setEmailId("abc@abc.com");
		userdto.setPassword("pass");
		

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		GetUserByIdResponse umsRes = new GetUserByIdResponse();
		umsRes.setUserById(user);
		umsRes.setSuccessful(true);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsRes);

		UpdateUserResponse updateRes = new UpdateUserResponse();
		updateRes.setSuccessful(true);
		updateRes.setUpdateUser(user);
		Mockito.when(
				umsUserClientService
				.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
				.thenReturn(updateRes);

		UpdateUserByTokenRequest upReq = new UpdateUserByTokenRequest();
		upReq.setUserDetailsRequestDto(userdto);

		GetUserResponse response = sdUmsService.updateUserByToken(upReq);
		System.out.println(response);
	}

	@Test
	public void testisEmailExist() {
		IsEmailExistRequest req = new IsEmailExistRequest();
		req.setEmailId("abc@abc.com");

		com.snapdeal.ums.ext.user.IsUserExistsResponse umsResponse = new com.snapdeal.ums.ext.user.IsUserExistsResponse();
		umsResponse.setSuccessful(true);
		Mockito.when(
				umsUserClientService
				.isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isEmailExist(req);
	}

	@Test(expected = IMSServiceException.class)
	public void testisEmailExist_false() {
		IsEmailExistRequest req = new IsEmailExistRequest();
		req.setEmailId("abc@abc.com");

		com.snapdeal.ums.ext.user.IsUserExistsResponse umsResponse = new com.snapdeal.ums.ext.user.IsUserExistsResponse();
		umsResponse.setSuccessful(false);
		Mockito.when(
				umsUserClientService
				.isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isEmailExist(req);
	}

	@Test(expected = IMSServiceException.class)
	public void testisEmailExist_nullResponse() {
		IsEmailExistRequest req = new IsEmailExistRequest();
		req.setEmailId("abc@abc.com");

		Mockito.when(
				umsUserClientService
				.isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
				.thenReturn(null);

		sdUmsService.isEmailExist(req);
	}

	@Test
	public void testisUserExist() {
		IsUserExistRequest req = new IsUserExistRequest();
		req.setUserId("1");
		GetUserByIdResponse umsResponse = new GetUserByIdResponse();
		umsResponse.setSuccessful(true);
		umsResponse.setUserById(new UserSRO());

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isUserExist(req);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testisUserExist_invalidID() {
		IsUserExistRequest req = new IsUserExistRequest();
		req.setUserId("abc");
		GetUserByIdResponse umsResponse = new GetUserByIdResponse();
		umsResponse.setSuccessful(true);
		umsResponse.setUserById(new UserSRO());

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isUserExist(req);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testisUserExist_nullResponse() {
		IsUserExistRequest req = new IsUserExistRequest();
		req.setUserId("1");
		GetUserByIdResponse umsResponse = null;

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isUserExist(req);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testisUserExist_false() {
		IsUserExistRequest req = new IsUserExistRequest();
		req.setUserId("1");
		GetUserByIdResponse umsResponse = new GetUserByIdResponse();
		umsResponse.setSuccessful(false);
		umsResponse.setUserById(new UserSRO());

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isUserExist(req);
	}

	@Test
	public void testisUserExist_userNull() {
		IsUserExistRequest req = new IsUserExistRequest();
		req.setUserId("1");
		GetUserByIdResponse umsResponse = new GetUserByIdResponse();
		umsResponse.setSuccessful(true);
		umsResponse.setUserById(null);

		Mockito.when(
				umsUserClientService
				.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
				.thenReturn(umsResponse);

		sdUmsService.isUserExist(req);
	}

	@Test
	public void testloginUser() {
		LoginUserRequest req = new LoginUserRequest();
		req.setEmailId("abc@abc.com");
		req.setPassword("pass");

		com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());
		user.setEnabled(true);

		res.setGetUserByEmail(user);
		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		sdUmsService.loginUser(req);
	}

	@Test(expected = ValidationException.class)
	public void testloginUser_passwordmismatch() {
		LoginUserRequest req = new LoginUserRequest();
		req.setEmailId("abc@abc.com");
		req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

		com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("password"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		res.setGetUserByEmail(user);
		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		sdUmsService.loginUser(req);
	}

	@Test(expected = RequestParameterException.class)
	public void testloginUser_mobile() {
		LoginUserRequest req = new LoginUserRequest();
		req.setEmailId("");
		req.setMobileNumber("8908989898");
		req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

		com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@abc.com");
		user.setCreated(new Date());

		res.setGetUserByEmail(user);
		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		sdUmsService.loginUser(req);
	}

	@Test(expected = IMSServiceException.class)
	public void testloginUser_nulluser() {
		LoginUserRequest req = new LoginUserRequest();
		req.setEmailId("abc@abc.com");
		req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

		com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		UserSRO user = null;

		res.setGetUserByEmail(user);
		Mockito.when(
				umsUserClientService
				.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
				.thenReturn(res);

		sdUmsService.loginUser(req);
	}
/*
	@Test
	public void testcreateSocialUser_userexists() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abcdegsdf@gmail.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

<<<<<<< HEAD
      System.out.println(response);

   }

   @Test
   @Ignore
   public void testCreateGuestUserByEmail() {

      CreateGuestUserResponse response = sdUmsService.createGuestUserByEmail(null);
      System.out.println(response);
   }

   @Test
   @Ignore
   public void testVerifyGuestUser() {
      VerifyUserResponse response = sdUmsService.verifyGuestUser(new VerifyUserRequest());
      System.out.println(response);
   }

   @Test
   public void testGetUserByToken() {
      GetUserByTokenRequest req = new GetUserByTokenRequest();
      req.setToken("token");

      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");

      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      user.setEmail("abc@abc.com");

      GetUserByIdResponse umsRes = new GetUserByIdResponse();
      umsRes.setUserById(user);
      umsRes.setSuccessful(true);

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsRes);

      GetUserResponse response = sdUmsService.getUserByToken(req);
      System.out.println(response);
   }

   @Test
   public void testUpdateUserByToken() {
      GetUserByTokenRequest req = new GetUserByTokenRequest();
      req.setToken("token");

      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");

      UserDetailsByEmailRequestDto userdto = new UserDetailsByEmailRequestDto();
      userdto.setDob("1995-06-01");
      userdto.setEmailId("abc@abc.com");
      userdto.setPassword("pass");

      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      user.setEmail("abc@abc.com");

      GetUserByIdResponse umsRes = new GetUserByIdResponse();
      umsRes.setUserById(user);
      umsRes.setSuccessful(true);

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsRes);

      UpdateUserResponse updateRes = new UpdateUserResponse();
      updateRes.setSuccessful(true);
      updateRes.setUpdateUser(user);
      Mockito.when(
               umsUserClientService
                        .updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class)))
               .thenReturn(updateRes);

      UpdateUserByTokenRequest upReq = new UpdateUserByTokenRequest();
      upReq.setUserDetailsRequestDto(userdto);

      GetUserResponse response = sdUmsService.updateUserByToken(upReq);
      System.out.println(response);
   }
  
   @Test
   public void testisEmailExist() {
      IsEmailExistRequest req = new IsEmailExistRequest();
      req.setEmailId("abc@abc.com");

      com.snapdeal.ums.ext.user.IsUserExistsResponse umsResponse = new com.snapdeal.ums.ext.user.IsUserExistsResponse();
      umsResponse.setSuccessful(true);
      Mockito.when(
               umsUserClientService
                        .isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isEmailExist(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testisEmailExist_false() {
      IsEmailExistRequest req = new IsEmailExistRequest();
      req.setEmailId("abc@abc.com");

      com.snapdeal.ums.ext.user.IsUserExistsResponse umsResponse = new com.snapdeal.ums.ext.user.IsUserExistsResponse();
      umsResponse.setSuccessful(false);
      Mockito.when(
               umsUserClientService
                        .isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isEmailExist(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testisEmailExist_nullResponse() {
      IsEmailExistRequest req = new IsEmailExistRequest();
      req.setEmailId("abc@abc.com");

      Mockito.when(
               umsUserClientService
                        .isUserExists(any(com.snapdeal.ums.ext.user.IsUserExistsRequest.class)))
               .thenReturn(null);

      sdUmsService.isEmailExist(req);
   }

   @Test
   public void testisUserExist() {
      IsUserExistRequest req = new IsUserExistRequest();
      req.setUserId("1");
      GetUserByIdResponse umsResponse = new GetUserByIdResponse();
      umsResponse.setSuccessful(true);
      umsResponse.setUserById(new UserSRO());

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isUserExist(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testisUserExist_invalidID() {
      IsUserExistRequest req = new IsUserExistRequest();
      req.setUserId("abc");
      GetUserByIdResponse umsResponse = new GetUserByIdResponse();
      umsResponse.setSuccessful(true);
      umsResponse.setUserById(new UserSRO());

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isUserExist(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testisUserExist_nullResponse() {
      IsUserExistRequest req = new IsUserExistRequest();
      req.setUserId("1");
      GetUserByIdResponse umsResponse = null;

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isUserExist(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testisUserExist_false() {
      IsUserExistRequest req = new IsUserExistRequest();
      req.setUserId("1");
      GetUserByIdResponse umsResponse = new GetUserByIdResponse();
      umsResponse.setSuccessful(false);
      umsResponse.setUserById(new UserSRO());

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isUserExist(req);
   }

   @Test
   public void testisUserExist_userNull() {
      IsUserExistRequest req = new IsUserExistRequest();
      req.setUserId("1");
      GetUserByIdResponse umsResponse = new GetUserByIdResponse();
      umsResponse.setSuccessful(true);
      umsResponse.setUserById(null);

      Mockito.when(
               umsUserClientService
                        .getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class)))
               .thenReturn(umsResponse);

      sdUmsService.isUserExist(req);
   }

   @Test
   public void testloginUser() {
      LoginUserRequest req = new LoginUserRequest();
      req.setEmailId("abc@abc.com");
      req.setPassword("pass");

      com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      user.setEmail("abc@abc.com");

      res.setGetUserByEmail(user);
      Mockito.when(
               umsUserClientService
                        .getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
               .thenReturn(res);

      sdUmsService.loginUser(req);
   }

   @Test(expected = ValidationException.class)
   public void testloginUser_passwordmismatch() {
      LoginUserRequest req = new LoginUserRequest();
      req.setEmailId("abc@abc.com");
      req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

      com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("password"));
      user.setEmail("abc@abc.com");

      res.setGetUserByEmail(user);
      Mockito.when(
               umsUserClientService
                        .getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
               .thenReturn(res);

      sdUmsService.loginUser(req);
   }

   @Test(expected = RequestParameterException.class)
   public void testloginUser_mobile() {
      LoginUserRequest req = new LoginUserRequest();
      req.setEmailId("");
      req.setMobileNumber("8908989898");
      req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

      com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      user.setEmail("abc@abc.com");

      res.setGetUserByEmail(user);
      Mockito.when(
               umsUserClientService
                        .getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
               .thenReturn(res);

      sdUmsService.loginUser(req);
   }

   @Test(expected = IMSServiceException.class)
   public void testloginUser_nulluser() {
      LoginUserRequest req = new LoginUserRequest();
      req.setEmailId("abc@abc.com");
      req.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));

      com.snapdeal.ums.ext.user.GetUserByEmailResponse res = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

      UserSRO user = null;

      res.setGetUserByEmail(user);
      Mockito.when(
               umsUserClientService
                        .getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class)))
               .thenReturn(res);

      sdUmsService.loginUser(req);
   }
   
   @Test
   public void testcreateSocialUser_userexists() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abcdegsdf@gmail.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   userByEmailResponse.setGetUserByEmail(user);
	   
	   com.snapdeal.ums.ext.user.UpdateUserResponse updateUMSUserResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
	   updateUMSUserResponse.setUpdateUser(user);
	   
	   Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateUMSUserResponse);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   sdUmsService.createSocialUser(req);
	   
   }*/
   
   // This validation is moved to request parameters in controller.
   @Ignore
   @Test(expected = RequestParameterException.class)
   public void testcreateSocialUser_invalidemail() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abc.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   userByEmailResponse.setGetUserByEmail(user);
	   
	   com.snapdeal.ums.ext.user.UpdateUserResponse updateUMSUserResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
	   updateUMSUserResponse.setUpdateUser(user);
	   
	   Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateUMSUserResponse);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   sdUmsService.createSocialUser(req);
	   
   }
 
   @Test(expected = IMSServiceException.class)
   public void testcreateSocialUser_user_doesnt_exist() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abcdegsdf@gmail.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   socialdto.setSocialSrc("facebook");
	   socialdto.setSocialId("1");
	   CreateUserWithDetailsResponse createUserWithDetailsResponse = new CreateUserWithDetailsResponse() ;
		createUserWithDetailsResponse.setCode("213");
		createUserWithDetailsResponse.setMessage("success");
		//createUserWithDetailsResponse.setSavedUser(socialdto);
		createUserWithDetailsResponse.setSuccessful(true);
		Mockito.when(umsUserClientService.createUserWithDetails(any(CreateUserWithDetailsRequest.class)))
		.thenReturn(createUserWithDetailsResponse) ;
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse.setGetUserByEmail(null);
	   
	   com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse2 = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse2.setGetUserByEmail(user);
	   
	   res.setCreateUser(user);
	   
	   Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse,userByEmailResponse2);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   FacebookUserResponse fbRes = new FacebookUserResponse();
	   fbRes.setAddIfNotExistsFacebookUser(true);
	   
	   Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
	   sdUmsService.createSocialUser(req);
	   
   }
   
   @Test(expected = IMSServiceException.class)
   public void testcreateSocialUser_fbUserAlreadyexists() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abcdegsdf@gmail.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   socialdto.setSocialSrc("facebook");
	   socialdto.setSocialId("1");
	   
	   CreateUserWithDetailsResponse createUserWithDetailsResponse = new CreateUserWithDetailsResponse() ;
		createUserWithDetailsResponse.setCode("213");
		createUserWithDetailsResponse.setMessage("success");
		//createUserWithDetailsResponse.setSavedUser(socialdto);
		createUserWithDetailsResponse.setSuccessful(false);
		Mockito.when(umsUserClientService.createUserWithDetails(any(CreateUserWithDetailsRequest.class)))
		.thenReturn(createUserWithDetailsResponse) ;
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse.setGetUserByEmail(null);
	   
	   com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse2 = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse2.setGetUserByEmail(user);
	   
	   res.setCreateUser(user);
	   
	   Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse,userByEmailResponse2);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   FacebookUserResponse fbRes = new FacebookUserResponse();
	   fbRes.setAddIfNotExistsFacebookUser(false);
	   
	   Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
	   sdUmsService.createSocialUser(req);
	   
   }
   
   
   @Test(expected = IMSServiceException.class)
   @Ignore
   public void testcreateSocialUser_user_doesnt_exist_creation_failed() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abc@abc.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   socialdto.setSocialSrc("facebook");
	   socialdto.setSocialId("1");
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse.setGetUserByEmail(null);
	   
	   com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();
	   
	   res.setCreateUser(null);
	   
	   Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   FacebookUserResponse fbRes = new FacebookUserResponse();
	   fbRes.setAddIfNotExistsFacebookUser(true);
	   
	   Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
	   sdUmsService.createSocialUser(req);
	   
   }
   
   @Test(expected = IMSServiceException.class)
   public void testcreateSocialUser_fbuser_already_exists() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abcdegsdf@gmail.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   socialdto.setSocialSrc("facebook");
	   socialdto.setSocialId("1");
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse.setGetUserByEmail(null);
	   
	   com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   
	   CreateUserWithDetailsResponse createUserWithDetailsResponse = new CreateUserWithDetailsResponse() ;
		createUserWithDetailsResponse.setCode("213");
		createUserWithDetailsResponse.setMessage("success");
		//createUserWithDetailsResponse.setSavedUser(socialdto);
		createUserWithDetailsResponse.setSuccessful(false);
		Mockito.when(umsUserClientService.createUserWithDetails(any(CreateUserWithDetailsRequest.class)))
		.thenReturn(createUserWithDetailsResponse) ;
	   
	   res.setCreateUser(user);
	   
	   Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   FacebookUserResponse fbRes = new FacebookUserResponse();
	   fbRes.setAddIfNotExistsFacebookUser(true);
	   
	   Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
	   sdUmsService.createSocialUser(req);
	   
   }
   
   @Test(expected = IMSServiceException.class)
   public void testcreateSocialUser_nullSocialSrc() {
	   
	   CreateSocialUserRequest req = new CreateSocialUserRequest();
	   
	   SocialUserRequestDto socialdto = new SocialUserRequestDto();
	   socialdto.setEmailId("abcdegsdf@gmail.com");
	   socialdto.setDob("1995-06-01");
	   socialdto.setGender(Gender.FEMALE);
	   socialdto.setSocialSrc(null);
	   socialdto.setSocialId("1");
	   
	   req.setSocialUserDto(socialdto);
	   
	   com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
	   
	   userByEmailResponse.setGetUserByEmail(null);
	   
	   com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   res.setCreateUser(user);
	   
	   Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);
	   
	   Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);
	   
	   Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
	   
	   FacebookUserResponse fbRes = new FacebookUserResponse();
	   fbRes.setAddIfNotExistsFacebookUser(true);
	   
	   Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
	   sdUmsService.createSocialUser(req);
	   
   }
   
   @Test
   public void test_loginUserWithToken() {
	   
	   Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
	   Mockito.when(globalTokenService.getUserIdByGlobalToken(any(String.class),any(String.class))).thenReturn("1");
	   Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
	   
	   
	   UserSRO user = new UserSRO();
	   user.setId(1);
	   user.setEnabled(true);
	   user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
	   user.setEmail("abc@gmail.com");
	   
	   GetUserByIdResponse res = new GetUserByIdResponse();
	   res.setUserById(user);
	   
	   LoginWithTokenRequest imsRequest = new LoginWithTokenRequest();
	   imsRequest.setGlobalToken("globaltoken");
	   
	   Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(res);
	   sdUmsService.loginUserWithToken(imsRequest);
   }
   
   @Test(expected = IMSServiceException.class)
   public void test_loginUserWithToken_usernull() {
	   
	   Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
	   Mockito.when(globalTokenService.getUserIdByGlobalToken(any(String.class),any(String.class))).thenReturn("1");
	   Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
	   
	   UserSRO user = null;
	   
	   GetUserByIdResponse res = new GetUserByIdResponse();
	   res.setUserById(user);
	   
	   LoginWithTokenRequest imsRequest = new LoginWithTokenRequest();
	   imsRequest.setGlobalToken("globaltoken");
	   
	   Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(res);
	   sdUmsService.loginUserWithToken(imsRequest);
   }
/*=======
		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		userByEmailResponse.setGetUserByEmail(user);

		com.snapdeal.ums.ext.user.UpdateUserResponse updateUMSUserResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
		updateUMSUserResponse.setUpdateUser(user);

		Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateUMSUserResponse);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		sdUmsService.createSocialUser(req);

	}

	// This validation is moved to request parameters in controller.
	@Test(expected = RequestParameterException.class)
	@Ignore
	public void testcreateSocialUser_invalidemail() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abc.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		userByEmailResponse.setGetUserByEmail(user);

		com.snapdeal.ums.ext.user.UpdateUserResponse updateUMSUserResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
		updateUMSUserResponse.setUpdateUser(user);

		Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateUMSUserResponse);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		sdUmsService.createSocialUser(req);

	}

	@Test
	public void testcreateSocialUser_user_doesnt_exist() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abcdegsdf@gmail.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);
		socialdto.setSocialSrc("facebook");
		socialdto.setSocialId("1");

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse.setGetUserByEmail(null);

		com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse2 = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse2.setGetUserByEmail(user);

		res.setCreateUser(user);

		Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse,userByEmailResponse2);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		FacebookUserResponse fbRes = new FacebookUserResponse();
		fbRes.setAddIfNotExistsFacebookUser(true);

		Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
		sdUmsService.createSocialUser(req);

	}

	@Test
	public void testcreateSocialUser_fbUserAlreadyexists() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abcdegsdf@gmail.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);
		socialdto.setSocialSrc("facebook");
		socialdto.setSocialId("1");

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse.setGetUserByEmail(null);

		com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse2 = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse2.setGetUserByEmail(user);

		res.setCreateUser(user);

		Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse,userByEmailResponse2);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		FacebookUserResponse fbRes = new FacebookUserResponse();
		fbRes.setAddIfNotExistsFacebookUser(false);

		Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
		sdUmsService.createSocialUser(req);

	}


	@Test(expected = IMSServiceException.class)
	public void testcreateSocialUser_user_doesnt_exist_creation_failed() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abc@abc.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);
		socialdto.setSocialSrc("facebook");
		socialdto.setSocialId("1");

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse.setGetUserByEmail(null);

		com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();

		res.setCreateUser(null);

		Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		FacebookUserResponse fbRes = new FacebookUserResponse();
		fbRes.setAddIfNotExistsFacebookUser(true);

		Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
		sdUmsService.createSocialUser(req);

	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testcreateSocialUser_fbuser_already_exists() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abcdegsdf@gmail.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);
		socialdto.setSocialSrc("facebook");
		socialdto.setSocialId("1");

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse.setGetUserByEmail(null);

		com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		res.setCreateUser(user);
		userByEmailResponse.setGetUserByEmail(user);

		Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		FacebookUserResponse fbRes = new FacebookUserResponse();
		fbRes.setAddIfNotExistsFacebookUser(true);

		Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
		sdUmsService.createSocialUser(req);

	}

	@Test(expected = IMSServiceException.class)
	public void testcreateSocialUser_nullSocialSrc() {

		CreateSocialUserRequest req = new CreateSocialUserRequest();

		SocialUserRequestDto socialdto = new SocialUserRequestDto();
		socialdto.setEmailId("abcdegsdf@gmail.com");
		socialdto.setDob("1995-06-01");
		socialdto.setGender(Gender.FEMALE);
		socialdto.setSocialSrc(null);
		socialdto.setSocialId("1");

		req.setSocialUserDto(socialdto);

		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();

		userByEmailResponse.setGetUserByEmail(null);

		com.snapdeal.ums.ext.user.CreateUserResponse res = new com.snapdeal.ums.ext.user.CreateUserResponse();

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		res.setCreateUser(user);

		Mockito.when(umsUserClientService.createUser(any(com.snapdeal.ums.ext.user.CreateUserRequest.class))).thenReturn(res);

		Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(userByEmailResponse);

		Mockito.doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));

		FacebookUserResponse fbRes = new FacebookUserResponse();
		fbRes.setAddIfNotExistsFacebookUser(true);

		Mockito.when(fbUserClientService.addIfNotExistsFacebookUser(any(FacebookUserRequest.class))).thenReturn(fbRes);
		sdUmsService.createSocialUser(req);

	}

	@Test
	public void test_loginUserWithToken() {

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
		Mockito.when(globalTokenService.getUserIdByGlobalToken(any(String.class),any(String.class))).thenReturn("1");
		Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));

		UserSRO user = new UserSRO();
		user.setId(1);
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
		user.setEmail("abc@gmail.com");
		user.setCreated(new Date());

		GetUserByIdResponse res = new GetUserByIdResponse();
		res.setUserById(user);

		LoginWithTokenRequest imsRequest = new LoginWithTokenRequest();
		imsRequest.setGlobalToken("globaltoken");

		Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(res);
		sdUmsService.loginUserWithToken(imsRequest);
	}

	@Test(expected = IMSServiceException.class)
	public void test_loginUserWithToken_usernull() {

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
		Mockito.when(globalTokenService.getUserIdByGlobalToken(any(String.class),any(String.class))).thenReturn("1");
		Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));

		UserSRO user = null;

		GetUserByIdResponse res = new GetUserByIdResponse();
		res.setUserById(user);

		LoginWithTokenRequest imsRequest = new LoginWithTokenRequest();
		imsRequest.setGlobalToken("globaltoken");

		Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(res);
		sdUmsService.loginUserWithToken(imsRequest);
	}*/
   
   @Test
   @Ignore
   public void testForgotPasswordViaEmail() {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");

      com.snapdeal.ums.ext.user.GetUserByEmailResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
      UserSRO user = new UserSRO();
      user.setId(1);
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setGetUserByEmail(user);
      
      Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(umsResponse);
      
      OTPServiceResponse otpres = new OTPServiceResponse();
      String i =  "123";
      otpres.setOtpId(i);
      Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(otpres);
      
      ForgotPasswordRequest request = new ForgotPasswordRequest();
      request.setEmailId("abc@gmail.com");
      request.setMobileNumber(null);
      
      
      ForgotPasswordResponse response = new ForgotPasswordResponse();
      response.setOtpId("123");
      response.setUserId("1");
      
      try {
         Assert.assertEquals(sdUmsService.forgotPassword(request),response);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   @Ignore
   @Test(expected = IMSServiceException.class)
   public void testForgotPassword_usernull()throws ValidationException, IMSServiceException {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");

      com.snapdeal.ums.ext.user.GetUserByEmailResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
      UserSRO user = null;
      umsResponse.setGetUserByEmail(user);
      
      Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenThrow(IMSServiceException.class);
      
      OTPServiceResponse otpres = new OTPServiceResponse();
      String i =  "123";
      otpres.setOtpId(i);
      Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(otpres);
      
      ForgotPasswordRequest request = new ForgotPasswordRequest();
      request.setEmailId("abc@gmail.com");
      request.setMobileNumber(null);
      
      
      ForgotPasswordResponse response = new ForgotPasswordResponse();
      response.setOtpId("123");com.snapdeal.ums.ext.user.GetUserByEmailRequest umsRequest = new com.snapdeal.ums.ext.user.GetUserByEmailRequest(
            request.getEmailId());
      response.setUserId("1");      
         Assert.assertEquals(sdUmsService.forgotPassword(request),response);
      
   }
   
   @Ignore
   @Test(expected=IMSServiceException.class)
   public void testForgotPassword_umsResponseNull() {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");

      com.snapdeal.ums.ext.user.GetUserByEmailResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse(null);
      
      Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenThrow(IMSServiceException.class);
      
      OTPServiceResponse otpres = new OTPServiceResponse();
      String i =  "123";
      otpres.setOtpId(i);
      Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(otpres);
      
      ForgotPasswordRequest request = new ForgotPasswordRequest();
      request.setEmailId("abc@gmail.com");
      request.setMobileNumber(null);
      
      
      ForgotPasswordResponse response = new ForgotPasswordResponse();
      response.setOtpId("123");
      response.setUserId("1");
         Assert.assertEquals(sdUmsService.forgotPassword(request),response);
         
      
   }
   
   @Test(expected = IMSServiceException.class)
   public void testForgotPasswordViaMobile() throws ValidationException, IMSServiceException {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByEmailResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByEmailResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setGetUserByEmail(user);
      
      Mockito.when(umsUserClientService.getUserByEmail(any(com.snapdeal.ums.ext.user.GetUserByEmailRequest.class))).thenReturn(umsResponse);
      Mockito.when(userService.getUserByMobile(any(GetUserByMobileRequest.class))).thenThrow(IMSServiceException.class);
      
      ForgotPasswordRequest request = new ForgotPasswordRequest();
      request.setEmailId(null);
      request.setMobileNumber("9876543210");
      
      sdUmsService.forgotPassword(request);
         
   }
   
   
   @Test(expected = ValidationException.class)
   public void testInvalidForgotPasswordRequest() throws ValidationException, IMSServiceException{
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      ForgotPasswordRequest request = new ForgotPasswordRequest();
      request.setEmailId(null);
      request.setMobileNumber(null);
      
      sdUmsService.forgotPassword(request);
      
   }
   
   @Test
   public void testChangePassword() throws ValidationException, IMSServiceException {
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("newPass");
      request.setConfirmNewPassword("newPass");
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
   // Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
   // Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
      try {
         ChangePasswordResponse response = sdUmsService.changePassword(request);
         Assert.assertEquals(response.isSuccess(),true);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   @Test(expected = IMSServiceException.class)
   public void testChangePassword_oldEqualtoNew() throws ValidationException, IMSServiceException {
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("pass");
      request.setConfirmNewPassword("pass");
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
      Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
         ChangePasswordResponse response = sdUmsService.changePassword(request);
         Assert.assertEquals(response.isSuccess(),true);
      
   }
   
   @Test(expected = IMSServiceException.class)
   public void testChangePassword_newAndConfirmPasswordMismatch() throws ValidationException, IMSServiceException {
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("newpass");
      request.setConfirmNewPassword("diffpass");
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
      Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
         ChangePasswordResponse response = sdUmsService.changePassword(request);
         Assert.assertEquals(response.isSuccess(),true);
      
   }
   
   @Test(expected = IMSServiceException.class)
   public void testChangePassword_userNull() throws ValidationException, IMSServiceException {
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("newpass");
      request.setConfirmNewPassword("newpass");
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = null;
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
      Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
         ChangePasswordResponse response = sdUmsService.changePassword(request);
         Assert.assertEquals(response.isSuccess(),true);
      
   }
   
   @Test(expected = IMSServiceException.class)
   public void testChangePasswordPasswordMismatch() throws ValidationException, IMSServiceException {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("newPass");
      request.setConfirmNewPassword("newPass");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("diffpass"));
      umsResponse.setUserById(user);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
      Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      ChangePasswordResponse response = sdUmsService.changePassword(request);
   }
   
   @Test(expected = IMSServiceException.class)
   public void testChangePassword_umsupdationfail() throws ValidationException, IMSServiceException {
      
      ChangePasswordRequest request = new ChangePasswordRequest();
      request.setOldPassword("pass");
      request.setNewPassword("newPass");
      request.setConfirmNewPassword("newPass");
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setUid("1");
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("pass"));
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(false);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("1");
      Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      Mockito.doNothing().when(activityDataService).validateToken(any(String.class));
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
         ChangePasswordResponse response = sdUmsService.changePassword(request);
         Assert.assertEquals(response.isSuccess(),true);
      
   }
   
   @Ignore
   @Test(expected = IMSServiceException.class)
   public void testResetPasswordOTPVericationFailure() throws ValidationException, IMSServiceException, KeyManagerConnectionException, IOException {
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      VerifyOTPServiceResponse verifyOtpResponse = new VerifyOTPServiceResponse();
      verifyOtpResponse.setStatus(OtpConstants.STATUS_FAILURE);
      
      Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(verifyOtpResponse);
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setId(1);
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      ResetPasswordRequest request = new ResetPasswordRequest();
      request.setUserId("571391");
      sdUmsService.resetPassword(request);
   }
   
   /*@Test
   public void testResetPassword() throws ValidationException, IMSServiceException {
      
      VerifyOTPServiceResponse verifyOtpResponse = new VerifyOTPServiceResponse(OtpConstants.STATUS_SUCCESS, "test - otp verification successful", null, null);
      Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(verifyOtpResponse);
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setId(1);
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
      ResetPasswordRequest req = new ResetPasswordRequest();
      req.setUserId("1");
      req.setNewPassword("");
      
      
      try {
         ResetPasswordResponse response = sdUmsService.resetPassword(req);
         Assert.assertEquals(response.isSuccess(),true);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test(expected = IMSServiceException.class)
   public void testResetPassword_userNull() throws ValidationException, IMSServiceException {
      
      VerifyOTPServiceResponse verifyOtpResponse = new VerifyOTPServiceResponse(OtpConstants.STATUS_SUCCESS, "test - otp verification successful",null, null);
      Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(verifyOtpResponse);
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = null;
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(true);
      
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
      ResetPasswordRequest req = new ResetPasswordRequest();
      req.setUserId("1");
      req.setNewPassword("");
      
      
         ResetPasswordResponse response = sdUmsService.resetPassword(req);
   }
   
   @Test(expected = IMSServiceException.class)
   public void testResetPassword_umsUpdateFail() throws ValidationException, IMSServiceException {
      
      VerifyOTPServiceResponse verifyOtpResponse = new VerifyOTPServiceResponse(OtpConstants.STATUS_SUCCESS, "test - otp verification successful",null, null);
      Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(verifyOtpResponse);
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("1");
      HttpHeaders header = new HttpHeaders();
      header.set("client-id", "abc");
      
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = new com.snapdeal.ums.ext.user.GetUserByIdResponse();
      UserSRO user = new UserSRO();
      user.setId(1);
      umsResponse.setUserById(user);
      
      Mockito.when(umsUserClientService.getUserById(any(com.snapdeal.ums.ext.user.GetUserByIdRequest.class))).thenReturn(umsResponse);
      
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = new com.snapdeal.ums.ext.user.UpdateUserResponse();
      updateResponse.setSuccessful(false);
      
      Mockito.when(umsUserClientService.updateUser(any(com.snapdeal.ums.ext.user.UpdateUserRequest.class))).thenReturn(updateResponse);
      
      ResetPasswordRequest req = new ResetPasswordRequest();
      req.setUserId("1");
      req.setNewPassword("");
      
         ResetPasswordResponse response = sdUmsService.resetPassword(req);
   }*/
   
  
}