package com.snapdeal.merchant.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.response.MerchantKeyResponse;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.rest.service.IIntegrationService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

@Controller
@RequestMapping("/api/integrate")
public class IntegrationController extends AbstractController{

	
	@Autowired
	IIntegrationService integrationService;
	
	@Autowired
	RMSUtil rmsUtil;
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/sandbox")
	public @ResponseBody GenericMerchantResponse getMerchantSandboxKey(MerchantKeyRequest request, 
			HttpServletRequest servRequest) throws MerchantException {

		// call the service
		MerchantKeyResponse response = integrationService.getMerchantSandBoxKey(request);

		return getResponse(response);

	}
	
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/production")
	public @ResponseBody GenericMerchantResponse getMerchantProductionKey(MerchantKeyRequest request, 
			HttpServletRequest servRequest) throws MerchantException {

		// call the service
		MerchantKeyResponse response = integrationService.getMerchantProductionKey(request);

		return getResponse(response);

	}
	 
}
