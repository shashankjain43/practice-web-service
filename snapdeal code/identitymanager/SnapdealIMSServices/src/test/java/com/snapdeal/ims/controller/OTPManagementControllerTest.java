package com.snapdeal.ims.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.IsOTPValidRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.ims.service.IOTPServiceGeneration;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class OTPManagementControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private OTPManagementController otpManagementController;
	@Mock
	private IOTPServiceGeneration otpService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(otpManagementController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGenerateOTPSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidGenerateOTPRequest());
		when(otpService.generateAndSendOTP(any(GenerateOTPRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.GENERATE_OTP)
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		GenerateOTPResponse generateOtpResponse = getObjectFromJsonString(
				result, GenerateOTPResponse.class);

		Assert.assertEquals("Unable to generate OTP", getValidOTPResponse(),
				generateOtpResponse);
	}

	@Test
	public void testGenerateOTPWithWrongEmail() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getGenerateOTPRequestWithWrongEmail());
		when(otpService.generateAndSendOTP(any(GenerateOTPRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.GENERATE_OTP)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Email Id should not exceed 127 characters.", result
						.getResolvedException().getMessage());
	}

	@Test
	public void testGenerateOTPWithWrongToken() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getGenerateOTPRequestWithWrongToken());
		when(otpService.generateAndSendOTP(any(GenerateOTPRequest.class)))
				.thenReturn(getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.GENERATE_OTP)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Token is blank", result.getResolvedException().getMessage());
	}

	@Test
	public void testReSendOTPSuccess() throws Exception {

		Gson gson = new Gson();
		String validRequestString = gson.toJson(getValidResendOTPRequest());
		when(otpService.reSendOTP(any(ResendOTPRequest.class))).thenReturn(
				getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.OTP + RestURIConstants.RESEND_OTP)
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		GenerateOTPResponse resendOtpResponse = getObjectFromJsonString(result,
				GenerateOTPResponse.class);

		Assert.assertEquals("Unable to resend OTP", getValidOTPResponse(),
				resendOtpResponse);

	}

	@Test
	public void testReSendOTPWithWrongOtp() throws Exception {

		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getResendOTPRequestWithWrongOtp());
		when(otpService.reSendOTP(any(ResendOTPRequest.class))).thenReturn(
				getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.OTP + RestURIConstants.RESEND_OTP)
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
	public void testReSendOTPWithWrongToken() throws Exception {

		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getResendOTPRequestWithWrongToken());
		when(otpService.reSendOTP(any(ResendOTPRequest.class))).thenReturn(
				getValidOTPResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.OTP + RestURIConstants.RESEND_OTP)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals("RequestParameterException is not thrown", result
				.getResolvedException().getClass(),
				RequestParameterException.class);

		Assert.assertEquals("RequestParameterException is not thrown",
				"Token exceeded its permissible limit of 127 characters", result.getResolvedException().getMessage());

	}

   @Test
   public void testIsOTPValidSuccess() throws Exception {

      Gson gson = new Gson();
      String validRequestString = gson.toJson(getValidIsOTPValidRequest());
      when(otpService.isOTPValid(any(IsOTPValidRequest.class))).thenReturn(getValidIsOTPValidResponse());

      MvcResult result = this.mockMvc
               .perform(get(RestURIConstants.OTP + "otp/valid/" + "testOtpId1/" + "OTP1").contentType(
                        MediaType.APPLICATION_JSON).content(validRequestString))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
      IsOTPValidResponse isOTPValidResponse = getObjectFromJsonString(result,
               IsOTPValidResponse.class);

      Assert.assertEquals("Unable to resend OTP", getValidIsOTPValidResponse(), isOTPValidResponse);

   }
   
   private IsOTPValidResponse getValidIsOTPValidResponse() {

      IsOTPValidResponse response = new IsOTPValidResponse();
      response.setStatus(true);
      return response;

   }
	
   private IsOTPValidRequest getValidIsOTPValidRequest() {

      IsOTPValidRequest request = new IsOTPValidRequest();
      request.setOtpId("testOtpId1");
      request.setOtp("OTP1");
      return request;

   }
	
	private ResendOTPRequest getValidResendOTPRequest() {

		ResendOTPRequest request = new ResendOTPRequest();
		request.setOtpId("testOtpId1");
		request.setToken("token1");
		return request;

	}

	private ResendOTPRequest getResendOTPRequestWithWrongOtp() {

		ResendOTPRequest request = new ResendOTPRequest();
		request.setOtpId(null);
		request.setToken("token1");
		return request;

	}

	private ResendOTPRequest getResendOTPRequestWithWrongToken() {

		ResendOTPRequest request = new ResendOTPRequest();
		request.setOtpId("testOtpId1");
		request.setToken("11111111111111111111"
	            + "11111111111111111111111111" + "11111111111111111111111111"
	            + "11111111111111111111111111" + "11111111111111111111111111"
	            + "11111111111111111111111111" + "1111111111111111111111"
	            + "1111111111111111111111" + "1111111111111111111111"
	            + "1111111111111111111111" + "1111111111111111111111");
		return request;

	}

	private GenerateOTPRequest getValidGenerateOTPRequest() {

		GenerateOTPRequest request = new GenerateOTPRequest();
		request.setEmailId("test@yopmail.com");
		request.setMobileNumber("9999999999");
		request.setToken("testToken");
		request.setPurpose(OTPPurpose.MOBILE_VERIFICATION);
		return request;

	}

	private GenerateOTPRequest getGenerateOTPRequestWithWrongEmail() {

		GenerateOTPRequest request = new GenerateOTPRequest();
		request.setEmailId("11111111111111111111"
				+ "11111111111111111111111111" + "11111111111111111111111111"
				+ "11111111111111111111111111" + "11111111111111111111111111"
				+ "11111111111111111111111111" + "1111111111111111111111"
				+ "1111111111111111111111" + "1111111111111111111111"
				+ "1111111111111111111111" + "1111111111111111111111");
		request.setMobileNumber("9999999999");
		request.setToken("testToken");
		request.setPurpose(OTPPurpose.MOBILE_VERIFICATION);
		return request;

	}

	private GenerateOTPRequest getGenerateOTPRequestWithWrongToken() {

		GenerateOTPRequest request = new GenerateOTPRequest();
		request.setEmailId(null);
		request.setMobileNumber("99999");
		request.setToken(null);
		request.setPurpose(OTPPurpose.MOBILE_VERIFICATION);

		return request;

	}

	private GenerateOTPResponse getValidOTPResponse() {

		GenerateOTPResponse response = new GenerateOTPResponse();
		response.setOtpId("testOtpId1");
		return response;

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
}
