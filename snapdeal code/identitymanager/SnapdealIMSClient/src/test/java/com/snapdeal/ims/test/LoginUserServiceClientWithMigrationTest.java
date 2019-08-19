package com.snapdeal.ims.test;

import com.snapdeal.ims.client.ILoginUserServiceClient;
import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.LoginUserServiceClientImpl;
import com.snapdeal.ims.client.impl.OTPClientServiceImpl;
import com.snapdeal.ims.client.impl.UserMigrationServiceClientImpl;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordVerifyRequest;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.util.DBConnector;
import com.snapdeal.ims.utils.ClientDetails;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LoginUserServiceClientWithMigrationTest {

   private enum UserCreateCriteria {

      SD_USER_ONLY, FC_USER_ONLY, SD_FC_USER_ONLY, SD_MIGRATED_FROM_SD_ONLY, FC_MIGRATED_FROM_FC_ONLY, SD_FC_USER_MIGRATED_FROM_SD_ONLY, SD_FC_USER_MIGRATED_FROM_FC_ONLY;
   };
   
   ILoginUserServiceClient loginUserServiceClient = new LoginUserServiceClientImpl();
   IUserServiceClient userServiceClient = new UserServiceClientImpl();
   IOTPServiceClient otpServiceClient = new OTPClientServiceImpl();
   IUserMigrationServiceClient userMigrationServiceClient = new UserMigrationServiceClientImpl();

   Random r = new Random();
   int number = r.nextInt(10000);

   @Before
   public void setup() throws Exception {
      ClientDetails.init("localhost", "8080", "snapdeal", "1", 1500000);
   }

   @Test
   public void testSDOnlyUserExist() throws Exception {
      // Create fc user
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      // update mobile number.
      if (null != createUserSD) {
         IsUserExistRequest userExistRequest = new IsUserExistRequest();
         userExistRequest.setUserId(createUserSD.getUserDetails().getUserId());
         IsUserExistResponse userExistResponse = userServiceClient.isUserExist(userExistRequest);
         if (userExistResponse.isExist()) {
            Assert.assertTrue(true);
         } else {
            Assert.assertTrue(false);
         }

      }

   }

   private void setupSD() throws Exception {
      ClientDetails.init("localhost", "8080", "snapdeal", "1", 1500000);
      // ClientDetails.init("identity-stg.snapdeal.com", "443", "q%!8x7tg6df!",
      // "AA6F0FD7BBA87D81");
   }

   private void setupFC() throws Exception {
      ClientDetails.init("localhost", "8080", "freecharge", "2", 1500000);
   }

   private void setupStaging2SD() throws Exception {
      /*
       * Client Id: 9782751690B09848
       * Secret:zitsgfqo^a2p
       */
      ClientDetails.init("ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com", "443",
               "q%!8x7tg6df!", "AA6F0FD7BBA87D81", 1500000);
      /*
       * ClientDetails.init(
       * "ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com",
       * "443", "zitsgfqo^a2p", "9782751690B09848", 1500000);
       */

   }

   private void setupStaging2FC() throws Exception {
      ClientDetails.init("ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com", "443",
               "q%!8x7tg6dyT", "AA6F0FD7BBA87D83", 1500000);

   }

   private void setupOC() throws Exception {
      ClientDetails.init("localhost", "8080", "onecheck", "3", 1500000);
   }

   @Test
   public void test_SNAPDEALTECH_54401() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber = "7000000002";
      // fc user create
      setupFC();
      CreateUserResponse createUserFC = createUserFC(email, password, mobileNumber);
      Assert.assertNotNull(createUserFC);
      // sd social user
      setupSD();

      SocialUserResponse createSocialUserSD = createSocialUserSD(email, mobileNumber);
      Assert.assertNotNull(createSocialUserSD);
      // upgrade via fc
      setupFC();
      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobileNumber);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(createUserFC.getTokenInformationDTO().getToken());
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      Assert.assertNotNull(otpResponse);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

         userUpgradeRequest.setOtpId(otpResponse.getOtpId());
         userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
         userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
         userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
         userUpgradeRequest.setToken(createUserFC.getTokenInformationDTO().getToken());
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         if (otp != null) {
            userUpgradeRequest.setOtp(otp);
         }
         UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                  .upgradeUser(userUpgradeRequest);
         if (upgradeResponse != null) {
            Assert.assertEquals(true, upgradeResponse.isSuccess());
         }
      }
      LoginUserResponse loginWithToken = loginWithToken(createUserFC.getTokenInformationDTO()
               .getGlobalToken());
      Assert.assertNotNull(loginWithToken);
      Assert.assertNotNull(loginWithToken.getUpgradationInformation());
      Assert.assertEquals(loginWithToken.getUpgradationInformation().getState(),
               State.FC_ACCOUNT_MIGRATED);
   }

   @Test
   public void test_SNAPDEALTECH_54401_SD() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber = "7000000010";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      Assert.assertNotNull(createUserSD);
      SocialUserResponse createSocialUserSD = createSocialUserSD(email, mobileNumber);
      Assert.assertNotNull(createSocialUserSD);
      // upgrade via fc
      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobileNumber);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(createSocialUserSD.getTokenInformation().getToken());
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      Assert.assertNotNull(otpResponse);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

         userUpgradeRequest.setOtpId(otpResponse.getOtpId());
         userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.FACEBOOK_VERIFIED);
         userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
         userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
         userUpgradeRequest.setToken(createSocialUserSD.getTokenInformation().getToken());
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         if (otp != null) {
            userUpgradeRequest.setOtp(otp);
         }
         UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                  .upgradeUser(userUpgradeRequest);
         if (upgradeResponse != null) {
            Assert.assertEquals(true, upgradeResponse.isSuccess());
         }
      }
      try {
         LoginUserResponse loginWithToken = loginWith(email, password);
         Assert.fail("Should not allow login.");
      } catch (ServiceException ex) {
         Assert.assertEquals(IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errCode(),
                  ex.getErrCode());
      }
   }

   @Test
   public void test_SNAPDEALTECH_54600() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber = "7000000014";

      setupFC();
      CreateUserResponse createUserFC = createUserFC(email, password, mobileNumber);
      Assert.assertNotNull(createUserFC);
      setupSD();
      String password2 = "password2";
      CreateUserResponse createUserSD = createUserSD(email, password2);
      Assert.assertNotNull(createUserSD);

      LoginUserResponse loginWith = loginWith(email, password2);
      Assert.assertNotNull(loginWith);

      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobileNumber);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(loginWith.getTokenInformation().getToken());
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      Assert.assertNotNull(otpResponse);
      UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
      userUpgradeRequest.setEmailId(email);
      userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

      userUpgradeRequest.setOtpId(otpResponse.getOtpId());
      userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
      userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
      userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
      userUpgradeRequest.setToken(loginWith.getTokenInformation().getToken());
      String otp = DBConnector.getOtp(otpResponse.getOtpId());
      if (otp != null) {
         userUpgradeRequest.setOtp(otp);
      }
      UpgradeUserResponse upgradeResponse = userMigrationServiceClient
               .upgradeUser(userUpgradeRequest);
      if (upgradeResponse != null) {
         Assert.assertEquals(true, upgradeResponse.isSuccess());
      }
      // now change to FC and login.
      setupFC();
      LoginUserResponse loginInFC = loginWith(email, password2);
      Assert.assertNotNull(loginInFC);

      try {
         GetUserByTokenRequest tokenReq = new GetUserByTokenRequest();
         tokenReq.setToken(loginInFC.getTokenInformation().getToken());
         GetUserResponse userByToken = userServiceClient.getUserByToken(tokenReq);
         Assert.assertNull(userByToken);
      } catch (ServiceException ex) {
         Assert.assertTrue(ex.getErrMsg(), true);
      }

      GenerateOTPRequest generateOTPRequest2 = new GenerateOTPRequest();
      generateOTPRequest2.setPurpose(OTPPurpose.LINK_ACCOUNT);
      generateOTPRequest2.setMobileNumber(mobileNumber);
      generateOTPRequest2.setEmailId(email);
      generateOTPRequest2.setToken(loginInFC.getTokenInformation().getToken());
      GenerateOTPResponse sendOTP = otpServiceClient.sendOTP(generateOTPRequest2);
      Assert.assertNotNull(sendOTP);

      VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();
      verifyUserUpgradeRequest.setOtpId(sendOTP.getOtpId());
      verifyUserUpgradeRequest.setOtp(DBConnector.getOtp(sendOTP.getOtpId()));
      verifyUserUpgradeRequest.setToken(loginInFC.getTokenInformation().getToken());
      verifyUserUpgradeRequest.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_MOBILE_OTP);
      verifyUserUpgradeRequest.setTargetSrcToBeValidated(Merchant.FREECHARGE);
      VerifyUpgradeUserResponse verifyUpgradeUser = userMigrationServiceClient.verifyUpgradeUser(verifyUserUpgradeRequest);
      Assert.assertNotNull(verifyUpgradeUser);

      LoginUserResponse loginAfterLink = loginWith(email, password2);
      Assert.assertNotNull(loginAfterLink);

   }

   private SocialUserResponse createSocialUserSD(String email, String mobileNumber)
            throws HttpTransportException, ServiceException {
      CreateSocialUserRequest request = new CreateSocialUserRequest();
      request.getSocialUserDto().setEmailId(email);
      request.getSocialUserDto().setSocialSrc("facebook");
      request.getSocialUserDto().setSocialId("45678987698");
      request.getSocialUserDto().setFirstName("John");
      request.getSocialUserDto().setMiddleName("Kumar");
      request.getSocialUserDto().setLastName("Cena");
      request.getSocialUserDto().setAboutMe("hi");
      request.getSocialUserDto().setDisplayName("haowen");
      request.getSocialUserDto().setAboutMe("Wrestler");
      request.getSocialUserDto().setPhotoURL("");
      request.getSocialUserDto().setMobileNumber(mobileNumber);
      request.getSocialUserDto().setDob("1990-12-05");
      request.getSocialUserDto().setGender(Gender.MALE);
      request.getSocialUserDto().setLanguagePref(Language.ENGLISH);
      request.setUserMachineIdentifier("jndf");
      request.setUserAgent("abc");
      SocialUserResponse response = userServiceClient.createOrLoginUserWithSocial(request);
      return response;
   }

   @Test
   public void createSocialUserWithEmailAndMobileTest() throws ServiceException, Exception {

      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber1 = "8095794092";

      setupOC();
      CreateSocialUserWithMobileResponse createUserOC = createUserOC(email, password, mobileNumber1);

      if (createUserOC != null) {
         CreateUserMobileVerifyRequest verifyRequest = new CreateUserMobileVerifyRequest();
         verifyRequest.setOtpId(createUserOC.getOtpId());
         String otp = DBConnector.getOtp(createUserOC.getOtpId());
         verifyRequest.setOtp(otp);
         verifyRequest.setUserMachineIdentifier("jndf");
         verifyRequest.setUserAgent("abc");
         CreateUserResponse userResponse = userServiceClient
                  .verifySocialUserWithMobile(verifyRequest);
         if (userResponse != null) {
            Assert.assertTrue(true);
         }
      }
   }

   @Test
   public void testSignoutAndLogin() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      Assert.assertNotNull(createUserSD);

      SignoutRequest signoutReq = new SignoutRequest();
      signoutReq.setToken(createUserSD.getTokenInformationDTO().getToken());
      loginUserServiceClient.signout(signoutReq);

      GetUserByTokenRequest getUserbyTokenRequest = new GetUserByTokenRequest();
      getUserbyTokenRequest.setToken(createUserSD.getTokenInformationDTO().getToken());
      GetUserResponse userByToken = null;
      try {
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         System.out.println();
         Assert.assertNull(userByToken);
      }

   }

   @Test
   public void testChangePasswordAndLogin() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      Assert.assertNotNull(createUserSD);
      LoginUserResponse loginWith = loginWith(email, password);
      Assert.assertNotNull(loginWith);

      Assert.assertNotEquals(createUserSD.getTokenInformationDTO().getGlobalToken(), loginWith
               .getTokenInformation().getGlobalToken());

      ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
      changePasswordRequest.setOldPassword("password");
      changePasswordRequest.setToken(createUserSD.getTokenInformationDTO().getToken());
      String password2 = "password1";
      changePasswordRequest.setNewPassword(password2);
      changePasswordRequest.setConfirmNewPassword(password2);
      ChangePasswordResponse changePassword = loginUserServiceClient
               .changePassword(changePasswordRequest);
      Assert.assertNotNull(changePassword);
      Assert.assertTrue(changePassword.isSuccess());

      GetUserByTokenRequest getUserbyTokenRequest = new GetUserByTokenRequest();
      getUserbyTokenRequest.setToken(loginWith.getTokenInformation().getToken());
      GetUserResponse userByToken = null;
      try {
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch(ServiceException ex) {
         System.out.println();
         Assert.assertNull(userByToken);
      }
      // =============================================================
      LoginUserResponse loginWith2 = loginWith(email, password2);

      LoginUserResponse loginWith3 = loginWith(email, password2);
      ChangePasswordRequest changePasswordAndLogin = new ChangePasswordRequest();
      changePasswordAndLogin.setOldPassword(password2);
      changePasswordAndLogin.setToken(loginWith2.getTokenInformation().getToken());
      changePasswordAndLogin.setNewPassword(password);
      changePasswordAndLogin.setConfirmNewPassword(password);
      ChangePasswordWithLoginResponse changePasswordAndLoginResp = loginUserServiceClient
               .changePasswordAndLogin(changePasswordAndLogin);
      Assert.assertNotNull(changePasswordAndLoginResp);
      Assert.assertTrue(changePasswordAndLoginResp.isSuccess());

      GetUserResponse userByToken2 = null;
      try {
         getUserbyTokenRequest.setToken(loginWith2.getTokenInformation().getToken());
         userByToken2 = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         System.out.println();
         Assert.assertNull(userByToken2);
      }

      LoginUserResponse loginWithToken = null;
      try {
         loginWithToken = loginWithToken(loginWith3.getTokenInformation().getGlobalToken());
         Assert.fail("token should have expired");
      } catch (ServiceException ex) {
         Assert.assertTrue(ex.getErrMsg(), true);
      }
      try {
         getUserbyTokenRequest.setToken(loginWith3.getTokenInformation().getToken());
         userByToken2 = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         System.out.println();
         Assert.assertNull(userByToken2);
      }

      try {
         getUserbyTokenRequest
                  .setToken(changePasswordAndLoginResp.getTokenInformation().getToken());
         userByToken2 = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.assertNotNull(userByToken2);
      } catch (ServiceException ex) {
         Assert.fail("Should throw exception.");
      }

   }

   @Test
   public void testResetPasswordAndLogin() throws Exception {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      Assert.assertNotNull(createUserSD);
      LoginUserResponse loginWith = loginWith(email, password);
      Assert.assertNotNull(loginWith);

      String newPassword = "password1";

      ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
      forgotPasswordRequest.setEmailId(email);
      ForgotPasswordResponse forgotPasswordWithOTP = loginUserServiceClient
               .forgotPasswordWithOTP(forgotPasswordRequest);
      Assert.assertNotNull(forgotPasswordWithOTP);

      String otp = DBConnector.getOtp(forgotPasswordWithOTP.getOtpId());

      ForgotPasswordVerifyRequest forgotPasswordVerifyRequest = new ForgotPasswordVerifyRequest();
      forgotPasswordVerifyRequest.setOtpId(forgotPasswordWithOTP.getOtpId());
      forgotPasswordVerifyRequest.setOtp(otp);
      forgotPasswordVerifyRequest.setUserId(forgotPasswordWithOTP.getUserId());
      forgotPasswordVerifyRequest.setNewPassword(newPassword);
      forgotPasswordVerifyRequest.setConfirmPassword(newPassword);
      ResetPasswordResponse resetPasswordWithOTP = loginUserServiceClient.resetPasswordWithOTP(forgotPasswordVerifyRequest);
      Assert.assertNotNull(resetPasswordWithOTP);

      GetUserByTokenRequest getUserbyTokenRequest = new GetUserByTokenRequest();
      getUserbyTokenRequest.setToken(loginWith.getTokenInformation().getToken());
      GetUserResponse userByToken = null;
      try {
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         Assert.assertNull(userByToken);
      }
      getUserbyTokenRequest.setToken(createUserSD.getTokenInformationDTO().getToken());
      try {
         userByToken = null;
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         Assert.assertNull(userByToken);
      }
      // ========================================================================

      LoginUserResponse loginWith2 = loginWith(email, newPassword);

      ForgotPasswordRequest forgotPasswordRequest2 = new ForgotPasswordRequest();
      forgotPasswordRequest2.setEmailId(email);
      ForgotPasswordResponse forgotPasswordWithOTP2 = loginUserServiceClient
               .forgotPasswordWithOTP(forgotPasswordRequest2);
      Assert.assertNotNull(forgotPasswordWithOTP2);

      String otp2 = DBConnector.getOtp(forgotPasswordWithOTP2.getOtpId());

      ForgotPasswordVerifyRequest forgotPasswordVerifyRequest2 = new ForgotPasswordVerifyRequest();
      forgotPasswordVerifyRequest2.setOtpId(forgotPasswordWithOTP2.getOtpId());
      forgotPasswordVerifyRequest2.setOtp(otp2);
      forgotPasswordVerifyRequest2.setUserId(forgotPasswordWithOTP2.getUserId());
      String newPassword2 = password;
      forgotPasswordVerifyRequest2.setNewPassword(newPassword2);
      forgotPasswordVerifyRequest2.setConfirmPassword(newPassword2);

      ResetPasswordWithLoginResponse resetPasswordWithOTPAndLogin = loginUserServiceClient
               .resetPasswordWithOTPAndLogin(forgotPasswordVerifyRequest2);

      Assert.assertNotNull(resetPasswordWithOTPAndLogin);
      Assert.assertNotNull(resetPasswordWithOTPAndLogin.getTokenInformation());

      getUserbyTokenRequest.setToken(resetPasswordWithOTPAndLogin.getTokenInformation().getToken());
      try {
         userByToken = null;
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.assertNotNull(userByToken);
      } catch (ServiceException ex) {
         Assert.fail(ex.getMessage());
      }

      getUserbyTokenRequest.setToken(loginWith2.getTokenInformation().getToken());
      try {
         userByToken = null;
         userByToken = userServiceClient.getUserByToken(getUserbyTokenRequest);
         Assert.fail("Should throw exception.");
      } catch (ServiceException ex) {
         Assert.assertNull(userByToken);
      }

   }

   private LoginUserResponse loginWithToken(String globalToken) throws HttpTransportException,
            ServiceException {

      LoginWithTokenRequest request = new LoginWithTokenRequest();
      request.setGlobalToken(globalToken);
      LoginUserResponse loginUserWithToken = null;
         loginUserWithToken = loginUserServiceClient.loginUserWithToken(request);

      return loginUserWithToken;
   }

   @Test
   public void testFCUserSDSocialMigrate() throws Exception {
      setupFC();

      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber1 = "8095794092";
      // create user
      CreateUserResponse createUserFC = createUserFC(email, password, mobileNumber1);
      setupSD();
      CreateSocialUserWithMobileResponse createSocialUser = createSocialUser(email, mobileNumber1);

      setupFC();

      LoginUserResponse loginUserResponse = loginWith(email, password);

      if (loginUserResponse != null) {
         GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
         generateOTPRequest.setEmailId(email);
         generateOTPRequest.setMobileNumber(mobileNumber1);

         generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
         generateOTPRequest.setToken(loginUserResponse.getTokenInformation().getToken());
         GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
         if (otpResponse != null) {
            UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
            userUpgradeRequest.setEmailId(email);
            userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

            userUpgradeRequest.setOtpId(otpResponse.getOtpId());
            userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
            userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
            userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
            userUpgradeRequest.setToken(loginUserResponse.getTokenInformation().getToken());
            String otp = DBConnector.getOtp(otpResponse.getOtpId());
            if (otp != null) {
               userUpgradeRequest.setOtp(otp);
            }
            UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                     .upgradeUser(userUpgradeRequest);
            if (upgradeResponse != null) {
               Assert.assertEquals(true, upgradeResponse.isSuccess());
            }
         }
      }

   }

   private CreateSocialUserWithMobileResponse createSocialUser(String email, String mobileNumber)
            throws HttpTransportException,
            ServiceException {

      CreateSocialUserWithMobileRequest request = new CreateSocialUserWithMobileRequest();
      request.getSocialUserDto().setEmailId(email);
      request.getSocialUserDto().setSocialSrc("facebook");
      request.getSocialUserDto().setSocialId("4567886878");
      request.getSocialUserDto().setFirstName("John");
      request.getSocialUserDto().setMiddleName("Kumar");
      request.getSocialUserDto().setLastName("Cena");
      request.getSocialUserDto().setAboutMe("hi");
      request.getSocialUserDto().setDisplayName("haowen");
      request.getSocialUserDto().setAboutMe("Wrestler");
      request.getSocialUserDto().setPhotoURL("");
      request.getSocialUserDto().setMobileNumber(mobileNumber);
      request.getSocialUserDto().setDob("1990-12-05");
      request.getSocialUserDto().setGender(Gender.MALE);
      request.getSocialUserDto().setLanguagePref(Language.ENGLISH);
      request.setUserMachineIdentifier("jndf");
      request.setUserAgent("abc");
      CreateSocialUserWithMobileResponse response = userServiceClient
               .createSocialUserWithMobile(request);
      return response;
   }

   @Test
   public void testSDFCUserExistSDMigratedTest() throws Exception {
      // Create fc user
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber1 = "8095794092";

      setupFC();
      CreateUserResponse createUserFC = createUserFC(email, password, mobileNumber1);
      // create sd user
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password);
      // update mobile number.
      if (null != createUserSD) {
         GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
         generateOTPRequest.setEmailId(email);
         generateOTPRequest.setMobileNumber(mobileNumber1);
         generateOTPRequest.setPurpose(OTPPurpose.UPDATE_MOBILE);
         generateOTPRequest.setToken(createUserSD.getTokenInformationDTO().getToken());
         GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         UpdateMobileNumberRequest updateMobileNumberRequest = new UpdateMobileNumberRequest();
         updateMobileNumberRequest.setMobileNumber(mobileNumber1);
         updateMobileNumberRequest.setOTP(otp);
         updateMobileNumberRequest.setOtpId(otpResponse.getOtpId());
         updateMobileNumberRequest.setToken(createUserSD.getTokenInformationDTO().getToken());
         /*UpdateMobileNumberResponse updateMobileNumber = userServiceClient
                  .updateMobileNumber(updateMobileNumberRequest);
         Assert.assertNotNull(updateMobileNumber);
         Assert.assertNotNull(updateMobileNumber.getUserDetails());*/
      }

      // login sd
      LoginUserResponse loginUserResponse = loginWith(email, password);
      String mobileNumber2 = "9597679247";

      if (loginUserResponse != null) {
         GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
         generateOTPRequest.setEmailId(email);
         generateOTPRequest.setMobileNumber(mobileNumber2);
         generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
         generateOTPRequest.setToken(loginUserResponse.getTokenInformation().getToken());
         GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
         if (otpResponse != null) {
            UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
            userUpgradeRequest.setEmailId(email);
            userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

            userUpgradeRequest.setOtpId(otpResponse.getOtpId());
            userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
            userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
            userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
            userUpgradeRequest.setToken(loginUserResponse.getTokenInformation().getToken());
            String otp = DBConnector.getOtp(otpResponse.getOtpId());
            if (otp != null) {
               userUpgradeRequest.setOtp(otp);
            }
            UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                     .upgradeUser(userUpgradeRequest);
            Assert.assertNotNull(upgradeResponse);
            Assert.assertTrue(upgradeResponse.isSuccess());
         }
         GetUserByTokenRequest getUserByTokenRequest = new GetUserByTokenRequest();
         // get user details
         getUserByTokenRequest.setToken(loginUserResponse.getTokenInformation().getToken());
         GetUserResponse userByToken = userServiceClient.getUserByToken(getUserByTokenRequest);
         Assert.assertNotNull(userByToken);
         Assert.assertNotNull(userByToken.getUserDetails());
         Assert.assertNotNull(userByToken.getUserDetails().getGender());
         Assert.assertEquals(mobileNumber2, userByToken.getUserDetails().getMobileNumber());
      }
   }

   @Test
   public void createUserWithEmailAndMobileTest() throws ServiceException, Exception {
      CreateUserEmailRequest request = new CreateUserEmailRequest();
      request.getUserDetailsByEmailDto().setEmailId("johnson" + number + "@gmail.com");
      request.getUserDetailsByEmailDto().setPassword("password");
      request.getUserDetailsByEmailDto().setFirstName("John");
      request.getUserDetailsByEmailDto().setMiddleName("Kumar");
      request.getUserDetailsByEmailDto().setLastName("Cena");
      request.getUserDetailsByEmailDto().setDisplayName("Johnson");
      request.getUserDetailsByEmailDto().setGender(Gender.MALE);
      request.getUserDetailsByEmailDto().setDob("1990-12-05");
      request.getUserDetailsByEmailDto().setLanguagePref(Language.ENGLISH);
      request.setUserMachineIdentifier("jndf");
      request.setUserAgent("abc");
      CreateUserResponse response = userServiceClient.createUserWithEmail(request);
      if (response != null) {
         LoginWithTokenRequest loginWithTokenRequest = new LoginWithTokenRequest();
         loginWithTokenRequest.setGlobalToken(response.getTokenInformationDTO().getGlobalToken());
         LoginUserResponse loginUserResponse = loginUserServiceClient
                  .loginUserWithToken(loginWithTokenRequest);
         if (loginUserResponse != null) {
            GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
            generateOTPRequest.setEmailId(request.getUserDetailsByEmailDto().getEmailId());
            generateOTPRequest.setMobileNumber("8123779148");
            generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
            generateOTPRequest.setToken(loginUserResponse.getTokenInformation().getToken());
            GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
            if (otpResponse != null) {
               UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
               userUpgradeRequest.setEmailId(request.getUserDetailsByEmailDto().getEmailId());
               userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

               userUpgradeRequest.setOtpId(otpResponse.getOtpId());
               userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
               userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
               userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
               userUpgradeRequest.setToken(loginUserResponse.getTokenInformation().getToken());
               String otp = DBConnector.getOtp(otpResponse.getOtpId());
               if (otp != null) {
                  userUpgradeRequest.setOtp(otp);
               }
               UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                        .upgradeUser(userUpgradeRequest);
               if (upgradeResponse != null) {
                  Assert.assertEquals(true, upgradeResponse.isSuccess());
               }
            }
         }
      } else {
         Assert.assertFalse(false);
      }

   }

   private List<String> getUserEmailList(int SIZE) {
      List<String> emailList = new ArrayList<String>();
      String email;
      for (int i = 1; i <= SIZE; i++) {
         number = r.nextInt(100000);
         email = "snapsdfcuser" + number + "@snapdeal.com";
         emailList.add(email);
      }
      return emailList;
   }

   @Test
   public void testFCUserExistFCMigrateTest() throws Exception {

      int SIZE = 1;
      String passwordfc = "passowrdfc";
      String mobileNumber = "6000100000";

      List<String> emailList = getUserEmailList(SIZE);
      setupStaging2FC();
      for (String email : emailList) {
         long mob = (Long.parseLong(mobileNumber)) + 1;
         mobileNumber = String.valueOf(mob);

         CreateUserResponse createUserFC = createUserFC(email, passwordfc, mobileNumber);
         LoginUserResponse loginUserResponse = loginWith(email, passwordfc);
         if (loginUserResponse != null) {
            GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
            generateOTPRequest.setEmailId(email);
            generateOTPRequest.setMobileNumber(mobileNumber);
            generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
            generateOTPRequest.setToken(loginUserResponse.getTokenInformation().getToken());
            GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
            if (otpResponse != null) {
               UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
               userUpgradeRequest.setEmailId(email);
               userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

               userUpgradeRequest.setOtpId(otpResponse.getOtpId());
               userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
               userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
               userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
               userUpgradeRequest.setToken(loginUserResponse.getTokenInformation().getToken());
               String otp = DBConnector.getOtp(otpResponse.getOtpId());
               if (otp != null) {
                  userUpgradeRequest.setOtp(otp);
               }
               UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                        .upgradeUser(userUpgradeRequest);
               if (upgradeResponse != null) {
                  LoginUserResponse loginUserResponse2 = loginWith(email, passwordfc);
                  if (loginUserResponse2 != null) {
                     Assert.assertTrue(true);
                  }
               }
            }
         }
      }
   }

   @Test
   public void testSDFCUserExistFCMigrateTest() throws Exception {

      setupStaging2SD();

      int SIZE = 10;
      String passwordsd = "passwordsd";
      String passwordfc = "passowrdfc";
      String mobileNumber = "6000001000";

      List<String> emailList = getUserEmailList(SIZE);
      Map<String, Boolean> mapOfFcMigratedUser = new HashMap<String, Boolean>();
      for (String email : emailList) {
         CreateUserResponse createUserSD = createUserSD(email, passwordsd);
      }

      setupStaging2FC();
      int count = 0;
      for (String email : emailList) {
         count++;
         long mob = (Long.parseLong(mobileNumber)) + 1;
         mobileNumber = String.valueOf(mob);

         CreateUserResponse createUserFC = createUserFC(email, passwordfc, mobileNumber);
         if (count % 2 == 0) {
            LoginUserResponse loginUserResponse = loginWith(email, passwordfc);
            if (loginUserResponse != null) {
               GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
               generateOTPRequest.setEmailId(email);
               generateOTPRequest.setMobileNumber(mobileNumber);
               generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
               generateOTPRequest.setToken(loginUserResponse.getTokenInformation().getToken());
               GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
               if (otpResponse != null) {
                  UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
                  userUpgradeRequest.setEmailId(email);
                  userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

                  userUpgradeRequest.setOtpId(otpResponse.getOtpId());
                  userUpgradeRequest
                           .setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
                  userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
                  userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
                  userUpgradeRequest.setToken(loginUserResponse.getTokenInformation().getToken());
                  String otp = DBConnector.getOtp(otpResponse.getOtpId());
                  if (otp != null) {
                     userUpgradeRequest.setOtp(otp);
                  }
                  UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                           .upgradeUser(userUpgradeRequest);
                  mapOfFcMigratedUser.put(email, true);
               } else {
                  mapOfFcMigratedUser.put(email, false);
               }
            } else {
               mapOfFcMigratedUser.put(email, false);
            }
         }
      }
      System.out.println("Email generated - ");
      for (String email : emailList) {
         System.out.println(email);
      }

      System.out.println("Email migrated from FC - ");
      for (Map.Entry<String, Boolean> map : mapOfFcMigratedUser.entrySet()) {
         if (map.getValue()) {
            System.out.println(map.getKey());
         }

      }
   }

   @Test
   public void testFCUserFetchUpgradeFromSD() {
      String email = "johnson" + number + "@snapdeal.com";
      String password = "password";
      String mobileNumber = "7000000001";
      try {

         setupFC();
         // create user
         CreateUserResponse createUser = createUserFC(email, password, mobileNumber);

         // login user
         setupSD();
         // LoginUserResponse loginWith = loginWith(email, password);
         UserUpgradeByEmailRequest request = new UserUpgradeByEmailRequest();
         // request.setToken(loginWith.getTokenInformation().getToken());
         request.setUserAgent("userAgent");
         request.setUserMachineIdentifier("userMachineIdentifier");
         request.setEmailId(email);
         UserUpgradationResponse userUpgradeStatus = userMigrationServiceClient
                  .userUpgradeStatus(request);
         Assert.assertNotNull(userUpgradeStatus);
         Assert.assertNotNull(userUpgradeStatus.getUpgradationInformation());
         Assert.assertEquals(userUpgradeStatus.getUpgradationInformation().getUpgrade(),
                  Upgrade.UPGRADE_RECOMMENDED);

      } catch (ServiceException e) {
         Assert.fail(e.getErrMsg());
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   private CreateUserResponse createUserFC(String requestEmailId, String password,
            String phoneNumber) throws HttpTransportException, ServiceException {
      CreateUserEmailMobileRequest request = new CreateUserEmailMobileRequest();
      UserRequestDto userRequestDto = new UserRequestDto();
      userRequestDto.setEmailId(requestEmailId);
      userRequestDto.setPassword(password);
      userRequestDto.setMobileNumber(phoneNumber);
      userRequestDto.setFirstName("John");
      userRequestDto.setMiddleName("Kumar");
      userRequestDto.setLastName("Cena");
      userRequestDto.setDisplayName("Johnson");
      userRequestDto.setGender(Gender.MALE);
      userRequestDto.setDob("1990-12-05");
      userRequestDto.setLanguagePref(Language.ENGLISH);
      request.setUserMachineIdentifier("jndf");
      request.setUserAgent("abc");
      request.setUserRequestDto(userRequestDto);
      CreateUserResponse createUser = userServiceClient.createUserWithEmailAndMobile(request);

		return createUser;
	}
	
	private CreateSocialUserWithMobileResponse createUserOC(String requestEmailId,
			String password, String phoneNumber) throws HttpTransportException,
			ServiceException {
		CreateSocialUserWithMobileRequest request = new CreateSocialUserWithMobileRequest();
		SocialUserRequestDto userRequestDto = new SocialUserRequestDto();
		userRequestDto.setEmailId(requestEmailId);
		userRequestDto.setMobileNumber(phoneNumber);
		userRequestDto.setFirstName("John");
		userRequestDto.setMiddleName("Kumar");
		userRequestDto.setLastName("Cena");
		userRequestDto.setDisplayName("Johnson");
		userRequestDto.setGender(Gender.MALE);
		userRequestDto.setDob("1990-12-05");
		userRequestDto.setLanguagePref(Language.ENGLISH);
		userRequestDto.setSocialSrc(SocialSource.FACEBOOK.toString());
		userRequestDto.setSocialId("3456789098765");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		request.setSocialUserDto(userRequestDto);
		CreateSocialUserWithMobileResponse createUser = userServiceClient.createSocialUserWithMobile(request);
		return createUser;
	}


	private CreateUserResponse createUserSD(String requestEmailId,
			String password) throws HttpTransportException, ServiceException {
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		request.getUserDetailsByEmailDto().setEmailId(requestEmailId);
		request.getUserDetailsByEmailDto().setPassword(password);
		// request.getUserDetailsByEmailDto().setFirstName("John");
		request.getUserDetailsByEmailDto().setMiddleName("Kumar");
		request.getUserDetailsByEmailDto().setLastName("Cena");
		request.getUserDetailsByEmailDto().setDisplayName("Johnson");
		request.getUserDetailsByEmailDto().setGender(Gender.MALE);
		request.getUserDetailsByEmailDto().setDob("1990-12-05");
		request.getUserDetailsByEmailDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		return userServiceClient.createUserWithEmail(request);
	}
	

	@Test
   public void testLogin() throws Exception {
      setupStaging2SD();
      String email = "snapsdfcuser16351@snapdeal.com";
      String password = "passwordsd";
      LoginUserResponse response = loginWith(email, password);
      if (response != null) {
         Assert.assertTrue(true);
      } else {
         Assert.assertTrue(false);
      }

   }

   @Test
   public void testUserVerifiedMobileExists() throws Exception {
      setupStaging2SD();
      IsVerifiedMobileExistRequest request = new IsVerifiedMobileExistRequest();
      request.setMobileNumber("8765432109");
      request.setUserAgent("abc");
      request.setUserMachineIdentifier("def");
      IsVerifiedMobileExistResponse response = userServiceClient.isVerifiedMobileExist(request);
      if (response != null) {
         Assert.assertTrue(true);
      } else {
         Assert.assertTrue(false);
      }

   }

   @Test
   public void testloginForFc() throws Exception {
      setupStaging2FC();
      String email = "snapsdfcuser79929@snapdeal.com";
      String password = "passowrdfc";
      LoginUserResponse response = loginWith(email, password);
      if (response != null) {
         Assert.assertTrue(true);
      }
   }




	private LoginUserResponse loginWith(String email, String password)
			throws HttpTransportException, ServiceException {
		LoginUserRequest loginUserRequest = new LoginUserRequest();
		if (email != null) {
			loginUserRequest.setEmailId(email);
		}
		if (password != null) {
			loginUserRequest.setPassword(password);
		}
		loginUserRequest.setUserAgent("ABC");
		loginUserRequest.setUserMachineIdentifier("DEF");

		return loginUserServiceClient.loginUserWithPassword(loginUserRequest);
	}

	private void linkAccountFromSDTest(String email, String password)
			throws ServiceException, Exception {

		LoginUserResponse loginUserResponse = loginWith(email, password);
		if (loginUserResponse != null
				&& loginUserResponse.getUpgradationInformation() != null) {
			if (loginUserResponse.getUpgradationInformation().getState() == State.FC_ACCOUNT_MIGRATED) {
				GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
				generateOTPRequest.setEmailId(email);
				generateOTPRequest.setMobileNumber("9599679148");
				generateOTPRequest.setPurpose(OTPPurpose.LINK_ACCOUNT);
				generateOTPRequest.setToken(loginUserResponse
						.getTokenInformation().getToken());
				GenerateOTPResponse otpResponse = otpServiceClient
						.sendOTP(generateOTPRequest);
				if (otpResponse != null) {
					VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();
					verifyUserUpgradeRequest
							.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_MOBILE_OTP);
					verifyUserUpgradeRequest
							.setTargetSrcToBeValidated(Merchant.SNAPDEAL);
					verifyUserUpgradeRequest.setEmailId(email);
					verifyUserUpgradeRequest.setToken(loginUserResponse
							.getTokenInformation().getToken());

					verifyUserUpgradeRequest.setOtpId(otpResponse.getOtpId());
					String otp = DBConnector.getOtp(otpResponse.getOtpId());
					if (otp != null) {
						verifyUserUpgradeRequest.setOtp(otp);
					}
					VerifyUpgradeUserResponse upgradeResponse = userMigrationServiceClient
							.verifyUpgradeUser(verifyUserUpgradeRequest);
					if (upgradeResponse != null) {

					}
				}
			}

		}
		Assert.assertFalse(false);
	}

	@Test
	public void linkAccountFromSDUsingOCPassword() throws ServiceException,
			Exception {
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		String requestEmailId = "johnson" + number + "@snapdeal.com";
		request.getUserDetailsByEmailDto().setEmailId(requestEmailId);
		request.getUserDetailsByEmailDto().setPassword("password");
		request.getUserDetailsByEmailDto().setFirstName("John");
		request.getUserDetailsByEmailDto().setMiddleName("Kumar");
		request.getUserDetailsByEmailDto().setLastName("Cena");
		request.getUserDetailsByEmailDto().setDisplayName("Johnson");
		request.getUserDetailsByEmailDto().setGender(Gender.MALE);
		request.getUserDetailsByEmailDto().setDob("1990-12-05");
		request.getUserDetailsByEmailDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		CreateUserResponse response = userServiceClient
				.createUserWithEmail(request);
		if (response != null) {
			LoginWithTokenRequest loginWithTokenRequest = new LoginWithTokenRequest();
			loginWithTokenRequest.setGlobalToken(response
					.getTokenInformationDTO().getGlobalToken());
			LoginUserResponse loginUserResponse = loginUserServiceClient
					.loginUserWithToken(loginWithTokenRequest);
			if (loginUserResponse != null) {
				if (loginUserResponse != null) {
					GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
					generateOTPRequest.setEmailId(request
							.getUserDetailsByEmailDto().getEmailId());
					generateOTPRequest.setMobileNumber("9599679148");
					generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
					generateOTPRequest.setToken(loginUserResponse
							.getTokenInformation().getToken());
					GenerateOTPResponse otpResponse = otpServiceClient
							.sendOTP(generateOTPRequest);
					if (otpResponse != null) {
						UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
						userUpgradeRequest.setEmailId(request
								.getUserDetailsByEmailDto().getEmailId());
						userUpgradeRequest.setMobileNumber(generateOTPRequest
								.getMobileNumber());

						userUpgradeRequest.setOtpId(otpResponse.getOtpId());
						userUpgradeRequest
								.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
						userUpgradeRequest
								.setUpgradeChannel(UpgradeChannel.MOBILE_WAP);
						userUpgradeRequest
								.setUpgradeSource(UpgradeSource.SIGN_IN);
						userUpgradeRequest.setToken(loginUserResponse
								.getTokenInformation().getToken());
						String otp = DBConnector.getOtp(otpResponse.getOtpId());
						if (otp != null) {
							userUpgradeRequest.setOtp(otp);
						}
						UpgradeUserResponse upgradeResponse = userMigrationServiceClient
								.upgradeUser(userUpgradeRequest);
						if (upgradeResponse != null) {
							Assert.assertEquals(true,
									upgradeResponse.isSuccess());
						}

						DBConnector.changeUpgradeStatus(requestEmailId,
								Upgrade.LINK_SD_ACCOUNT.toString(),
								State.FC_ACCOUNT_MIGRATED.toString());
						linkAccountFromSDTest(requestEmailId, "password");

					}
				}
			}
		} else {
			Assert.assertFalse(false);
		}
	}
	
   private boolean createUserWithCriteria(UserCreateCriteria criteria, String email,
            String sdPassword, String fcPassword, String mobile) {

      try {
         LoginUserResponse response = null;
         
         switch (criteria) {

             case SD_USER_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               break;
            case FC_USER_ONLY:
               setupFC();
               createUserFC(email, fcPassword, mobile);
               break;
            case SD_FC_USER_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               break;
            case SD_MIGRATED_FROM_SD_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               response = loginWith(email, sdPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case FC_MIGRATED_FROM_FC_ONLY:
               setupFC();
               createUserFC(email, fcPassword, mobile);
               response = loginWith(email, fcPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case SD_FC_USER_MIGRATED_FROM_SD_ONLY:   
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               setupSD();
               response = loginWith(email, sdPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case SD_FC_USER_MIGRATED_FROM_FC_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               setupFC();
               response = loginWith(email, fcPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
         }
      } catch (Exception e) {

         return false;
      }
      return true;
	}
	
   @Test
   public void testCreateCustomUser(){
      createUserWithCriteria(UserCreateCriteria.FC_MIGRATED_FROM_FC_ONLY, "stone.cold24@snapdeal.com",
               "password", "password1", "9056120003");
   }
   
   private boolean upgradeUser(String email, String token,String mobile) throws HttpTransportException,
            ServiceException {

      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobile);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(token);
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

         userUpgradeRequest.setOtpId(otpResponse.getOtpId());
         userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
         userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
         userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
         userUpgradeRequest.setToken(token);
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         if (otp != null) {
            userUpgradeRequest.setOtp(otp);
         }
         UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                  .upgradeUser(userUpgradeRequest);

         return upgradeResponse.isSuccess();

      }
      return false;
   }
   
/*   private boolean upgradeUser(String email, String token,String mobile) throws HttpTransportException,
            ServiceException {

      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobile);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(token);
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

   private boolean createUserWithCriteria(UserCreateCriteria criteria, String email,
            String sdPassword, String fcPassword, String mobile) {

      try {
         LoginUserResponse response = null;
         
         switch (criteria) {

             case SD_USER_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               break;
            case FC_USER_ONLY:
               setupFC();
               createUserFC(email, fcPassword, mobile);
               break;
            case SD_FC_USER_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               break;
            case SD_MIGRATED_FROM_SD_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               response = loginWith(email, sdPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case FC_MIGRATED_FROM_FC_ONLY:
               setupFC();
               createUserFC(email, fcPassword, mobile);
               response = loginWith(email, fcPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case SD_FC_USER_MIGRATED_FROM_SD_ONLY:   
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               setupSD();
               response = loginWith(email, sdPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
            case SD_FC_USER_MIGRATED_FROM_FC_ONLY:
               setupSD();
               createUserSD(email, sdPassword);
               setupFC();
               createUserFC(email, fcPassword, mobile);
               setupFC();
               response = loginWith(email, fcPassword);
               return upgradeUser(email, response.getTokenInformation().getToken(),mobile);
         }
      } catch (Exception e) {

         return false;
      }
      return true;
   }
   
   @Test
   public void testCreateCustomUser(){
      
      
      createUserWithCriteria(UserCreateCriteria.FC_MIGRATED_FROM_FC_ONLY, "stone.cold24@snapdeal.com",
               "password", "password1", "9056120003");
      
      
   }
   
   private boolean upgradeUser(String email, String token,String mobile) throws HttpTransportException,
            ServiceException {

      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobile);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(token);
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

         userUpgradeRequest.setOtpId(otpResponse.getOtpId());
         userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
         userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
         userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
         userUpgradeRequest.setToken(token);
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         if (otp != null) {
            userUpgradeRequest.setOtp(otp);
         }
         UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                  .upgradeUser(userUpgradeRequest);

         return upgradeResponse.isSuccess();

      }
      return false;
   }
   
/*   private boolean upgradeUser(String email, String token,String mobile) throws HttpTransportException,
            ServiceException {

      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setMobileNumber(mobile);
      generateOTPRequest.setPurpose(OTPPurpose.UPGRADE_USER);
      generateOTPRequest.setToken(token);
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      if (otpResponse != null) {
         UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
         userUpgradeRequest.setEmailId(email);
         userUpgradeRequest.setMobileNumber(generateOTPRequest.getMobileNumber());

         userUpgradeRequest.setOtpId(otpResponse.getOtpId());
         userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
         userUpgradeRequest.setUpgradeChannel(UpgradeChannel.WEB);
         userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_IN);
         userUpgradeRequest.setToken(token);
         String otp = DBConnector.getOtp(otpResponse.getOtpId());
         if (otp != null) {
            userUpgradeRequest.setOtp(otp);
         }
         UpgradeUserResponse upgradeResponse = userMigrationServiceClient
                  .upgradeUser(userUpgradeRequest);

         return upgradeResponse.isSuccess();
   }*/

   @Test
   public void testE1P1AndSocialUpgradeUsingE1P1LoginViaSocial() throws Exception {
      // create user in fc
      // create social user in sd
      // upgrade via fc
      // login via sd
      String requestEmailId = "avrillavigne" + number + "@snapdeal.com";
      String password = "password";
      String mobile = "6100000001";

      setupFC();
      CreateUserResponse createUserFC = createUserFC(requestEmailId, password, mobile);
      setupSD();
      SocialUserResponse createSocialUserSD = createSocialUserSD(requestEmailId, mobile);
      Assert.assertNotNull(createSocialUserSD);
      setupFC();
      boolean upgradeUser = upgradeUser(requestEmailId, createUserFC.getTokenInformationDTO().getToken(), mobile);
      Assert.assertTrue("Upgrade failed", upgradeUser);
      setupSD();
      // Again login via social.
      SocialUserResponse createSocialUserSD2 = createSocialUserSD(requestEmailId, mobile);
      Assert.assertNotNull(createSocialUserSD2);
   }

   /**
    * SNAPDEALTECH-57812
    * 
    * @throws Exception
    */
   @Test
   public void testE1P1E1P2MigrateE1P1LinkE1P1_OCPassword() throws Exception {
      String email = "avrillavigne" + number + "@snapdeal.com";
      String password1 = "password1";
      String mobile = "6230000005";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password1);
      Assert.assertNotNull(createUserSD);
      setupFC();
      String password2 = "password2";
      CreateUserResponse createUserFC = createUserFC(email, password2, mobile);
      Assert.assertNotNull(createUserFC);
      
      boolean upgradeUser = upgradeUser(email, createUserFC.getTokenInformationDTO().getToken(), mobile);
      Assert.assertTrue(upgradeUser);

      setupSD();
      LoginUserResponse loginUserResponse = loginWith(email, password2);

      boolean linkAccount = linkAccount(email, password2, loginUserResponse.getTokenInformation()
               .getToken());
      Assert.assertTrue(linkAccount);
      GetUserByTokenRequest tokReq = new GetUserByTokenRequest();
      tokReq.setToken(loginUserResponse.getTokenInformation().getToken());
      try {

         GetUserResponse userByToken = userServiceClient.getUserByToken(tokReq);
         Assert.assertNotNull(userByToken);
      } catch (ServiceException ex) {
         Assert.fail(ex.getErrCode());
      }
   }

   private boolean linkAccount(String email, String password, String token)
            throws HttpTransportException,
 ServiceException {
      GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      generateOTPRequest.setEmailId(email);
      generateOTPRequest.setPurpose(OTPPurpose.LINK_ACCOUNT);
      generateOTPRequest.setToken(token);
      GenerateOTPResponse otpResponse = otpServiceClient.sendOTP(generateOTPRequest);
      Assert.assertNotNull(otpResponse);
      VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();
      verifyUserUpgradeRequest.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_MOBILE_OTP);
      verifyUserUpgradeRequest.setTargetSrcToBeValidated(Merchant.SNAPDEAL);
      verifyUserUpgradeRequest.setEmailId(email);
      verifyUserUpgradeRequest.setToken(token);

      verifyUserUpgradeRequest.setOtpId(otpResponse.getOtpId());
      String otp = DBConnector.getOtp(otpResponse.getOtpId());
      if (otp != null) {
         verifyUserUpgradeRequest.setOtp(otp);
      }
      VerifyUpgradeUserResponse upgradeResponse = userMigrationServiceClient
               .verifyUpgradeUser(verifyUserUpgradeRequest);
      Assert.assertNotNull(upgradeResponse);
      return upgradeResponse.isSuccess();
   }

   @Test
   public void testE1P1Upgrade_resetPasswordOtherSide() throws Exception {
      String email = "avrillavigne" + number + "@snapdeal.com";
      String password1 = "password1";
      String mobile = "6230000006";
      setupSD();
      CreateUserResponse createUserSD = createUserSD(email, password1);
      Assert.assertNotNull(createUserSD);
      setupFC();
      String password2 = "password2";
      CreateUserResponse createUserFC = createUserFC(email, password2, mobile);
      Assert.assertNotNull(createUserFC);

      boolean upgradeUser = upgradeUser(email, createUserFC.getTokenInformationDTO().getToken(),
               mobile);
      Assert.assertTrue(upgradeUser);

      // ==============
      setupSD();
      ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
      forgotPasswordRequest.setEmailId(email);
      ForgotPasswordResponse forgotPasswordWithOTP = loginUserServiceClient
               .forgotPasswordWithOTP(forgotPasswordRequest);
      Assert.assertNotNull(forgotPasswordWithOTP);
      String otp = DBConnector.getOtp(forgotPasswordWithOTP.getOtpId());
      Assert.assertNotNull(otp);
      ForgotPasswordVerifyRequest forgotPasswordVerifyRequest = new ForgotPasswordVerifyRequest();
      forgotPasswordVerifyRequest.setOtpId(forgotPasswordWithOTP.getOtpId());
      forgotPasswordVerifyRequest.setOtp(otp);
      forgotPasswordVerifyRequest.setNewPassword("password1");
      forgotPasswordVerifyRequest.setConfirmPassword("password1");
      forgotPasswordVerifyRequest.setUserId(forgotPasswordWithOTP.getUserId());
      ResetPasswordWithLoginResponse resetPasswordWithOTP = loginUserServiceClient
               .resetPasswordWithOTPAndLogin(forgotPasswordVerifyRequest);

      Assert.assertNotNull(resetPasswordWithOTP);
      Assert.assertTrue(resetPasswordWithOTP.isSuccess());
      Assert.assertNotNull(resetPasswordWithOTP.getTokenInformation());
      // ================

      UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
      userUpgradeByEmailRequest.setToken(resetPasswordWithOTP.getTokenInformation().getToken());
      UserUpgradationResponse userUpgradeStatus = userMigrationServiceClient
               .userUpgradeStatus(userUpgradeByEmailRequest);
      Assert.assertNotNull(userUpgradeStatus);
      Assert.assertNotNull(userUpgradeStatus.getUpgradationInformation());
      Assert.assertEquals(Upgrade.UPGRADE_COMPLETED, userUpgradeStatus.getUpgradationInformation()
               .getUpgrade());
      Assert.assertEquals(State.OC_ACCOUNT_EXISTS, userUpgradeStatus.getUpgradationInformation()
               .getState());
   }

}