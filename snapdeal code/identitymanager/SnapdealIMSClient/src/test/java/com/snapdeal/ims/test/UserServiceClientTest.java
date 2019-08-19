package com.snapdeal.ims.test;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.OTPClientServiceImpl;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.ClientConfig;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.CreateUserWithMobileOnlyRequest;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsTokenValidRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsTokenValidResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class UserServiceClientTest {

	IUserServiceClient userServiceClient =new UserServiceClientImpl();
	IOTPServiceClient  otpServiceClient = new OTPClientServiceImpl();

	Random r = new Random();
	int number = r.nextInt(10000);

	@Before
	public void setup() throws Exception {
      ClientDetails.init("54.169.81.238", "8080", "q%!8x7tg6df!","AA6F0FD7BBA87D81",12000);
	}

	@Test
	public void createUserWithEmailRequestTest() throws ServiceException,
	Exception {
		CreateUserEmailRequest request = new CreateUserEmailRequest();
		request.getUserDetailsByEmailDto().setPassword("password");
		request.getUserDetailsByEmailDto().setEmailId(
				"zawar.siddhant123@gmail.com");
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
	}

	@Test
	public void createUserWithEmailAndMobileTest() throws ServiceException,
	Exception {
		CreateUserEmailMobileRequest request = new CreateUserEmailMobileRequest();
		request.getUserRequestDto().setPassword("password");
		request.getUserRequestDto().setMobileNumber("7838183408");
		request.getUserRequestDto().setEmailId(
				"siddhant.zawar.2011@gmail.com");
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
	}

	@Test
	public void createUserWithMobileGenerateTest() throws ServiceException,
	Exception {
		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		request.getUserRequestDto().setPassword("password");
		request.getUserRequestDto().setMobileNumber("7838183405");
		request.getUserRequestDto().setEmailId(
				"zawar.siddhant1234@gmail.com");
		request.getUserRequestDto().setFirstName("John");
		request.getUserRequestDto().setMiddleName("Kumar");
		request.getUserRequestDto().setLastName("Cena");
		request.getUserRequestDto().setDisplayName("Johnson");
		request.getUserRequestDto().setDob("1990-12-05");
		request.getUserRequestDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		OTPResponse response = userServiceClient.createUserWithMobile(request);
		System.out.println(response);
	}

	@Test
	public void createUserWithMobileVerifyTest() throws ServiceException,
	Exception {
		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1234");
		request.setOtpId("7493f07f-a8a8-4d70-9132-a85788418610");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		CreateUserResponse response = userServiceClient
				.verifyUserWithMobile(request);
		System.out.println(response);
	}

	@Test
	public void createGuestUserWithEmailTest() throws ServiceException,
	Exception {
		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();
		request.setEmailId("aksjdklsdsd@gmail.com");
		request.setPurpose("GUEST USER WITH TEST");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		CreateGuestUserResponse response = userServiceClient
				.createGuestUserWithEmail(request);
		System.out.println(response);
	}

	@Test
	public void verifyGuestUserTest() throws ServiceException, Exception {
		VerifyUserRequest request = new VerifyUserRequest();
		request.setCode("VjAxI2IyYjNkZTBjOTA4ODQ3ZmM4ZDA2MTg1NTVlNzAyNzJl");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		VerifyUserResponse response = userServiceClient
				.verifyUser(request);
		System.out.println(response);
	}

	@Test
	public void createUserWithSocialRequestTest() throws ServiceException,
	Exception {
		CreateSocialUserRequest request = getCreateSocialUserRequest();
		//	request.getSocialUserDto().setDisplayName("Öakjdsakjdb");
		request.getSocialUserDto().setDisplayName("akjdsakjdb");
		ClientConfig config = new ClientConfig();
		config.setAppRequestId("12234354334");
		request.setClientConfig(config);
		SocialUserResponse response = userServiceClient
				.createOrLoginUserWithSocial(request);
		System.out.println(response);
	}

	/*
	 * @Test
	 * 
	 * @Ignore public void createUserWithGmailRequestTest() throws
	 * ServiceException, Exception {
	 * 
	 * CreateSocialUserRequest request = getCreateSocialUserRequest();
	 * CreateSocialUserResponse response =
	 * userServiceClient.createUserWithGmail(request);
	 * System.out.println(response); }
	 */

	private CreateSocialUserRequest getCreateSocialUserRequest() {

		CreateSocialUserRequest request = new CreateSocialUserRequest();
		request.getSocialUserDto().setEmailId("prakashdey" + number + "@gmail.com");
		request.getSocialUserDto().setSocialSrc("facebook");
		request.getSocialUserDto().setSocialId("45678987698");
		request.getSocialUserDto().setFirstName("John");
		request.getSocialUserDto().setMiddleName("Kumar");
		request.getSocialUserDto().setLastName("Cena");
		request.getSocialUserDto().setAboutMe("hi");
		request.getSocialUserDto().setDisplayName("haowen");
		request.getSocialUserDto().setAboutMe("Wrestler");
		request.getSocialUserDto().setPhotoURL("");
		request.getSocialUserDto().setMobileNumber("9189742211");
		request.getSocialUserDto().setDob("1990-12-05");
		request.getSocialUserDto().setGender(Gender.MALE);
		request.getSocialUserDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		return request;
	}

	@Test
	public void updateUserByIdRequestTest() throws ServiceException, Exception {
		UpdateUserByIdRequest request = new UpdateUserByIdRequest();
		request.setUserId("VjAxI2ZlZTE3M2M5LTFkOTMtNDVmYy05OWM5LWEyYWQ0ZTUyOTQ2Mg");
/*		request.getUserDetailsRequestDto().setFirstName("FNAfterUpdate");
		request.getUserDetailsRequestDto().setMiddleName("MNAfterUpdate");
		request.getUserDetailsRequestDto().setLastName("LNAfterUpdt");
		request.getUserDetailsRequestDto().setDisplayName("");
		request.getUserDetailsRequestDto().setGender(null);
		request.getUserDetailsRequestDto().setDob("2000-1-12");
		request.getUserDetailsRequestDto().setLanguagePref(Language.ENGLISH);
*/		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		GetUserResponse response = userServiceClient.updateUserById(request);
		System.out.println(response);
	}



	@Test
	public void updateUserbyTokenRequestTest() throws ServiceException,
	Exception {
		UpdateUserByTokenRequest request = new UpdateUserByTokenRequest();
		request.getUserDetailsRequestDto().setDisplayName("Kishan");
		request.getUserDetailsRequestDto().setDob("1990-12-05");
		request.getUserDetailsRequestDto().setFirstName("John");
		request.getUserDetailsRequestDto().setGender(Gender.MALE);
		request.getUserDetailsRequestDto().setLanguagePref(Language.ENGLISH);
		request.getUserDetailsRequestDto().setLastName("Cena");
		request.getUserDetailsRequestDto().setMiddleName("Kumar");
		request.setToken("prp47GXEsIVBah5VkwHWX6G1oi_x2viVuV7ULRWQOtMnXhm8INTQnmG0zdl6cW6yRJh8QXJCd5lHuGKQgXOh0w");
		GetUserResponse response = userServiceClient.updateUserbyToken(request);
		System.out.println(response);
	}

	@Test
	public void isUserExistTest() throws ServiceException, Exception {
		IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId("951495");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		IsUserExistResponse response = userServiceClient.isUserExist(request);
		System.out.println(response);
	}

	@Test
	public void isEmailExistTest() throws ServiceException, Exception {
		IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId("johnson7258@gmail.com");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		IsEmailExistResponse response = userServiceClient.isEmailExist(request);
		System.out.println(response);
	}

	@Test
	public void isMobileExistTest() throws ServiceException, Exception {
		IsVerifiedMobileExistRequest request = new IsVerifiedMobileExistRequest();
		request.setMobileNumber("7369341151");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		IsVerifiedMobileExistResponse response = userServiceClient
				.isVerifiedMobileExist(request);
		System.out.println(response);
	}

	@Test
	public void getUserByEmailTest() throws ServiceException, Exception {
		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId("qrpraveen.jain@snapdeal.com");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		GetUserResponse response = userServiceClient.getUserByEmail(request);
		System.out.println(response);
	}

	@Test
	public void getUserByIDTest() throws ServiceException, Exception {
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId("VjAxI2JlODg2MTQzLTNiZjktNGJkMS1hZWYzLWFjNDQ1M2RlZmQ3N1");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		GetUserResponse response = userServiceClient.getUserById(request);
		System.out.println(response);
	}

	@Test
	public void getUserByMobileTest() throws ServiceException, Exception {
		GetUserByMobileRequest request = new GetUserByMobileRequest();
		request.setMobileNumber("9654334642");

		GetUserResponse response = userServiceClient.getUserByVerifiedMobile(request);
		System.out.println(response);
	}

	@Test
	public void getUserByTokenTest() throws ServiceException, Exception {
		GetUserByTokenRequest request = new GetUserByTokenRequest();
		request.setToken("5ItLAGsFE4k1EIaXR66tH-YOp68W69laxuqZj8FuaukYl12tCUQQ3nEm1d8xNiv9i1wYsKEhoVLDi61v99vDKrbIs6VAGqNsKQxABFdnVxc");
		GetUserResponse response = userServiceClient.getUserByToken(request);
		System.out.println(response);
	}

	@Test
	public void updateMobileMobileNumberTest() throws ServiceException,
	Exception {
		UpdateMobileNumberRequest request = new UpdateMobileNumberRequest();
		request.setMobileNumber("9599341553");
		request.setOtpId("4075c7b9-d97a-46b2-a8d3-091dd533aef6");
		request.setOTP("7324");
		request.setToken("H4RVohfCB0QKUvlM-dmL2RNkgTP_BKvdGMyoJKqfxvsWqOq2phxGNCW1D8k6ys4RfKZLXaRQngNzqQNCPg-CsQ");
		request.setUserAgent("abc");
		request.setUserMachineIdentifier("jndf");
		UpdateMobileNumberResponse response = userServiceClient
				.updateMobileNumber(request);
		System.out.println(response);
	}

	@Test
	public void testChangeState() throws ServiceException, Exception {
		ConfigureUserStateRequest request = new ConfigureUserStateRequest();

		request.setUserId("VjAxI2E0M2I5ZTkzLTc1MzgtNDM5Yy1iMTNhLWQ3MGM1ZDQxYWNjZA");
		request.setEmailId("imstestmaryroman.2704069396@yahoo.in");
		request.setEnable(false);
		ConfigureUserStateResponse response = userServiceClient.configureUserState(request);
		System.out.println(response);
	}

	@Test
	public void createSocialUserWithMobileRequestTest() throws ServiceException,
	Exception {

		CreateSocialUserWithMobileRequest request = getCreateSocialUserWithMobileRequest();
		CreateSocialUserWithMobileResponse response = userServiceClient
				.createSocialUserWithMobile(request);
		System.out.println(response);
	}


	private CreateSocialUserWithMobileRequest getCreateSocialUserWithMobileRequest() {

		CreateSocialUserWithMobileRequest request = new CreateSocialUserWithMobileRequest();
		//request.getSocialUserDto().setEmailId("subhash" + number + "@gmail.com");
		request.getSocialUserDto().setEmailId("subhash10@gmail.com");
		request.getSocialUserDto().setSocialSrc("facebook");
		request.getSocialUserDto().setSocialId("4567886878");
		request.getSocialUserDto().setFirstName("John");
		request.getSocialUserDto().setMiddleName("Kumar");
		request.getSocialUserDto().setLastName("Cena");
		request.getSocialUserDto().setAboutMe("hi");
		request.getSocialUserDto().setDisplayName("haowen");
		request.getSocialUserDto().setAboutMe("Wrestler");
		request.getSocialUserDto().setPhotoURL("");
		request.getSocialUserDto().setMobileNumber("9953329969");
		request.getSocialUserDto().setDob("1990-12-05");
		request.getSocialUserDto().setGender(Gender.MALE);
		request.getSocialUserDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		return request;
	}


	@Test
	public void testIsTokenValid() throws Exception{
		IsTokenValidRequest request = new IsTokenValidRequest() ;
		request.setToken("LcLA6_peZgw8pcmINCBtAxXfwu28sdn4Zq4sQbYdwCaCSxQXV_4NfhSf_PXD-73ddC9sTsE55tS_tWzzh5tsvQ");
		IsTokenValidResponse response = userServiceClient.validateToken(request);
		System.out.println(response);
	}   

	
   @Test
   public void verifySocialUserWithMobileRequestTest() 
            throws ServiceException,
                   Exception {

      CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
      request.setOtp("0146");
      request.setOtpId("55");

      CreateUserResponse response = userServiceClient
               .verifySocialUserWithMobile(request);
      System.out.println(response);
   }
  
   @Test
   public void getIMSUserVerificationUrl() throws HttpTransportException, ServiceException {

      GetIMSUserVerificationUrlRequest request = new GetIMSUserVerificationUrlRequest();
      request.setEmail("devendramudaliar@gmail.com");
      request.setPurpose(VerificationPurpose.VERIFY_NEW_USER);
      request.setMerchant(Merchant.FREECHARGE);
      GetIMSUserVerificationUrlResponse response = userServiceClient
               .getIMSUserVerificationUrl(request);
      System.out.println(response);

   }
   @Test
	public void closeAccountTest() throws ServiceException, Exception {
		CloseAccountByEmailRequest request = new CloseAccountByEmailRequest();
		request.setEmailId("zawar.siddhant1234@gmail.com");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		CloseAccountResponse response = userServiceClient.closeAccount(request);
		System.out.println(response);
	}
	@Test
	public void verifyUserWithMobileTest() throws HttpTransportException, ServiceException {
		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("1234");
		request.setOtpId("e8dec505-39ad-427f-bde4-b765ab43d87a");
		CreateUserResponse response = userServiceClient.verifyUserWithMobile(request);
		System.out.println(response);
	}


	@Test
	public void createUserMobileOnly() throws HttpTransportException, ServiceException{
		CreateUserWithMobileOnlyRequest createUserWithMobileOnlyRequest = new CreateUserWithMobileOnlyRequest();
		String num= "792";
		createUserWithMobileOnlyRequest.setMobileNumber("6403303"+num);
		OTPResponse otpResponse = userServiceClient.createMobileOnlyUserGenerateOTP(createUserWithMobileOnlyRequest);
		System.out.println(otpResponse);
		CreateUserMobileVerifyRequest createUserMobileVerifyRequest = new CreateUserMobileVerifyRequest();
		createUserMobileVerifyRequest.setOtp("1234");
		createUserMobileVerifyRequest.setOtpId(otpResponse.getOtpId());
		CreateUserResponse response = userServiceClient.loginWithOtp(createUserMobileVerifyRequest);
		System.out.println(response);
	}
	
	@Test
	public void loginMobileOnlyviaEmailOTP() throws HttpTransportException, ServiceException{
		String num = "124";
		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		request.getUserRequestDto().setPassword("password");
		request.getUserRequestDto().setMobileNumber("6403303"+num);
		request.getUserRequestDto().setEmailId(
				"siddhant.zawar"+num+"@gmail.com");
		request.getUserRequestDto().setFirstName("John");
		request.getUserRequestDto().setMiddleName("Kumar");
		request.getUserRequestDto().setLastName("Cena");
		request.getUserRequestDto().setDisplayName("Johnson");
		request.getUserRequestDto().setDob("1990-12-05");
		request.getUserRequestDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		OTPResponse response = userServiceClient.createUserWithMobile(request);
		System.out.println(response);
		CreateUserMobileVerifyRequest createUserMobileVerifyRequest = new CreateUserMobileVerifyRequest();
		createUserMobileVerifyRequest.setOtp("1234");
		createUserMobileVerifyRequest.setOtpId(response.getOtpId());
		createUserMobileVerifyRequest.setUserMachineIdentifier("jndf");
		createUserMobileVerifyRequest.setUserAgent("abc");
		CreateUserResponse createUserResponse = userServiceClient.verifyUserWithMobile(createUserMobileVerifyRequest);
		System.out.println(createUserResponse);
		GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
		generateOTPRequest.setEmailId(createUserResponse.getUserDetails().getEmailId());
	//	generateOTPRequest.setMobileNumber(createUserResponse.getUserDetails().getMobileNumber());
		generateOTPRequest.setPurpose(OTPPurpose.LOGIN_WITH_EMAIL_OTP);
	//	generateOTPRequest.setToken(createUserResponse.getTokenInformationDTO().getToken());
		GenerateOTPResponse generateOTPResponse = otpServiceClient.sendOTP(generateOTPRequest);
		System.out.println(generateOTPResponse);
		CreateUserMobileVerifyRequest createUserMobileVerifyRequest1 = new CreateUserMobileVerifyRequest();
		createUserMobileVerifyRequest1.setOtp("1234");
		createUserMobileVerifyRequest1.setOtpId(generateOTPResponse.getOtpId());
		CreateUserResponse createUserResponse1 = userServiceClient.loginWithOtp(createUserMobileVerifyRequest1);
		System.out.println(createUserResponse1);
	}
	
	@Test
	public void loginMobileOnlyViaMobileOTP() throws HttpTransportException, ServiceException{
		String num = "311";
		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		request.getUserRequestDto().setPassword("password");
		request.getUserRequestDto().setMobileNumber("6403303"+num);
		request.getUserRequestDto().setEmailId(
				"siddhant.zawar"+num+"@gmail.com");
		request.getUserRequestDto().setFirstName("John");
		request.getUserRequestDto().setMiddleName("Kumar");
		request.getUserRequestDto().setLastName("Cena");
		request.getUserRequestDto().setDisplayName("Johnson");
		request.getUserRequestDto().setDob("1990-12-05");
		request.getUserRequestDto().setLanguagePref(Language.ENGLISH);
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		OTPResponse response = userServiceClient.createUserWithMobile(request);
		System.out.println(response);
		CreateUserMobileVerifyRequest createUserMobileVerifyRequest = new CreateUserMobileVerifyRequest();
		createUserMobileVerifyRequest.setOtp("1234");
		createUserMobileVerifyRequest.setOtpId(response.getOtpId());
		createUserMobileVerifyRequest.setUserMachineIdentifier("jndf");
		createUserMobileVerifyRequest.setUserAgent("abc");
		CreateUserResponse createUserResponse = userServiceClient.verifyUserWithMobile(createUserMobileVerifyRequest);
		System.out.println(createUserResponse);
		GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
		//generateOTPRequest.setEmailId(createUserResponse.getUserDetails().getEmailId());
		generateOTPRequest.setMobileNumber(createUserResponse.getUserDetails().getMobileNumber());
		generateOTPRequest.setPurpose(OTPPurpose.LOGIN_WITH_MOBILE_OTP);
		//generateOTPRequest.setToken(createUserResponse.getTokenInformationDTO().getToken());
		GenerateOTPResponse generateOTPResponse = otpServiceClient.sendOTP(generateOTPRequest);
		System.out.println(generateOTPResponse);
		CreateUserMobileVerifyRequest createUserMobileVerifyRequest1 = new CreateUserMobileVerifyRequest();
		createUserMobileVerifyRequest1.setOtp("1234");
		createUserMobileVerifyRequest1.setOtpId(generateOTPResponse.getOtpId());
		CreateUserResponse createUserResponse1 = userServiceClient.loginWithOtp(createUserMobileVerifyRequest1);
		System.out.println(createUserResponse1);
	}

	@Test
	public void isMobileOnly() throws HttpTransportException, ServiceException{
		MobileOnlyRequest request = new MobileOnlyRequest();
		request.setMobileNumber("9403303795");
		MobileOnlyResponse response = userServiceClient.isMobileOnly(request);
		System.out.println(response);
	}
	
	@Test
	public void configureUserState() throws HttpTransportException, ServiceException{
		ConfigureUserStateRequest request = new ConfigureUserStateRequest();
		request.setMobileNumber("9403303795");
		ConfigureUserStateResponse response = userServiceClient.configureUserState(request);
		System.out.println(response);
	}

}