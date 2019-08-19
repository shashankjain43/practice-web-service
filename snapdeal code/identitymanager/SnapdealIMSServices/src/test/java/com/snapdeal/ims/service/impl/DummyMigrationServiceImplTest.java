package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.constants.DummyMigrationEmails;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.token.service.impl.BaseTest;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import junit.framework.Assert;


public class DummyMigrationServiceImplTest extends BaseTest {
	
	@InjectMocks
	private DummyMigrationServiceImpl dummyMigrationService;
	
	@org.mockito.Mock
	private AuthorizationContext context;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		initConfig();
	}
	
	@Test
	public void getMigrationStatusForSDUserMigrationTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);
		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		try {
			dto = dummyMigrationService.getUserUpgradeStatus(createUserUpgradeByEmailRequest("invalid@abc.com"), false);
		} catch (IMSServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
		}
	}
	
	@Test
	public void getMigrationStatusForFCUserMigrationTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

	}

	@Test
	public void getMigrationStatusForSDFCUserMigrationTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);

		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_RECOMMENDED);
	
	}

	@Test
	public void getMigrationStatusForSDUserAlreadyMigratedTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);
		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.SD_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);

	}

	@Test
	public void getMigrationStatusForFCUserAlreadyMigratedTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_OC_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.LINK_SD_ACCOUNT);

		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.FC_ACCOUNT_MIGRATED);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.VERIFY_OC_PASSWORD);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);

	}

	@Test
	public void getMigrationStatusForSDFCUserAlreadyMigratedTest() {
		Mockito.when(context.get(any(String.class))).thenReturn("1");
		UserUpgradationResponse dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_SUCCESS.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.OC_ACCOUNT_EXISTS);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);
		
		dto = dummyMigrationService.
				getUserUpgradeStatus(createUserUpgradeByEmailRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_FAILURE.getEmail()), false);
		Assert.assertEquals(dto.getUpgradationInformation().getState(), com.snapdeal.ims.enums.State.OC_ACCOUNT_EXISTS);
		Assert.assertEquals(dto.getUpgradationInformation().getAction(), com.snapdeal.ims.enums.Action.NO_ACTION_REQUIRED);
		Assert.assertEquals(dto.getUpgradationInformation().getSkip(), com.snapdeal.ims.enums.Skip.SKIP_TRUE);
		Assert.assertEquals(dto.getUpgradationInformation().getUpgrade(), com.snapdeal.ims.enums.Upgrade.UPGRADE_COMPLETED);
	}
	
	@Test
	public void userUpgradeTest() {
		UpgradeUserResponse response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(response.isSuccess(), true);
		
		
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		response = dummyMigrationService.
				upgradeUser(createUserUpgradeRequest(DummyMigrationEmails.MIGRATED_SD_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(response.isSuccess(), false);
		
		try {
			response = dummyMigrationService.upgradeUser(createUserUpgradeRequest("invalid@abc.com"));
		} catch (IMSServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
		}
	}
	
	@Test
	public void verifyUpgradeUser() {
		VerifyUpgradeUserResponse res  = dummyMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(res.isSuccess(), true);
		res  = dummyMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS.getEmail()));
		Assert.assertEquals(res.isSuccess(), true);
		
		res  = dummyMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(res.isSuccess(), false);
		res  = dummyMigrationService.
				verifyUpgradeUser(createVerifyUpgradeUser(DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE.getEmail()));
		Assert.assertEquals(res.isSuccess(), false);
		
		try {
			res = dummyMigrationService.verifyUpgradeUser(createVerifyUpgradeUser("invalid@abc.com"));
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
}


