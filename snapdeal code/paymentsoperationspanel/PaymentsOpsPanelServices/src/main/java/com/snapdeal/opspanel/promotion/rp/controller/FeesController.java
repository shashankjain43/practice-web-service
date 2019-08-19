package com.snapdeal.opspanel.promotion.rp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.payments.feeengine.client.FeeEngineClient;
import com.snapdeal.payments.feeengine.model.request.CreateFeeDetailsRequest;
import com.snapdeal.payments.feeengine.model.request.GetAllApplicableFeeDetailsRequest;
import com.snapdeal.payments.feeengine.model.request.GetAllFeeDetailsRequest;
import com.snapdeal.payments.feeengine.model.request.UpdateFeeDetailsRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("fees/")
public class FeesController {

	@Autowired
	FeeEngineClient feesEngine;

	@RequestMapping(value = "createFee", method = RequestMethod.POST)
	public GenericResponse createFeeDetails(@RequestBody CreateFeeDetailsRequest createFeeDetailsRequest)
			throws Exception {

		return OPSUtils.getGenericResponse(feesEngine.createFeeDetails(createFeeDetailsRequest));

	}
	
	@Audited(context = "MOB", searchId = "request.feeId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value="updateFee",method=RequestMethod.POST)
	public GenericResponse updateFeeDetails(@RequestBody UpdateFeeDetailsRequest updateFeeDetailRequest){
		
		return OPSUtils.getGenericResponse(feesEngine.updateFeeDetails(updateFeeDetailRequest));
	}
	
	@RequestMapping(value="getAllApplicableFeeDetails",method=RequestMethod.POST)
	public GenericResponse getAllApplicableFeeDetails(@RequestBody GetAllApplicableFeeDetailsRequest request){
		
		return OPSUtils.getGenericResponse(feesEngine.getAllApplicableFeeDetails(request));
	}
	
	@RequestMapping(value="getAllFeeDetails",method=RequestMethod.POST)
	public GenericResponse getAllFeeDetails(@RequestBody GetAllFeeDetailsRequest request){
		
		return OPSUtils.getGenericResponse(feesEngine.getAllFeeDetails(request));
	}
}
