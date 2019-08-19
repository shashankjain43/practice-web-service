package com.snapdeal.opspanel.userpanel.ruleDashboard.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.ruleDashboard.service.RuleDashboardService;
import com.snapdeal.payments.dashboard.client.RuleDashboardClient;
import com.snapdeal.payments.dashboard.exception.InternalClientException;
import com.snapdeal.payments.dashboard.exception.InternalServerException;
import com.snapdeal.payments.dashboard.exception.ValidationException;
import com.snapdeal.payments.dashboard.profileManager.request.AssociateProfileRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetAllProfilesForTransactionTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetAllTransactionTypeResponse;
import com.snapdeal.payments.dashboard.profileManager.request.GetLatestProfilesForTransactionTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetProfilesForTxnTypeByProfileNameRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetTagNamesForEntityTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.response.AssociateProfileResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetAllProfilesForTransactionTypeResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetAllTransactionTypeWithProfilesResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetEntityTypesResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetLatestProfilesForTransactionTypeResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetProfilesForTxnTypeByProfileNameResponse;
import com.snapdeal.payments.dashboard.profileManager.response.GetTagNamesForEntityTypeResponse;
import com.snapdeal.payments.dashboard.ruleManager.request.AddRuleRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.GetRuleByNameRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.GetRulesByTagsRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleDescriptionRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleMessageRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleTagsRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.ValidateRuleVariableExpressionRequest;
import com.snapdeal.payments.dashboard.ruleManager.response.AddRuleResponse;
import com.snapdeal.payments.dashboard.ruleManager.response.GetRuleByNameResponse;
import com.snapdeal.payments.dashboard.ruleManager.response.GetRulesByTagsResponse;
import com.snapdeal.payments.dashboard.systemManager.request.AddSystemRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetAllSystemNamesRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetSystemInfoByNameAndEntityRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetSystemInfoByNameRequest;
import com.snapdeal.payments.dashboard.systemManager.response.AddSystemResponse;
import com.snapdeal.payments.dashboard.systemManager.response.GetAllSystemNamesResponse;
import com.snapdeal.payments.dashboard.systemManager.response.GetSystemInfoByNameAndEntityResponse;
import com.snapdeal.payments.dashboard.systemManager.response.GetSystemInfoByNameResponse;
import com.snapdeal.payments.dashboard.tagManager.request.GetCategoriesForDomainRequest;
import com.snapdeal.payments.dashboard.tagManager.request.GetTagsForDomainRequest;
import com.snapdeal.payments.dashboard.tagManager.response.GetCategoriesForDomainResponse;
import com.snapdeal.payments.dashboard.tagManager.response.GetTagsForDomainResponse;
import com.snapdeal.payments.dashboard.transactionManager.request.AddProfilesForTxnTypeRequest;
import com.snapdeal.payments.dashboard.transactionManager.request.GetLatestActiveProfilesForTxnTypeRequest;
import com.snapdeal.payments.dashboard.transactionManager.request.GetProfilesForTxnTypeByVersionRequest;
import com.snapdeal.payments.dashboard.transactionManager.response.GetLatestActiveProfilesForTxnTypeResponse;
import com.snapdeal.payments.dashboard.transactionManager.response.GetProfilesForTxnTypeByVersionResponse;
import com.snapdeal.payments.dashboard.variableManager.request.AddPrimitiveVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.AddProfileVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.AddVelocityVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetAllVariablesRequest;
/*import com.snapdeal.payments.dashboard.variableManager.request.GetTagsByContextRequest;*/
import com.snapdeal.payments.dashboard.variableManager.request.GetValidGranularityRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetValidOperationsForVariableTypeRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariableDetailedInfoRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariableInfoRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariablesByPrefixRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariablesByTypeRequest;
import com.snapdeal.payments.dashboard.variableManager.request.UpdateTagsForVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.UpdateVelocityVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.response.AddPrimitiveVariableResponse;
import com.snapdeal.payments.dashboard.variableManager.response.AddProfileVariableResponse;
import com.snapdeal.payments.dashboard.variableManager.response.AddVelocityVariableResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetAllVariablesResponse;
/*import com.snapdeal.payments.dashboard.variableManager.response.GetTagsByContextResponse;*/
import com.snapdeal.payments.dashboard.variableManager.response.GetValidGranularityResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetValidOperationsForVariableTypeResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetVariableDetailedInfoResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetVariableInfoResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetVariablesByPrefixResponse;
import com.snapdeal.payments.dashboard.variableManager.response.GetVariablesByTypeResponse;
import com.snapdeal.payments.dashboard.variableManager.response.UpdateVelocityVariableResponse;

@Slf4j
@Service("ruleDashboardService")
public class RuleDashboardServiceImpl implements RuleDashboardService{
	
	@Autowired
	RuleDashboardClient ruleDashboardClient;
	
	@Override
	public AddPrimitiveVariableResponse addPrimitiveVariable(AddPrimitiveVariableRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.addPrimitiveVariable(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling addPrimitiveVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addPrimitiveVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addPrimitiveVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addPrimitiveVariable ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void addProfilesForTransactionType(AddProfilesForTxnTypeRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.addProfilesForTransactionType(request);;
		} catch (ValidationException e) {
			log.info("ValidationException while calling addProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addProfilesForTransactionType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public AddProfileVariableResponse addProfileVariable(AddProfileVariableRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.addProfileVariable(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling addProfileVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addProfileVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addProfileVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addProfileVariable ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public AddRuleResponse addRule(AddRuleRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.addRule(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling addRule ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addRule ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addRule ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addRule ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public AddSystemResponse addSystem(AddSystemRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.addSystem(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling addSystem ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addSystem ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addSystem ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addSystem ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public AddVelocityVariableResponse addVelocityVariable(AddVelocityVariableRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.addVelocityVariable(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling addVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling addVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling addVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling addVelocityVariable ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public AssociateProfileResponse associateProfile(AssociateProfileRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.associateProfile(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling associateProfile ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling associateProfile ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling associateProfile ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling associateProfile ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetAllSystemNamesResponse getAllSystemNamesRequests(GetAllSystemNamesRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllSystemNamesRequest(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllSystemNamesRequests ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllSystemNamesRequests ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllSystemNamesRequests ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllSystemNamesRequests ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetAllVariablesResponse getAllVariables(GetAllVariablesRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllVariables(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllVariables ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllVariables ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllVariables ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllVariables ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetVariablesByTypeResponse getAllVariablesByType(GetVariablesByTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllVariablesByType(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllVariablesByType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllVariablesByType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllVariablesByType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllVariablesByType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetLatestActiveProfilesForTxnTypeResponse getLatestActiveProfilesForTxnType(GetLatestActiveProfilesForTxnTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getLatestActiveProfilesForTxnType(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getLatestActiveProfilesForTxnType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getLatestActiveProfilesForTxnType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getLatestActiveProfilesForTxnType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getLatestActiveProfilesForTxnType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetLatestProfilesForTransactionTypeResponse getLatestProfilesForTransactionType(GetLatestProfilesForTransactionTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getLatestProfilesForTransactionType(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getLatestProfileForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getLatestProfileForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getLatestProfileForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getLatestProfileForTransactionType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetProfilesForTxnTypeByProfileNameResponse getProfilesForTxnTypeByProfileName(GetProfilesForTxnTypeByProfileNameRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getProfilesForTxnTypeByProfileName(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getProfilesForTxnTypeByProfileName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getProfilesForTxnTypeByProfileName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getProfilesForTxnTypeByProfileName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getProfilesForTxnTypeByProfileName ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}

	@Override
	public GetProfilesForTxnTypeByVersionResponse getProfilesForTxnTypeByVersion(GetProfilesForTxnTypeByVersionRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getProfilesForTxnTypeByVersion(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getProfilesForTxnTypeByVersion ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getProfilesForTxnTypeByVersion ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getProfilesForTxnTypeByVersion ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getProfilesForTxnTypeByVersion ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetRuleByNameResponse getRuleByName(GetRuleByNameRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getRuleByName(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getRuleByName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getRuleByName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getRuleByName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getRuleByName ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetRulesByTagsResponse getRulesByTags(GetRulesByTagsRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getRulesByTags(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getRulesByTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getRulesByTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getRulesByTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getRulesByTags ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetSystemInfoByNameAndEntityResponse getSystemInfoByNameAndEntity(GetSystemInfoByNameAndEntityRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getSystemInfoByNameAndEntity(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getSystemInfoByNameAndEntity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getSystemInfoByNameAndEntity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getSystemInfoByNameAndEntity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getSystemInfoByNameAndEntity ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetSystemInfoByNameResponse getSystemInfoBySystemName(GetSystemInfoByNameRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getSystemInfoBySystemName(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getSystemInfoBySystemName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getSystemInfoBySystemName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getSystemInfoBySystemName ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getSystemInfoBySystemName ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	/*@Override
	public GetTagsByContextResponse getTagsByContextResponse(GetTagsByContextRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getTagsByContextResponse(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getTagsByContextResponse ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getTagsByContextResponse ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getTagsByContextResponse ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		}
	}*/
	
	@Override
	public GetValidGranularityResponse getValidGranularity(GetValidGranularityRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getValidGranularity(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getValidGranularity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getValidGranularity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getValidGranularity ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getValidGranularity ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetValidOperationsForVariableTypeResponse getValidOperations(GetValidOperationsForVariableTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getValidOperations(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getValidOperations ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getValidOperations ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getValidOperations ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getValidOperations ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetVariableDetailedInfoResponse getVariableDetailedInfo(GetVariableDetailedInfoRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getVariableDetailedInfo(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getVariableDetailedInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getVariableDetailedInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getVariableDetailedInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getVariableDetailedInfo ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetVariableInfoResponse getVariableInfo(GetVariableInfoRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getVariableInfo(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getVariableInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getVariableInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getVariableInfo ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getVariableInfo ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetVariablesByPrefixResponse getVariablesByPrefix(GetVariablesByPrefixRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getVariablesByPrefix(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getVariablesByPrefix ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getVariablesByPrefix ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getVariablesByPrefix ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getVariablesByPrefix ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void updateRuleDescription(UpdateRuleDescriptionRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.updateRuleDescription(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling updateRuleDescription ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling updateRuleDescription ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling updateRuleDescription ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling updateRuleDescription ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void updateRuleMessage(UpdateRuleMessageRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.updateRuleMessage(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling updateRuleMessage ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling updateRuleMessage ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling updateRuleMessage ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling updateRuleMessage ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void updateRuleTags(UpdateRuleTagsRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.updateRuleTags(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling updateRuleTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling updateRuleTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling updateRuleTags ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling updateRuleTags ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void updateTagsForVariable(UpdateTagsForVariableRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.updateTagsForVariable(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling updateTagsForVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling updateTagsForVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling updateTagsForVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling updateTagsForVariable ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public UpdateVelocityVariableResponse updateVelocityVariable(UpdateVelocityVariableRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.updateVelocityVariable(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling updateVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling updateVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling updateVelocityVariable ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling updateVelocityVariable ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public void validate(ValidateRuleVariableExpressionRequest request) throws OpsPanelException{
		try {
			ruleDashboardClient.validateRuleVariableExpression(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling validate ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling validate ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling validate ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling validate ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetTagsForDomainResponse getTagsForDomain(GetTagsForDomainRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getTagsForDomain(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getTagsForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getTagsForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getTagsForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getTagsForDomain ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetCategoriesForDomainResponse getCategoriesForDomain(GetCategoriesForDomainRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getCategoriesForDomain(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getCategoriesForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getCategoriesForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getCategoriesForDomain ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getCategoriesForDomain ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetAllTransactionTypeResponse getAllTransactionType() throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllTransactionType();
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllTransactionType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetAllTransactionTypeWithProfilesResponse getAllTransactionTypeWithProfiles() throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllTransactionTypeWithProfiles();
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllTransactionTypeWithProfiles ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllTransactionTypeWithProfiles ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllTransactionTypeWithProfiles ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllTransactionTypeWithProfiles ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetAllProfilesForTransactionTypeResponse getAllProfilesForTransactionType(GetAllProfilesForTransactionTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getAllProfilesForTransactionType(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getAllProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getAllProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getAllProfilesForTransactionType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getAllProfilesForTransactionType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	@Override
	public GetEntityTypesResponse getEntityTypes() throws OpsPanelException{
		try {
			return ruleDashboardClient.getEntityTypes();
		} catch (ValidationException e) {
			log.info("ValidationException while calling getEntityTypes ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getEntityTypes ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getEntityTypes ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getEntityTypes ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}
	
	
	@Override
	public GetTagNamesForEntityTypeResponse getTagNamesForEntityType(GetTagNamesForEntityTypeRequest request) throws OpsPanelException{
		try {
			return ruleDashboardClient.getTagNamesForEntityType(request);
		} catch (ValidationException e) {
			log.info("ValidationException while calling getTagNamesForEntityType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalServerException e) {
			log.info("InternalServerException while calling getTagNamesForEntityType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch (InternalClientException e) {
			log.info("InternalClientException while calling getTagNamesForEntityType ...");
			log.info("Exception : [" + e.getErrorCode().toString() + " , " + e.getMessage() + "]");
			throw new OpsPanelException(e.getErrorCode().toString(), e.getMessage(), "RULE_DASHBOARD");
		} catch(Exception e){
			log.info("Other Exception while calling getTagNamesForEntityType ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "RULE_DASHBOARD");
		}
	}

}
