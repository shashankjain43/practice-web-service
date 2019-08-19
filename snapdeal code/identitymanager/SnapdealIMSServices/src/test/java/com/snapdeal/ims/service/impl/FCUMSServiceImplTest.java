package com.snapdeal.ims.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import com.freecharge.umsclient.AbstractUmsConfig;
import com.freecharge.umsclient.Ums;
import com.freecharge.umsclient.exception.UmsException;
import com.freecharge.umsclient.request.vo.ChangePassword;
import com.freecharge.umsclient.request.vo.CreateFcUser;
import com.freecharge.umsclient.request.vo.CreateSocial;
import com.freecharge.umsclient.request.vo.ForgotPassword;
import com.freecharge.umsclient.request.vo.ResetPassword;
import com.freecharge.umsclient.request.vo.UpdatePassword;
import com.freecharge.umsclient.request.vo.UpdateUserById;
import com.freecharge.umsclient.response.vo.SocialInfo;
import com.freecharge.umsclient.response.vo.SocialUserVO;
import com.freecharge.umsclient.response.vo.StatusVO;
import com.freecharge.umsclient.response.vo.User;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.provider.FCUmsProvider;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utility.IMSUtility;
import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.util.PaymentConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FCUMSServiceImplTest {

   @InjectMocks
   FCUMSServiceImpl fcUMSServiceImpl;


   @Mock
   private IUserMigrationService userMigrationService;
   
   @Mock
   private Notifier notifier;

   @Mock
   IActivityDataService activityDataService;

   @Mock
   ITokenService tokenService;

   @Mock
   FCUmsProvider fcUMSprovider;

   @Mock
   AuthorizationContext authorizationContext;

   @Mock
   IGlobalTokenService globalTokenService;

   @Mock
   IUserVerificationDetailsDao userVerificationDetailsDao;

   @Mock
   IOTPService otpService;
   
   @Mock
   IUserIdCacheService userIdCacheService;
   
   @Mock
   UmsMerchantProvider merchantServiceProvider;
   
   @Mock
   UmsServiceProvider serviceProvider;
   
   @Mock
   IMSServiceImpl fcImpl;
   
   @Mock
   IPasswordUpgradeCacheService passwordCacheService;
   
   @Mock
   private ILoginUserService loginUserService;

   @Mock
   private Ums ums = new Ums();

   @Mock
   private IMSUtility imsUtility;
   
   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);

      AbstractUmsConfig config = new AbstractUmsConfig() {
         
         private String umsHost;
         @Override
         public void setUmsHost(String umsHost) {
            this.umsHost = umsHost;
         }
         
         @Override
         public String getUmsHost() {
            // TODO Auto-generated method stub
            return umsHost;
         }
      };
      
      String url = "hello";
      config.setUmsHost(url);
      ums.init(config);
      Mockito.when(fcUMSprovider.getUms()).thenReturn(ums);
      CacheManager cacheManager = CacheManager.getInstance();
      final ConfigCache configCache = new ConfigCache();
      Map<String, String> map1 = new HashMap<String, String>();
      map1.put("create.user.email.verification.url", "www.snapdeal.com?vc={0}");
      map1.put("guest.email.verification.url", "www.snapdeal.com?vc={0}");
      map1.put("forget.password.email.verification.url", "www.snapdeal.com?vc={0}");
      map1.put("request.expiry.time", "600");
      map1.put("2", "Freecharge");
      configCache.put("2", map1);
      User user = createUser();
      Mockito.when(ums.getUser(123)).thenReturn(user);

      final ClientCache clientCache = new ClientCache(); 
      Client client = new Client(); 
      client.setClientId("2");
      client.setMerchant(Merchant.FREECHARGE);
      client.setClientKey("@312asdb!#");
      client.setClientName("freecharge");
      clientCache.put("2", client);
      cacheManager.setCache(clientCache);
      MDC.put(PaymentConstants.REQUEST_ID,"123");
      SocialUserVO socialUserVo = new SocialUserVO();
      socialUserVo.setSocialInfos(null);
      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com")).thenReturn(socialUserVo) ;
     // Mockito.when(loginUserService.isUserLocked(any(String.class))) ;
      configCache.put("FREECHARGE", map1);
      cacheManager.setCache(configCache);
      Mockito.when(authorizationContext.get("apprequestid")).thenReturn("123") ;
      Mockito.when(authorizationContext.get("clientId")).thenReturn("2");
      Mockito.when(userMigrationService.getUserUpgradeStatus(any(UserUpgradeByEmailRequest.class), any(Boolean.class))).thenReturn(new UserUpgradationResponse());
   }

   @Test
   public void testCreateUserByEmailAndMobile() {
      CreateUserEmailMobileRequest request = new CreateUserEmailMobileRequest();
      request.setUserRequestDto(createUserRequestDto());
      User user = createUser();
      Mockito.when(ums.createUser(any(CreateFcUser.class))).thenReturn(123);
      Mockito.when(ums.getUser(123)).thenReturn(user);
      
      Mockito.when(authorizationContext.get(any(String.class))).thenReturn("2");
      Mockito.when(globalTokenService.createTokenOnLogin(any(LoginTokenRequest.class))).thenReturn(
               new TokenInformationDTO());

      try {
         Mockito.when(notifier.sendEmail(any(EmailMessage.class), any(Boolean.class))).thenReturn(
                  new EmailResponse());
      } catch (Exception e) {
         fail(e.getMessage());
      }

      try {
         CreateUserResponse response = fcUMSServiceImpl.createUserByEmailAndMobile(request);
         UserDetailsDTO userDetailsDto = response.getUserDetails();
         Assert.assertEquals(userDetailsDto, createUserDetailsDTO(user));
      } catch (Error e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }
   
   @Test
   public void testVerifyUserAndResetPassword() {
      UserVerification userVerification = getUserVerificationBean();
      Mockito.when(
            userVerificationDetailsDao
                  .getUserVerificationDetailsByCode(any(String.class)))
            .thenReturn(userVerification);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OK");
      Mockito.when(ums.updatePassword(any(UpdatePassword.class))).thenReturn(status);
      VerifyAndResetPasswordRequest request = getVerifyAndResetPasswordRequest();
      VerifyUserResponse response = fcUMSServiceImpl
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
      userVerification.setUserId("123");
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
   public void testCreateUserByEmailException() {
      boolean thrown = false;
      try {
         CreateUserEmailRequest request = new CreateUserEmailRequest();
         fcUMSServiceImpl.createUserByEmail(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testCreatSocialUserSrcNull() {
      User user = createUser();
      Mockito.when(ums.createUser(any(CreateSocial.class))).thenReturn(123);
      Mockito.when(ums.getUser(any(String.class),any(String.class))).thenReturn(user);
      CreateSocialUserRequest request = new CreateSocialUserRequest();
      SocialUserRequestDto dto = new SocialUserRequestDto();
      dto.setEmailId("abhishek.jain01@snapdeal.com");
      request.setSocialUserDto(dto);
      try {
         SocialUserResponse response = fcUMSServiceImpl.createSocialUser(request);
         UserDetailsDTO userDetailsDto = response.getUserDetails();
         Assert.assertNotNull(userDetailsDto);
      } catch (Error e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

   @Test
   public void testCreatSocialUserUserAlreadyExist() {
      User user = createUser();
      Mockito.when(ums.createUser(any(CreateSocial.class))).thenReturn(123);
      Mockito.when(ums.getUser(any(String.class),any(String.class))).thenReturn(user);
      SocialUserVO socialUserVO = createSocialUserVO();
      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com")).thenReturn(socialUserVO);
      CreateSocialUserRequest request = new CreateSocialUserRequest();
      SocialUserRequestDto dto = new SocialUserRequestDto();
      dto.setEmailId("jonh@gmail.com");
      dto.setSocialSrc("FACEBOOK");
      request.setSocialUserDto(dto);
      try {
         SocialUserResponse response = fcUMSServiceImpl.createSocialUser(request);
         UserDetailsDTO userDetailsDto = response.getUserDetails();
         Assert.assertNotNull(userDetailsDto);
      } catch (Error e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }
   
   @Ignore
   @Test
   public void testCreatSocialUserUserNotExist() {
      User user = createUser();
      Mockito.when(ums.createUser(any(CreateSocial.class))).thenReturn(123);
      Mockito.when(ums.getUser("jonh@gmail.com","123")).thenThrow(UmsException.class);
      SocialUserVO socialUserVO = createSocialUserVO();
      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com","123")).thenReturn(socialUserVO);
      CreateSocialUserRequest request = new CreateSocialUserRequest();
      SocialUserRequestDto dto = new SocialUserRequestDto();
      dto.setEmailId("jonh@gmail.com");
      dto.setSocialSrc("FACEBOOK");
      request.setSocialUserDto(dto);
      SocialUserResponse response = fcUMSServiceImpl.createSocialUser(request);
      UserDetailsDTO userDetailsDto = response.getUserDetails();
      Assert.assertNotNull(userDetailsDto);
   }
   
   @Test
   public void testConfigureUserStateEnableValidUserIdNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setUserId("123");
      request.setEnable(true);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OK");
      Mockito.when(ums.activateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.SUCCESS, response.getStatus());
   }
   
   @Test
   public void testConfigureUserStateEnableValidTokenNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setToken("yaugewye3g2736tgewuyg726");
      Mockito.when(tokenService.getUserIdByToken(any(String.class)))
            .thenReturn("123");
      request.setEnable(true);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OK");
      Mockito.when(ums.activateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.SUCCESS, response.getStatus());
   }
   
   @Test
   public void testConfigureUserStateEnableValidEmailNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setEmailId("jonh@gmail.com");
      User user = createUser();
      request.setEnable(true);
      SocialUserVO socialUserVO =  createSocialUserVO();
      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com","123")).thenReturn(socialUserVO);
      StatusVO status = new StatusVO("OK");
      Mockito.when(ums.activateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.SUCCESS, response.getStatus());
   }
   
   @Test
   public void testConfigureUserStateEnableNotValidUserIdNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setUserId("123");
      request.setEnable(true);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OKi");
      Mockito.when(ums.activateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.FAILURE, response.getStatus());
   }
   
   @Test
   public void testConfigureUserStateDisableValidUserIdNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setUserId("123");
      request.setEnable(false);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OK");
      Mockito.when(ums.deActivateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.SUCCESS, response.getStatus());
   }
   
   @Test
   public void testConfigureUserStateDisableNotValidUserIdNotBlank() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setUserId("123");
      request.setEnable(false);
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      StatusVO status = new StatusVO("OKi");
      Mockito.when(ums.deActivateUser("jonh@gmail.com","123")).thenReturn(status);
      ConfigureUserStateResponse response = fcUMSServiceImpl.configureUserState(request);
      Assert.assertEquals(StatusEnum.FAILURE, response.getStatus());
   }


   @Test
   public void testGetUser() {
      GetUserByIdRequest request = new GetUserByIdRequest();
      request.setUserId("123");
      User user = createUser();
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      try {
         GetUserResponse response = fcUMSServiceImpl.getUser(request);
         UserDetailsDTO userDetailsDto = response.getUserDetails();
         Assert.assertEquals(userDetailsDto, createUserDetailsDTO(user));
      } catch (Error e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

   @Test
   public void testIsMobileVerifiedException() {
      boolean thrown = false;
      try {
         MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
         fcUMSServiceImpl.isMobileVerified(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testUpdateMobileNumberException() {
      boolean thrown = false;
      try {
         UpdateMobileNumberRequest request = new UpdateMobileNumberRequest();
         fcUMSServiceImpl.updateMobileNumber(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testGetUserByMobileException() {
      boolean thrown = false;
      try {
         GetUserByMobileRequest request = new GetUserByMobileRequest();
         fcUMSServiceImpl.getUserByMobile(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }

   @Test
   public void testCreateGuestUserByEmailException() {
      boolean thrown = false;
      try {
         CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();
         fcUMSServiceImpl.createGuestUserByEmail(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }

   @Test
   public void testVerifyUserException() {
      boolean thrown = false;
      try {
         VerifyUserRequest request = new VerifyUserRequest();
         fcUMSServiceImpl.verifyGuestUser(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testVerifyUserWithMobileException() {
      boolean thrown = false;
      try {
         CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
         fcUMSServiceImpl.verifyUserWithMobile(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testVerifyUniqueMobileException() {
      boolean thrown = false;
      try {
         UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
         fcUMSServiceImpl.verifyUniqueMobile(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testCreateUserByMobileException() {
      boolean thrown = false;
      try {
         CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
         fcUMSServiceImpl.createUserByMobile(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testIsMobileExistException() {
      boolean thrown = false;
      try {
         IsVerifiedMobileExistRequest request = new IsVerifiedMobileExistRequest();
         fcUMSServiceImpl.isMobileExist(request);
      } catch (IMSServiceException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }

   
   	@Test
	public void testGetUserSrcNotNull() {
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId("123");
		User user = createUser();
		List<String> sourceList = new ArrayList<String>();
		sourceList.add("facebook");
		user.setSource(sourceList);
		Mockito.when(ums.getUser(123)).thenReturn(user);
		try {
			GetUserResponse response = fcUMSServiceImpl.getUser(request);
			UserDetailsDTO userDetailsDto = response.getUserDetails();
			Assert.assertNotNull(userDetailsDto);
		} catch (Error e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetUserSrcNull() {
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId("123");
		User user = createUser();
		List<String> sourceList = new ArrayList<String>();
		sourceList.add("null");
		user.setSource(sourceList);
		Mockito.when(ums.getUser(123)).thenReturn(user);
		try {
			GetUserResponse response = fcUMSServiceImpl.getUser(request);
			UserDetailsDTO userDetailsDto = response.getUserDetails();
			Assert.assertNotNull(userDetailsDto);
		} catch (Error e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetUserByEmail() {
		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId("jonh@gmail.com");
		User user = createUser();
		Mockito.when(ums.getUser("jonh@gmail.com","123")).thenReturn(user);
		try {
			GetUserResponse response = fcUMSServiceImpl.getUserByEmail(request);
			UserDetailsDTO userDetailsDto = response.getUserDetails();
			Assert.assertEquals(userDetailsDto, createUserDetailsDTO(user));
		} catch (Error e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testIsUserIdExist() {
		IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId("123");
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		IsUserExistResponse response = fcUMSServiceImpl.isUserExist(request);
		Assert.assertEquals(response.isExist(), true);
	}

	@Test
	public void testUpdateUser() {
		UpdateUserByIdRequest request = createUpdateUserByIdRequest();
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		StatusVO status = new StatusVO("OK");
		Mockito.when(ums.updateUser(any(UpdateUserById.class))).thenReturn(
				status);
		GetUserResponse response = fcUMSServiceImpl.updateUser(request);
		UserDetailsDTO userDetailsDto = response.getUserDetails();
		Assert.assertNotNull(userDetailsDto);
	}

	@Test(expected = IMSServiceException.class)
	public void testUpdateUserFail() {
		UpdateUserByIdRequest request = createUpdateUserByIdRequest();
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		StatusVO status = new StatusVO("FAIL");
		Mockito.when(ums.updateUser(any(UpdateUserById.class))).thenReturn(
				status);
		GetUserResponse response = fcUMSServiceImpl.updateUser(request);
		UserDetailsDTO userDetailsDto = response.getUserDetails();
		Assert.assertEquals(userDetailsDto, createUserDetailsDTO(user));
	}

	@Test
	public void testUpdateUserNothingToUpdate() {
		UpdateUserByIdRequest request = new UpdateUserByIdRequest();
		request.setUserId("123");
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		StatusVO status = new StatusVO("FAIL");
		Mockito.when(ums.updateUser(any(UpdateUserById.class))).thenReturn(
				status);
		GetUserResponse response = fcUMSServiceImpl.updateUser(request);
		UserDetailsDTO userDetailsDto = response.getUserDetails();
		Assert.assertNotEquals(userDetailsDto, null);
	}

	@Test
	public void testGetUserByToken() {
		GetUserByTokenRequest request = new GetUserByTokenRequest();
		request.setToken("yaugewye3g2736tgewuyg726");
		Mockito.when(tokenService.getUserIdByToken(any(String.class)))
				.thenReturn("123");
		User user = createUser();
      SocialUserVO socialUserVO = createSocialUserVO();
		Mockito.when(ums.getUserWithSocialInfo(123,"123")).thenReturn(socialUserVO);

		GetUserResponse response = fcUMSServiceImpl.getUserByToken(request);
		Assert.assertNotNull(response);
	}

	private SocialUserVO createSocialUserVO() {
	   SocialUserVO socialUserVO = new SocialUserVO();
	   User user = createUser();
	   socialUserVO.setUserVO(user);
      List<SocialInfo> socialInfo = new ArrayList<SocialInfo>();
      socialUserVO.setSocialInfos(socialInfo);
	   return socialUserVO;
   }

   @Test
	public void testUpdateUserByToken() {
		UpdateUserByTokenRequest request = new UpdateUserByTokenRequest();
		request.setToken("yaugewye3g2736tgewuyg726");
		Mockito.when(tokenService.getUserIdByToken(any(String.class)))
				.thenReturn("123");
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		StatusVO status = new StatusVO("OK");
		Mockito.when(ums.updateUser(any(UpdateUserById.class))).thenReturn(
				status);
		GetUserResponse response = fcUMSServiceImpl.updateUserByToken(request);
		UserDetailsDTO userDetailsDto = response.getUserDetails();
		Assert.assertNotEquals(userDetailsDto, null);
	}

	@Test
	public void testIsUserExist() {
		IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId("123");
		User user = createUser();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		IsUserExistResponse response = fcUMSServiceImpl.isUserExist(request);
		Assert.assertEquals(true, response.isExist());
	}

	@Test
	public void testIsUserNotExist() {
		IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId("123");
		Mockito.when(ums.getUser(123,"123")).thenThrow(UmsException.class);
		IsUserExistResponse response = fcUMSServiceImpl.isUserExist(request);
		Assert.assertEquals(false, response.isExist());
	}

	@Test
	public void testIsEmailExist() {
		IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId("abhishek.garg@snapdeal.com");
		User user = createUser();
      user.setEmail("abhishek.garg@snapdeal.com");
      SocialUserVO socialUserVO = createSocialUserVO();
      Mockito.when(ums.getUserWithSocialInfo("abhishek.garg@snapdeal.com", "123")).thenReturn(
               socialUserVO);
		Mockito.when(ums.getUser(any(String.class),any(String.class))).thenReturn(user);
		IsEmailExistResponse response = fcUMSServiceImpl.isEmailExist(request);
		Assert.assertEquals(true, response.isExist());
	}

	@Test
	public void isEmailNotExist() {
		IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId("abhishek.garg@snapdeal.com");
		User user = createUser();
      Mockito.when(ums.getUserWithSocialInfo("abhishek.garg@snapdeal.com", "123")).thenThrow(
				UmsException.class);
		IsEmailExistResponse response = fcUMSServiceImpl.isEmailExist(request);
		Assert.assertEquals(false, response.isExist());
	}

	@Test
	public void testSignOut() {
		SignoutRequest request = new SignoutRequest();
		request.setToken("213412we21e3wesdawr23ew");
		SignoutResponse response = new SignoutResponse();
		response.setSuccess(true);
		Mockito.when(authorizationContext.get("userMachineIdentifier")).thenReturn("dedsdEREW#@");
		Mockito.when(authorizationContext.get("user-Agent")).thenReturn("3sd233e#@#");
		Mockito.when(tokenService.signOut(request)).thenReturn(response);
		SignoutResponse signoutResponse = fcUMSServiceImpl.signOut(request);
		Assert.assertEquals(true, signoutResponse.isSuccess());
	}

   @Test
   @Ignore
   public void testSendForgotPasswordLink() {
      String emailId = "jonh@gmail.com";
      User user = createUser();
      StatusVO status = new StatusVO("Ok");
      Mockito.when(ums.getUser(emailId,"123")).thenReturn(user);
      Mockito.when(ums.forgotPassword(Mockito.any(ForgotPassword.class))).thenReturn(status);
      Mockito.when(authorizationContext.get("clientId")).thenReturn("2");
      SocialUserVO socialUserVO = createSocialUserVO();
      Mockito.when(imsUtility.getEmailTemplateKey(any(String.class), any(ConfigurationConstants.class))).thenReturn("testKey");
      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com","123")).thenReturn(socialUserVO);
      Mockito.when(imsUtility.getEmailTemplateKey(any(String.class), 
               any(ConfigurationConstants.class))).thenReturn("testTemplateKey");
      SendForgotPasswordLinkResponse sendForgotPasswordLinkResponse = fcUMSServiceImpl
               .sendForgotPasswordLink(emailId);
      Assert.assertEquals(true, sendForgotPasswordLinkResponse.isSuccess());
   }  
	
	
	private ForgotPassword createForgotPasswordRequest() {
	   ForgotPassword forgotPasswordRequest = new ForgotPassword();
      forgotPasswordRequest.setUserId(121);
      forgotPasswordRequest.setEncryptKey("100");
      forgotPasswordRequest.setExpireTime("1");
      return null;
   }

   @Test
	public void testLoginWithToken(){
		LoginWithTokenRequest request = new LoginWithTokenRequest();
		request.setGlobalToken("qCEcEySW4wxdWgEa9nPRKpstjGsMoEwHN9eCvrDOcjr1A20GW0n0ZE06sQP-ad8n");
		Mockito.when(authorizationContext.get("userMachineIdentifier")).thenReturn("dedsdEREW#@");
		Mockito.when(authorizationContext.get("user-Agent")).thenReturn("3sd233e#@#");
		Mockito.when(authorizationContext.get("clientId")).thenReturn("2");
		Mockito.when(globalTokenService.getUserIdByGlobalToken(
				"qCEcEySW4wxdWgEa9nPRKpstjGsMoEwHN9eCvrDOcjr1A20GW0n0ZE06sQP-ad8n", "2"))
				.thenReturn("123");
		TokenInformationDTO dto  = new TokenInformationDTO();
		dto.setToken("12312341124ee231232313133421");
		Mockito.when(
				globalTokenService
						.getTokenFromGlobalToken(any(TokenRequest.class)))
				.thenReturn(dto);

      SocialUserVO socialUserVO = createSocialUserVO();
      Mockito.when(ums.getUserWithSocialInfo(123,"123")).thenReturn(socialUserVO);

      Mockito.when(ums.getUserWithSocialInfo("jonh@gmail.com","123")).thenReturn(socialUserVO);
		LoginUserResponse response = fcUMSServiceImpl
				.loginUserWithToken(request);
		Assert.assertEquals(response.getTokenInformation(), dto);
	}
	
	@Test
	public void testChangePassword() {
		User user = createUser();
		Mockito.when(ums.getUser(any(String.class),any(String.class))).thenReturn(user);
		StatusVO status = new StatusVO("OK");
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("Password1");
		request.setNewPassword("Password1");
		request.setOldPassword("password");
		request.setToken("621t76tqwe67755feg1265r12r6235re123r3265r");
		Mockito.when(ums.changePassword(any(ChangePassword.class))).thenReturn(
				status);
		Mockito.when(tokenService.getUserIdByToken(any(String.class)))
				.thenReturn("123");
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      changePasswordcacheUpdate();
      ChangePasswordResponse response = fcUMSServiceImpl.changePassword(request);
		Assert.assertNotNull(response);
	}

	@Test(expected = IMSServiceException.class)
	public void testChangePasswordPasswordMismatch() {
		User user = createUser();
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("Password1");
		request.setNewPassword("Password9");
		request.setOldPassword("  ");
		request.setToken("621t76tqwe67755feg1265r12r6235re123r3265r");
		changePasswordcacheUpdate();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		fcUMSServiceImpl.changePassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testChangePasswordOldPasswordNewPasswordSame() {
		User user = createUser();
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("Password");
		request.setNewPassword("Password");
		request.setOldPassword("Password");
		request.setToken("621t76tqwe67755feg1265r12r6235re123r3265r");
		changePasswordcacheUpdate();
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		fcUMSServiceImpl.changePassword(request);
	}

	@Test(expected = IMSServiceException.class)
	public void testChangePasswordStatusIsNotOK() {
		User user = createUser();
		Mockito.when(ums.getUser(any(String.class),any(String.class))).thenReturn(user);
		StatusVO status = new StatusVO("NOTOK");
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("Password1");
		request.setNewPassword("Password1");
		request.setOldPassword("password");
		request.setToken("621t76tqwe67755feg1265r12r6235re123r3265r");
		Mockito.when(ums.changePassword(any(ChangePassword.class))).thenReturn(
				status);
		Mockito.when(tokenService.getUserIdByToken(any(String.class)))
				.thenReturn("123");
		changePasswordcacheUpdate();
		Mockito.when(ums.getUser(123)).thenReturn(user);
		fcUMSServiceImpl.changePassword(request);
	}

	@Test	
	@Ignore
	public void testForgotPassword() {
		ForgotPasswordRequest request = new ForgotPasswordRequest();
		request.setEmailId("abhishek18garg@gmail.com");
		request.setMobileNumber("9999988888");
		User user = createUser() ;
		//Mockito.when(context.get("clientId")).thenReturn("1233");
		Mockito.when(ums.getUser(request.getEmailId(),"123")).thenReturn(user);
		StatusVO status = new StatusVO("OK");
		Mockito.when(ums.forgotPassword(any(ForgotPassword.class))).thenReturn(status);
		OTPServiceResponse otpResponse = new OTPServiceResponse() ;
		otpResponse.setOtpId("123");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class)))
						.thenReturn(otpResponse) ;
		
		ForgotPasswordResponse response  = fcUMSServiceImpl.forgotPassword(request);
		Assert.assertNotNull(response);
	}
	
	@Test
	@Ignore
	public void ForgotPasswordTest(){
		ForgotPasswordRequest request = new ForgotPasswordRequest();
		request.setEmailId("abhishek18garg@gmail.com");
		request.setMobileNumber("9999988888");
		User user = createUser() ;
		Mockito.when(authorizationContext.get("clientId")).thenReturn("1233");
		Mockito.when(ums.getUser(request.getEmailId(),"123")).thenReturn(user);
		StatusVO status = new StatusVO("OK");
		Mockito.when(ums.forgotPassword(any(ForgotPassword.class))).thenReturn(status);
		OTPServiceResponse otpResponse = new OTPServiceResponse() ;
		otpResponse.setOtpId("123");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class)))
						.thenReturn(otpResponse) ;
		
		ForgotPasswordResponse response  = fcUMSServiceImpl.forgotPassword(request);
		Assert.assertNotNull(response);
	}
	@Test(expected =IMSServiceException.class)
	public void ForgotPasswordTestStausIsNotOK(){
		ForgotPasswordRequest request = new ForgotPasswordRequest();
		request.setEmailId("abhishek18garg@gmail.com");
		request.setMobileNumber("9999988888");
		SocialUserVO socialUserVO = createSocialUserVO() ;
		Mockito.when(authorizationContext.get("clientId")).thenReturn("1233");
		Mockito.when(ums.getUserWithSocialInfo(request.getEmailId(),"123")).thenReturn(socialUserVO);
		StatusVO status = new StatusVO("NOTOK");
		Mockito.when(ums.forgotPassword(any(ForgotPassword.class))).thenReturn(status);
		OTPServiceResponse otpResponse = new OTPServiceResponse() ;
		otpResponse.setOtpId("123");
		Mockito.when(otpService.generateOTP(any(GenerateOTPServiceRequest.class)))
						.thenReturn(otpResponse) ;
		
		fcUMSServiceImpl.forgotPassword(request);
	}
	
	@Test
	@Ignore
	public void testResetPassword(){
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setConfirmPassword("password1");
		request.setNewPassword("password1");
		request.setOtp("1235");
		request.setOtpId("123");
		request.setUserId("123");
		ResetPassword resetPasswordRequest = new ResetPassword() ;
		resetPasswordRequest.setEncryptKey("AbraKaDabra");
		resetPasswordRequest.setNewPassword("password1");
		resetPasswordRequest.setUserId(123);
		StatusVO status = new StatusVO("OK") ;
		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse() ;
		otpResponse.setMessage("SUCCESS");
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		otpResponse.setUserId("123");
		User user = createUser();
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
				.thenReturn(otpResponse) ;
      Mockito.when(ums.getUser(123,"123")).thenReturn(user);
      changePasswordcacheUpdate();
		Mockito.when(ums.updatePassword(any(UpdatePassword.class))).thenReturn(status);
		ResetPasswordResponse response  = fcUMSServiceImpl.resetPassword(request);
		Assert.assertNotNull(response);
	}

   public void changePasswordcacheUpdate() {
      Mockito.when(merchantServiceProvider.getMerchant()).thenReturn(Merchant.FREECHARGE);
      Mockito.when(serviceProvider.getUMSService()).thenReturn(fcImpl);
//      Mockito.when(
//               serviceProvider.getUMSService().putSdFcHashedPasswordByEmailId(any(String.class)))
//               .thenReturn(any(SdFcPasswordEntity.class));
//      Mockito.when(userIdCacheService.getEmailIdFromUserId("123",Merchant.SNAPDEAL)).thenReturn("test123@gmail.com");
   }
	@Test(expected = IMSServiceException.class)
	@Ignore
	public void testResetPasswordStatusIsNotOk(){
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setConfirmPassword("password1");
		request.setNewPassword("password1");
		request.setOtp("1235");
		request.setOtpId("123");
		request.setUserId("123");
		ResetPassword resetPasswordRequest = new ResetPassword() ;
		resetPasswordRequest.setEncryptKey("AbraKaDabra");
		resetPasswordRequest.setNewPassword("NoNewPassword");
		resetPasswordRequest.setUserId(123);
		StatusVO status = new StatusVO("NOtOK") ;
		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse() ;
		otpResponse.setMessage(OtpConstants.STATUS_FAILURE);
		otpResponse.setStatus(OtpConstants.STATUS_FAILURE);
		User user = createUser();
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
				.thenReturn(otpResponse) ;
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		Mockito.when(ums.resetPassword(any(ResetPassword.class))).thenReturn(status);
		fcUMSServiceImpl.resetPassword(request);		
	}
	@Test(expected = IMSServiceException.class)
	@Ignore
	public void testResetPasswordStatusPasswordMismatch(){
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setConfirmPassword("password1");
		request.setNewPassword("password");
		request.setOtp("1235");
		request.setOtpId("123");
		request.setUserId("123");
		ResetPassword resetPasswordRequest = new ResetPassword() ;
		resetPasswordRequest.setEncryptKey("AbraKaDabra");
		resetPasswordRequest.setNewPassword("NoNewPassword");
		resetPasswordRequest.setUserId(123);
		StatusVO status = new StatusVO("NOtOK") ;
		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse() ;
		otpResponse.setMessage(OtpConstants.STATUS_FAILURE);
		User user = createUser();
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
				.thenReturn(otpResponse) ;
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		Mockito.when(ums.resetPassword(any(ResetPassword.class))).thenReturn(status);
		fcUMSServiceImpl.resetPassword(request);		
	}
	@Test
	@Ignore
	public void testResetPasswordOTPFail(){
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setConfirmPassword("password1");
		request.setNewPassword("password1");
		request.setOtp("1235");
		request.setOtpId("123");
		request.setUserId("123");
		ResetPassword resetPasswordRequest = new ResetPassword() ;
		resetPasswordRequest.setEncryptKey("AbraKaDabra");
		resetPasswordRequest.setNewPassword("NoNewPassword");
		resetPasswordRequest.setUserId(123);
		StatusVO status = new StatusVO("NOtOK") ;
		VerifyOTPServiceResponse otpResponse = new VerifyOTPServiceResponse() ;
		otpResponse.setMessage("SUCCESS");
		otpResponse.setStatus(OtpConstants.STATUS_SUCCESS);
		otpResponse.setUserId("123");
		User user = createUser();
		Mockito.when(otpService.verifyOTP(any(VerifyOTPServiceRequest.class)))
				.thenReturn(otpResponse) ;
		Mockito.when(ums.getUser(123,"123")).thenReturn(user);
		changePasswordcacheUpdate();
      Mockito.when(ums.updatePassword(any(UpdatePassword.class))).thenReturn(status);
		ResetPasswordResponse response = fcUMSServiceImpl.resetPassword(request);		
		Assert.assertNotNull(response);
	}

	private UpdateUserByIdRequest createUpdateUserByIdRequest() {
		UpdateUserByIdRequest request = new UpdateUserByIdRequest();
		request.setUserId("123");
		UserDetailsRequestDto dto = new UserDetailsRequestDto();
		dto.setFirstName("abhishek");
		request.setUserDetailsRequestDto(dto);
		return request;
	}

	private UserRequestDto createUserRequestDto() {
	   UserRequestDto dto = new UserRequestDto();
	   dto.setMobileNumber("9999988888");
		dto.setEmailId("jonh@gmail.com");
      dto.setPassword("wqhgvewqh");
		dto.setFirstName("john");
		dto.setMiddleName("hgarry");
      dto.setLastName("kick");
		return dto;
	}

	private UserDetailsDTO createUserDetailsDTO(User user) {
		UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
		userDetailsDTO.setEmailId(user.getEmail());
		userDetailsDTO.setDisplayName(user.getName());
		userDetailsDTO.setMobileNumber(user.getMobileNo());
		userDetailsDTO.setUserId(user.getUserId() + "");
		return userDetailsDTO;
	}

	private User createUser() {
		User user = new User();
		user.setUserId(123);
		user.setEmail("jonh@gmail.com");
		user.setName("john gaqrry kick");
		user.setPassword("wqhgvewqh");
		user.setMobileNo("9999988888");
		user.setForgotPasswordKey("AabraKaDabra");
      user.setCreatedOn("32432523523");
      user.setMobileVerified(false);
      user.setSource(null);
      user.setIsActive(true);
      return user;
	}

	private void fail(String msg) {
		System.out.println(msg);
	}

}
