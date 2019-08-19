package com.snapdeal.merchant.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.CreateOfflineMerchantRequest;
import com.snapdeal.merchant.request.CreateMPMerchantRequest;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.GetMerchantUIDataRequest;
import com.snapdeal.merchant.request.MerchantGetKYCDocListRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantUpdateDetailsRequest;
import com.snapdeal.merchant.request.MerchantUploadDocumentRequest;
import com.snapdeal.merchant.request.UpdateSourceOfAcquisitionRequest;
import com.snapdeal.merchant.response.CreateMPMerchantResponse;
import com.snapdeal.merchant.response.CreateOfflineMerchantResponse;
import com.snapdeal.merchant.response.GetMerchantStateResponse;
import com.snapdeal.merchant.response.GetMerchantUIDataResponse;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantGetKYCDocListResponse;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;
import com.snapdeal.merchant.response.MerchantUpdateDetailsResponse;
import com.snapdeal.merchant.response.MerchantUploadDocumentResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.service.IMerchantManagementService;
import com.snapdeal.merchant.rest.service.ISessionService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.mob.enums.SourceOfAcquisition;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/merchant")
@Slf4j
public class MerchantManagementController extends AbstractController {

    @Autowired
    IMerchantManagementService merchantService;

	@Autowired
	ISessionService sessionService;
	
	@Autowired
	MOBUtil  mobutil;

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/profile")
	public @ResponseBody GenericMerchantResponse showMerchantProfile(MerchantProfileRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {

      // apply validation check
      verifyError(result, servRequest);

      // call the service
      MerchantDetailResponse response = merchantService.getMerchantDetails(request);

      return getResponse(response);

   }

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/roles")
	public @ResponseBody GenericMerchantResponse getAllRoles(MerchantRoleRequest request, BindingResult result,
			HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
      verifyError(result, servRequest);

      // call the service
      MerchantRoleResponse response = merchantService.getMerchantRoles(request);

      return getResponse(response);

	}

/*	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.POST, value = "/v1/create/offlinemerchant")
	public @ResponseBody GenericMerchantResponse createOfflineMerchant(@RequestBody @Valid CreateOfflineMerchantRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {
		
		verifyError(result, servRequest);
		
		CreateOfflineMerchantResponse response = merchantService.createOfflineMerchant(request);
		
		return getResponse(response);
	}*/

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.POST, value = "/v1/create/merchant")
	public @ResponseBody GenericMerchantResponse createMerchant(@RequestBody @Valid CreateMPMerchantRequest request, BindingResult result,
			HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);
		
		CreateMPMerchantResponse createMerchantResponse  = merchantService.createMerchant(request);
		
		MerchantLoginRequest loginRequest = new MerchantLoginRequest();
		loginRequest.setLoginName(request.getEmail());
		loginRequest.setPassword(request.getPassword());
		MerchantLoginResponse loginResponse = null;
		try
		{
			loginResponse = sessionService.login(loginRequest);
		}
		catch(MerchantException e)
		{
			log.info("Merchant Created with Merchant Id: {}",createMerchantResponse.getMerchantId());
			log.info("But unable to login Exception : {}",e);
			throw new MerchantException(ErrorConstants.AUTO_LOGIN_ERROR_CODE , ErrorConstants.AUTO_LOGIN_ERROR_MSG);
		}
		
		
		UpdateSourceOfAcquisitionRequest sourceOfAcquisitionrequest =  new UpdateSourceOfAcquisitionRequest();
		sourceOfAcquisitionrequest.setMerchantId(createMerchantResponse.getMerchantId());
		sourceOfAcquisitionrequest.setSourceOfAcquisition(SourceOfAcquisition.SELF_SERVE.getSourceOfAcquisition());
		sourceOfAcquisitionrequest.setToken(loginResponse.getToken());
		
		mobutil.updatesourceofacquisition(sourceOfAcquisitionrequest);
		
		return getResponse(loginResponse);

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.POST, value = "/v1/update/merchant")
	public @ResponseBody GenericMerchantResponse updateMerchantDetails(
			@RequestBody @Valid MerchantUpdateDetailsRequest request, BindingResult result,
			HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);

		// call the service
		MerchantUpdateDetailsResponse response = merchantService.updateMerchantDetails(request);

		return getResponse(response);

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/uidata")
	public @ResponseBody GenericMerchantResponse getAllUIData(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "parent", required = false) String parent,
			@RequestParam(value = "integrationMode", required = true) String integrationMode,
			GetMerchantUIDataRequest request, BindingResult result, HttpServletRequest servRequest)
					throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);

		GetMerchantUIDataResponse response = merchantService.getUIData(request);

		return getResponse(response);

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/merchantstate")
	public @ResponseBody GenericMerchantResponse getMerchantProfileStatus(GetMerchantStateRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {

		verifyError(result, servRequest);

		GetMerchantStateResponse response = merchantService.getMerchantProfileStatus(request);

		return getResponse(response);

	}
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(value = "/v1/uploaddocument", method = RequestMethod.POST)
	public @ResponseBody GenericMerchantResponse uploadDocument(@Valid MerchantUploadDocumentRequest request,
			BindingResult result ,@RequestPart(value = "file", required = false) MultipartFile file,HttpServletRequest servRequest)
					throws MerchantException {
		
		verifyError(result, servRequest);

		MerchantUploadDocumentResponse response = merchantService.uploadDocument(request, file);

		return getResponse(response);
	}
	

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(value = "/v1/get/kyc/documentlist", method = RequestMethod.GET)
	public @ResponseBody GenericMerchantResponse GetKYCDocList(@RequestParam(value = "businessType", required = true) String businessType,
			MerchantGetKYCDocListRequest request, BindingResult result ,HttpServletRequest servRequest)
					throws MerchantException {
		
		verifyError(result, servRequest);

		MerchantGetKYCDocListResponse response = merchantService.GetKYCDocList(request);

		return getResponse(response);
	}
	

}
