package com.snapdeal.payments.view.test.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.controller.RequestViewController;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.request.commons.request.GetPendingActionsBetweenPartyRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserActionsHistoryRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserPendingActionsRequest;
import com.snapdeal.payments.view.request.commons.response.GetActionsWithFilterResponse;
import com.snapdeal.payments.view.request.commons.response.GetPendingActionsBetweenPartiesResponse;
import com.snapdeal.payments.view.request.commons.response.GetRequestViewResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserActionsHistoryResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserPendingActionsResponse;
import com.snapdeal.payments.view.request.commons.service.IRequestViewService;

@ContextConfiguration(locations = { "classpath:*///spring/application-context.xml" })
@RunWith(MockitoJUnitRunner.class)
public class RequestViewControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private RequestViewController requestViewController;

	@Mock
	private IRequestViewService requestViewService;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(requestViewController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/*@Test
	public void testGetSplitViewTransactions() {
		 
		GetSplitViewTransactionsResponse serviceResponse = new GetSplitViewTransactionsResponse();
		Mockito.when(
				requestViewService
						.getSplitViewTransactions(any(GetSplitViewTransactionsRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = get(RestURIConstants.VIEW+
					RestURIConstants.REQUEST_VIEW+ RestURIConstants.SPLIT_TRANSACTIONS)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetSplitViewTransactionsResponse response = getObjectFromJsonString(
					result, GetSplitViewTransactionsResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
	}*/
	
	@Test
	public void testGetActionsWithFilters() {
		 
		GetUserActionsHistoryResponse serviceResponse = new GetUserActionsHistoryResponse();
		Mockito.when(
				requestViewService
						.getUserActionsHistory(any(GetUserActionsHistoryRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW+
					RestURIConstants.REQUEST_VIEW+ RestURIConstants.USER_ACTION_HISTORY)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetActionsWithFilterResponse response = getObjectFromJsonString(
					result, GetActionsWithFilterResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
	}
	
	@Test
	public void testGetMutualPendingActions() {
		 
		GetPendingActionsBetweenPartiesResponse serviceResponse = 
				new GetPendingActionsBetweenPartiesResponse ();
		Mockito.when(
				requestViewService
						.getPendingActionsBetweenParties(any(GetPendingActionsBetweenPartyRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW+
					RestURIConstants.REQUEST_VIEW+ RestURIConstants.PENDING_ACTIONS_FOR_PARTY)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetPendingActionsBetweenPartiesResponse response = getObjectFromJsonString(
					result, GetPendingActionsBetweenPartiesResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
	}
	@Test
	public void testGetPendingActionsForUser() {
		 
		GetUserPendingActionsResponse serviceResponse = 
				new GetUserPendingActionsResponse();
		Mockito.when(
				requestViewService
						.getUserPendingActions(any(GetUserPendingActionsRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW+
					RestURIConstants.REQUEST_VIEW+ RestURIConstants.PENDING_ACTIONS_FOR_PARTY)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetUserPendingActionsResponse response = getObjectFromJsonString(
					result, GetUserPendingActionsResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
	}
	
	
	@Test
	public void testGetRequestViewSearch() {
		 
		GetRequestViewResponse serviceResponse = 
				new GetRequestViewResponse();
		Mockito.when(
				requestViewService
						.getRequestViewSearch(any(GetRequestViewSearchRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW+
					RestURIConstants.REQUEST_VIEW+ RestURIConstants.SEARCH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetRequestViewResponse response = getObjectFromJsonString(
					result, GetRequestViewResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
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
