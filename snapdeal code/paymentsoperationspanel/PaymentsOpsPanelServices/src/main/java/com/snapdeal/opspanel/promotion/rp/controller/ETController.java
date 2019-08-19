package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateRequest;
import com.snapdeal.opspanel.promotion.model.TemplatesEntity;
import com.snapdeal.opspanel.promotion.request.TemplateRequestModel;
import com.snapdeal.opspanel.promotion.service.TemplateService;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.notification.api.Notifier;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("etmanager/")
@Slf4j
public class ETController {
	
	@Autowired
	HttpServletRequest servletRequest;
	
	@Autowired
	private Notifier notifier;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	TokenService tokenService;
	
	@Audited(context = "ET", searchId = "templateRequestModel.merchantName", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/getalltemplates",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getMailTemplatesByUid(@RequestBody TemplateRequestModel templateRequestModel) throws WalletServiceException, OpsPanelException
	{
		List<TemplatesEntity>templatesList=templateService.getAllTemplatesData(templateRequestModel.getMerchantName());

		GenericResponse genericResponse=new GenericResponse();
		Map<String,Object> reponseMap=new HashMap<>();
		
		reponseMap.put("templates",templatesList);
		genericResponse.setData(reponseMap);
					
		return genericResponse;
	}
	
	@Audited(context = "ET", searchId = "fileSpecificTemplateRequest.userId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/addtemplatespecifictoupload",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addMailTemlatesToFileUpload(@RequestBody FileSpecificTemplateRequest fileSpecificTemplateRequest) 
	throws WalletServiceException, OpsPanelException
	{
			String token = servletRequest.getHeader("token");
			String emailId = tokenService.getEmailFromToken(token);

			fileSpecificTemplateRequest.setUserId(emailId);
		
		    String templateid=templateService.addTemplatesData(fileSpecificTemplateRequest);
		
		    GenericResponse genericResponse=new GenericResponse();
			Map<String,Object> reponseMap=new HashMap<>();
		
			reponseMap.put("msg","Template added sucesfully");
			reponseMap.put("templateId",templateid);
			genericResponse.setData(reponseMap);
	
			return genericResponse;
	}
	
	@Audited(context = "ET", searchId = "templateRequestModel.merchantName", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/gettemplatedetails",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getSpecifiTemplate(@RequestBody TemplateRequestModel templateRequestModel) throws WalletServiceException, OpsPanelException
	{
		   GenericResponse genericResponse=new GenericResponse();
		   genericResponse.setData(templateService.getTemplatesData(templateRequestModel.getTemplateId()));
		   
		   return genericResponse;
	}
}
