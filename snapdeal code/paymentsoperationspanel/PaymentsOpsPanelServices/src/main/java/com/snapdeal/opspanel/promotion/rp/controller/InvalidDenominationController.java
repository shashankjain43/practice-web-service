package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.csv.request.LIstToCSVRequest;
import com.snapdeal.opspanel.csv.service.CSVServices;

import com.snapdeal.opspanel.webappcomponent.entity.PlansDenominationFileEntity;
import com.snapdeal.opspanel.webappcomponent.request.ChangePlanValidityRequest;
import com.snapdeal.opspanel.webappcomponent.request.CheckPlanValidityRequest;
import com.snapdeal.opspanel.webappcomponent.request.OperatorCirleBlockGetRequest;
import com.snapdeal.opspanel.webappcomponent.request.OperatorCirleBlockSetRequest;
import com.snapdeal.opspanel.webappcomponent.request.OperatorsForProductRequest;
import com.snapdeal.opspanel.webappcomponent.request.UploadFileRequest;
import com.snapdeal.opspanel.webappcomponent.response.UploadFileFilteredResponse;
import com.snapdeal.opspanel.webappcomponent.service.PlansDenominationService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("denomination/")
public class InvalidDenominationController {

	@Autowired
	PlansDenominationService plansDenominationService;
	
	@Autowired
	CSVServices csvServices;

	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_VIEW') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/getCircles", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCircles() throws Exception {

		log.info( "Received getCircles Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.getCircles() );
	}	
	
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_VIEW') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/getOperatorsForProduct", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getOperatorsForProduct(
			@RequestBody OperatorsForProductRequest request 
			) throws Exception {

		log.info( "Received getOperators Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.getOperatorsForProduct(request) );
	}
	
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK'))")
	@RequestMapping(value = "/getInvalidDenominationSampleFile", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getInvalidDenominationSampleFile() {
			return getGenericResponse("/samplefiles/invalidDenomination/InvalidDenominationSampleFile.txt");
	}

	@Audited(context = "Denoimination")
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK'))")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity uploadFile(
			@RequestPart(value = "invaliddenominationsuploadfile" ) MultipartFile invaliddenominationsuploadfile 
			) throws Exception {

		log.info( "Received uploadFile Request in Denomination Controller" );
		
		UploadFileRequest uploadFileRequest = new UploadFileRequest();
		uploadFileRequest.setInvaliddenominationsuploadfile(invaliddenominationsuploadfile);
		
		UploadFileFilteredResponse plansDenominationFileEntityList = plansDenominationService.uploadFile(uploadFileRequest);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-disposition", "attachment; filename=" + "ListToCSV.txt");
		httpHeaders.add("Content-Type", "text/csv");
		
		LIstToCSVRequest listToCSVRequest = new LIstToCSVRequest();
		
		List<Object> list = new ArrayList<Object>();
		
		for(PlansDenominationFileEntity plansDenominationFileEntity : plansDenominationFileEntityList.getPlansDenominationFileEntityList()){
			list.add(plansDenominationFileEntity);
		}
		
		listToCSVRequest.setClassName(PlansDenominationFileEntity.class);
		listToCSVRequest.setObjects(list);
		
		StringBuffer sb = csvServices.getListToCSV(listToCSVRequest , "|");

		return new ResponseEntity(sb.toString().getBytes(), httpHeaders, HttpStatus.OK);
	}
	
	@Audited(context = "Denoimination", searchId = "checkPlansValidityRequest.operatorId")
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_VIEW') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/checkPlanValidity", method = RequestMethod.POST)
	public @ResponseBody GenericResponse checkPlanValidity(
			@RequestBody CheckPlanValidityRequest checkPlansValidityRequest 
			) throws Exception {

		log.info( "Received checkPlansValidity Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.checkPlanValidity(checkPlansValidityRequest) );
	}

	@Audited(context = "Denoimination", searchId = "changePlanValidityRequest.operatorId")
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/changePlanValidity", method = RequestMethod.POST)
	public @ResponseBody GenericResponse changePlansValidity(
			@RequestBody ChangePlanValidityRequest changePlanValidityRequest 
			) throws Exception {

		log.info( "Received changePlansValidity Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.changePlanValidity( changePlanValidityRequest ) );
	}
	
	@Audited(context = "Denoimination")
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_VIEW') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_BULK') or hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/getOperatorCircleblock", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getOperatorCircleblock(
			@RequestBody OperatorCirleBlockGetRequest operatorCirleBlockGetRequest 
			) throws Exception {

		log.info( "Received getOperatorCircleblock Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.getOperatorCircleblock( operatorCirleBlockGetRequest ) );
	}

	@Audited(context = "Denoimination", searchId = "operatorCirleBlockSetRequest.operatorId")
	@PreAuthorize("(hasPermission('OPS_ONDECK_INVALID_DENOMINATION_UPDATE'))")
	@RequestMapping(value = "/setOperatorCircleblock", method = RequestMethod.POST)
	public @ResponseBody GenericResponse setOperatorCircleblock(
			@RequestBody OperatorCirleBlockSetRequest operatorCirleBlockSetRequest 
			) throws Exception {

		log.info( "Received setOperatorCircleblock Request in Denomination Controller" );
		return GenericControllerUtils.getGenericResponse( plansDenominationService.setOperatorCircleblock( operatorCirleBlockSetRequest ) );
	}
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
}
