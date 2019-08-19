package com.snapdeal.ims.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.snapdeal.ims.client.ILoginUserServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.LoginUserServiceClientImpl;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordVerifyRequest;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.SendForgotPasswordLinkRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.util.DBConnector;
import com.snapdeal.ims.utils.ClientDetails;
	
public class LoginUserServiceClientTest {

	
	ILoginUserServiceClient loginUserServiceClient =new LoginUserServiceClientImpl();
   IUserServiceClient userServiceClient = new UserServiceClientImpl();

	@Before
	public void setup() throws Exception {
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83", 6000000);
	      }

	@Test
	public void loginUserRequestTest() throws ServiceException, Exception {

		LoginUserRequest request = new LoginUserRequest();
		request.setEmailId("t009@gmail.com");
		request.setPassword("password");
		//request.setMobileNumber("9099999999");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		LoginUserResponse response = loginUserServiceClient.loginUserWithPassword(request);
		System.out.println(response);
	}

	@Test
	public void changePasswordrRequestTest() throws ServiceException, Exception {

		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("password1");
		request.setNewPassword("password1");
		request.setOldPassword("password");
		request.setToken("5ItLAGsFE4k1EIaXR66tH15uuMiWn4O6gamDsTn9WQK6tGZMbE57i4AXOZoUZBGAvusqUA7HH3vnPgRYkLzQKOpw3n3pHUODuXPKwqNsVCs");

		ChangePasswordResponse response = loginUserServiceClient
				.changePassword(request);
		System.out.println(response);
	}
	
	@Test
	public void changePasswordrRequestWithLoginTest() throws ServiceException, Exception {

		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setConfirmNewPassword("password");
		request.setNewPassword("password");
		request.setOldPassword("password1");
		request.setToken("prqUzvHdMrQOwnF2udCYt8xrl_MAF8F15nJn48HMLZJVfDIVKaw-8BtE7yMGuBZNLI6dz1kDv-WLiAlSyQrFJg");

		ChangePasswordWithLoginResponse response = loginUserServiceClient
				.changePasswordAndLogin(request);
		System.out.println(response);
		System.out.println(response.getTokenInformation());
	}
	
	@Test
	public void forgotPasswordOtpVerifyRequestTest() throws ServiceException,
			Exception {


		ForgotPasswordVerifyRequest request = new ForgotPasswordVerifyRequest();
		request.setNewPassword("abc1234");
		request.setConfirmPassword("abc1234");
		request.setOtp("1234");
		request.setUserId("VjAxI2E0M2E0NjVlLWJkZjAtNGRmNC1iMzg1LWY2NDM3NGM1MzU4Mg");
		request.setOtpId("91b02aae-877e-4299-b184-76060540647b");

		ResetPasswordResponse response = loginUserServiceClient
				.resetPasswordWithOTP(request);
		System.out.println(response);
	}

	@Test
	public void forgotPasswordOtpVerifyAndLoginRequestTest() throws ServiceException,
			Exception {


		ForgotPasswordVerifyRequest request = new ForgotPasswordVerifyRequest();
		request.setNewPassword("password");
		request.setConfirmPassword("password");
		request.setOtp("5049");
		request.setUserId("9516289");
		request.setOtpId("4060e5ac-719f-4b4a-b9c8-dc47cf1f7edd");


		ResetPasswordWithLoginResponse response = loginUserServiceClient
				.resetPasswordWithOTPAndLogin(request);
		System.out.println(response);
		System.out.println(response.getTokenInformation());
	}

	@Test
	public void forgotPasswordOtpGenerateRequestTest() throws ServiceException,
			Exception {
		ForgotPasswordRequest request = new ForgotPasswordRequest();
		request.setEmailId("imstestrichardhoffman.man.6745828725@yahoo.net");
		request.setMobileNumber("");

		ForgotPasswordResponse response = loginUserServiceClient
				.forgotPasswordWithOTP(request);
		System.out.println(response);
	}
/*
	@Test
	public void mobileVerificationStatusRequestTest() throws ServiceException,
			Exception {

		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId("1212");

		MobileVerificationStatusResponse response = loginUserServiceClient
				.isMobileVerified(request);
		System.out.println(response);
	}
*/
	@Test
	public void loginUserWithTokenRequestTest() throws ServiceException,
			Exception {
		LoginWithTokenRequest request = new LoginWithTokenRequest();
		request.setGlobalToken("tR3-iaZFGVb3CwRdtyRP8XpWXT0FgKBs2yxepoiMIdmSk_HMxPiRimKrFKtNMbb4Ii5gmEfJY98kJZpMLYnAFg");

		LoginUserResponse response = loginUserServiceClient
				.loginUserWithToken(request);
		System.out.println(response);
	}

	@Test
	public void signoutRequestTest() throws ServiceException, Exception {

		SignoutRequest request = new SignoutRequest();
		request.setHardSignout(true);
		request.setToken("jiolx3F0RWE8cncpG3FIgrSHhso7XRPGJ-xZU1hXBgQuIL-v98iCcuDNFBrdx7AdMjOh2RfjtgACU9SZOpOb6GoieqG4oCZzB5IZplTleqQ");
		request.setUserAgent("abc");
		request.setUserMachineIdentifier("jndf");
		SignoutResponse response = loginUserServiceClient.signout(request);
		System.out.println(response);

	}

	@Test
	public void hardSignoutRequestTest() throws ServiceException, Exception {

		SignoutRequest request = new SignoutRequest();
		request.setHardSignout(true);
		request.setToken("jiolx3F0RWE8cncpG3FIgvDx2yFDdqUXhcLJThoIy6tmN1yE4knDPQQhWvnIauiO5pMmPFwSXbyiLa2j_tYoxjxgE_yd14n80a-AHSIEjFl4MJis-nrhwUMPjifjK89E");
		request.setUserAgent("abc");
		request.setUserMachineIdentifier("jndf");
		SignoutResponse response = loginUserServiceClient.signout(request);
		System.out.println(response);

	}

	@Test
	public void testSendForgotPasswordLink() throws ServiceException {
		SendForgotPasswordLinkRequest request = new SendForgotPasswordLinkRequest();
		request.setUserAgent("userAgent");
		request.setUserMachineIdentifier("userMachineIdentifier");
		request.setEmail("himanshu.goel@snapdeal.com");
		SendForgotPasswordLinkResponse sendForgotPasswordLink = loginUserServiceClient.sendForgotPasswordLink(request);
		Assert.assertNotNull(sendForgotPasswordLink);
		Assert.assertTrue(sendForgotPasswordLink.isSuccess());
	}
	
	@Test
	public void testVerifyUserForgotPassword() throws ServiceException {
		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setNewPassword("password123");
		request.setConfirmPassword("password123");
		request.setUserAgent("userAgent");
		request.setUserMachineIdentifier("userMachineIdentifier");
		request.setVerificationCode("M1bHGrJD60QKic1ABcjmMcMe7ncwcyyeBh7RTTZ8pyMEpzCC__oqbyGJvAgElJvL");
		VerifyUserResponse verifyUser = loginUserServiceClient.verifyAndResetPassword(request);
		Assert.assertNotNull(verifyUser);
      Assert.assertEquals(verifyUser.getStatus(), StatusEnum.SUCCESS);
   }
	
	@Test
	public void testGetTransferToken() throws ServiceException {
	   GetTransferTokenRequest request =  new GetTransferTokenRequest();
      request.setToken("cR35L9XNgB_KZK5ZgWBJwpeqlZJKenvW2HXqj7IzuBaOwkolENpNrQJ_2QHKlGDoNIz844_TGOz_-114pAouIQ");
      request.setTargetImsConsumer("testConsumer");
      GetTransferTokenResponse response = loginUserServiceClient.getTransferToken(request);
      Assert.assertNotNull("Unable to fetch transfer token", response.getTransferTokenDto()
               .getTransferToken());
      System.out.println(response);
	   
	}
	
   @Test
   public void loginUserWithTranferTokenRequestTest() throws ServiceException, Exception {
      LoginWithTransferTokenRequest request = new LoginWithTransferTokenRequest();
      request.setToken("jqCBFyCv9PRsf6-i37GJ_jDFbE1uJkul_uyNxQWJiQ_WlSftXBiInSoIGejASMkO");

      LoginUserResponse response = loginUserServiceClient.loginUserWithTransferToken(request);
      System.out.println(response);
   }

   private GetUserResponse getUserByEmail(String email) throws HttpTransportException,
            ServiceException {

      GetUserByEmailRequest request = new GetUserByEmailRequest();
      request.setEmailId(email);
      return userServiceClient.getUserByEmail(request);
   }

   private GetUserResponse getUserByToken(String token) throws HttpTransportException,
            ServiceException {

      GetUserByTokenRequest request = new GetUserByTokenRequest();
      request.setToken(token);
      return userServiceClient.getUserByToken(request);
   }

   @Test
   public void testForgotPassword_regression() throws HttpTransportException, ServiceException {

      String email = "johnson4967@snapdeal.com";

      getUserByEmail(email);
      ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

      forgotPasswordRequest.setEmailId(email);

      ForgotPasswordResponse forgotPasswordWithOTP = loginUserServiceClient.forgotPasswordWithOTP(forgotPasswordRequest);
      Assert.assertNotNull(forgotPasswordWithOTP);
      String otp = DBConnector.getOtp(forgotPasswordWithOTP.getOtpId());
      Assert.assertNotNull(otp);

      ForgotPasswordVerifyRequest forgotPasswordVerifyRequest = new ForgotPasswordVerifyRequest();
      forgotPasswordVerifyRequest.setOtpId(forgotPasswordWithOTP.getOtpId());
      forgotPasswordVerifyRequest.setOtp(otp);
      forgotPasswordVerifyRequest.setNewPassword("password1");
      forgotPasswordVerifyRequest.setConfirmPassword("password1");
      forgotPasswordVerifyRequest.setUserId(forgotPasswordWithOTP.getUserId());
      ResetPasswordResponse resetPasswordWithOTP = loginUserServiceClient
               .resetPasswordWithOTP(forgotPasswordVerifyRequest);

      Assert.assertNotNull(resetPasswordWithOTP);
      Assert.assertTrue(resetPasswordWithOTP.isSuccess());
   }

   @Test
   public void testForgotPasswordandLogin_regression() throws HttpTransportException,
            ServiceException {

      String email = "johnson4967@snapdeal.com";

      getUserByEmail(email);
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
      GetUserResponse userByToken = getUserByToken(resetPasswordWithOTP.getTokenInformation()
               .getToken());
      Assert.assertNotNull(userByToken);
   }

   @Test
   public void testChangePassword_oldLogin() throws HttpTransportException, ServiceException {
      String email = "johnson4967@snapdeal.com";

      GetUserResponse userByEmail = getUserByEmail(email);

      LoginUserRequest loginUserRequest = new com.snapdeal.ims.request.LoginUserRequest();
      loginUserRequest.setEmailId(email);
      String oldPassword = "password1";
      loginUserRequest.setPassword(oldPassword);
      loginUserRequest.setUserAgent("userAgent");
      loginUserRequest.setUserMachineIdentifier("userMachineIdentifier");
      LoginUserResponse loginUserWithPassword = loginUserServiceClient
               .loginUserWithPassword(loginUserRequest);

      LoginUserResponse loginUserWithPassword2 = loginUserServiceClient
               .loginUserWithPassword(loginUserRequest);

      ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
      changePasswordRequest.setToken(loginUserWithPassword2.getTokenInformation().getToken());
      changePasswordRequest.setOldPassword(oldPassword);
      String newPassword = "password";
      changePasswordRequest.setNewPassword(newPassword);
      changePasswordRequest.setConfirmNewPassword(newPassword);
      ChangePasswordResponse changePassword = loginUserServiceClient.changePassword(changePasswordRequest);

      try {
         getUserByToken(loginUserWithPassword.getTokenInformation().getToken());
         Assert.fail();
      } catch (ServiceException e) {
         Assert.assertTrue(true);
      }

   }
}