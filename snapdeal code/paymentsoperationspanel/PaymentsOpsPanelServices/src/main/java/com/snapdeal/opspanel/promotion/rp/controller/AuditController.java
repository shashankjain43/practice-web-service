package com.snapdeal.opspanel.promotion.rp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.request.AuditRequest;
import com.snapdeal.opspanel.audit.service.AuditingService;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;

import lombok.extern.slf4j.Slf4j;


@Controller
@Validated
@RequestMapping("audit/")
@Slf4j
public class AuditController {

	@Autowired
	AuditingService auditService;
	
	@RequestMapping(value= "/getRequestAndResponse" , method = RequestMethod.POST)
	public @ResponseBody GenericResponse viewAuditResponse(@RequestBody @Valid AuditRequest auditRequest , BindingResult bindingResult) throws OpsPanelException{
		GenericControllerUtils.checkBindingResult(bindingResult, "view Audit Response in AuditController");
		log.info("Getting Audit response for request - " + auditRequest);
		return GenericControllerUtils.getGenericResponse(auditService.getResponse(auditRequest));
		
	}
	
	@RequestMapping(value= "/getCount" , method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCount(@RequestBody @Valid AuditRequest auditRequest , BindingResult bindingResult) throws OpsPanelException{
		GenericControllerUtils.checkBindingResult(bindingResult, "view Audit Response in AuditController");
		log.info("Getting Total Count Audit response for request - " + auditRequest);
		return GenericControllerUtils.getGenericResponse(auditService.getCount(auditRequest));
		
	}
}