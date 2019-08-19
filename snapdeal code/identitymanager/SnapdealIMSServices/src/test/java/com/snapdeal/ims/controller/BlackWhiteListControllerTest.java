package com.snapdeal.ims.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;
import com.snapdeal.ims.service.IBlackWhiteListService;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
public class BlackWhiteListControllerTest {

	private MockMvc mockMvc;
	@Mock
	private IBlackWhiteListService blackWhiteListService;

	@InjectMocks
	private BlackWhiteListController blackWhiteListController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(blackWhiteListController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWhitelistEmailSuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson.toJson(getWhitelistEmailRequest());
		when(
				blackWhiteListService
						.WhitelistEmail(any(WhitelistEmailRequest.class)))
				.thenReturn(getWhitelistEmailResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/whitelist/email")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		WhitelistEmailResponse response = getObjectFromJsonString(result,
				WhitelistEmailResponse.class);

		Assert.assertEquals("Invalid response from whitelist with email",
				getWhitelistEmailResponse(), response);
	}

	private WhitelistEmailResponse getWhitelistEmailResponse() {

		WhitelistEmailResponse response = new WhitelistEmailResponse();
		response.setSuccess(true);
		return response;
	}

	private WhitelistEmailRequest getWhitelistEmailRequest() {

		WhitelistEmailRequest request = new WhitelistEmailRequest();
		request.setEmailId("johnsnow@gmail.com");
		return request;

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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(blackWhiteListController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBlackListEntitySuccess() throws Exception {
		Gson gson = new Gson();
		String validRequestString = gson
				.toJson(getValidBlacklistEntityRequest());

		when(
				blackWhiteListService
						.addBlacklistEntity(any(BlacklistEntityRequest.class)))
				.thenReturn(getValidBlacklistEntityResponse());

		MvcResult result = this.mockMvc
				.perform(
						post(RestURIConstants.USER + "/blacklist/entity")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validRequestString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		BlacklistEntityResponse response = getObjectFromJsonString(result,
				BlacklistEntityResponse.class);

		Assert.assertEquals("Invalid response from black list entity request",
				getValidBlacklistEntityResponse(), response);

	}

	private BlacklistEntityRequest getValidBlacklistEntityRequest() {
		BlacklistEntityRequest request = new BlacklistEntityRequest();
		request.setBlackListType(EntityType.EMAIL);
		request.setEntity("abc@snapdeal.com");

		return request;
	}

	private BlacklistEntityResponse getValidBlacklistEntityResponse() {
		BlacklistEntityResponse response = new BlacklistEntityResponse();
		response.setSuccess(true);
		return response;
	}
}
