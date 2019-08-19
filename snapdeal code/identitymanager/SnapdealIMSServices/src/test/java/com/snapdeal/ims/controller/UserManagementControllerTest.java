package com.snapdeal.ims.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Reason;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
//import com.snapdeal.ims.test.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.IUserService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
//@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class UserManagementControllerTest {

	private MockMvc mockMvc;
	@Mock
	private IUserService userService;

	@InjectMocks
	private UserManagementController userManagementController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(userManagementController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private CreateUserEmailRequest getValidCreateUserEmailRequest() {

		CreateUserEmailRequest request = new CreateUserEmailRequest();

		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();
		userDetailsByEmailDto.setDisplayName("abhi");
		userDetailsByEmailDto.setFirstName("testFirst");
		userDetailsByEmailDto.setEmailId("test@yopmail.com");
		userDetailsByEmailDto.setLastName("testLast");
		userDetailsByEmailDto.setMiddleName("testMiddle");
		userDetailsByEmailDto.setPassword("password");
		userDetailsByEmailDto.setGender(Gender.MALE);
		userDetailsByEmailDto.setLanguagePref(Language.ENGLISH);
		userDetailsByEmailDto.setDob("1993-09-21");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);
		return request;
	}

	private CreateUserEmailRequest getCreateUserEmailRequestWithWrongDob() {

		CreateUserEmailRequest request = new CreateUserEmailRequest();

		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();
		userDetailsByEmailDto.setDisplayName("abhi");
		userDetailsByEmailDto.setFirstName("testFirst");
		userDetailsByEmailDto.setEmailId("test@yopmail.com");
		userDetailsByEmailDto.setLastName("testLast");
		userDetailsByEmailDto.setMiddleName("testMiddle");
		userDetailsByEmailDto.setPassword("password");
		userDetailsByEmailDto.setGender(Gender.MALE);
		userDetailsByEmailDto.setLanguagePref(Language.ENGLISH);
		userDetailsByEmailDto.setDob("iiiiiiiiiii");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);
		return request;
	}

	private CreateUserEmailRequest getCreateUserEmailRequestWithWrongEmail() {

		CreateUserEmailRequest request = new CreateUserEmailRequest();

		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();
		userDetailsByEmailDto.setDisplayName("abhi");
		userDetailsByEmailDto.setFirstName("testFirst");
		userDetailsByEmailDto.setEmailId("cominvalideemail");
		userDetailsByEmailDto.setLastName("testLast");
		userDetailsByEmailDto.setMiddleName("testMiddle");
		userDetailsByEmailDto.setPassword("password");
		userDetailsByEmailDto.setGender(Gender.MALE);
		userDetailsByEmailDto.setLanguagePref(Language.ENGLISH);
		userDetailsByEmailDto.setDob("1993-01-09");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);
		return request;
	}

	private CreateUserEmailRequest getCreateUserEmailRequestWithWrongName() {

		CreateUserEmailRequest request = new CreateUserEmailRequest();

		UserDetailsByEmailRequestDto userDetailsByEmailDto = new UserDetailsByEmailRequestDto();
		userDetailsByEmailDto.setDisplayName("abhi");
		userDetailsByEmailDto.setFirstName("!!!!!!");
		userDetailsByEmailDto.setEmailId("test@yopmail.com");
		userDetailsByEmailDto.setLastName("testLast");
		userDetailsByEmailDto.setMiddleName("testMiddle");
		userDetailsByEmailDto.setPassword("password");
		userDetailsByEmailDto.setGender(Gender.MALE);
		userDetailsByEmailDto.setLanguagePref(Language.ENGLISH);
		userDetailsByEmailDto.setDob("1993-03-09");

		request.setUserDetailsByEmailDto(userDetailsByEmailDto);
		return request;
	}

	private CreateUserResponse getValidCreateUserResponse() {

		CreateUserResponse response = new CreateUserResponse();
		TokenInformationDTO tokenInformation = new TokenInformationDTO();
		tokenInformation.setGlobalToken("global1");
		tokenInformation.setGlobalTokenExpiry("expiry");
		tokenInformation.setToken("token1");
		tokenInformation.setTokenExpiry("tokenExpiry");
		response.setTokenInformationDTO(tokenInformation);
		UserDetailsDTO userDetails = new UserDetailsDTO();
		userDetails.setDisplayName("abhi");
		userDetails.setFirstName("testFirst");
		userDetails.setEmailId("test@yopmail.com");
		userDetails.setLastName("testLast");
		userDetails.setMiddleName("testMiddle");
		userDetails.setGender(Gender.MALE);
		userDetails.setLanguagePref(Language.ENGLISH);
		userDetails.setDob("1993-01-09");
		response.setUserDetails(userDetails);
		return response;

	}

	private CreateGuestUserEmailRequest getValidGuestUserRequest() {

		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();
		request.setEmailId("test@yopmail.com");
		request.setPurpose("testPurpose");
		return request;

	}

	private CreateGuestUserEmailRequest getGuestUserRequestWithWrongEmail() {

		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();
		request.setEmailId("wrongemail");
		request.setPurpose("testPurpose");
		return request;

	}

	private CreateGuestUserEmailRequest getGuestUserRequestWithWrongPurpose() {

		CreateGuestUserEmailRequest request = new CreateGuestUserEmailRequest();
		request.setEmailId("test@yopmail.com");
		request.setPurpose("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
		         + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
		         + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
		         + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
		         + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		return request;

	}

	private CreateGuestUserResponse getValidGuestUserResponse() {
		CreateGuestUserResponse response = new CreateGuestUserResponse();
		response.setUserId("testUser1");
		response.setSdUserId("sdUserId");
		return response;
	}

	private VerifyUserRequest getValidVerifyRequest() {

		VerifyUserRequest request = new VerifyUserRequest();
		request.setCode("testcode1");
		return request;
	}

	private VerifyUserResponse getValidVerifyResponse() {

		VerifyUserResponse response = new VerifyUserResponse();
		response.setStatus(StatusEnum.SUCCESS);
		return response;
	}
	
   private ResendEmailVerificationLinkResponse getValidResendEmailVerificationLinkResponse() {

      ResendEmailVerificationLinkResponse response = new ResendEmailVerificationLinkResponse();
      response.setSuccess(true);
      return response;
   }
   
   private ConfigureUserStateResponse getValidConfigureUserStateResponse() {

      ConfigureUserStateResponse response = new ConfigureUserStateResponse();
      response.setStatus(StatusEnum.SUCCESS);
      return response;
   }

	private CreateUserMobileGenerateRequest getValidCreateUserMobileGenerateRequest() {

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userDetails = new UserRequestDto();
		userDetails.setDisplayName("abhi");
		userDetails.setFirstName("testFirst");
		userDetails.setEmailId("test@yopmail.com");
		userDetails.setLastName("testLast");
		userDetails.setMiddleName("testMiddle");
		userDetails.setGender(Gender.MALE);
		userDetails.setLanguagePref(Language.ENGLISH);
		userDetails.setDob("1993-01-09");
		userDetails.setMobileNumber("9999999999");
		userDetails.setPassword("password");
		request.setUserRequestDto(userDetails);
		return request;

	}

	private CreateUserMobileGenerateRequest getCreateUserMobileWithWrongEmail() {

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userDetails = new UserRequestDto();
		userDetails.setDisplayName("abhi");
		userDetails.setFirstName("testFirst");
		userDetails.setEmailId("hhh");
		userDetails.setLastName("testLast");
		userDetails.setMiddleName("testMiddle");
		userDetails.setGender(Gender.MALE);
		userDetails.setLanguagePref(Language.ENGLISH);
		userDetails.setDob("1993-01-09");
		userDetails.setPassword("password");
		userDetails.setMobileNumber("9999999999");
		request.setUserRequestDto(userDetails);
		return request;

	}

	private CreateUserMobileGenerateRequest getCreateUserMobileWithWrongName() {

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userDetails = new UserRequestDto();
		userDetails.setDisplayName("abh!!!i");
		userDetails.setFirstName("testFirst");
		userDetails.setEmailId("test@yopmail.com");
		userDetails.setLastName("testLast");
		userDetails.setMiddleName("testMiddle");
		userDetails.setGender(Gender.MALE);
		userDetails.setLanguagePref(Language.ENGLISH);
		userDetails.setDob("1993-01-09");
		userDetails.setPassword("password");
		userDetails.setMobileNumber("9999999999");
		request.setUserRequestDto(userDetails);
		return request;

	}

	private CreateUserMobileGenerateRequest getCreateUserMobileWithWrongPassword() {

		CreateUserMobileGenerateRequest request = new CreateUserMobileGenerateRequest();
		UserRequestDto userDetails = new UserRequestDto();
		userDetails.setDisplayName("abhi");
		userDetails.setFirstName("testFirst");
		userDetails.setEmailId("test@yopmail.com");
		userDetails.setLastName("testLast");
		userDetails.setMiddleName("testMiddle");
		userDetails.setGender(Gender.MALE);
		userDetails.setLanguagePref(Language.ENGLISH);
		userDetails.setDob("1993-01-09");
		userDetails.setMobileNumber("9999999999");
		userDetails.setPassword("pwd");
		request.setUserRequestDto(userDetails);
		return request;

	}

	private OTPResponse getValidOTPResponse() {

		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setOtpId("otpId1");
		otpResponse.setAccountState("testState");
		return otpResponse;
	}

	private CreateUserMobileVerifyRequest getValidCreateUserMobileVerifyRequest() {

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("tstOtp");
		request.setOtpId("otpId");
		return request;

	}

	private CreateUserMobileVerifyRequest getMobileVerifyRequestWithWrongOtp() {

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp(null);
		request.setOtpId("otpId");
		return request;

	}

	private CreateUserMobileVerifyRequest getMobileVerifyRequestWithWrongOtpid() {

		CreateUserMobileVerifyRequest request = new CreateUserMobileVerifyRequest();
		request.setOtp("testOtp");
		request.setOtpId("");
		return request;

	}

	@Test
	@Ignore
	public void testCreateUserWithEmailSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidCreateUserEmailRequest());
		when(userService.createUserByEmail(any(CreateUserEmailRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/email").contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		CreateUserResponse response = getObjectFromJsonString(result,
				CreateUserResponse.class);

		Assert.assertEquals("Invalid response from create user with email",
				getValidCreateUserResponse(), response);
	}

	@Test
	public void testCreateUserWithEmailWithWrongDob() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserEmailRequestWithWrongDob());
		when(userService.createUserByEmail(any(CreateUserEmailRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/email").contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please provide a valid date of birth in format yyyy-mm-dd",
				result.getResolvedException().getMessage());
	}

	@Test
	public void testCreateUserWithEmailWithWrongName() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserEmailRequestWithWrongName());
		when(userService.createUserByEmail(any(CreateUserEmailRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/email").contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid value for name.", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testCreateUserWithEmailWithWrongEmail() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserEmailRequestWithWrongEmail());
		when(userService.createUserByEmail(any(CreateUserEmailRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/email").contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid email address", result
						.getResolvedException().getMessage());
	}

	@Test
	@Ignore
	public void testCreateGuestUserWithEmailSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidGuestUserRequest());
		when(
				userService
						.createGuestUserByEmail(any(CreateGuestUserEmailRequest.class)))
				.thenReturn(getValidGuestUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/guest/create")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		CreateGuestUserResponse response = getObjectFromJsonString(result,
				CreateGuestUserResponse.class);

		Assert.assertEquals("Invalid response from create user with email",
				getValidGuestUserResponse(), response);
	}

	@Test
	public void testCreateGuestUserWithEmailWithWrongEmail() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getGuestUserRequestWithWrongEmail());
		when(
				userService
						.createGuestUserByEmail(any(CreateGuestUserEmailRequest.class)))
				.thenReturn(getValidGuestUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/guest/create")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid email address", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testCreateGuestUserWithEmailWithWrongPurpose() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getGuestUserRequestWithWrongPurpose());
		when(
				userService
						.createGuestUserByEmail(any(CreateGuestUserEmailRequest.class)))
				.thenReturn(getValidGuestUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/guest/create")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Purpose length exceeded permissable limit", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testVerifyUserSuccess() throws Exception {
		when(userService.verifyUser(any(VerifyUserRequest.class))).thenReturn(
				getValidVerifyResponse());

		MvcResult result = this.mockMvc
				.perform(get(RestURIConstants.USER + "/guest/verify/288"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		VerifyUserResponse response = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertNotNull("Verify user failed", response);
	}

	@Test
	public void testVerifyUserWithNoCode() throws Exception {
		when(userService.verifyUser(any(VerifyUserRequest.class))).thenReturn(
				getValidVerifyResponse());

		MvcResult result = this.mockMvc
				.perform(get(RestURIConstants.USER + "/guest/verify/"))
				.andExpect(status().is4xxClientError()).andReturn();
		VerifyUserResponse response = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertNull("Verify user passed even without code", response);
	}

	@Test
	@Ignore
	public void testCreateUserWithMobileSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidCreateUserMobileGenerateRequest());
		when(
				userService
						.createUserWithMobile(any(CreateUserMobileGenerateRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/generate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		OTPResponse response = getObjectFromJsonString(result,
				OTPResponse.class);

		Assert.assertEquals("Invalid response from create user with email",
				getValidOTPResponse(), response);

	}

	@Test
	public void testCreateUserWithMobileWithWrongEmail() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserMobileWithWrongEmail());
		when(
				userService
						.createUserWithMobile(any(CreateUserMobileGenerateRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/generate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid email address", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testCreateUserWithMobileWithWrongName() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserMobileWithWrongName());
		when(
				userService
						.createUserWithMobile(any(CreateUserMobileGenerateRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/generate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid value for name.", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testCreateUserWithMobileWithWrongPassword() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getCreateUserMobileWithWrongPassword());
		when(
				userService
						.createUserWithMobile(any(CreateUserMobileGenerateRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/generate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Password length must be minimum 6 characters", result
						.getResolvedException().getMessage());
	}

	@Test
	@Ignore
	public void testVerifyUserWithMobileSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidCreateUserMobileVerifyRequest());
		when(
				userService
						.verifyUserWithMobile(any(CreateUserMobileVerifyRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/verify")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		CreateUserResponse response = getObjectFromJsonString(result,
				CreateUserResponse.class);

		Assert.assertEquals("Invalid response from create user with email",
				getValidCreateUserResponse(), response);

	}

	@Test
	public void testVerifyUserWithMobileWithWrongOtp() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getMobileVerifyRequestWithWrongOtp());
		when(
				userService
						.verifyUserWithMobile(any(CreateUserMobileVerifyRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/verify")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter your OTP", result.getResolvedException()
						.getMessage());

	}

	@Test
	public void testVerifyUserWithMobileWithWrongOtpid() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getMobileVerifyRequestWithWrongOtpid());
		when(
				userService
						.verifyUserWithMobile(any(CreateUserMobileVerifyRequest.class)))
				.thenReturn(getValidCreateUserResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/mobile/verify")
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"OTP ID is blank", result.getResolvedException().getMessage());

	}
	
   @Test
   @Ignore
   public void testConfigureUserStateSuccess() throws Exception {
      Gson gson = new Gson();
      String validRequestString = gson.toJson(getValidConfigureUserStateRequest());
      when(userService.configureUserState(any(ConfigureUserStateRequest.class))).thenReturn(
               getValidConfigureUserStateResponse());

      MvcResult result = this.mockMvc
               .perform(put(RestURIConstants.USER + "/enable").contentType(
                        MediaType.APPLICATION_JSON).content(validRequestString))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
      ConfigureUserStateResponse response = getObjectFromJsonString(result, ConfigureUserStateResponse.class);

      Assert.assertEquals("Invalid response from create user with email",
               getValidConfigureUserStateResponse(), response);
   }
   
   @Test
   @Ignore
   public void testConfigureUserStateWithWrongToken() throws Exception {
      Gson gson = new Gson();
      String invalidRequestString = gson.toJson(getInValidConfigureUserStateRequestToken());
      when(userService.configureUserState(any(ConfigureUserStateRequest.class))).thenReturn(
               getValidConfigureUserStateResponse());

      MvcResult result = this.mockMvc
               .perform(
                     put(RestURIConstants.USER + "/enable").contentType(
                           MediaType.APPLICATION_JSON).content(
                                    invalidRequestString))
               .andExpect(status().is4xxClientError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andReturn();
      
      Assert.assertEquals("RequestParameterException is not thrown", result
               .getResolvedException().getClass(),
               RequestParameterException.class);

         Assert.assertEquals("RequestParameterException is not thrown",
               "Token exceeded its permissible limit of 127 characters", result
                     .getResolvedException().getMessage());
   }
   
   @Test
   @Ignore
   public void testConfigureUserStateWithWrongUserId() throws Exception {
      Gson gson = new Gson();
      String invalidRequestString = gson.toJson(getInValidConfigureUserStateRequestUserId());
      when(userService.configureUserState(any(ConfigureUserStateRequest.class))).thenReturn(
               getValidConfigureUserStateResponse());

      MvcResult result = this.mockMvc
               .perform(
                     put(RestURIConstants.USER + "/enable").contentType(
                           MediaType.APPLICATION_JSON).content(
                                    invalidRequestString))
               .andExpect(status().is4xxClientError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andReturn();
      
      Assert.assertEquals("RequestParameterException is not thrown", result
               .getResolvedException().getClass(),
               RequestParameterException.class);

         Assert.assertEquals("RequestParameterException is not thrown",
               "User ID exceeded its permissible limit of 127 characters", result
                     .getResolvedException().getMessage());
   }
	
   private ConfigureUserStateRequest getValidConfigureUserStateRequest() {
     
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setToken("-DhEKXyP17DrBgw99K2L4NitvrEspoyRqCTpZYl2YSuRB6vQYz0yuINyUOCpOcoO-YfhjyI1qGmi-6wJumXzWw");
      request.setUserId("576837");
      request.setEnable(false);
      request.setReason(Reason.FRAUD);
      request.setDescription("initited by company");
      return request;
   
   }
   
   private ConfigureUserStateRequest getInValidConfigureUserStateRequestToken() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setToken("324924932748327984793279-DhEasdasdaKXyP17DrBgw99K2L4NitvrEspoyRqCTpZYl2YSuRB6vQYz0yuINyUOCpOcoO-YfhjyI1qGmi-6wJumXzWw342432432423322342");
      request.setUserId("576837");
      request.setEnable(false);
      request.setReason(Reason.FRAUD);
      request.setDescription("initited by company");
      return request;
   }
   
   private ConfigureUserStateRequest getInValidConfigureUserStateRequestUserId() {
      ConfigureUserStateRequest request = new ConfigureUserStateRequest();
      request.setToken("-DhEasdasdaKXyP17DrBgw99K2L4NitvrEspoyRqCTpZYl2YSuRB6vQYz0yuINyUOCpOcoO-YfhjyI1qGmi-6wJumXzWw34");
      request.setUserId("5768373ru0d3jfoijewafoindefconedvcosdnvwpu9ehviuwernviwbgiuvwerf9ug302u03u2030r9u203r023rhf03hc320f302f23fn230fn32f023fn230f023fn230fn20fn");
      request.setEnable(false);
      request.setReason(Reason.FRAUD);
      request.setDescription("initited by company");
      return request;
   }

   @Test
   @Ignore
   public void testResendEmailVerificationLinkSuccess() throws Exception {
      Gson gson = new Gson();
      ResendEmailVerificationLinkRequest request = new ResendEmailVerificationLinkRequest();
      request.setEmailId("test@yopmail.com");
      String validRequestString = gson.toJson(request);
      when(userService.resendEmailVerificationLink(any(ResendEmailVerificationLinkRequest.class))).thenReturn(
               getValidResendEmailVerificationLinkResponse());

      MvcResult result = this.mockMvc
               .perform(post(RestURIConstants.USER + "/resend/verify/email").contentType(
                        MediaType.APPLICATION_JSON).content(validRequestString))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
      ResendEmailVerificationLinkResponse response = getObjectFromJsonString(result, ResendEmailVerificationLinkResponse.class);

      Assert.assertEquals("Invalid response from create user with email",
               getValidResendEmailVerificationLinkResponse(), response);
   }
   
   
   @Test
   public void testResendEmailVerificationLinkWithWrongEmail() throws Exception {
      Gson gson = new Gson();
      ResendEmailVerificationLinkRequest request = new ResendEmailVerificationLinkRequest();
      request.setEmailId("testopmail.com");
      String invalidRequestString = gson
            .toJson(request);
      when(userService.resendEmailVerificationLink(any(ResendEmailVerificationLinkRequest.class)))
            .thenReturn(getValidResendEmailVerificationLinkResponse());

      MvcResult result = this.mockMvc
            .perform(
                  post(RestURIConstants.USER + "/resend/verify/email").contentType(
                        MediaType.APPLICATION_JSON).content(
                        invalidRequestString))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

      Assert.assertEquals("RequestParameterException is not thrown", result
            .getResolvedException().getClass(),
            RequestParameterException.class);

      Assert.assertEquals("RequestParameterException is not thrown",
            "Please enter a valid email address", result
                  .getResolvedException().getMessage());
   }

	/*
	 * @Test public void testCreateOrLoginSocialUser() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testUpdateUserById() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testUpdateUserByToken() { fail("Not yet implemented");
	 * }
	 * 
	 * @Test public void testGetUserByEmail() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetUser() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetUserByMobile() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetUserByToken() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testUpdateMobileMobileNumber() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testForgotPassword() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testChangePassword() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testResetPassword() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testIsUserExist() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testIsMobileExist() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testIsEmailExist() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testConfigureUserState() { fail("Not yet implemented");
	 * }
	 */

	private <T> T getObjectFromJsonString(MvcResult result, Class<T> classOfT) {

		Gson gson = new Gson();

		String content;
		try {
			content = result.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return gson.fromJson(content, classOfT);

	}

}
