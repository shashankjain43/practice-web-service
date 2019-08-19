package com.snapdeal.ims.controller;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.SendForgotPasswordLinkRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class LoginUserManagementControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private LoginUserManagementController loginUserManagementController;
	@Mock
	private ILoginUserService loginUserService;
	@Mock
	private AuthorizationContext context;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(
					loginUserManagementController).build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testSignInSuccess() throws Exception {

		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidLoginUserRequest());

		when(loginUserService.loginUser(any(LoginUserRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("Invalid response from signin",
				getValidLoginUserResponse(), loginUserResponse);
	}

	@Test
	@Ignore
	public void testSignInFailWithWrongParameters() throws Exception {

		Gson gson = new Gson();
		String invalidRequestString = gson.toJson(getInvalidLoginUserRequest());

		when(loginUserService.loginUser(any(LoginUserRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN).contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter a valid email address", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testSignInFailWithNoUserAgent() throws Exception {

		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidLoginUserRequest());

		when(loginUserService.loginUser(any(LoginUserRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString())).thenReturn(
				null);

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Machine identifier and user agent are mandatory.", result
						.getResolvedException().getMessage());
	}
	
	@Test
	public void testSignInFailWithProvidingBothEmailAndMobileNumber() throws Exception {

		Gson gson = new Gson();
		String validRequestString = gson.toJson(getLoginUserRequestWithBothEmailAndNumber());

		when(loginUserService.loginUser(any(LoginUserRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString())).thenReturn(
				"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please provide only email id or mobile number", result
						.getResolvedException().getMessage());
	}

	@Test
	@Ignore
	public void testSignInWithTokenSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidLoginWithTokenRequest());

		when(
				loginUserService
						.loginUserWithToken(any(LoginWithTokenRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN_TOKEN).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("Invalid response from signin",
				getValidLoginUserResponse(), loginUserResponse);
	}

	@Test
	public void testSignInWithTokenFailWithWrongParameters() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getInvalidLoginWithTokenRequest());

		when(
				loginUserService
						.loginUserWithToken(any(LoginWithTokenRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN_TOKEN).contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))

				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Global Token is blank", result.getResolvedException()
						.getMessage());
	}

	@Test
	@Ignore
	public void testSignInWithTokenFailWithWrongUserAgent() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidLoginWithTokenRequest());

		when(
				loginUserService
						.loginUserWithToken(any(LoginWithTokenRequest.class)))
				.thenReturn(getValidLoginUserResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString())).thenReturn(
				null);

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNIN_TOKEN).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
				LoginUserResponse.class);

		Assert.assertEquals("Invalid response from signin",
				getValidLoginUserResponse(), loginUserResponse);
	}

	@Test
	public void testSignoutSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidSignoutRequest());

		when(
				loginUserService.signout(any(SignoutRequest.class),
						(HttpHeaders) eq(null))).thenReturn(
				getValidSignoutResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNOUT).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		SignoutResponse signoutResponse = getObjectFromJsonString(result,
				SignoutResponse.class);

		Assert.assertEquals("Invalid response from signout",
				getValidSignoutResponse(), signoutResponse);
	}

	@Test
	public void testSignoutWithNullToken() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson.toJson(getInvalidSignoutRequest());

		when(
				loginUserService.signout(any(SignoutRequest.class),
						(HttpHeaders) eq(null))).thenReturn(
				getValidSignoutResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.SIGNOUT).contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		SignoutResponse signoutResponse = getObjectFromJsonString(result,
				SignoutResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Token is blank", result.getResolvedException().getMessage());
	}

	@Test
	@Ignore
	public void testSendForgotPasswordLinkSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidSendForgotPasswordLinkRequest());

		when(loginUserService.sendForgotPasswordLink(any(String.class)))
				.thenReturn(getValidSendForgotPasswordLinkResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		SendForgotPasswordLinkResponse sendForgotPasswordLinkResponse = getObjectFromJsonString(
				result, SendForgotPasswordLinkResponse.class);

		Assert.assertEquals("Invalid response from SendForgotPasswordLink",
				getValidSendForgotPasswordLinkResponse(),
				sendForgotPasswordLinkResponse);
	}

	@Test
	public void testSendForgotPasswordLinkWithWrongParameters()
			throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getInvalidSendForgotPasswordLinkRequest());

		when(loginUserService.sendForgotPasswordLink(any(String.class)))
				.thenReturn(getValidSendForgotPasswordLinkResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD).contentType(
								MediaType.APPLICATION_JSON).content(
								invalidRequestString))

				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		SendForgotPasswordLinkResponse sendForgotPasswordLinkResponse = getObjectFromJsonString(
				result, SendForgotPasswordLinkResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter email", result.getResolvedException()
						.getMessage());

	}

	@Test
	@Ignore
	public void testSendForgotPasswordLinkWithFalseResponse() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidSendForgotPasswordLinkRequest());

		when(loginUserService.sendForgotPasswordLink(any(String.class)))
				.thenReturn(getInvalidSendForgotPasswordLinkResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD).contentType(
								MediaType.APPLICATION_JSON).content(
								validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		SendForgotPasswordLinkResponse sendForgotPasswordLinkResponse = getObjectFromJsonString(
				result, SendForgotPasswordLinkResponse.class);

		Assert.assertEquals("Invalid response from SendForgotPasswordLink",
				false, sendForgotPasswordLinkResponse.isSuccess());

	}

	@Test
	public void testVerifyUserSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidVerifyRequest());
		when(
				loginUserService
						.verifyUserAndResetPassword(any(VerifyAndResetPasswordRequest.class)))
				.thenReturn(getValidVerifyResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))

				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		VerifyUserResponse verifyUserResponse = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertEquals("Invalid response from VerifyUser", null,
				verifyUserResponse.getStatus());

	}

	@Test
	public void testVerifyUserWithWrongPassword() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getInvalidVerifyRequestWithNullPassword());

		when(
				loginUserService
						.verifyUserAndResetPassword(any(VerifyAndResetPasswordRequest.class)))
				.thenReturn(getValidVerifyResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))

				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		VerifyUserResponse verifyUserResponse = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter your password", result.getResolvedException()
						.getMessage());
	}

	@Test
	public void testVerifyUserWithWrongVerificationCode() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getInvalidVerifyRequestWithNullVerificationCode());

		when(
				loginUserService
						.verifyUserAndResetPassword(any(VerifyAndResetPasswordRequest.class)))
				.thenReturn(getValidVerifyResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))

				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		VerifyUserResponse verifyUserResponse = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Code is blank", result.getResolvedException().getMessage());
	}

	@Test
	public void testVerifyUserWithWrongNewPassword() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getInvalidVerifyRequestWithNullNewPassword());

		when(
				loginUserService
						.verifyUserAndResetPassword(any(VerifyAndResetPasswordRequest.class)))
				.thenReturn(getValidVerifyResponse());

		when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
				.thenReturn(
						"Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

		when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
				.thenReturn("605718d93d1b");

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.FORGOT_PASSWORD_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))

				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		VerifyUserResponse verifyUserResponse = getObjectFromJsonString(result,
				VerifyUserResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Please enter your password", result.getResolvedException()
						.getMessage());
	}

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

	private VerifyAndResetPasswordRequest getValidVerifyRequest() {

		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setConfirmPassword("password");
		request.setNewPassword("password2");
		request.setVerificationCode("code1");
		return request;

	}
	
	@Test
	@Ignore
   public void testSignInWithTransferTokenSuccess() throws Exception {
      Gson gson = new Gson();
      String validRequestString = gson
            .toJson(getValidLoginWithTransferTokenRequest());

      when(
            loginUserService
                  .loginUserWithTransferToken(any(LoginWithTransferTokenRequest.class)))
            .thenReturn(getValidLoginUserResponse());

      when(context.get(IMSRequestHeaders.USER_AGENT.toString()))
            .thenReturn(
                  "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");

      when(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()))
            .thenReturn("605718d93d1b");

      MvcResult result = this.mockMvc
            .perform(
                  post(RestURIConstants.SIGNIN_TRANSFER_TOKEN).contentType(
                        MediaType.APPLICATION_JSON).content(
                        validRequestString))

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
      LoginUserResponse loginUserResponse = getObjectFromJsonString(result,
            LoginUserResponse.class);

      Assert.assertEquals("Invalid response from signin",
            getValidLoginUserResponse(), loginUserResponse);
   }

	private VerifyAndResetPasswordRequest getInvalidVerifyRequestWithNullPassword() {

		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setConfirmPassword(null);
		request.setNewPassword("password2");
		request.setVerificationCode("code1");
		return request;

	}

	private VerifyAndResetPasswordRequest getInvalidVerifyRequestWithNullVerificationCode() {

		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setConfirmPassword("password");
		request.setNewPassword("password2");
		request.setVerificationCode(null);
		return request;

	}

	private VerifyAndResetPasswordRequest getInvalidVerifyRequestWithNullNewPassword() {

		VerifyAndResetPasswordRequest request = new VerifyAndResetPasswordRequest();
		request.setConfirmPassword("password");
		request.setNewPassword(null);
		request.setVerificationCode("code1");
		return request;

	}

	private VerifyUserResponse getValidVerifyResponse() {
		VerifyUserResponse response = new VerifyUserResponse();
		response.setStatus(StatusEnum.SUCCESS);
		return response;

	}

	private SendForgotPasswordLinkRequest getValidSendForgotPasswordLinkRequest() {
		SendForgotPasswordLinkRequest request = new SendForgotPasswordLinkRequest();
		request.setEmail("testuser@yopmail.com");
		return request;
	}

	private SendForgotPasswordLinkRequest getInvalidSendForgotPasswordLinkRequest() {
		SendForgotPasswordLinkRequest request = new SendForgotPasswordLinkRequest();
		request.setEmail(null);
		return request;
	}

	private SendForgotPasswordLinkResponse getValidSendForgotPasswordLinkResponse() {
		SendForgotPasswordLinkResponse response = new SendForgotPasswordLinkResponse();
		response.setSuccess(true);
		return response;
	}

	private SendForgotPasswordLinkResponse getInvalidSendForgotPasswordLinkResponse() {
		SendForgotPasswordLinkResponse response = new SendForgotPasswordLinkResponse();
		response.setSuccess(false);
		return response;
	}

	private SignoutRequest getValidSignoutRequest() {
		SignoutRequest request = new SignoutRequest();
		request.setHardSignout(false);
		request.setToken("testtoken");
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private SignoutRequest getInvalidSignoutRequest() {
		SignoutRequest request = new SignoutRequest();
		request.setHardSignout(false);
		request.setToken(null);
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private SignoutResponse getValidSignoutResponse() {
		SignoutResponse response = new SignoutResponse();
		response.setSuccess(true);
		return response;
	}

	private LoginWithTokenRequest getValidLoginWithTokenRequest() {
		LoginWithTokenRequest request = new LoginWithTokenRequest();
		request.setGlobalToken("globaltoken");
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private LoginWithTokenRequest getInvalidLoginWithTokenRequest() {
		LoginWithTokenRequest request = new LoginWithTokenRequest();
		request.setGlobalToken(null);
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private LoginUserRequest getValidLoginUserRequest() {

		LoginUserRequest request = new LoginUserRequest();
		request.setEmailId("testuser@yopmail.com");
		request.setMobileNumber("");
		request.setPassword("password");
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}
	
	private LoginUserRequest getLoginUserRequestWithBothEmailAndNumber() {

		LoginUserRequest request = new LoginUserRequest();
		request.setEmailId("testuser@yopmail.com");
		request.setMobileNumber("9999999999");
		request.setPassword("password");
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private LoginUserRequest getInvalidLoginUserRequest() {

		LoginUserRequest request = new LoginUserRequest();
		request.setEmailId("testuser@yopm");
		request.setMobileNumber("9990");
		request.setPassword("password");
		request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
		request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
		return request;
	}

	private LoginUserResponse getValidLoginUserResponse() {

		LoginUserResponse response = new LoginUserResponse();

		TokenInformationDTO tokenInformation = new TokenInformationDTO();
		tokenInformation.setGlobalToken("testglobal");
		tokenInformation.setGlobalTokenExpiry("testglobalexpiry");
		tokenInformation.setToken("testtoken");
		tokenInformation.setTokenExpiry("tokenexpiry");

		response.setTokenInformation(tokenInformation);
		response.setUpgradationInformation(new UpgradationInformationDTO());

		UserDetailsDTO userDetails = new UserDetailsDTO();
		userDetails.setUserId("userId1");
		userDetails.setEmailId("testuser@yopmail.com");
		userDetails.setFirstName("testuser");
		userDetails.setSdUserId(45);
		response.setUserDetails(userDetails);

		UserSocialDetailsDTO userSocialDetails = new UserSocialDetailsDTO();
		userSocialDetails.setAboutMe("hii");
		userSocialDetails.setPhotoURL("http://myphoto");
		userSocialDetails.setSocialId("socialId1");
		response.setUserSocialDetails(userSocialDetails);

		return response;
	}
	
	private LoginWithTransferTokenRequest getValidLoginWithTransferTokenRequest()
	{
	   LoginWithTransferTokenRequest request = new LoginWithTransferTokenRequest();
      request.setToken("globaltoken");
      request.setUserAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
      request.setUserMachineIdentifier("119f2f47-70f2-4df5-aa3e-e95f761e4e9c");
      return request;
	}
}
