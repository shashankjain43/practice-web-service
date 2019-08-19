package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.cache.VaultCache;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.ITokenCacheService;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.NewConfigurationConstant;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.ISocialUserDao;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.SocialUser;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Reason;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.migration.Migration;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.provider.RandomUpgradeChoiceUtil;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utility.IMSUtility;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;

public class IMSServiceTest {

	@InjectMocks
	@Spy
	private IMSServiceImpl imsService;

	@Mock
	private ILoginUserService loginUserService;
	
	@Mock
	IGlobalTokenDetailsDAO globalTokenDao;

	@Mock
	private	ITokenCacheService imsCacheService;

   
   @Mock
   private IUserDao userDao;
   
   @Mock
   private IUserCacheService userCacheService;
   
   @Mock
   @Qualifier("userMigrationService")
   private IUserMigrationService userMigrationService;

   @Mock
   private ISocialUserDao socialUserDao;

   @Mock
   private IActivityDataService activityDataService;

   @Mock
   private AuthorizationContext authorizationContext;

   @Mock
   private IGlobalTokenService globalTokenService;

   @Mock
   private ITokenService tokenService;

   @Mock
   private IUserVerificationDetailsDao userVerificationDetailsDao;

   @Mock
   UmsMerchantProvider merchantProvider;
   
   @Mock
   UmsServiceProvider serviceProvider;
   
   @Mock
   private ClientConfiguration clientConfiguration;
   
   @Mock
   IOTPService otpService;
   
   @Mock
   private Notifier notifier;
   
   @Mock
   SnapdealUMSServiceImpl snapdealUmsService;
   
   @Mock
   IUserIdCacheService userIdCacheService;
   
   @Mock
   UmsMerchantProvider merchantServiceProvider;
   
   @Mock
   IMSServiceImpl fcImpl;
   
   @Mock
   IPasswordUpgradeCacheService passwordCacheService;

   @Mock
   @Qualifier("dummyMigrationService")
   IUserMigrationService dummyMigrationService;
   
   @Mock
   @Qualifier("userMigrationService")
   IUserMigrationService userMigrationServiceImpl;
   
   @Mock
   private IMSUtility imsUtility;
   
   @Mock
   private WalletService walletService;
   
   @Mock
   private NotifierServiceDelegater notifierService;
   @Mock
   private RandomUpgradeChoiceUtil randomUpgradeChoiceUtil;
   
   @Mock
   private Migration migration;

	protected void initConfig() {

		Map<String, String> globalMap = new HashMap<String, String>();
		globalMap.put("default.tokengeneration.service.version", "V01");
		globalMap.put("default.tokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.TokenGenerationServiceV01Impl");
		globalMap.put("default.globaltokengeneration.service.version", "V01");
		globalMap.put("default.globaltokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV01Impl");
		globalMap.put("cipher.unique.key", "U25hcGRlYWxVbmlxdWVLZXk=");
		globalMap.put("days.to.renew.token", "5");
		globalMap.put("forget.password.email.verification.url",
				"http://dev.snapdela.com:8080");
		globalMap.put("forget.password.email.template", "Sample Template");
		final ConfigCache configCache = new ConfigCache();
		configCache.put("global", globalMap);
		// map.clear();
		Map<String, String> clientMap = new HashMap<String, String>();
		clientMap.put("global.token.expiry.time", "30");
		clientMap.put("token.expiry.time", "30");
		configCache.put("WEB", clientMap);
		CacheManager.getInstance().setCache(configCache);

		ClientCache clientConfig = new ClientCache();
		Client client = getClient();
		clientConfig.put(client.getClientId(), client);

		Map<String, String> sdClientConfigMap = new HashMap<String, String>();
		sdClientConfigMap.put("upgrade.enabled", "true");
		configCache.put("3", sdClientConfigMap);

		Map<String, String> fcClientConfigMap = new HashMap<String, String>();
		fcClientConfigMap.put("upgrade.enabled", "true");
		configCache.put("4", fcClientConfigMap);

		Client sdClient = getSDClient();
		clientConfig.put(sdClient.getClientId(), sdClient);

		Client fcClient = getFCClient();
		clientConfig.put(fcClient.getClientId(), fcClient);
		CacheManager.getInstance().setCache(clientConfig);
		
		
		VaultCache vaultCache = new VaultCache();
		vaultCache.put(NewConfigurationConstant.CIPHER_UNIQUE_KEY.getValue(),"U25hcGRlYWxVbmlxdWVLZXk=");
		vaultCache.put(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION.getValue(),"test123");
		CacheManager.getInstance().setCache(vaultCache);
	}

	private Client getClient() {
		Client cli = new Client();
		cli.setClientId("2");
		cli.setClientKey("snapdeal");
		cli.setClientName("test client");
		cli.setMerchant(Merchant.ONECHECK);
		cli.setClientType(ClientType.USER_FACING);
		cli.setClientPlatform(ClientPlatform.WEB);
		return cli;
	}
	private Client getSDClient() {
		Client cli = new Client();
		cli.setClientId("3");
		cli.setClientKey("snapdeal");
		cli.setClientName("test client");
		cli.setMerchant(Merchant.SNAPDEAL);
		cli.setClientType(ClientType.USER_FACING);
		cli.setClientPlatform(ClientPlatform.WEB);
		return cli;
	}

	private Client getFCClient() {
		Client cli = new Client();
		cli.setClientId("4");
		cli.setClientKey("freecharge");
		cli.setClientName("test client");
		cli.setMerchant(Merchant.FREECHARGE);
		cli.setClientType(ClientType.USER_FACING);
		cli.setClientPlatform(ClientPlatform.WEB);
		return cli;
	}

	@Before
	public void setup() {

		initConfig();

		MockitoAnnotations.initMocks(this);

		User mockUser = new User();

		mockUser.setStatus(UserStatus.UNVERIFIED);
		mockUser.setSdFcUserId(1300000099);
		mockUser.setEnabled(true);
		mockUser.setLanguagePref(Language.ENGLISH);

		Mockito.when(userDao.getUserById(any(String.class))).thenReturn(mockUser);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(mockUser);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(mockUser);

		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				User mockUser = invocation.getArgumentAt(0, User.class);

				mockUser.setStatus(UserStatus.UNVERIFIED);
				mockUser.setSdFcUserId(1300000099);

				mockUser.setEnabled(true);
				mockUser.setLanguagePref(Language.ENGLISH); 
				return null;
			}

		}).when(userDao).create(any(User.class));

		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				User mockUser = invocation.getArgumentAt(0, User.class);

				mockUser.setStatus(UserStatus.UNVERIFIED);
				mockUser.setSdFcUserId(1300000099);
				mockUser.setEnabled(true);
				mockUser.setLanguagePref(Language.ENGLISH);
				return null;
			}


		}).when(userDao).updateById(any(User.class));


		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("2");
		Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
				new TokenInformationDTO());
		Mockito.when(globalTokenService.getUserIdByGlobalToken(any(String.class), any(String.class)))
		.thenReturn("1");
		Mockito.when(tokenService.getUserIdByToken(eq("INVALID_TOKEN"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

	}


	@Test(expected=IMSServiceException.class)
	public void testCreateUserByEmail() {

		// 1. Request Object
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();

		userDetailsByEmailDto.setEmailId("abc@xyz.com");
		userDetailsByEmailDto.setPassword("abcdef");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);

		// 2. Response Object
		CreateUserResponse response = imsService.createUserByEmail(request);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByEmail_existingEmail() {
		// 1. Request Object
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();

		userDetailsByEmailDto.setEmailId("johnson1278@gmail.com");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);

		// 3. Mockito usecase specific beheviour
		User user = new User();
		user.setEmailId("johnson1278@gmail.com");
		user.setOriginatingSrc(Merchant.ONECHECK);
	
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(null));

		user.setStatus(UserStatus.UNVERIFIED);
		user.setLanguagePref(Language.ENGLISH);

		Mockito.doThrow(new DuplicateKeyException("")).when(userDao).create(eq(user));

		// 2. Response Object
		CreateUserResponse response = imsService.createUserByEmail(request);

		System.out.println(response);

	}

	@Test(expected = IMSServiceException.class)
	public void testCreateGuestUserByEmail() {
		// 1. Request Object
		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();

		// 2. Response Object
		CreateGuestUserResponse response = imsService.createGuestUserByEmail(request);

		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateGuestUserByEmail_existingEmail() {
		// 1. Request Object
		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();

		request.setEmailId("johnson1278@gmail.com");

		// 3. Mockito usecase specific beheviour
		User user = new User();
		user.setEmailId("johnson1278@gmail.com");
		user.setOriginatingSrc(Merchant.ONECHECK);
		user.setStatus(UserStatus.GUEST);

		Mockito.doThrow(new DuplicateKeyException("")).when(userDao).create(eq(user));

		// 2. Response Object
		CreateGuestUserResponse response = imsService.createGuestUserByEmail(request);

		System.out.println(response);
	}

	@Ignore
	@Test
	public void testCreateSocialUser() {
		// 1. Request Object
		CreateSocialUserRequest request = new CreateSocialUserRequest();

		// 2. Response Object
		SocialUserResponse response = imsService.createSocialUser(request);

		System.out.println(response);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testCreateSocialUser_existingEmail() {
		// 1. Request Object
		CreateSocialUserRequest request = new CreateSocialUserRequest();
		SocialUserRequestDto socialUserDto = new SocialUserRequestDto();
		socialUserDto.setEmailId("johnson1278@gmail.com");
		request.setSocialUserDto(socialUserDto);

		// 3. Mockito usecase specific beheviour
		User user = new User();
		user.setEmailId("johnson1278@gmail.com");
		user.setPassword(ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
		user.setOriginatingSrc(Merchant.ONECHECK);

		user.setSdFcUserId(1300000099);
		user.setStatus(UserStatus.UNVERIFIED);

		user.setLanguagePref(Language.ENGLISH);
		user.setEnabled(true);

		SocialUser socialUser = new SocialUser();
		socialUser.setUser(user);

		Mockito.doThrow(new DuplicateKeyException("")).when(socialUserDao)
		.createSocialUser(eq(socialUser));

		// 2. Response Object
		SocialUserResponse response = imsService.createSocialUser(request);

		System.out.println(response);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testCreateSocialUser_existingSocialId() {
		// 1. Request Object
		CreateSocialUserRequest request = new CreateSocialUserRequest();
		SocialUserRequestDto socialUserDto = new SocialUserRequestDto();
		socialUserDto.setSocialId("FB_SOCIAL_ID");
		request.setSocialUserDto(socialUserDto);

		// 3. Mockito usecase specific beheviour
		SocialUser socialUser = new SocialUser();

		User mockUser = new User();
		mockUser.setPassword(ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
		mockUser.setOriginatingSrc(Merchant.ONECHECK);

		mockUser.setStatus(UserStatus.UNVERIFIED);
		mockUser.setSdFcUserId(1300000099);

		mockUser.setEnabled(true);
		mockUser.setLanguagePref(Language.ENGLISH);

		socialUser.setSocialId("FB_SOCIAL_ID");
		socialUser.setUser(mockUser);

		Mockito.doThrow(new DuplicateKeyException("")).when(socialUserDao)
		.createSocialUser(eq(socialUser));

		// 2. Response Object
		SocialUserResponse response = imsService.createSocialUser(request);

		System.out.println(response);
	}

	@Ignore
	@Test
	public void testUpdateUser() {
		// 1. Request Object
		UpdateUserByIdRequest request = new UpdateUserByIdRequest();

		// 2. Response Object
		GetUserResponse response = imsService.updateUser(request);

		System.out.println(response);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testUpdateUser_userNotExist() {
		// 1. Request Object
		UpdateUserByIdRequest request = new UpdateUserByIdRequest();
		request.setUserId("INVALID_ID");

		User mockUser = new User();
		mockUser.setUserId("INVALID_ID");
		mockUser.setOriginatingSrc(Merchant.ONECHECK);

		// 3. Mockito usecase specific beheviour
		Mockito.doThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg())).when(userDao)

		.updateById(any(User.class));

		// 2. Response Object
		GetUserResponse response = imsService.updateUser(request);

		System.out.println(response);
	}

	@Ignore
	@Test
	public void testUpdateUserByToken() {
		// 1. Request Object
		UpdateUserByTokenRequest request = new UpdateUserByTokenRequest();

		// 2. Response Object
		GetUserResponse response = imsService.updateUserByToken(request);

		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testUpdateUserByToken_tokenNotExist() {
		// 1. Request Object
		UpdateUserByTokenRequest request = new UpdateUserByTokenRequest();
		request.setToken("INVALID_TOKEN");

		// 2. Response Object
		GetUserResponse response = imsService.updateUserByToken(request);

		System.out.println(response);
	}

	@Ignore
	@Test(expected = IMSServiceException.class)
	public void testUpdateUserByToken_tokenPresent_butUserNotExist() {
		// 1. Request Object
		UpdateUserByTokenRequest request = new UpdateUserByTokenRequest();
		request.setToken("VALID_TOKEN_BUT_USER_DELETED");

		// 3. Mockito usecase specific beheviour
		User user = new User();
		user.setUserId("DELETED_ID");
		user.setOriginatingSrc(Merchant.ONECHECK);

		Mockito.when(tokenService.getUserIdByToken(eq("VALID_TOKEN_BUT_USER_DELETED"))).thenReturn(
				"DELETED_ID");

		Mockito.doThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg())).when(userDao)
		.updateById(eq(user));

		// 2. Response Object
		GetUserResponse response = imsService.updateUserByToken(request);

		System.out.println(response);
	}

	@Test
	public void testGetUser() {
		// 1. Request Object
		GetUserByIdRequest request = new GetUserByIdRequest();

		// 2. Response Object
		GetUserResponse response = imsService.getUser(request);

		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetUser_userNotExist() {
		// 1. Request Object
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId("INVALID_ID");

		// 3. Mockito usecase specific beheviour
		Mockito.when(userDao.getUserById(eq("INVALID_ID"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		GetUserResponse response = imsService.getUser(request);

		System.out.println(response);
	}

	@Test
	public void testGetUserByEmail() {
		// 1. Request Object
		GetUserByEmailRequest request = new GetUserByEmailRequest();

		// 2. Response Object
		GetUserResponse response = imsService.getUserByEmail(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetUserByEmail_emailNotExist() {
		// 1. Request Object
		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId("INVALID_EMAILID");

		// 3. Mockito usecase specific beheviour
		Mockito.when(userDao.getUserByEmail(eq("INVALID_EMAILID"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		GetUserResponse response = imsService.getUserByEmail(request);

		System.out.println(response);
	}

	@Test
	public void testGetUserByToken() {
		// 1. Request Object
		GetUserByTokenRequest request = new GetUserByTokenRequest();

		// 2. Response Object
		GetUserResponse response = imsService.getUserByToken(request);

		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetUserByToken_tokenNotPresent() {
		// 1. Request Object
		GetUserByTokenRequest request = new GetUserByTokenRequest();
		request.setToken("INVALID_TOKEN");

		// 2. Response Object
		GetUserResponse response = imsService.getUserByToken(request);

		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetUserByToken_tokenPresent_butUserNotExist() {
		// 1. Request Object
		GetUserByTokenRequest request = new GetUserByTokenRequest();
		request.setToken("VALID_TOKEN_BUT_USER_DELETED");

		// 3. Mockito usecase specific beheviour
		Mockito.when(tokenService.getUserIdByToken(eq("VALID_TOKEN_BUT_USER_DELETED"))).thenReturn(
				"DELETED_ID");
		Mockito.when(userDao.getUserById(eq("DELETED_ID"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		GetUserResponse response = imsService.getUserByToken(request);

		System.out.println(response);
	}

	@Test
	public void testIsUserExist_exists() {
		// 1. Request Object
		IsUserExistRequest request = new IsUserExistRequest();

		// 2. Response Object
		IsUserExistResponse response = imsService.isUserExist(request);
		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testIsUserExist_notExist() {
		// 1. Request Object
		IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId("INVALID_ID");

		// 3. Mockito usecase specific beheviour
		Mockito.when(userDao.getUserById(eq("INVALID_ID"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		IsUserExistResponse response = imsService.isUserExist(request);
		System.out.println(response);
	}

	@Test
	public void testIsEmailExist_exists() {
		// 1. Request Object
		IsEmailExistRequest request = new IsEmailExistRequest();

		// 2. Response Object
		IsEmailExistResponse response = imsService.isEmailExist(request);
		System.out.println(response);
	}


	@Test(expected = IMSServiceException.class)
	public void testIsEmailExist_notExist() {
		// 1. Request Object
		IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId("INVALID_EMAIL_ID");

		// 3. Mockito usecase specific beheviour
		Mockito.when(userDao.getUserByEmail(eq("INVALID_EMAIL_ID"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		IsEmailExistResponse response = imsService.isEmailExist(request);
	}

	@Test
	public void testIsMobileVerified_verified() {
		// 1. Request Object
		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId("MOBILE_VERIFIED");

		// 3. Mockito usecase specific beheviour
		User mockUser = new User();
		mockUser.setMobileVerified(true);
		Mockito.when(userDao.getUserById(eq("MOBILE_VERIFIED"))).thenReturn(mockUser);

		// 2. Response Object
		MobileVerificationStatusResponse response = imsService.isMobileVerified(request);
		System.out.println(response);
	}

	@Test
	public void testIsMobileVerified_notVerified() {
		// 1. Request Object
		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId("MOBILE_UNVERIFIED");

		// 3. Mockito usecase specific beheviour
		User mockUser = new User();
		mockUser.setMobileVerified(false);
		Mockito.when(userDao.getUserById(eq("MOBILE_UNVERIFIED"))).thenReturn(mockUser);

		// 2. Response Object
		MobileVerificationStatusResponse response = imsService.isMobileVerified(request);
		System.out.println(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testIsMobileVerified_userNotExist() {
		// 1. Request Object
		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId("USER_NOT_EXIST");

		// 3. Mockito usecase specific beheviour
		Mockito.when(userDao.getUserById(eq("USER_NOT_EXIST"))).thenThrow(
				new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg()));

		// 2. Response Object
		MobileVerificationStatusResponse response = imsService.isMobileVerified(request);
		System.out.println(response);
	}

	@Test
	public void testConfigureUserStateByEmailId(){
		ConfigureUserStateRequest request = new ConfigureUserStateRequest() ;
		request.setEmailId("abhishek@gmail.com");
		request.setEnable(false);
		request.setReason(Reason.FRAUD);
		request.setDescription("fraud initiated by company");

		User user = new User() ;
		user.setEmailId(request.getEmailId());
		user.setUserId("123dw23221");
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(user) ;

		ConfigureUserStateResponse response = imsService.configureUserState(request) ;
		try{
			Assert.assertNotNull(response) ;
		}catch(Error e){
			fail(e.getMessage()) ;
		}catch(Exception e){
			fail(e.getMessage()) ;
		}

	}
	@Test
	public void testConfigureUserStateByUserId(){
		ConfigureUserStateRequest request = new ConfigureUserStateRequest() ;
		request.setUserId("saadq23132");;
		request.setEnable(false);
		request.setReason(Reason.FRAUD);
		request.setDescription("fraud initiated by company");

		User user = new User() ;
		user.setEmailId(request.getEmailId());
		user.setUserId("123dw23221");
		Mockito.when(userDao.getUserById(any(String.class))).thenReturn(user) ;

		ConfigureUserStateResponse response = imsService.configureUserState(request) ;
		try{
			Assert.assertNotNull(response) ;
		}catch(Error e){
			fail(e.getMessage()) ;
		}catch(Exception e){
			fail(e.getMessage()) ;
		}

	}
	@Test
	public void testConfigureUserStateByToken(){
		ConfigureUserStateRequest request = new ConfigureUserStateRequest() ;
		request.setToken("dwsae2q23132432312wqer233w23");
		request.setEnable(false);
		request.setReason(Reason.FRAUD);
		request.setDescription("fraud initiated by company");

		User user = new User() ;
		user.setEmailId(request.getEmailId());
		user.setUserId("123dw23221");
		Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("123dw23221") ;
		Mockito.when(userDao.getUserById(any(String.class))).thenReturn(user) ;

		ConfigureUserStateResponse response = imsService.configureUserState(request) ;
		try{
			Assert.assertNotNull(response) ;
		}catch(Error e){
			fail(e.getMessage()) ;
		}catch(Exception e){
			fail(e.getMessage()) ;
		}

	}
	@Test
	public void testConfigureUserMobileNumber(){
		ConfigureUserStateRequest request = new ConfigureUserStateRequest() ;
		request.setMobileNumber("8222918189");
		request.setEnable(false);
		request.setReason(Reason.FRAUD);
		request.setDescription("fraud initiated by company");

		User user = new User() ;
		user.setEmailId(request.getEmailId());
		user.setUserId("123dw23221");
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(user) ;

		ConfigureUserStateResponse response = imsService.configureUserState(request) ;
		try{
			Assert.assertNotNull(response) ;
		}catch(Error e){
			fail(e.getMessage()) ;
		}catch(Exception e){
			fail(e.getMessage()) ;
		}

	}
	@Test(expected = IMSServiceException.class)
	public void testConfigureUserStateExpectException(){
		ConfigureUserStateRequest request = new ConfigureUserStateRequest() ;
		request.setMobileNumber("8222918189");
		request.setEnable(false);
		request.setReason(Reason.FRAUD);
		request.setDescription("fraud initiated by company");

		User user = null ;
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(user) ;

		imsService.configureUserState(request) ;
	}
	@Test( expected = ValidationException.class)
	public void testLogin(){
		LoginUserRequest request = new LoginUserRequest() ;
		request.setMobileNumber("822918189");
		request.setPassword("password");

		User user = new User() ;
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword("password1"));

		imsService.loginUser(request) ;
	}
	private void fail(String message){
		System.out.println(message) ;
	}

	@Test(expected = IMSServiceException.class)
	public void verifyUserAndResetPassword_InvalidVerificationCode_test() {
		VerifyAndResetPasswordRequest request = getVerifyAndResetPasswordRequest();
		request.setVerificationCode("invalid");
		imsService.verifyUserAndResetPassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void verifyUserAndResetPassword_invalidPurpose_test() {
		UserVerification userVerification = getUserVerificationBean();
		userVerification.setPurpose(VerificationPurpose.VERIFY_NEW_USER);
		Mockito.when(
				userVerificationDetailsDao
				.getUserVerificationDetailsByCode(any(String.class)))
		.thenReturn(userVerification);
		VerifyAndResetPasswordRequest request = getVerifyAndResetPasswordRequest();
		imsService.verifyUserAndResetPassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void verifyUserAndResetPassword_passwordMisMatch_test() {
		UserVerification userVerification = getUserVerificationBean();
		Mockito.when(
				userVerificationDetailsDao
				.getUserVerificationDetailsByCode(any(String.class)))
		.thenReturn(userVerification);
		VerifyAndResetPasswordRequest request = getVerifyAndResetPasswordRequest();
		request.setNewPassword("wrongpass");
		imsService.verifyUserAndResetPassword(request);
	}

	@Test
	public void verifyUserAndResetPassword_test() {
		UserVerification userVerification = getUserVerificationBean();
		Mockito.when(
				userVerificationDetailsDao
				.getUserVerificationDetailsByCode(any(String.class)))
		.thenReturn(userVerification);
		VerifyAndResetPasswordRequest request = getVerifyAndResetPasswordRequest();
		VerifyUserResponse response = imsService
				.verifyUserAndResetPassword(request);
		Assert.assertEquals(response.getStatus(), StatusEnum.SUCCESS);
	}

	private UserVerification getUserVerificationBean() {
		UserVerification userVerification = new UserVerification();
		userVerification
		.setCode("M1bHGrJD60QKic1ABcjmMcMe7ncwcyyeBh7RTTZ8pyMEpzCC__oqbyGJvAgElJvL");
		Date d = new Date();
		userVerification.setCodeExpiryTime(new Timestamp(232312414132431L));
		userVerification.setCreatedTime(new Timestamp(d.getTime()));
		userVerification.setPurpose(VerificationPurpose.VERIFY_GUEST_USER);
		userVerification.setUserId("asdaksloiwqohdfkjabkfbaksbf");
		return userVerification;
	}

	private VerifyAndResetPasswordRequest getVerifyAndResetPasswordRequest() {
		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setConfirmPassword("password");
		request.setNewPassword("password");
		request.setVerificationCode("M1bHGrJD60QKic1ABcjmMcMe7ncwcyyeBh7RTTZ8pyMEpzCC__oqbyGJvAgElJvL");
		return request;
	}

	@Test
	public void testResetPasswordSuccess() throws IMSServiceException, ValidationException {

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		otpResponse.setUserId("130000001");
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);
		Mockito.when(userCacheService.getUserBySdFcId(any(Integer.class))).thenReturn(
				getTestIMSUser());
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("130000001");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password2");
		request.setConfirmPassword("password2");
		changePasswordcacheUpdate();
		ResetPasswordResponse response = imsService.resetPassword(request);
		Assert.assertTrue("Unable to reset password correctly",response.isSuccess());
	}

	public void changePasswordcacheUpdate() {
		Mockito.when(merchantServiceProvider.getMerchant()).thenReturn(Merchant.ONECHECK);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(fcImpl);
		//      Mockito.when(
		//               serviceProvider.getUMSService().putSdFcHashedPasswordByEmailId(any(String.class)))
		//               .thenReturn(any(SdFcPasswordEntity.class));
		//      Mockito.when(userIdCacheService.getEmailIdFromUserId("123",Merchant.SNAPDEAL)).thenReturn("test123@gmail.com");
	}

	@Test(expected = IMSServiceException.class)
	public void testResetPasswordWithWrongOtp() throws IMSServiceException, ValidationException{

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setStatus(OtpConstants.STATUS_FAILURE);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("11");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password2");
		request.setConfirmPassword("password2");
		ResetPasswordResponse response = imsService.resetPassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testResetPasswordWithWrongPasswordInput() throws IMSServiceException, ValidationException{
		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("11");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password1");
		request.setConfirmPassword("password2");
		ResetPasswordResponse response = imsService.resetPassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testResetPasswordForNullUser() throws IMSServiceException, ValidationException {

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);
		Mockito.when(userDao.getUserById(any(String.class))).thenReturn(null);
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("11");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password1");
		request.setConfirmPassword("password2");
		ResetPasswordResponse response = imsService.resetPassword(request);
	}

	@Test
	@Ignore
	public void testSendForgotPasswordLinkSuccess() throws Exception {

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestIMSUser());
		Mockito.when(notifier.sendEmail(any(EmailMessage.class), eq(true))).thenReturn(null);
		Mockito.when(imsUtility.getEmailTemplateKey(any(String.class), 
				any(ConfigurationConstants.class))).thenReturn("testTemplateKey");
		imsService.sendForgotPasswordLink("test@yopmail.com");
		Mockito.when(imsUtility.getEmailTemplateKey(any(String.class), any(ConfigurationConstants.class))).thenReturn("testKey");
		Mockito.verify(userDao, Mockito.times(1)).getUserByEmail(any(String.class));
		Mockito.verify(notifier, Mockito.times(1)).sendEmail(any(EmailMessage.class), eq(true));

	}

	@Test(expected = IMSServiceException.class)
	public void testSendForgotPasswordLinkForNullUser() throws Exception {

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);
		Mockito.when(notifier.sendEmail(any(EmailMessage.class), eq(true))).thenReturn(null);

		imsService.sendForgotPasswordLink("test@yopmail.com");
	}

	@Test(expected = IMSServiceException.class)
	public void testSendForgotPasswordLinkEmailSendingFailed() throws Exception {

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);
		Mockito.when(notifier.sendEmail(any(EmailMessage.class), eq(true))).thenThrow(
				new ValidationException());

		imsService.sendForgotPasswordLink("test@yopmail.com");
	}
	@Test
	public void testCreateUserByMobileSDUserSuccess() {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);

		Assert.assertEquals("Unable to create user by mobile correctly",
				UserStatus.UNVERIFIED.getValue(), response.getAccountState());
		Assert.assertEquals("Unable to create user by mobile correctly", "otp22", response.getOtpId());
	}

	@Test
	public void testCreateUserByMobileFCUserSuccess() {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("4");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.FREECHARGE);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp23");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);

		Assert.assertEquals("Unable to create user by mobile correctly",
				UserStatus.UNVERIFIED.getValue(), response.getAccountState());
		Assert.assertEquals("Unable to create user by mobile correctly", "otp23",response.getOtpId());
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileSDUserAlreadyExists() {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(true);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileFCUserAlreadyExists()  {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("4");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.FREECHARGE);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(true);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileOCMobileAlreadyExists()  {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(
				getTestRegisteredUser());
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileOCEmailAlreadyExists()  {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestRegisteredUser());

		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test
	public void testCreateUserByMobileOCTempMobileExists()  {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(getTestTempUser());
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(null);
		Mockito.doNothing().when(userDao).delete(any(String.class));
		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp22");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);

		Assert.assertEquals("Unable to create user by mobile correctly",
				UserStatus.UNVERIFIED.getValue(), response.getAccountState());
		Assert.assertEquals("Unable to create user by mobile correctly", "otp22", response.getOtpId());

	}

	@Test
	public void testCreateUserByMobileOCTempEmailExists()   {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(null);
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestTempUser());
		Mockito.doNothing().when(userDao).delete(any(String.class));
		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp26");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);

		Assert.assertEquals("Unable to create user by mobile correctly",
				UserStatus.UNVERIFIED.getValue(), response.getAccountState());
		Assert.assertEquals("Unable to create user by mobile correctly", "otp26", response.getOtpId());

	}

	@Test
	public void testCreateUserByMobileOCTempEmailMobileExists()   {

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(getTestTempUser());
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestTempUser());
		Mockito.doNothing().when(userDao).delete(any(String.class));
		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp26");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);

		Assert.assertEquals("Unable to create user by mobile correctly",
				UserStatus.UNVERIFIED.getValue(), response.getAccountState());
		Assert.assertEquals("Unable to create user by mobile correctly", "otp26", response.getOtpId());

	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileOCTempEmailRegisteredMobileExists(){

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(
				getTestRegisteredUser());
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestTempUser());
		Mockito.doNothing().when(userDao).delete(any(String.class));
		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp26");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testCreateUserByMobileOCTempMobileRegisteredEmailExists(){

		Mockito.when(authorizationContext.get(IMSRequestHeaders.CLIENT_ID.toString()))
		.thenReturn("3");
		Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
		IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();
		isEmailExistResponse.setExist(false);
		Mockito.when(snapdealUmsService.isEmailExist(any(IsEmailExistRequest.class))).thenReturn(
				isEmailExistResponse);
		Mockito.when(serviceProvider.getUMSService()).thenReturn(snapdealUmsService);
		Mockito.when(userDao.getUserByMobileNumber(any(String.class))).thenReturn(getTestTempUser());
		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(getTestRegisteredUser());
		Mockito.doNothing().when(userDao).delete(any(String.class));
		OTPServiceResponse otpResponse = new OTPServiceResponse();
		otpResponse.setOtpId("otp26");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
				otpResponse);

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userRequestDto = getTestUserDto();
		request.setUserRequestDto(userRequestDto);
		OTPResponse response = imsService.createUserByMobile(request);
	}

	@Test
	public void verifyUserWithMobileSDPresentFCAbsent(){

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1111");
		request.setOtpId("dksjdksjkdjskdj-test");

		VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		verifyOTPServiceResponse.setUserId("11111");
		verifyOTPServiceResponse.setMobileNumber("9099999999");
		verifyOTPServiceResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(userCacheService.getUserById(any(String.class))).thenReturn(getTestIMSUser());
		Mockito.when(userCacheService.putUser(any(User.class))).thenReturn(true);
		Mockito.when(userCacheService.invalidateUserByEmail(any(String.class))).thenReturn(true);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
				verifyOTPServiceResponse);

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("2");
		Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
		Mockito.doNothing().when(walletService).createSDWalletTask(any(String.class), any(String.class), any(String.class));

		Mockito.when(
				userMigrationServiceImpl
				.getIMSUserUpgradeStatus(any(UserUpgradeByEmailRequest.class))).thenReturn(
						getSDPresentFCabsentStatus());
		UpgradeUserResponse upgradeResponse = new UpgradeUserResponse();
		upgradeResponse.setSuccess(true);
		Mockito.when(userMigrationServiceImpl.upgradeUser(any(UserUpgradeRequest.class))).thenReturn(
				upgradeResponse);

		CreateUserResponse response = imsService.verifyUserWithMobile(request);

		Assert.assertEquals("VerifyUserWithMobile failed","1234", response.getUserDetails().getUserId());
	}

	@Test
	public void verifyUserWithMobileFCPresentSDAbsent(){

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1111");
		request.setOtpId("dksjdksjkdjskdj-test");

		VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		verifyOTPServiceResponse.setUserId("11111");
		verifyOTPServiceResponse.setMobileNumber("9099999999");
		verifyOTPServiceResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(userCacheService.getUserById(any(String.class))).thenReturn(getTestIMSUser());
		Mockito.when(userCacheService.putUser(any(User.class))).thenReturn(true);
		Mockito.when(userCacheService.invalidateUserByEmail(any(String.class))).thenReturn(true);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
				verifyOTPServiceResponse);

		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("2");
		Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
		Mockito.doNothing().when(walletService).createSDWalletTask(any(String.class), any(String.class), any(String.class));
		Mockito.when(
				userMigrationServiceImpl
				.getIMSUserUpgradeStatus(any(UserUpgradeByEmailRequest.class))).thenReturn(
						getFCPresentSDabsentStatus());
		UpgradeUserResponse upgradeResponse = new UpgradeUserResponse();
		upgradeResponse.setSuccess(true);
		Mockito.when(userMigrationServiceImpl.upgradeUser(any(UserUpgradeRequest.class))).thenReturn(
				upgradeResponse);

		CreateUserResponse response = imsService.verifyUserWithMobile(request);

		Assert.assertEquals("VerifyUserWithMobile failed","1234", response.getUserDetails().getUserId());
	}

	@Test
	public void verifyUserWithMobileOCPresent(){

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1111");
		request.setOtpId("dksjdksjkdjskdj-test");

		VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		verifyOTPServiceResponse.setUserId("11111");
		verifyOTPServiceResponse.setMobileNumber("9099999999");
		verifyOTPServiceResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(userCacheService.getUserById(any(String.class))).thenReturn(getTestIMSUser());
		Mockito.when(userCacheService.putUser(any(User.class))).thenReturn(true);
		Mockito.when(userCacheService.invalidateUserByEmail(any(String.class))).thenReturn(true);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
				verifyOTPServiceResponse);
		Mockito.when(authorizationContext.get(any(String.class))).thenReturn("2");
		Mockito.doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
		Mockito.doNothing().when(walletService).createSDWalletTask(any(String.class), any(String.class), any(String.class));

		Mockito.when(
				userMigrationServiceImpl
				.getIMSUserUpgradeStatus(any(UserUpgradeByEmailRequest.class))).thenReturn(
						getOCPresentStatus());
		UpgradeUserResponse upgradeResponse = new UpgradeUserResponse();
		upgradeResponse.setSuccess(true);
		Mockito.when(userMigrationServiceImpl.upgradeUser(any(UserUpgradeRequest.class))).thenReturn(
				upgradeResponse);

		CreateUserResponse response = imsService.verifyUserWithMobile(request);

		Assert.assertEquals("VerifyUserWithMobile failed","1234", response.getUserDetails().getUserId());
	}

	@Test(expected = IMSServiceException.class)
	public void verifyUserWithMobileForWrongOtp() {

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1111");
		request.setOtpId("dksjdksjkdjskdj-test");

		VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		verifyOTPServiceResponse.setUserId("11111");
		verifyOTPServiceResponse.setMobileNumber("9000000000");
		verifyOTPServiceResponse.setStatus(OtpConstants.STATUS_FAILURE);
		Mockito.when(userCacheService.getUserById(any(String.class))).thenReturn(getTestIMSUser());
		Mockito.when(userCacheService.putUser(any(User.class))).thenReturn(true);
		Mockito.when(userCacheService.invalidateUserByEmail(any(String.class))).thenReturn(true);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
				verifyOTPServiceResponse);

		CreateUserResponse response = imsService.verifyUserWithMobile(request);
	}

	@Test(expected = IMSServiceException.class)
	public void verifyUserWithMobileIfUserDeleted() {

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1111");
		request.setOtpId("dksjdksjkdjskdj-test");

		VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		verifyOTPServiceResponse.setUserId("11111");
		verifyOTPServiceResponse.setMobileNumber("9000000000");
		verifyOTPServiceResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(userCacheService.getUserById(any(String.class))).thenReturn(null);
		Mockito.when(userCacheService.putUser(any(User.class))).thenReturn(true);
		Mockito.when(userCacheService.invalidateUserByEmail(any(String.class))).thenReturn(true);
		Mockito.when(userDao.getUserById(any(String.class))).thenReturn(null);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
				verifyOTPServiceResponse);

		CreateUserResponse response = imsService.verifyUserWithMobile(request);
	}

	private UpgradeStatus getSDPresentFCabsentStatus() {

		UpgradeStatus status = new UpgradeStatus();
		status.setUserId("111");
		status.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
		status.setDontUpgrade(false);
		status.setEmail("test@yopmail.com");
		status.setCurrentState(State.SD_ENABLED_FC_DISABLED_EXISTS);
		status.setSdId("1111");
		return status;

	}

	private UpgradeStatus getFCPresentSDabsentStatus() {

		UpgradeStatus status = new UpgradeStatus();
		status.setUserId("111");
		status.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
		status.setDontUpgrade(false);
		status.setEmail("test@yopmail.com");
		status.setCurrentState(State.SD_DISABLED_FC_ENABLED_EXISTS);
		status.setSdId("1111");
		return status;

	}

	private UpgradeStatus getOCPresentStatus() {

		UpgradeStatus status = new UpgradeStatus();
		status.setUserId("111");
		status.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
		status.setDontUpgrade(false);
		status.setEmail("test@yopmail.com");
		status.setCurrentState(State.OC_ACCOUNT_EXISTS);
		status.setSdId("1111");
		return status;

	}

	private User getTestRegisteredUser() {

		User registeredUser = new User();
		registeredUser.setStatus(UserStatus.REGISTERED);
		return registeredUser;
	}

	private User getTestTempUser() {

		User tempUser = new User();
		tempUser.setStatus(UserStatus.TEMP);
		return tempUser;
	}

	private UserRequestDto getTestUserDto() {

		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setDisplayName("testDisplay");
		userRequestDto.setDob("2000-01-01");
		userRequestDto.setEmailId("test1@yopmail.com");
		userRequestDto.setFirstName("testFirst");
		userRequestDto.setGender(Gender.MALE);
		userRequestDto.setLanguagePref(Language.ENGLISH);
		userRequestDto.setLastName("TestLast");
		userRequestDto.setMobileNumber("9099999999");
		userRequestDto.setPassword("password");
		return userRequestDto;
	}

	private User getTestIMSUser() {

		User user = new User();
		user.setDisplayName("testDisplay");
		user.setDob(new Date());
		user.setEmailId("test@yopmail.com");
		user.setFirstName("Test1");
		user.setGender(Gender.MALE);
		user.setLanguagePref(Language.ENGLISH);
		user.setMobileNumber("9099999999");
		user.setPassword("password");
		user.setSdUserId(67);
		user.setSdFcUserId(789);
		user.setUserId("1234");
		user.setEnabled(true);
		user.setOriginatingSrc(Merchant.SNAPDEAL);
		return user;
	}

	@Test
	public void testResetPasswordAndLoginSuccess() {

		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("userid");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password2");
		request.setConfirmPassword("password2");

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setUserId("userid");
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);

		Mockito.when(userCacheService.getUserById(any(String.class)))
		.thenReturn(getTestIMSUser());

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(
				getTestIMSUser());

		User user = getTestIMSUser();
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request
				.getNewPassword()));
		Mockito.when(userCacheService.getUserByEmail(any(String.class)))
		.thenReturn(user);
		/*
		 * Mockito.when(userDao.getUserByMobileNumber(any(String.class)))
		 * .thenReturn(user);
		 */

		TokenInformationDTO tokenResponse = new TokenInformationDTO();
		Mockito.when(
				globalTokenService
				.createTokenOnLogin(any(LoginTokenRequest.class)))
		.thenReturn(tokenResponse);

		UserUpgradationResponse userUpgradationResponse = new UserUpgradationResponse();
		Mockito.when(
				userMigrationService
						.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class)))
				.thenReturn(userUpgradationResponse);
		changePasswordcacheUpdate();
		ResetPasswordWithLoginResponse response = imsService
				.resetPasswordAndLogin(request);
		System.out.println(response);
		Assert.assertTrue("Password has been reset successfully",
				response.isSuccess());
	}

	@Test(expected = IMSServiceException.class)
	public void testResetPasswordAndLoginSuccessFailWithDifferentNewAndConfirmPassword() {

		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("userid");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password1");
		request.setConfirmPassword("password2");

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setUserId("userid");
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);

		Mockito.when(userCacheService.getUserById(any(String.class)))
		.thenReturn(getTestIMSUser());

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(
				getTestIMSUser());

		User user = getTestIMSUser();
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request
				.getNewPassword()));
		Mockito.when(userCacheService.getUserByEmail(any(String.class)))
		.thenReturn(user);
		/*
		 * Mockito.when(userDao.getUserByMobileNumber(any(String.class)))
		 * .thenReturn(user);
		 */

		TokenInformationDTO tokenResponse = new TokenInformationDTO();
		Mockito.when(
				globalTokenService
				.createTokenOnLogin(any(LoginTokenRequest.class)))
		.thenReturn(tokenResponse);

		UserUpgradationResponse userUpgradationResponse = new UserUpgradationResponse();
		Mockito.when(
				userMigrationService
						.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class)))
				.thenReturn(userUpgradationResponse);
		changePasswordcacheUpdate();
		ResetPasswordWithLoginResponse response = imsService
				.resetPasswordAndLogin(request);
		System.out.println(response);
		Assert.assertTrue("Password has been reset successfully",
				response.isSuccess());
	}

	@Test(expected = IMSServiceException.class)
	public void testResetPasswordAndLoginSuccessFailWithWrongOTP() {

		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("userid");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password2");
		request.setConfirmPassword("password2");

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setUserId("userid");
		otpResponse.setStatus(OtpConstants.STATUS_FAILURE);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);

		Mockito.when(userCacheService.getUserById(any(String.class)))
		.thenReturn(getTestIMSUser());

		Mockito.when(userDao.getUserByEmail(any(String.class))).thenReturn(
				getTestIMSUser());

		User user = getTestIMSUser();
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request
				.getNewPassword()));
		Mockito.when(userCacheService.getUserByEmail(any(String.class)))
		.thenReturn(user);
		/*
		 * Mockito.when(userDao.getUserByMobileNumber(any(String.class)))
		 * .thenReturn(user);
		 */

		TokenInformationDTO tokenResponse = new TokenInformationDTO();
		Mockito.when(
				globalTokenService
				.createTokenOnLogin(any(LoginTokenRequest.class)))
		.thenReturn(tokenResponse);

		UserUpgradationResponse userUpgradationResponse = new UserUpgradationResponse();
		Mockito.when(
				userMigrationService
						.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class)))
				.thenReturn(userUpgradationResponse);
		changePasswordcacheUpdate();
		ResetPasswordWithLoginResponse response = imsService
				.resetPasswordAndLogin(request);
		System.out.println(response);
		Assert.assertTrue("Password has been reset successfully",
				response.isSuccess());
	}

	@Test(expected = RequestParameterException.class)
	public void testResetPasswordAndLoginSuccessFailWithNullUser() {

		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setUserId("userid");
		request.setOtpId("testOtpid1");
		request.setOtp("testotp1");
		request.setNewPassword("password2");
		request.setConfirmPassword("password2");

		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
		otpResponse.setUserId("userid");
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
		.thenReturn(otpResponse);

		Mockito.when(userCacheService.getUserById(any(String.class)))
		.thenReturn(null);

		Mockito.when(userDao.getUserByEmail(any(String.class)))
		.thenReturn(null);

		User user = getTestIMSUser();
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request
				.getNewPassword()));
		Mockito.when(userCacheService.getUserByEmail(any(String.class)))
		.thenReturn(null);
		/*
		 * Mockito.when(userDao.getUserByMobileNumber(any(String.class)))
		 * .thenReturn(user);
		 */

		TokenInformationDTO tokenResponse = new TokenInformationDTO();
		Mockito.when(
				globalTokenService
				.createTokenOnLogin(any(LoginTokenRequest.class)))
		.thenReturn(tokenResponse);

		UserUpgradationResponse userUpgradationResponse = new UserUpgradationResponse();
		Mockito.when(
				userMigrationService
						.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class)))
				.thenReturn(userUpgradationResponse);
		changePasswordcacheUpdate();
		ResetPasswordWithLoginResponse response = imsService
				.resetPasswordAndLogin(request);
		System.out.println(response);
		Assert.assertTrue("Password has been reset successfully",
				response.isSuccess());
	}

	//TODO: Have to correct the implementation
	@Ignore
	@Test
	public void testChangePasswordAndLoginSuccess() {


		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setToken("valid token");
		request.setOldPassword("password1");
		request.setNewPassword("password2");
		request.setConfirmNewPassword("password2");

		Mockito.when(tokenService.getUserIdByToken(any(String.class)))
		.thenReturn("123");
      
      VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse();
      otpResponse.setUserId("userid");
      otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);      
      Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
               .thenReturn(otpResponse);
      
      User user = new User();
      user.setUserId("userid");
      user.setEmailId("test@xyz.com");
      Mockito.when(userCacheService.getUserById(any(String.class)))
      .thenReturn(user);	
      Mockito.when(userDao.getUserByEmail(any(String.class)))
      .thenReturn(user);
      
      TokenInformationDTO tokenResponse = new TokenInformationDTO();
      Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class)))
      .thenReturn(tokenResponse);
      
      UserUpgradationResponse userUpgradationResponse = new UserUpgradationResponse();
      Mockito.when(userMigrationService.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class)))
      .thenReturn(userUpgradationResponse);
      
      ChangePasswordWithLoginResponse response = imsService.changePasswordWithLogin(request);
      System.out.println(response);
      Assert.assertTrue("Password has been reset successfully",response.isSuccess());
   }
	
	@Test
	public void testGetLinkForUpgradeCompletionSuccess() {

		Mockito.when(userCacheService.getUserByEmail("test")).thenReturn(getTestIMSUser());
		GetIMSUserVerificationUrlRequest request = new GetIMSUserVerificationUrlRequest();
		request.setEmail("test");
		request.setPurpose(VerificationPurpose.PARKING_INTO_WALLET);
		GetIMSUserVerificationUrlResponse response = imsService.getIMSUserVerificationUrl(request,null, null);
		Assert.assertNotNull("getVerification url failed", response);

	}

	@Test
	public void testGetIMSUserVerificationUrl_Parking_Success(){

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.PARKING_INTO_WALLET);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest, null, null);

		Assert.assertNotNull("getVerification url failed", imsUserVerificationUrl);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetIMSUserVerificationUrl_Parking_Exception() {

		Mockito.doThrow(
				new IMSServiceException(IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
						.errCode(), IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
						.errMsg())).when(userVerificationDetailsDao)
		.create(any(UserVerification.class));
		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.PARKING_INTO_WALLET);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest, null, null);

	}	

	@Test
	public void testGetIMSUserVerificationUrl_NewUser_UpgradeCompleted_Success(){

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest,
						Upgrade.UPGRADE_COMPLETED, "VJax34fdsu");

		Assert.assertNotNull("getVerification url failed", imsUserVerificationUrl);
	}

	@Test
	public void testGetIMSUserVerificationUrl_NewUser_LinkFC_Success(){

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest,
						Upgrade.LINK_FC_ACCOUNT, "VJax34fdsu");

		Assert.assertNotNull("getVerification url failed", imsUserVerificationUrl);
	}

	@Test
	public void testGetIMSUserVerificationUrl_NewUser_LinkSD_Success(){

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest,
						Upgrade.LINK_SD_ACCOUNT, "VJax34fdsu");

		Assert.assertNotNull("getVerification url failed", imsUserVerificationUrl);
	}

	@Test(expected = IMSServiceException.class)
	public void testGetIMSUserVerificationUrl_NewUser_NoUpgrade_Fail(){

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest,
						Upgrade.UPGRADE_RECOMMENDED, "VJax34fdsu");

	}

	@Test(expected = IMSServiceException.class)
	public void testGetIMSUserVerificationUrl_NewUser_UpgradeCompleted_Exception() {

		Mockito.doThrow(
				new IMSServiceException(IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
						.errCode(), IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
						.errMsg())).when(userVerificationDetailsDao)
		.create(any(UserVerification.class));

		GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
		getIMSUserVerificationUrlRequest.setEmail("test@mail.com");
		getIMSUserVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
		getIMSUserVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);

		GetIMSUserVerificationUrlResponse imsUserVerificationUrl = imsService
				.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest,
						Upgrade.UPGRADE_COMPLETED, "VJax34fdsu");

	}
	@Test
	@Ignore
	public void closeAccountTest() {
		CloseAccountByEmailRequest request=new CloseAccountByEmailRequest();
		request.setEmailId("test123@gmail.com");

		CloseAccountResponse expResponse=new CloseAccountResponse();
		expResponse.setStatus(true);

		User user=new User();
		user.setUserId("123");
		user.setStatus(UserStatus.UNVERIFIED);
		Mockito.when(userCacheService.getUserByEmail("test123@gmail.com")).thenReturn(user);
		Mockito.when(imsCacheService.deleteAllTokenByUserId("123")).thenReturn(true);
		CloseAccountResponse response = imsService.closeAccount(request);
		response.setStatus(true);
		Assert.assertEquals(expResponse,response);
	}
	
	@Test
	public void UpgradeUserStatusTest(){
		String emailId="test123@gmail.com";
		User user=new User();
		user.setUserId("123");
		user.setMobileVerified(true);
		user.setEmailId("test123@gmail.com");
		user.setFirstName("test");
		user.setStatus(UserStatus.UNVERIFIED);
		Mockito.when(userCacheService.getUserByEmail("test123@gmail.com")).thenReturn(user);
		user=imsService.updateUserStatus(emailId);
		Assert.assertEquals(user.getStatus(),UserStatus.REGISTERED);
		
	}
	
}
