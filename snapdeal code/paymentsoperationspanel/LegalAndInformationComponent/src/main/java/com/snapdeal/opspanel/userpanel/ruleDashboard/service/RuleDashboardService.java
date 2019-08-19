package com.snapdeal.opspanel.userpanel.ruleDashboard.service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
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

public interface RuleDashboardService {
	
	public AddPrimitiveVariableResponse addPrimitiveVariable(AddPrimitiveVariableRequest request) throws OpsPanelException;
	
	public void addProfilesForTransactionType(AddProfilesForTxnTypeRequest request) throws OpsPanelException;

	public AddProfileVariableResponse addProfileVariable(AddProfileVariableRequest request) throws OpsPanelException;
	
	public AddRuleResponse addRule(AddRuleRequest request) throws OpsPanelException;
	
	public AddSystemResponse addSystem(AddSystemRequest request) throws OpsPanelException;
	
	public AddVelocityVariableResponse addVelocityVariable(AddVelocityVariableRequest request) throws OpsPanelException;
	
	public AssociateProfileResponse associateProfile(AssociateProfileRequest request) throws OpsPanelException;
	
	public GetAllSystemNamesResponse getAllSystemNamesRequests(GetAllSystemNamesRequest request) throws OpsPanelException;
	
	public GetAllVariablesResponse getAllVariables(GetAllVariablesRequest request) throws OpsPanelException;
	
	public GetVariablesByTypeResponse getAllVariablesByType(GetVariablesByTypeRequest request) throws OpsPanelException;
	
	public GetLatestActiveProfilesForTxnTypeResponse getLatestActiveProfilesForTxnType(GetLatestActiveProfilesForTxnTypeRequest request) throws OpsPanelException;
	
	public GetLatestProfilesForTransactionTypeResponse getLatestProfilesForTransactionType(GetLatestProfilesForTransactionTypeRequest request) throws OpsPanelException;
	
	public GetProfilesForTxnTypeByProfileNameResponse getProfilesForTxnTypeByProfileName(GetProfilesForTxnTypeByProfileNameRequest request) throws OpsPanelException;
	
	public GetProfilesForTxnTypeByVersionResponse getProfilesForTxnTypeByVersion(GetProfilesForTxnTypeByVersionRequest request) throws OpsPanelException;
	
	public GetRuleByNameResponse getRuleByName(GetRuleByNameRequest request) throws OpsPanelException;
	
	public GetRulesByTagsResponse getRulesByTags(GetRulesByTagsRequest request) throws OpsPanelException;
	
	public GetSystemInfoByNameAndEntityResponse getSystemInfoByNameAndEntity(GetSystemInfoByNameAndEntityRequest request) throws OpsPanelException;
	
	public GetSystemInfoByNameResponse getSystemInfoBySystemName(GetSystemInfoByNameRequest request) throws OpsPanelException;
	
	/*public GetTagsByContextResponse getTagsByContextResponse(GetTagsByContextRequest request) throws OpsPanelException;*/
	
	public GetValidGranularityResponse getValidGranularity(GetValidGranularityRequest request) throws OpsPanelException;
	
	public GetValidOperationsForVariableTypeResponse getValidOperations(GetValidOperationsForVariableTypeRequest request) throws OpsPanelException;
	
	public GetVariableDetailedInfoResponse getVariableDetailedInfo(GetVariableDetailedInfoRequest request) throws OpsPanelException;
	
	public GetVariableInfoResponse getVariableInfo(GetVariableInfoRequest request) throws OpsPanelException;
	
	public GetVariablesByPrefixResponse getVariablesByPrefix(GetVariablesByPrefixRequest request) throws OpsPanelException;
	
	public void updateRuleDescription(UpdateRuleDescriptionRequest request) throws OpsPanelException;
	
	public void updateRuleMessage(UpdateRuleMessageRequest request) throws OpsPanelException;
	
	public void updateRuleTags(UpdateRuleTagsRequest request) throws OpsPanelException;
	
	public void updateTagsForVariable(UpdateTagsForVariableRequest request) throws OpsPanelException;
	
	public UpdateVelocityVariableResponse updateVelocityVariable(UpdateVelocityVariableRequest request) throws OpsPanelException;
	
	public void validate(ValidateRuleVariableExpressionRequest request) throws OpsPanelException;
	
	public GetTagsForDomainResponse getTagsForDomain(GetTagsForDomainRequest request) throws OpsPanelException;
	
	public GetCategoriesForDomainResponse getCategoriesForDomain(GetCategoriesForDomainRequest request) throws OpsPanelException;
	
	public GetAllTransactionTypeResponse getAllTransactionType() throws OpsPanelException;
	
	public GetAllTransactionTypeWithProfilesResponse getAllTransactionTypeWithProfiles() throws OpsPanelException;
	
	public GetAllProfilesForTransactionTypeResponse getAllProfilesForTransactionType(GetAllProfilesForTransactionTypeRequest request) throws OpsPanelException;
	
	public GetEntityTypesResponse getEntityTypes() throws OpsPanelException;
	
	public GetTagNamesForEntityTypeResponse getTagNamesForEntityType(GetTagNamesForEntityTypeRequest request) throws OpsPanelException;
	
}