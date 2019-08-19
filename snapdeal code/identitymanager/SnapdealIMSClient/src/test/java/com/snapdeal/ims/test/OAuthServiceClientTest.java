package com.snapdeal.ims.test;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.snapdeal.ims.client.ILoginUserServiceClient;
import com.snapdeal.ims.client.IOAuthServiceClient;
import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.LoginUserServiceClientImpl;
import com.snapdeal.ims.client.impl.OAuthServiceClientImpl;
import com.snapdeal.ims.client.impl.OTPClientServiceImpl;
import com.snapdeal.ims.client.impl.UserMigrationServiceClientImpl;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.OAuthTokenTypes;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.util.DBConnector;
import com.snapdeal.ims.utils.ClientDetails;

public class OAuthServiceClientTest {

	IOAuthServiceClient oauthServiceClient = new OAuthServiceClientImpl();
	ILoginUserServiceClient loginUserServiceClient = new LoginUserServiceClientImpl();
	IUserServiceClient userServiceClient = new UserServiceClientImpl();
	IOTPServiceClient otpServiceClient = new OTPClientServiceImpl();
	IUserMigrationServiceClient userMigrationServiceClient = new UserMigrationServiceClientImpl();
	
	Random r = new Random();
	int number = r.nextInt(10000);

	private void setupSD() throws Exception {
		ClientDetails.init("localhost", "8080", "q%!8x7tg6df!", "AA6F0FD7BBA87D81", 1500000);
	}

	private void setupFC() throws Exception {
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83", 1500000);
	}

	public void setupOC() throws Exception {
		ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", "AA6F0FD7BBA87D82", 6000000);
	}

	@Test
	public void getAuthCodeTest() throws ServiceException, Exception {
		setupFC();
		GetAuthCodeRequest getAuthCodeRequest = new GetAuthCodeRequest();
		getAuthCodeRequest.setMerchantId("1");
		getAuthCodeRequest.setToken(
				"-MsU418KdcNDYVyG_B8-UNs23aniZhf6eZABOwSxErvwdU-v2Z1_W4VJEGSkLmFb80AolGVJ_T9neGYGuD44LJcDMB7vY8ocUyBvx0g3XPy8lU16yQc66z9bbuCuOT13");
		getAuthCodeRequest.setUserAgent("abc");
		getAuthCodeRequest.setUserMachineIdentifier("abc");
		GetAuthCodeResponse getAuthCodeResponse = oauthServiceClient.getAuthCodeForMerchant(getAuthCodeRequest);
		System.out.println(getAuthCodeResponse);
	}

	@Test
	public void getAccessTokenTest() throws ServiceException, Exception {
		setupFC();
		GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
		getAccessTokenRequest.setAuthCode(
				"_SxpmneAlLRP-2PLsmYtanvfn-D42NjHaGMzoUVmA4UwCw5aYcWJiWsY3Jw7ARyWA178E5lDUia27MRB3jVoyW7smf8Vi9L1J4ZHkb1XRDw");
		getAccessTokenRequest.setMerchantId("1");
		getAccessTokenRequest.setUserAgent("abc");
		getAccessTokenRequest.setUserMachineIdentifier("abc");
		GetAccessTokenResponse getAccessTokenResponse = oauthServiceClient
				.getAccessTokenForAuthCode(getAccessTokenRequest);
		System.out.println(getAccessTokenResponse);
	}

	@Test
	public void getOAuthTokenDetailsTest() throws ServiceException, Exception {
		setupFC();
		GetOAuthTokenDetailsRequest request = new GetOAuthTokenDetailsRequest();
		request.setTokenType(OAuthTokenTypes.AUTH_CODE);
		request.setToken(
				"_SxpmneAlLRP-2PLsmYtanvfn-D42NjHaGMzoUVmA4UwCw5aYcWJiWsY3Jw7ARyWA178E5lDUia27MRB3jVoyW7smf8Vi9L1J4ZHkb1XRDw");
		request.setUserAgent("abc");
		request.setUserMachineIdentifier("abc");
		GetOAuthTokenDetailsResponse getTokenDetailsResponse = oauthServiceClient.getTokenDetails(request);
		System.out.println(getTokenDetailsResponse);
	}

	@Test
	public void testValidateOauthCode() throws Exception {
		String email = "atomizer77909" + number + "@snapdeal.com";
		String password = "password";
		String mobileNumber = "7669990252";
		// fc user create
		setupFC();
		CreateUserResponse createUserFC = createUserFC(email, password, mobileNumber);
		Assert.assertNotNull(createUserFC);
		// sd social user

		setupSD();
		String password2 = "password2";
		CreateUserResponse createUserSD = createUserSD(email, password2);
		Assert.assertNotNull(createUserSD);
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
			UpgradeUserResponse upgradeResponse = userMigrationServiceClient.upgradeUser(userUpgradeRequest);
			if (upgradeResponse != null) {
				Assert.assertEquals(true, upgradeResponse.isSuccess());
			}
		}
		LoginUserResponse loginWithToken = loginWithToken(createUserFC.getTokenInformationDTO().getGlobalToken());
		Assert.assertNotNull(loginWithToken);
		Assert.assertNotNull(loginWithToken.getUpgradationInformation());
		Assert.assertEquals(loginWithToken.getUpgradationInformation().getState(), State.FC_ACCOUNT_MIGRATED);

		setupFC();
		GetAuthCodeRequest getAuthCodeRequest = new GetAuthCodeRequest();
		getAuthCodeRequest.setMerchantId("1");
		getAuthCodeRequest.setToken(loginWithToken.getTokenInformation().getToken());
		getAuthCodeRequest.setUserAgent("abc");
		getAuthCodeRequest.setUserMachineIdentifier("jndf");
		GetAuthCodeResponse getAuthCodeResponse = oauthServiceClient.getAuthCodeForMerchant(getAuthCodeRequest);
		Assert.assertNotNull(getAuthCodeResponse);
		Assert.assertNotNull(getAuthCodeResponse.getAuthCode());

		GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
		getAccessTokenRequest.setAuthCode(getAuthCodeResponse.getAuthCode());
		getAccessTokenRequest.setMerchantId("1");
		getAccessTokenRequest.setUserAgent("abc");
		getAccessTokenRequest.setUserMachineIdentifier("jndf");
		GetAccessTokenResponse getAccessTokenResponse = oauthServiceClient
				.getAccessTokenForAuthCode(getAccessTokenRequest);
		
		/*ChangePasswordRequest changeRequest = new ChangePasswordRequest();
		changeRequest.setOldPassword(password);
		changeRequest.setNewPassword("pswdpswd");
		changeRequest.setConfirmNewPassword("pswdpswd");
		changeRequest.setToken(getAccessTokenResponse.getAccessToken());
		ChangePasswordResponse cResponse = loginUserServiceClient.changePassword(changeRequest);
		Assert.assertNotNull(cResponse);*/
		
		GetUserByTokenRequest getUserRequest = new GetUserByTokenRequest();
		getUserRequest.setToken(getAccessTokenResponse.getAccessToken());
		getUserRequest.setUserAgent("abc");
		getUserRequest.setUserMachineIdentifier("jndf");
		GetUserResponse userResponse = userServiceClient.getUserByToken(getUserRequest);
		Assert.assertNotNull(userResponse);
	}

	private CreateUserResponse createUserSD(String requestEmailId, String password)
			throws HttpTransportException, ServiceException {
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

	private CreateUserResponse createUserFC(String requestEmailId, String password, String phoneNumber)
			throws HttpTransportException, ServiceException {
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

	private LoginUserResponse loginWithToken(String globalToken) throws HttpTransportException, ServiceException {

		LoginWithTokenRequest request = new LoginWithTokenRequest();
		request.setGlobalToken(globalToken);
		LoginUserResponse loginUserWithToken = null;
		loginUserWithToken = loginUserServiceClient.loginUserWithToken(request);

		return loginUserWithToken;
	}

	private LoginUserResponse loginWithTransferToken(String tToken) throws HttpTransportException, ServiceException {
		LoginWithTransferTokenRequest request =  new LoginWithTransferTokenRequest();
		request.setToken(tToken);
		LoginUserResponse loginUserWithToken = null;
		loginUserWithToken = loginUserServiceClient.loginUserWithTransferToken(request);
		return loginUserWithToken;
	}

}
