package com.snapdeal.opspanel.promotion.rp.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.ims.request.AbusiveLanguageWordRequest;
import com.snapdeal.ims.request.AmlWordRequest;
import com.snapdeal.ims.request.DeleteAbusiveWordRequest;
import com.snapdeal.ims.request.GetAmlOrAbusiveWordlistRequest;
import com.snapdeal.ims.request.UpdateAbusiveWordRequest;
import com.snapdeal.ims.response.AddAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.DeleteAbusiveOrAmlWordResponse;
import com.snapdeal.ims.response.GetAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.UpdateAbusiveOrAmlWordResponse;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.AMLLastUpdatedResponse;
import com.snapdeal.opspanel.userpanel.response.GetBlockedTxnsResponse;
import com.snapdeal.opspanel.userpanel.service.AmlAbusiveServices;
import com.snapdeal.payments.fps.aht.model.GetUserTransactionDetailsRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;


@Slf4j
@Controller
@RequestMapping("/abuse")
public class AmlAbusiveController {

	@Autowired
	AmlAbusiveServices amlAbusiveServices;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	private TokenService tokenService;
	
	@Audited(context = "amlAbusiveServices", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/addAbusiveWords", method = RequestMethod.POST )
	public @ResponseBody GenericResponse addAbusiveWords(@RequestBody AbusiveLanguageWordRequest request) throws OpsPanelException {
		
		String token = httpServletRequest.getHeader("token");
		String auditBy = null;
		try {
			auditBy = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			log.info("Inside AmlAbusiveController.uploadAmlWordFile : OpsPanelException [" + e.getErrCode() + "," + e.getErrMessage() + "," + e.getSource() + "]");
		}
		request.setAuditBy(auditBy);
		AddAmlOrAbusiveWordResponse response = new AddAmlOrAbusiveWordResponse();
			response = amlAbusiveServices.addAbusiveWords(request);
			return GenericControllerUtils.getGenericResponse(response);
	}
	
	@Audited(context = "amlAbusiveServicesUpload", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/uploadAmlWordFile", method = RequestMethod.POST )
	public @ResponseBody GenericResponse uploadAmlWordFile(@RequestPart("file") MultipartFile file ) throws OpsPanelException {
		
		String token = httpServletRequest.getHeader("token");
		String auditBy = null;
		try {
			auditBy = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			log.info("Inside AmlAbusiveController.uploadAmlWordFile : OpsPanelException [" + e.getErrCode() + "," + e.getErrMessage() + "," + e.getSource() + "]");
		}
		
		AmlWordRequest request = new AmlWordRequest();
		request.setAuditBy(auditBy);
		request.setMultipartFile(file);
		AddAmlOrAbusiveWordResponse response = new AddAmlOrAbusiveWordResponse();
			response = amlAbusiveServices.uploadAmlWordFile(request);
			return GenericControllerUtils.getGenericResponse(response);
	}
	
	@Audited(context = "amlAbusiveServices", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/deleteAbusiveWord", method = RequestMethod.POST )
	public @ResponseBody GenericResponse deleteAbusiveWord(@RequestBody DeleteAbusiveWordRequest request) throws OpsPanelException {
		
		String token = httpServletRequest.getHeader("token");
		String auditBy = null;
		try {
			auditBy = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			log.info("Inside AmlAbusiveController.uploadAmlWordFile : OpsPanelException [" + e.getErrCode() + "," + e.getErrMessage() + "," + e.getSource() + "]");
		}
		request.setAuditBy(auditBy);
		
		DeleteAbusiveOrAmlWordResponse response = new DeleteAbusiveOrAmlWordResponse();
			response = amlAbusiveServices.deleteAbusiveWord(request);
			return GenericControllerUtils.getGenericResponse(response);
	}
	
	@Audited(context = "amlAbusiveServices", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/getAmlorAbusiveWord", method = RequestMethod.POST )
	public @ResponseBody GenericResponse getAmlorAbusiveWord(@RequestBody GetAmlOrAbusiveWordlistRequest request) throws OpsPanelException {
		GetAmlOrAbusiveWordResponse response = new GetAmlOrAbusiveWordResponse();
			response = amlAbusiveServices.getAmlorAbusiveWord(request);
			return GenericControllerUtils.getGenericResponse(response);
	}
	
	@Audited(context = "amlAbusiveServices", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/updateAbusiveWords", method = RequestMethod.POST )
	public @ResponseBody GenericResponse updateAbusiveWords(@RequestBody UpdateAbusiveWordRequest request) throws OpsPanelException {
		
		String token = httpServletRequest.getHeader("token");
		String auditBy = null;
		try {
			auditBy = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			log.info("Inside AmlAbusiveController.uploadAmlWordFile : OpsPanelException [" + e.getErrCode() + "," + e.getErrMessage() + "," + e.getSource() + "]");
		}
		request.setAuditBy(auditBy);
		
		UpdateAbusiveOrAmlWordResponse response = new UpdateAbusiveOrAmlWordResponse();
			response = amlAbusiveServices.updateAbusiveWords(request);
			return GenericControllerUtils.getGenericResponse(response);
	}
	
	/*@Audited(context = "amlAbusiveServices", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})*/
	@PreAuthorize("(hasPermission('OPS_LEGALPANEL_ABUSIVES_SANCTIONS'))")
	@RequestMapping(value = "/getLastUpdated", method = RequestMethod.GET )
	public @ResponseBody GenericResponse getLastUpdated() throws OpsPanelException {
		AMLLastUpdatedResponse response = new AMLLastUpdatedResponse();
			response = amlAbusiveServices.getLastUpdated();
			return GenericControllerUtils.getGenericResponse(response);
	}
}
