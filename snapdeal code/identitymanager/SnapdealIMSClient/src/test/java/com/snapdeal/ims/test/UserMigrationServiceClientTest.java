package com.snapdeal.ims.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.client.impl.UserMigrationServiceClientImpl;
import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class UserMigrationServiceClientTest {

	IUserMigrationServiceClient cli = new UserMigrationServiceClientImpl();

	@Before
	public void setup() throws Exception {
		ClientDetails.init("30.0.4.109", "8080", "jqie679mg#3k", "8008412DCA9C153D", 2500000);
	}

	@Test
	public void testUserUpgradeStatus() {
		UserUpgradeByEmailRequest request = new UserUpgradeByEmailRequest();
		request.setEmailId("snapsduseronlyupgrade1001success@snapupgrade.com");
		request.setUserAgent("userAgent");
		request.setUserMachineIdentifier("machineId");
		try {
			UserUpgradationResponse userUpgradeStatus = cli
					.userUpgradeStatus(request);
			org.junit.Assert.assertNotNull("", userUpgradeStatus);
		} catch (HttpTransportException e) {
			Assert.fail(e.getMessage());
		} catch (ServiceException e) {
			e.printStackTrace();
			org.junit.Assert.fail(e.getErrMsg());
		}
	}

	@Test
	public void testUserUpgradeStatus_noemail_token() {
		UserUpgradeByEmailRequest request = new UserUpgradeByEmailRequest();
		request.setUserAgent("userAgent");
		request.setUserMachineIdentifier("machineId");
		try {
			UserUpgradationResponse userUpgradeStatus = cli
					.userUpgradeStatus(request);
			Assert.fail("This is a fail feature test.");
		} catch (HttpTransportException e) {
			Assert.fail(e.getMessage());
		} catch (ServiceException e) {
			Assert.assertEquals(e.getErrCode(), IMSRequestExceptionCodes.EITHER_EMAIL_OR_TOKEN_MANDATORY.errCode());
		}
	}

   @Test
   public void testUpgradeUser() {
      UserUpgradeRequest request = new UserUpgradeRequest();
      request.setUserAgent("userAgent");
      request.setEmailId("subhash102oct@gmail.com");
      request.setUserMachineIdentifier("userMachineIdentifier");
      request.setVerifiedType(UserIdentityVerifiedThrough.GOOGLE_VERIFIED);
      request.setUpgradeSource(UpgradeSource.SIGN_IN);
      request.setMobileNumber("9953329969");
      request.setOtpId("fd600165-9ee8-45bd-bef7-47162863c04b");
      request.setOtp("0767");
      try {
         UpgradeUserResponse upgradeUser;
         upgradeUser = cli.upgradeUser(request);
         org.junit.Assert.assertNotNull("", upgradeUser);
      } catch (HttpTransportException e) {
         Assert.fail(e.getMessage());
      } catch (ServiceException e) {
         org.junit.Assert.assertEquals(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg(),
                  e.getErrMsg());
      }
   }

	@Test
   public void testUpgradeUser_no_upgrade_source() {
      UserUpgradeRequest request = new UserUpgradeRequest();
      request.setUserAgent("userAgent");
      request.setEmailId("snapsdmigrateduserupgrade1001success@snapupgrade.com");
      request.setUserMachineIdentifier("userMachineIdentifier");
      request.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
      try {
         UpgradeUserResponse upgradeUser;
            upgradeUser = cli.upgradeUser(request);
            Assert.fail();
      } catch (HttpTransportException e) {
            Assert.fail(e.getMessage());
      } catch (ServiceException e) {
         Assert.assertEquals(
               IMSRequestExceptionCodes.UPGRADE_SOURCE_MANDATORY.errCode(),
               e.getErrCode());
      }
   }

	@Test
	public void testVerifyUpgradeUser() {
		try {
			VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();

			verifyUserUpgradeRequest.setEmailId("johnson5796@gmail.com");
			verifyUserUpgradeRequest.setPassword("password");
			verifyUserUpgradeRequest.setTargetSrcToBeValidated(Merchant.SNAPDEAL);
			verifyUserUpgradeRequest.setUserAgent("userAgent");
			verifyUserUpgradeRequest.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_PASSWORD);
			verifyUserUpgradeRequest.setUserMachineIdentifier("userMachineIdentifier");
			VerifyUpgradeUserResponse upgradeUser = cli.verifyUpgradeUser(verifyUserUpgradeRequest);
			org.junit.Assert.assertNotNull("", upgradeUser);
		} catch (HttpTransportException e) {
			Assert.fail(e.getMessage());

		} catch (ServiceException e) {
			org.junit.Assert.assertEquals(
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg(),
					e.getErrMsg());
		}
	}
		
	@Test
   public void testVerifyUserWithLinkedState() throws ServiceException {

	   
      VerifyUserWithLinkedStateRequest verifyUserLinkedRequest = new VerifyUserWithLinkedStateRequest();
      verifyUserLinkedRequest.setEmailId("devendramudaliar@gmail.com");
      verifyUserLinkedRequest.setPassword("Snapdeal@2016");
      verifyUserLinkedRequest.setCode("ahzFv_ezb0Cosp2o5_ur1ie84LTAepJIqgk4fL1mkbFLEeiKJdsqjoVNWN8C0cHj");
      VerifyUserWithLinkedStateResponse verifyUserWithLinkedStateResponse = cli
               .verifyUserWithLinkedState(verifyUserLinkedRequest);

	}
}
