package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.cache.service.ISDFCPasswordCacheService;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.cache.service.IUserVerificationCacheService;
import com.snapdeal.ims.constants.DummyMigrationEmails;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.EmailVerificationSource;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.Migration;
import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;


@RunWith(MockitoJUnitRunner.class)
public class UserMigrationServiceImplTest {
	
	@Autowired
	@Qualifier("userMigrationService")
	IUserMigrationService userMigrationService;
	
	@InjectMocks
	@Spy
	private UserMigrationServiceImpl userMigrationProxy;
	
	@Mock   
	private IActivityDataService activityDataService;
	
	@Mock
	private IUserVerificationCacheService userVerificationService;
	
	@Mock
	private ILoginUserService loginUserService;
	
	@Mock
	ISDFCPasswordCacheService sdFcPasswordCacheService;
	
	@Mock
	IUserDao userDao;
	
	@Mock
	IUserCacheService userCacheService;
	
	@Mock
	Migration migration;
	
	@Mock
	private IMSServiceImpl imsService;
	
	@Mock
	private IUserService userService;
	
	@Mock
	private ITokenService tokenService;
	
	@Mock
	private MigrationDao migrationDao;
	
	@Mock
	private FortKnoxServiceHelper fortKnoxServiceHelper;
	
	@Before
	public void initailize(){
	   
	   CacheManager cacheManager = CacheManager.getInstance();
      final ConfigCache configCache = new ConfigCache();
      Map<String, String> map1 = new HashMap<String, String>();
      map1.put("cipher.unique.key","U25hcGRlYWxVbmlxdWVLZXk=");
      configCache.put("global", map1);
      cacheManager.setCache(configCache);
	}
	
	@Ignore
	@Test
	public void getMigrationStatusForSDUserMigrationTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		try {
			dto = userMigrationService.getUserUpgradeStatus(createUserUpgradeByEmailRequest("invalid@abc.com"), false);
		} catch (IMSServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
		}
	}
	
	@Ignore
	@Test
	public void getMigrationStatusForFCUserMigrationTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

	}

	@Ignore
	@Test
	public void getMigrationStatusForSDFCUserMigrationTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);
	
	}

	@Ignore
	@Test
	public void getMigrationStatusForSDUserAlreadyMigratedTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_FC_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.LINK_FC_ACCOUNT);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_FC_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.LINK_FC_ACCOUNT);

	}

	@Ignore
	@Test
	public void getMigrationStatusForFCUserAlreadyMigratedTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_SD_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.LINK_SD_ACCOUNT);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_SD_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.LINK_SD_ACCOUNT);

	}

	@Ignore
	@Test
	public void getMigrationStatusForSDFCUserAlreadyMigratedTest() {
		UserUpgradationResponse dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.OC_ACCOUNT_EXISTS);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);

		
		dto = userMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.OC_ACCOUNT_EXISTS);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);

	}
	
	@Ignore
	@Test
	public void userUpgradeTest() {
		UpgradeUserResponse response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		
		
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = userMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		
		try {
			response = userMigrationService.upgradeUser(createUserUpgradeRequest("invalid@abc.com"));
		} catch (IMSServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
		}
	}
	
	@Ignore
	@Test
	public void verifyUpgradeUser() {
		VerifyUpgradeUserResponse res  = userMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(res.isSuccess(), true);
		res  = userMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(res.isSuccess(), true);
		
		res  = userMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(res.isSuccess(), false);
		res  = userMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(res.isSuccess(), false);
		
		try {
			res = userMigrationService.verifyUpgradeUser(createVerifyUpgradeUser("invalid@abc.com"));
		} catch (IMSServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
		}
		
	}
	
	private UserUpgradeByEmailRequest createUserUpgradeByEmailRequest(String emailId) {
		UserUpgradeByEmailRequest request = new UserUpgradeByEmailRequest();
		request.setEmailId(emailId);
		return request;
		
	}
	
	private UserUpgradeRequest createUserUpgradeRequest(String emailId) {
		UserUpgradeRequest request = new UserUpgradeRequest();
		request.setEmailId(emailId);
		return request;
		
	}
	
	private VerifyUserUpgradeRequest createVerifyUpgradeUser(String emailId) {
		VerifyUserUpgradeRequest request = new VerifyUserUpgradeRequest();
		request.setEmailId(emailId);
		return request;
		
	}
	
   @Test
   public void testVerifyUserWithLinkedState_Parking_UpgradeCompleted() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_COMPLETED, null);

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("poMq-y-SSL7d2vQ5pbpYAr66QBSFuiz9njX57_5N6gk39mvxgt3bVxHDm70x9pl_");
      request.setPassword("password");
      request.setToken(null);
      userMigrationProxy.verifyUserWithLinkedState(request);

   }
   
   @Test
   public void testVerifyUserWithLinkedState_Parking_LinkedState() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.LINK_SD_ACCOUNT, null);

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken(null);
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);
      
      org.junit.Assert.assertTrue(
               "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }

   @Test
   public void testVerifyUserWithLinkedState_Parking_NoUpgrade() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_RECOMMENDED, null);

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken(null);
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);
      
      org.junit.Assert.assertTrue(
               "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_Parking_UpgradeCompleted_WithToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_COMPLETED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_NewUser_UpgradeCompleted_WithToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_NEW_USER, Upgrade.UPGRADE_COMPLETED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
     
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_NewUser_UpgradeCompleted_WithoutToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_NEW_USER, Upgrade.UPGRADE_COMPLETED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken(null);
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_NewUser_LinkSD_WithoutToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_NEW_USER, Upgrade.LINK_SD_ACCOUNT,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken(null);
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_NewUser_LinkFC_WithoutToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_NEW_USER, Upgrade.LINK_FC_ACCOUNT,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken(null);
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test
   public void testVerifyUserWithLinkedState_NewUser_LinkedState_WithToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_NEW_USER, Upgrade.LINK_FC_ACCOUNT,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test(expected = IMSServiceException.class)
   public void testVerifyUserWithLinkedState_InvalidPurpose() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.VERIFY_GUEST_USER, Upgrade.LINK_FC_ACCOUNT,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

   }
   
   @Test(expected = IMSServiceException.class)
   public void testVerifyUserWithLinkedState_Parking_InvalidUpgradeState() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.NO_UPGRADE_REQRUIRED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

   }
   
   @Test(expected = IMSServiceException.class)
   public void testVerifyUserWithLinkedState_NewUser_InvalidUpgradeState() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.NO_UPGRADE_REQRUIRED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);
   }

   
   @Test
   public void testVerifyUserWithLinkedState_Parking_NoUpgrade_WithToken() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_RECOMMENDED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

      org.junit.Assert.assertTrue(
      "verifyUserWithLinkedState returned unsuccessfully for Parking with link_sd case",
               verifyUserResponse.isSuccess());

   }
   
   @Test(expected = IMSServiceException.class)
   public void testVerifyUserWithLinkedState_InvalidVerificationCode() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_RECOMMENDED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVsh");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

   }
   
   @Test(expected = IMSServiceException.class)
   public void testVerifyUserWithLinkedState_EmailMismatch() throws CipherException {
      setMockExpectationsForVerifyUserWithLinkedStateWithParameters("test@mail.com",
               VerificationPurpose.PARKING_INTO_WALLET, Upgrade.UPGRADE_RECOMMENDED,
               "AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");

      UserVerification userVerificationDetails = new UserVerification();
      userVerificationDetails
               .setCode("poMq-y-SSL7d2vQ5pbpYAr66QBSFuiz9njX57_5N6gk39mvxgt3bVxHDm70x9pl_");
      userVerificationDetails.setEmailId("OtherEMail@mail.com");
      userVerificationDetails.setPurpose(VerificationPurpose.PARKING_INTO_WALLET);
      doReturn(userVerificationDetails).when(userVerificationService)
      .getUserVerificationDetailsByCode(any(String.class));
      
      VerifyUserWithLinkedStateRequest request = new VerifyUserWithLinkedStateRequest();
      request.setEmailId("test@mail.com");
      request.setCode("cVshRTOpTPu9exVpFy74S-AmspyxYGRD8AfTTnNKf3FaKD6bTGdu2JxBHUQGtWF2kObDyo0cDNSmCUIGrq-OTw");
      request.setPassword("password");
      request.setToken("AGD9VQfktblf3nzo7kwAcgkwp-BSuL_luC8uyuWl9SIw1JoTK_DaLOOCHvkgGVY9dneUpFnbCocw4wTWdXzifg");
      VerifyUserWithLinkedStateResponse verifyUserResponse = userMigrationProxy
               .verifyUserWithLinkedState(request);

   }
   
   private void setMockExpectationsForVerifyUserWithLinkedStateWithParameters(String emailId,
            VerificationPurpose purpose, Upgrade upgradeState, String requestToken)
            throws CipherException {

      UserVerification userVerificationDetails = new UserVerification();
      userVerificationDetails
               .setCode("poMq-y-SSL7d2vQ5pbpYAr66QBSFuiz9njX57_5N6gk39mvxgt3bVxHDm70x9pl_");
      userVerificationDetails.setCodeExpiryTime(new Timestamp(1444890447));
      userVerificationDetails.setCreatedTime(new Timestamp(1444890447));
      userVerificationDetails.setEmailId(emailId);
      userVerificationDetails.setMerchant(Merchant.ONECHECK);
      userVerificationDetails.setPurpose(purpose);
      userVerificationDetails.setUserId(null);

      UpgradationInformationDTO upgradationInformationDTO = new UpgradationInformationDTO();
      upgradationInformationDTO.setUpgrade(upgradeState);
      LoginUserResponse loginUserResponse = new LoginUserResponse();
      TokenInformationDTO tokenInformationDTO = new TokenInformationDTO();

      tokenInformationDTO.setToken("token");
      tokenInformationDTO.setGlobalToken("globalToken");

      UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
      userDetailsDTO.setEmailId(emailId);

      loginUserResponse.setUpgradationInformation(upgradationInformationDTO);
      loginUserResponse.setTokenInformation(tokenInformationDTO);
      loginUserResponse.setUserDetails(userDetailsDTO);

      GetUserResponse getUserResponse = new GetUserResponse();
      getUserResponse.setUserDetails(userDetailsDTO);

      User user = new User();
      user.setUserId("VJAXTEST");

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setUpgradeStatus(upgradeState);

      TokenDetailsDTO tokenDetailsDTO = new TokenDetailsDTO();
      tokenDetailsDTO.setExpiryTime(new Date());

      GlobalTokenDetailsEntity globalTokenDetailsEntity = new GlobalTokenDetailsEntity();
      globalTokenDetailsEntity.setExpiryTime(new Date());
      globalTokenDetailsEntity.setGlobalTokenId("globalTestTokenId");
      globalTokenDetailsEntity.setGlobalToken("globalTestToken");

      doNothing().when(activityDataService).setActivityDataByEmailId(any(String.class));
      doReturn(userVerificationDetails).when(userVerificationService)
               .getUserVerificationDetailsByCode(any(String.class));

      doReturn(loginUserResponse).when(loginUserService).loginUser(any(LoginUserRequest.class));
      doReturn(user).when(userDao).getUserByEmail(any(String.class));
      doNothing().when(userDao).updateById(any(User.class));
      doReturn(true).when(userCacheService).invalidateUserById(any(String.class));
      doReturn(upgradeStatus).when(migration).getIMSMigrationStatus(any(String.class));
      doReturn(getUserResponse).when(userService).getUserByToken(any(GetUserByTokenRequest.class));

      if (upgradeState.equals(Upgrade.UPGRADE_RECOMMENDED)) {
         doReturn(true).when(sdFcPasswordCacheService).setEmailVerificationSource(
                  any(String.class), any(EmailVerificationSource.class));
      } else if (upgradeState.equals(Upgrade.LINK_FC_ACCOUNT)
               || upgradeState.equals(Upgrade.LINK_SD_ACCOUNT)
               || upgradeState.equals(Upgrade.UPGRADE_COMPLETED)) {

         doReturn(upgradeStatus).when(migrationDao).getLatestUpgradeStatus(any(String.class));
         doNothing().when(migrationDao).updateUpgradationStatus(any(UpgradeStatus.class));
         
         doNothing().when(imsService).createNotificationOnMigrationStateChange(any(String.class),
                  any(String.class), any(WalletUserMigrationStatus.class));
         doNothing().when(fortKnoxServiceHelper).createFortKnoxTask(any(UserDetailsDTO.class));
         doReturn(getUserResponse).when(userService).getUserFromIMSInCaseOfLinkUserWithOcPassword(any(GetUserByIdRequest.class));
      }
      if (requestToken != null) {
         doReturn(tokenDetailsDTO).when(tokenService).getTokenDetailsForToken(any(String.class));
         doReturn(globalTokenDetailsEntity).when(tokenService).getGlobalTokenDetailsForToken(
                  any(String.class));
         doReturn(new SignoutResponse()).when(tokenService).signOut(any(SignoutRequest.class));
         doNothing().when(activityDataService).setActivityDataByUserId(any(String.class));
      }

   }
}


