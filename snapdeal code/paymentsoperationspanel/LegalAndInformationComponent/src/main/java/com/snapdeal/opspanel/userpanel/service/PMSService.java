package com.snapdeal.opspanel.userpanel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.userpanel.bulkFraud.BulkFraudBlacklistConstatnts;
import com.snapdeal.payments.pms.api.GetAllTagsForEntityRequest;
import com.snapdeal.payments.pms.api.GetAllTagsForEntityResponse;
import com.snapdeal.payments.pms.client.ProfileManagementClient;
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
import com.snapdeal.payments.pms.service.model.UpdateEntityRequest;

@Service("PmsService")
public class PMSService {
	
	@Autowired
	ProfileManagementClient profileManagementClient;
	
	public GetEntityResponse getEntity(GetEntityRequest request)throws ProfileManagementException{
		GetEntityResponse response = new GetEntityResponse();
		try {
			response = profileManagementClient.getEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public String updateEntity(UpdateEntityRequest request )throws ProfileManagementException{
		try {
			profileManagementClient.updateEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return "SUCCESS";
	}
	
	public GetEntityHistoryResponse getEntityHistory(GetEntityHistoryRequest request )throws ProfileManagementException{

		GetEntityHistoryResponse response = new GetEntityHistoryResponse();
		try {
			response = profileManagementClient.getEntityHistory(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	
	public GetEntityTagDetailsResponse getEntityTagDetails(GetEntityTagDetailsRequest request )throws ProfileManagementException{

		GetEntityTagDetailsResponse response = new GetEntityTagDetailsResponse();
		try {
			response = profileManagementClient.getEntityTagDetails(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public AddTagInfoToEntityResponse addTagInfoToEntity(AddTagInfoToEntityRequest request )throws ProfileManagementException{

		AddTagInfoToEntityResponse response = new AddTagInfoToEntityResponse();
		try {
			response = profileManagementClient.addTagInfoToEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public GetEntityTypesResponse getEntityTypes()throws ProfileManagementException{

		GetEntityTypesResponse response = new GetEntityTypesResponse();
		try {
			response = profileManagementClient.getEntityTypes();
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public GetTagNamesForEntityTypeResponse getTagNamesForEntityType(GetTagNamesForEntityTypeRequest request )throws ProfileManagementException{

		GetTagNamesForEntityTypeResponse response = new GetTagNamesForEntityTypeResponse();
		try {
			response = profileManagementClient.getTagNamesForEntityType(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public GetAllTagsForEntityResponse getAllTagsForEntity(GetAllTagsForEntityRequest request )throws ProfileManagementException{

		GetAllTagsForEntityResponse response = new GetAllTagsForEntityResponse();
		try {
			response = profileManagementClient.getAllTagsForEntity(request);
		} catch (ProfileManagementException e) {
			throw e;
		}
		return response;
	}
	
	public List<String> getActionType(){
		List<String> actions = new ArrayList<String>();
		actions.add(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING);
		actions.add(BulkFraudBlacklistConstatnts.FULL_BLOCKING);
		return actions;
		
	}


}
