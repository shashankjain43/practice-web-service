package com.snapdeal.payments.view.test.controller;

import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.snapdeal.payments.view.controller.MerchantViewController;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;


@RunWith(MockitoJUnitRunner.class)
public class MerchantViewControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MerchantViewController merchantViewController;

	@Mock
	private IMerchantViewService merchantViewServiceMock;
	
	//@Mock
	//private ValidateRequestAop validateRequest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(merchantViewController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/*//@Test
	public void testGetMerchantViewFilter() {
		 
		GetTransactionsResponse serviceResponse = new GetTransactionsResponse();
		Mockito.when(
				merchantViewServiceMock
						.getMerchantViewFilter(any(GetMerchantViewFilterRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW+
					RestURIConstants.MERCHANT_VIEW)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetMerchantTransactionsHistoryResponse response = getObjectFromJsonString(
					result, GetMerchantTransactionsHistoryResponse.class);
			Assert.assertNotNull("GetMerchantViewFilter controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}

	}*/
	
	/*//@Test
	public void testGetMerchantViewSearch() {
		 
		GetTransactionsResponse serviceResponse = new GetTransactionsResponse();
		Mockito.when(
				merchantViewServiceMock
						.getMerchantViewSearch(any(GetMerchantViewSearchRequest.class)))
				.thenReturn(serviceResponse);

		MvcResult result;
		try {
			GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest();
			Gson gson = new Gson();
			String validRequestString = gson.toJson(request);
			MockHttpServletRequestBuilder postRequest = post(RestURIConstants.VIEW
					+RestURIConstants.MERCHANT_VIEW+RestURIConstants.MERCHANT_VIEW_SEARCH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequestString);
			result = this.mockMvc
					.perform(postRequest)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andReturn();

			GetMerchantTransactionsHistoryResponse response = getObjectFromJsonString(
					result, GetMerchantTransactionsHistoryResponse.class);
			Assert.assertNotNull("GetMerchantViewSearch controller test failed", response);
		} catch (Exception e) {
			fail(e.getMessage()) ;
		} catch (Error e) {
			fail(e.getMessage()) ;
		}
	}*/


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