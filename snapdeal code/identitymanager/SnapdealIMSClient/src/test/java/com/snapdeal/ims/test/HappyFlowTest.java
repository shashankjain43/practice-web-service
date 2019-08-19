package com.snapdeal.ims.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.ILoginUserServiceClient;
import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.LoginUserServiceClientImpl;
import com.snapdeal.ims.client.impl.OTPClientServiceImpl;
import com.snapdeal.ims.client.impl.UserMigrationServiceClientImpl;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.dto.ClientConfig;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyOTPRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.VerifyOTPResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class HappyFlowTest {

	IUserServiceClient userServiceClient =new UserServiceClientImpl();
	IOTPServiceClient otpServiceClient =new OTPClientServiceImpl();
	IUserMigrationServiceClient cli = new UserMigrationServiceClientImpl();
	ILoginUserServiceClient loginUserServiceClient =new LoginUserServiceClientImpl();


	//ClientDetails.init("localhost", "8080", "snapdeal", "1",12000);
			//Freecharge
			//ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83	",12000);
			//Snapdeal
			//ClientDetails.init("localhost","8080","zitsgfqo^a2p","9782751690B09848",12000);
			//OneCheck
			//ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", " AA6F0FD7BBA87D82",12000);

	@Test
	public void Link_FC_VerifyUpgradeUsertest() throws Exception{
		String number = "25";
		//Freecharge Client
		//Creating freecharge user with email and mobile
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83	",12000);
		CreateUserResponse responseFreecharge = createUserWithEmailAndMobileTest(number);
		//Snapdeal client
		//creating snapdeal user with email
		ClientDetails.init("localhost","8080","zitsgfqo^a2p","9782751690B09848",12000);
		CreateUserResponse responseSnapdeal = createUserWithEmailRequestTest(number);
		
		//Generating otp with purpose upgrade user and upgrading user 
		GenerateOTPResponse upgradeUserOTPResponse = sendOtpRequestTest(responseSnapdeal,OTPPurpose.UPGRADE_USER,number);
		UpgradeUserResponse upgradeUserResponse = testUpgradeUser(responseSnapdeal,upgradeUserOTPResponse);
		
		//Get Transfer token from Snapdeal
		GetTransferTokenResponse getTransferTokenResponse = testGetTransferToken(responseSnapdeal);
		
		//OneCheck client
		//Login from onecheck client using transfertoken
		ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", "AA6F0FD7BBA87D82",120000);
		LoginUserResponse loginUserResponse = loginUserWithTranferTokenRequestTest(getTransferTokenResponse);
		
		CreateUserResponse transferResponse = new CreateUserResponse();
		transferResponse.setTokenInformationDTO(new TokenInformationDTO());
		transferResponse.setUserDetails(new UserDetailsDTO());
		transferResponse.getTokenInformationDTO().setToken(loginUserResponse.getTokenInformation().getToken());
		transferResponse.getUserDetails().setEmailId(loginUserResponse.getUserDetails().getEmailId());
		transferResponse.getUserDetails().setMobileNumber(loginUserResponse.getUserDetails().getMobileNumber());
		//verifyupgrade user
		GenerateOTPResponse linkOTPResponse = sendOtpRequestTest(transferResponse,OTPPurpose.LINK_ACCOUNT,number);
		testVerifyUpgradeUser(transferResponse,linkOTPResponse);
	}
	
	@Test
	public void Link_FC_SocialLogintest() throws Exception{
		String number = "27";
		//Freecharge Client
		//Creating freecharge user with email and mobile
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83	",12000);
		CreateUserResponse responseFreecharge = createUserWithEmailAndMobileTest(number);
		//Snapdeal client
		//creating snapdeal user with email
		ClientDetails.init("localhost","8080","zitsgfqo^a2p","9782751690B09848",12000);
		CreateUserResponse responseSnapdeal = createUserWithEmailRequestTest(number);
		
		//Generating otp with purpose upgrade user and upgrading user 
		GenerateOTPResponse upgradeUserOTPResponse = sendOtpRequestTest(responseSnapdeal,OTPPurpose.UPGRADE_USER,number);
		UpgradeUserResponse upgradeUserResponse = testUpgradeUser(responseSnapdeal,upgradeUserOTPResponse);
		
		//Get Transfer token from Snapdeal
		GetTransferTokenResponse getTransferTokenResponse = testGetTransferToken(responseSnapdeal);
		
		//OneCheck client
		//Login from onecheck client using transfertoken
		ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", "AA6F0FD7BBA87D82",120000);
		LoginUserResponse loginUserResponse = loginUserWithTranferTokenRequestTest(getTransferTokenResponse);
		
		//social login user
		createUserWithSocialRequestTest(loginUserResponse);
	}

	@Test
	public void Link_SD_VerifyUpgradeUsertest() throws Exception{
		String number = "27";
		//Freecharge Client
		//Creating freecharge user with email and mobile
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83	",12000);
		CreateUserResponse responseFreecharge = createUserWithEmailAndMobileTest(number);
		//Snapdeal client
		//creating snapdeal user with email
		ClientDetails.init("localhost","8080","zitsgfqo^a2p","9782751690B09848",12000);
		CreateUserResponse responseSnapdeal = createUserWithEmailRequestTest(number);
		
		//Generating otp with purpose upgrade user and upgrading user 
		GenerateOTPResponse upgradeUserOTPResponse = sendOtpRequestTest(responseFreecharge,OTPPurpose.UPGRADE_USER,number);
		UpgradeUserResponse upgradeUserResponse = testUpgradeUser(responseFreecharge,upgradeUserOTPResponse);
		
		//Get Transfer token from Freecharge
		GetTransferTokenResponse getTransferTokenResponse = testGetTransferToken(responseFreecharge);
		
		//OneCheck client
		//Login from onecheck client using transfertoken
		ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", "AA6F0FD7BBA87D82",120000);
		LoginUserResponse loginUserResponse = loginUserWithTranferTokenRequestTest(getTransferTokenResponse);
		
		CreateUserResponse transferResponse = new CreateUserResponse();
		transferResponse.setTokenInformationDTO(new TokenInformationDTO());
		transferResponse.setUserDetails(new UserDetailsDTO());
		transferResponse.getTokenInformationDTO().setToken(loginUserResponse.getTokenInformation().getToken());
		transferResponse.getUserDetails().setEmailId(loginUserResponse.getUserDetails().getEmailId());
		transferResponse.getUserDetails().setMobileNumber(loginUserResponse.getUserDetails().getMobileNumber());
		//verifyupgrade user
		GenerateOTPResponse linkOTPResponse = sendOtpRequestTest(transferResponse,OTPPurpose.LINK_ACCOUNT,number);
		testVerifyUpgradeUser(transferResponse,linkOTPResponse);
	}

	@Test
	public void Link_SD_SocialLogintest() throws Exception{
		String number = "28";
		//Freecharge Client
		//Creating freecharge user with email and mobile
		ClientDetails.init("localhost", "8080", "q%!8x7tg6dyT", "AA6F0FD7BBA87D83	",12000);
		CreateUserResponse responseFreecharge = createUserWithEmailAndMobileTest(number);
		//Snapdeal client
		//creating snapdeal user with email
		ClientDetails.init("localhost","8080","zitsgfqo^a2p","9782751690B09848",12000);
		CreateUserResponse responseSnapdeal = createUserWithEmailRequestTest(number);
		
		//Generating otp with purpose upgrade user and upgrading user 
		GenerateOTPResponse upgradeUserOTPResponse = sendOtpRequestTest(responseFreecharge,OTPPurpose.UPGRADE_USER,number);
		UpgradeUserResponse upgradeUserResponse = testUpgradeUser(responseFreecharge,upgradeUserOTPResponse);
		
		//Get Transfer token from Freecharge
		GetTransferTokenResponse getTransferTokenResponse = testGetTransferToken(responseFreecharge);
		
		//OneCheck client
		//Login from onecheck client using transfertoken
		ClientDetails.init("localhost", "8080", "l%$8c7yg4dg!", "AA6F0FD7BBA87D82",120000);
		LoginUserResponse loginUserResponse = loginUserWithTranferTokenRequestTest(getTransferTokenResponse);
		
		//social login user
		createUserWithSocialRequestTest(loginUserResponse);
	}

	public CreateUserResponse createUserWithEmailRequestTest(String number) throws ServiceException,
	Exception {
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		request.getUserDetailsByEmailDto().setPassword("password123");
		request.getUserDetailsByEmailDto().setEmailId(
				"zsiddhant"+number+"@gmail.com");
		request.getUserDetailsByEmailDto().setFirstName("John");
		request.getUserDetailsByEmailDto().setMiddleName("Kumar");
		request.getUserDetailsByEmailDto().setLastName("Cena");
		request.getUserDetailsByEmailDto().setDisplayName("Johnson");
		request.getUserDetailsByEmailDto().setGender(Gender.MALE);
		request.getUserDetailsByEmailDto().setDob("1990-12-23");
		request.getUserDetailsByEmailDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		CreateUserResponse response = userServiceClient
				.createUserWithEmail(request);
		System.out.println(response);
		return response;

	}


	public CreateUserResponse createUserWithEmailAndMobileTest(String number) throws ServiceException,
	Exception {
		CreateUserEmailMobileRequest request = new CreateUserEmailMobileRequest();
		request.getUserRequestDto().setPassword("password");
		request.getUserRequestDto().setMobileNumber("64033037" + number );
		request.getUserRequestDto().setEmailId(
				"zsiddhant"+number+"@gmail.com");
		request.getUserRequestDto().setFirstName("John");
		request.getUserRequestDto().setMiddleName("Kumar");
		request.getUserRequestDto().setLastName("Cena");
		request.getUserRequestDto().setDisplayName("Johnson");
		request.getUserRequestDto().setGender(Gender.MALE);
		request.getUserRequestDto().setDob("1990-12-05");
		request.getUserRequestDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		CreateUserResponse response = userServiceClient
				.createUserWithEmailAndMobile(request);
		System.out.println(response);
		return response;
	}

	public GenerateOTPResponse sendOtpRequestTest(CreateUserResponse createUserResponse,OTPPurpose otp,String number) throws ServiceException, Exception {

		GenerateOTPRequest request = new GenerateOTPRequest();
		request.setEmailId(createUserResponse.getUserDetails().getEmailId());
		request.setMobileNumber("64033037" + number );
		request.setPurpose(otp);
		request.setToken(createUserResponse.getTokenInformationDTO().getToken());
		GenerateOTPResponse response = otpServiceClient.sendOTP(request);
		System.out.println(response);
		return response;
	}

	public UpgradeUserResponse testUpgradeUser(CreateUserResponse createUserResponse, GenerateOTPResponse upgradeUserOTPResponse) {
		UserUpgradeRequest request = new UserUpgradeRequest();
		request.setUserAgent("userAgent");
		request.setEmailId(createUserResponse.getUserDetails().getEmailId());
		request.setUserMachineIdentifier("userMachineIdentifier");
		request.setToken(createUserResponse.getTokenInformationDTO().getToken());
		request.setVerifiedType(UserIdentityVerifiedThrough.MOBILE_OTP_VERIFIED);
		request.setUpgradeSource(UpgradeSource.SIGN_IN);
		request.setMobileNumber(createUserResponse.getUserDetails().getMobileNumber());
		request.setOtpId(upgradeUserOTPResponse.getOtpId());
		request.setOtp("1234");
		UpgradeUserResponse upgradeUser = null;
		try {
			upgradeUser = cli.upgradeUser(request);
			org.junit.Assert.assertNotNull("", upgradeUser);	
		} catch (HttpTransportException e) {
			Assert.fail(e.getMessage());
		} catch (ServiceException e) {
			org.junit.Assert.assertEquals(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg(),
					e.getErrMsg());
		}
		System.out.println(upgradeUser);
		return upgradeUser;
	}
	
	public GetTransferTokenResponse testGetTransferToken(CreateUserResponse createUserResponse) throws ServiceException {
	   GetTransferTokenRequest request =  new GetTransferTokenRequest();
      request.setToken(createUserResponse.getTokenInformationDTO().getToken());
      request.setTargetImsConsumer("FC_WALLET");
      GetTransferTokenResponse response = loginUserServiceClient.getTransferToken(request);
      System.out.println(response);
      return response;
	   
	}
	
   public LoginUserResponse loginUserWithTranferTokenRequestTest(GetTransferTokenResponse getTransferTokenResponse) throws ServiceException, Exception {
      LoginWithTransferTokenRequest request = new LoginWithTransferTokenRequest();
      request.setToken(getTransferTokenResponse.getTransferTokenDto().getTransferToken());
      LoginUserResponse response = loginUserServiceClient.loginUserWithTransferToken(request);
      System.out.println(response);
      return response;
   }
  
	public void testVerifyUpgradeUser(CreateUserResponse createUserResponse,GenerateOTPResponse otpResponse) {
		try {
			VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();

			verifyUserUpgradeRequest.setEmailId(createUserResponse.getUserDetails().getEmailId());
			verifyUserUpgradeRequest.setPassword("password123");
			verifyUserUpgradeRequest.setTargetSrcToBeValidated(Merchant.SNAPDEAL);
			verifyUserUpgradeRequest.setUserAgent("userAgent");
			verifyUserUpgradeRequest.setToken(createUserResponse.getTokenInformationDTO().getToken());
			verifyUserUpgradeRequest.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_EMAIL_OTP);
			verifyUserUpgradeRequest.setOtp("1234");
			verifyUserUpgradeRequest.setOtpId(otpResponse.getOtpId());
			verifyUserUpgradeRequest.setUserMachineIdentifier("userMachineIdentifier");
			VerifyUpgradeUserResponse upgradeUser = cli.verifyUpgradeUser(verifyUserUpgradeRequest);
			System.out.println(upgradeUser);
		} catch (HttpTransportException e) {
			Assert.fail(e.getMessage());

		} catch (ServiceException e) {
			org.junit.Assert.assertEquals(
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg(),
					e.getErrMsg());
		}
	}
	
	public void verifyOTP(CreateUserResponse cResponse,GenerateOTPResponse otpResponse) throws ServiceException, Exception {
		VerifyOTPRequest request = new VerifyOTPRequest() ;
		request.setOtp("1234");
		request.setOtpId(otpResponse.getOtpId());
		request.setOtpPurpose(OTPPurpose.MOBILE_VERIFICATION);
		request.setToken(cResponse.getTokenInformationDTO().getToken());
		VerifyOTPResponse response = otpServiceClient.verifyOTP(request)	;
		System.out.println(response);
	}
	
	public void createUserWithSocialRequestTest(LoginUserResponse loginUserResponse) throws ServiceException,
			Exception {
		CreateSocialUserRequest request = getCreateSocialUserRequest(loginUserResponse);
		ClientConfig config = new ClientConfig();
		config.setAppRequestId("12234354334");
		request.setClientConfig(config);
		SocialUserResponse response = userServiceClient
				.createOrLoginUserWithSocial(request);
		System.out.println(response);
	}

	public CreateSocialUserRequest getCreateSocialUserRequest(LoginUserResponse loginUserResponse) {

		CreateSocialUserRequest request = new CreateSocialUserRequest();
		request.getSocialUserDto().setEmailId(loginUserResponse.getUserDetails().getEmailId());
		request.getSocialUserDto().setSocialSrc("facebook");
		request.getSocialUserDto().setSocialId("45678987698");
		request.getSocialUserDto().setFirstName("John");
		request.getSocialUserDto().setMiddleName("Kumar");
		request.getSocialUserDto().setLastName("Cena");
		request.getSocialUserDto().setAboutMe("hi");
		request.getSocialUserDto().setDisplayName("haowen");
		request.getSocialUserDto().setAboutMe("Wrestler");
		request.getSocialUserDto().setPhotoURL("");
		request.getSocialUserDto().setMobileNumber(loginUserResponse.getUserDetails().getMobileNumber());
		request.getSocialUserDto().setDob("1990-12-05");
		request.getSocialUserDto().setGender(Gender.MALE);
		request.getSocialUserDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		return request;
	}

}
