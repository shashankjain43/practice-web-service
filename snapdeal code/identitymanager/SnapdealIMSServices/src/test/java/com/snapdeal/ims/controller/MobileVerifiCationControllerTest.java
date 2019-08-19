package com.snapdeal.ims.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.service.IMobileVerificationService;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class MobileVerifiCationControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private MobileVerifiCationController mobileVerifiCationController;
	@Mock
	private IMobileVerificationService mobileVerificationService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(
					mobileVerifiCationController).build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerifyUniqueMobileSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidMobileVerificationRequest());
		when(
				mobileVerificationService
						.verifyUniqueMobile(any(UniqueMobileVerificationRequest.class)))
				.thenReturn(getValidUniqueMobileVerificationResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.MOBILE_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		UniqueMobileVerificationResponse mobileVerificationResponse = getObjectFromJsonString(
				result, UniqueMobileVerificationResponse.class);

		Assert.assertEquals("Invalid response from verifyMobileVerification",
				getValidUniqueMobileVerificationResponse(),
				mobileVerificationResponse);
	}

	@Test
	public void testVerifyUniqueMobileWithNullOtp() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getMobileVerificationRequestWithoutOtp());
		when(
				mobileVerificationService
						.verifyUniqueMobile(any(UniqueMobileVerificationRequest.class)))
				.thenReturn(getValidUniqueMobileVerificationResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.MOBILE_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		UniqueMobileVerificationResponse mobileVerificationResponse = getObjectFromJsonString(
				result, UniqueMobileVerificationResponse.class);
		
		Assert.assertEquals("RequestParameterException is not thrown", result
	            .getResolvedException().getClass(),
	            RequestParameterException.class);

	}

	@Test
	public void testVerifyUniqueMobileWithNullUserId() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getMobileVerificationRequestWithoutUserId());
		when(
				mobileVerificationService
						.verifyUniqueMobile(any(UniqueMobileVerificationRequest.class)))
				.thenReturn(getValidUniqueMobileVerificationResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.MOBILE_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		UniqueMobileVerificationResponse mobileVerificationResponse = getObjectFromJsonString(
				result, UniqueMobileVerificationResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
               .getResolvedException().getClass(),
               RequestParameterException.class);
	}

	@Test
	@Ignore
	public void testVerifyUniqueMobileWithWrongOtp() throws Exception {
		Gson gson = new Gson();
		String invalidRequestString = gson
				.toJson(getMobileVerificationRequestWithWrongOtp());
		when(
				mobileVerificationService
						.verifyUniqueMobile(any(UniqueMobileVerificationRequest.class)))
				.thenReturn(getValidUniqueMobileVerificationResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(
								RestURIConstants.OTP
										+ RestURIConstants.MOBILE_VERIFY)
								.contentType(MediaType.APPLICATION_JSON)
								.content(invalidRequestString))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		UniqueMobileVerificationResponse mobileVerificationResponse = getObjectFromJsonString(
				result, UniqueMobileVerificationResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
               .getResolvedException().getClass(),
               RequestParameterException.class);
	}

	@Test
	public void testMobileVerificationStatusSuccess() throws Exception {

		when(
				mobileVerificationService
						.isMobileVerified(any(MobileVerificationStatusRequest.class)))
				.thenReturn(getValidMobileVerificationStatusResponse());

		MvcResult result = this.mockMvc
				.perform(get(RestURIConstants.OTP + "users/122/mobile/verify"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		MobileVerificationStatusResponse mobileStatusResponse = getObjectFromJsonString(
				result, MobileVerificationStatusResponse.class);

		Assert.assertEquals("mobileVerificationStatus failed",
				getValidMobileVerificationStatusResponse(),
				mobileStatusResponse);
	}

	@Test
	public void testMobileVerificationStatusWithWrongParameters()
			throws Exception {

		when(
				mobileVerificationService
						.isMobileVerified(any(MobileVerificationStatusRequest.class)))
				.thenReturn(getValidMobileVerificationStatusResponse());

		MvcResult result = this.mockMvc
				.perform(
						get(RestURIConstants.OTP + "users/11111111111111111111"
								+ "11111111111111111111111111"
								+ "11111111111111111111111111"
								+ "11111111111111111111111111"
								+ "11111111111111111111111111"
								+ "11111111111111111111111111"
								+ "1111111111111111111111/mobile/verify"))
				.andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		MobileVerificationStatusResponse mobileStatusResponse = getObjectFromJsonString(
				result, MobileVerificationStatusResponse.class);

		Assert.assertEquals("RequestParameterException is not thrown", result
               .getResolvedException().getClass(),
               RequestParameterException.class);
	}

	private MobileVerificationStatusRequest getInvalidMobileVerificationStatusRequest() {
		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId(null);
		return request;
	}

	private MobileVerificationStatusRequest getValidMobileVerificationStatusRequest() {
		MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
		request.setUserId("userId1");
		return request;
	}

	private MobileVerificationStatusResponse getValidMobileVerificationStatusResponse() {
		MobileVerificationStatusResponse response = new MobileVerificationStatusResponse();
		response.setSuccess(true);
		return response;

	}

	private UniqueMobileVerificationRequest getValidMobileVerificationRequest() {

		UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
		request.setOtp("testot");
		request.setOtpId("otpid1");
		request.setToken("token1");
		request.setUserId("testId1");
		return request;
	}

	private UniqueMobileVerificationRequest getMobileVerificationRequestWithoutOtp() {

		UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
		request.setOtp(null);
		request.setOtpId("otpid1");
		request.setToken("token1");
		request.setUserId("testId1");
		return request;
	}

	private UniqueMobileVerificationRequest getMobileVerificationRequestWithWrongOtp() {

		UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
		request.setOtp("lengthisgreaterthanmaxlimit");
		request.setOtpId("otpid1");
		request.setToken("token1");
		request.setUserId("testId1");
		return request;
	}

	private UniqueMobileVerificationRequest getMobileVerificationRequestWithoutUserId() {

		UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
		request.setOtp(null);
		request.setOtpId("otpid1");
		request.setToken("token1");
		request.setUserId(null);
		return request;
	}

	private UniqueMobileVerificationResponse getValidUniqueMobileVerificationResponse() {

		UniqueMobileVerificationResponse response = new UniqueMobileVerificationResponse();
		response.setMessage("test message");
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
