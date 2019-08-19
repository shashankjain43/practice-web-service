package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.ImsIdTypes;
import com.snapdeal.opspanel.userpanel.service.PMSService;
import com.snapdeal.payments.pms.api.GetAllTagsForEntityRequest;
import com.snapdeal.payments.pms.api.GetAllTagsForEntityResponse;
import com.snapdeal.payments.pms.client.ProfileManagementClient;
import com.snapdeal.payments.pms.entity.EntityStatus;
import com.snapdeal.payments.pms.exceptions.ProfileManagementException;
import com.snapdeal.payments.pms.service.model.AddTagInfoToEntityRequest;
import com.snapdeal.payments.pms.service.model.AddTagInfoToEntityResponse;
import com.snapdeal.payments.pms.service.model.GetEntityHistoryRequest;
import com.snapdeal.payments.pms.service.model.GetEntityHistoryResponse;
import com.snapdeal.payments.pms.service.model.GetEntityRequest;
import com.snapdeal.payments.pms.service.model.GetEntityResponse;
import com.snapdeal.payments.pms.service.model.GetEntityTagDetailsRequest;
import com.snapdeal.payments.pms.service.model.GetEntityTagDetailsResponse;
import com.snapdeal.payments.pms.service.model.GetEntityTypesResponse;
import com.snapdeal.payments.pms.service.model.GetTagNamesForEntityTypeRequest;
import com.snapdeal.payments.pms.service.model.GetTagNamesForEntityTypeResponse;
import com.snapdeal.payments.pms.service.model.TagStatus;
import com.snapdeal.payments.pms.service.model.UpdateEntityRequest;

@Slf4j
@Controller
@RequestMapping("/pms")
public class PMSController {

	@Autowired
	PMSService pmc;
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getentity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getEntity(@RequestBody GetEntityRequest request )throws ProfileManagementException{

		GetEntityResponse response = new GetEntityResponse();
		try {
			response = pmc.getEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateentity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse updateEntity(@RequestBody UpdateEntityRequest request )throws ProfileManagementException{
		try {
			pmc.updateEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse("SUCCESS");
	}
	
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getentityhistory",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getEntityHistory(@RequestBody GetEntityHistoryRequest request )throws ProfileManagementException{

		GetEntityHistoryResponse response = new GetEntityHistoryResponse();
		try {
			response = pmc.getEntityHistory(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getEntityTagDetails",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getEntityTagDetails(@RequestBody GetEntityTagDetailsRequest request )throws ProfileManagementException{

		GetEntityTagDetailsResponse response = new GetEntityTagDetailsResponse();
		try {
			response = pmc.getEntityTagDetails(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addTagInfoToEntity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addTagInfoToEntity(@RequestBody AddTagInfoToEntityRequest request )throws ProfileManagementException{

		AddTagInfoToEntityResponse response = new AddTagInfoToEntityResponse();
		try {
			response = pmc.addTagInfoToEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getEntityTypes",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getEntityTypes()throws ProfileManagementException{

		GetEntityTypesResponse response = new GetEntityTypesResponse();
		try {
			response = pmc.getEntityTypes();
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getTagNamesForEntityType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getTagNamesForEntityType(@RequestBody GetTagNamesForEntityTypeRequest request )throws ProfileManagementException{

		GetTagNamesForEntityTypeResponse response = new GetTagNamesForEntityTypeResponse();
		try {
			response = pmc.getTagNamesForEntityType(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getTagStatusTypes",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getTagStatusTypes()throws ProfileManagementException{

		List<String> listStatus = new ArrayList<String>();
		for(TagStatus tagStatus : TagStatus.values()){
			listStatus.add(tagStatus.name());
		}
		return getGenericResponse(listStatus);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getEntityStatusTypes",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getEntityStatusTypes()throws ProfileManagementException{

		List<String> listStatus = new ArrayList<String>();
		for(EntityStatus entityStatus : EntityStatus.values()){
			listStatus.add(entityStatus.name());
		}
		return getGenericResponse(listStatus);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllTagsForEntity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getTagNamesForEntityType(@RequestBody GetAllTagsForEntityRequest request )throws ProfileManagementException{

		GetAllTagsForEntityResponse response = new GetAllTagsForEntityResponse();
		try {
			response = pmc.getAllTagsForEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return getGenericResponse(response);
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value = "/getFraudSample", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFraudSample() {
		
		return getGenericResponse("/samplefiles/fraud/BulkFraudSample.csv");
	}
	
	@Audited(context = "PMS" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value = "/getActionType", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getActionType() {
		
		return getGenericResponse(pmc.getActionType());
	}
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
}
