package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.userpanel.ruleDashboard.request.GetProfilesForTxnTypeWithFilterRequest;
import com.snapdeal.opspanel.userpanel.ruleDashboard.response.GetProfilesForTxnTypeWithFilterResponse;
import com.snapdeal.opspanel.userpanel.ruleDashboard.response.ProfileInfoWithFilterResponse;
import com.snapdeal.opspanel.userpanel.ruleDashboard.service.RuleDashboardService;
import com.snapdeal.opspanel.userpanel.ruleDashboard.utils.RuleDashboardConstants;
import com.snapdeal.payments.dashboard.model.ProfileDetail;
import com.snapdeal.payments.dashboard.model.ProfileInfo;
import com.snapdeal.payments.dashboard.profileManager.request.AssociateProfileRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetAllProfilesForTransactionTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetLatestProfilesForTransactionTypeRequest;
//import com.snapdeal.payments.dashboard.profileManager.request.GetLatestProfileForTransactionTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetProfilesForTxnTypeByProfileNameRequest;
import com.snapdeal.payments.dashboard.profileManager.request.GetTagNamesForEntityTypeRequest;
import com.snapdeal.payments.dashboard.profileManager.response.GetLatestProfilesForTransactionTypeResponse;
import com.snapdeal.payments.dashboard.ruleManager.request.AddRuleRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.GetRuleByNameRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.GetRulesByTagsRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleDescriptionRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleMessageRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.UpdateRuleTagsRequest;
import com.snapdeal.payments.dashboard.ruleManager.request.ValidateRuleVariableExpressionRequest;
import com.snapdeal.payments.dashboard.systemManager.request.AddSystemRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetAllSystemNamesRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetSystemInfoByNameAndEntityRequest;
import com.snapdeal.payments.dashboard.systemManager.request.GetSystemInfoByNameRequest;
import com.snapdeal.payments.dashboard.tagManager.request.GetCategoriesForDomainRequest;
import com.snapdeal.payments.dashboard.tagManager.request.GetTagsForDomainRequest;
import com.snapdeal.payments.dashboard.transactionManager.request.AddProfilesForTxnTypeRequest;
import com.snapdeal.payments.dashboard.transactionManager.request.GetLatestActiveProfilesForTxnTypeRequest;
import com.snapdeal.payments.dashboard.transactionManager.request.GetProfilesForTxnTypeByVersionRequest;
import com.snapdeal.payments.dashboard.transactionManager.response.GetLatestActiveProfilesForTxnTypeResponse;
import com.snapdeal.payments.dashboard.variableManager.request.AddPrimitiveVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.AddProfileVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.AddVelocityVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetAllVariablesRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetValidGranularityRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetValidOperationsForVariableTypeRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariableDetailedInfoRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariableInfoRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariablesByPrefixRequest;
import com.snapdeal.payments.dashboard.variableManager.request.GetVariablesByTypeRequest;
import com.snapdeal.payments.dashboard.variableManager.request.UpdateTagsForVariableRequest;
import com.snapdeal.payments.dashboard.variableManager.request.UpdateVelocityVariableRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

@Controller
@RequestMapping(RuleDashboardConstants.RULE_DASHBOARD_PREFIX)
@Slf4j
public class RuleDashboardController {
	
	@Autowired
	RuleDashboardService service;
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addPrimitiveVariable",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addPrimitiveVariable(@RequestBody AddPrimitiveVariableRequest request) throws OpsPanelException{
		return getGenericResponse(service.addPrimitiveVariable(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addProfileVariable",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addProfileVariable(@RequestBody AddProfileVariableRequest request) throws OpsPanelException{
		return getGenericResponse(service.addProfileVariable(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addRule",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addRule(@RequestBody AddRuleRequest request) throws OpsPanelException{
		return getGenericResponse(service.addRule(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addSystem",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addSystem(@RequestBody AddSystemRequest request) throws OpsPanelException{
		return getGenericResponse(service.addSystem(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addVelocityVariable",method=RequestMethod.POST)
	public @ResponseBody GenericResponse addVelocityVariable(@RequestBody AddVelocityVariableRequest request) throws OpsPanelException{
		return getGenericResponse(service.addVelocityVariable(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/associateProfile",method=RequestMethod.POST)
	public @ResponseBody GenericResponse associateProfile(@RequestBody AssociateProfileRequest request) throws OpsPanelException{
		return getGenericResponse(service.associateProfile(request));
	}
	

	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllSystemNamesRequests",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getAllSystemNamesRequests(@RequestBody GetAllSystemNamesRequest request) throws OpsPanelException{
		return getGenericResponse(service.getAllSystemNamesRequests(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllVariables",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getAllVariables(@RequestBody GetAllVariablesRequest request) throws OpsPanelException{
		return getGenericResponse(service.getAllVariables(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllVariablesByType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getAllVariablesByType(@RequestBody GetVariablesByTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getAllVariablesByType(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getLatestActiveProfilesForTxnType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getLatestActiveProfilesForTxnType(@RequestBody GetLatestActiveProfilesForTxnTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getLatestActiveProfilesForTxnType(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getLatestProfilesForTransactionType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getLatestProfileForTransactionType(@RequestBody GetLatestProfilesForTransactionTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getLatestProfilesForTransactionType(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getProfilesForTxnTypeByProfileName",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getProfilesForTxnTypeByProfileName(@RequestBody GetProfilesForTxnTypeByProfileNameRequest request) throws OpsPanelException{
		return getGenericResponse(service.getProfilesForTxnTypeByProfileName(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getProfilesForTxnTypeByVersion",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getProfilesForTxnTypeByVersion(@RequestBody GetProfilesForTxnTypeByVersionRequest request) throws OpsPanelException{
		return getGenericResponse(service.getProfilesForTxnTypeByVersion(request));
	}
	
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getRuleByName",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getRuleByName(@RequestBody GetRuleByNameRequest request) throws OpsPanelException{
		return getGenericResponse(service.getRuleByName(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getRulesByTags",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getRulesByTags(@RequestBody GetRulesByTagsRequest request) throws OpsPanelException{
		return getGenericResponse(service.getRulesByTags(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getSystemInfoByNameAndEntity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getSystemInfoByNameAndEntity(@RequestBody GetSystemInfoByNameAndEntityRequest request) throws OpsPanelException{
		return getGenericResponse(service.getSystemInfoByNameAndEntity(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getSystemInfoBySystemName",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getSystemInfoBySystemName(@RequestBody GetSystemInfoByNameRequest request) throws OpsPanelException{
		return getGenericResponse(service.getSystemInfoBySystemName(request));
	}
	
//	@RequestMapping(value="/getTagsByContextResponse",method=RequestMethod.POST)
//	public @ResponseBody GenericResponse getTagsByContextResponse(@RequestBody GetTagsByContextRequest request) throws OpsPanelException{
//		return getGenericResponse(service.getTagsByContextResponse(request));
//	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getValidGranularity",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getValidGranularity(@RequestBody GetValidGranularityRequest request) throws OpsPanelException{
		return getGenericResponse(service.getValidGranularity(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getValidOperations",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getValidOperations(@RequestBody GetValidOperationsForVariableTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getValidOperations(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getVariableDetailedInfo",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getVariableDetailedInfo(@RequestBody GetVariableDetailedInfoRequest request) throws OpsPanelException{
		return getGenericResponse(service.getVariableDetailedInfo(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getVariableInfo",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getVariableInfo(@RequestBody GetVariableInfoRequest request) throws OpsPanelException{
		return getGenericResponse(service.getVariableInfo(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getVariablesByPrefix",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getVariablesByPrefix(@RequestBody GetVariablesByPrefixRequest request) throws OpsPanelException{
		return getGenericResponse(service.getVariablesByPrefix(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/addProfilesForTransactionType",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProfilesForTransactionType(@RequestBody AddProfilesForTxnTypeRequest request) throws OpsPanelException{
		service.addProfilesForTransactionType(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateRuleDescription",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateRuleDescription(@RequestBody UpdateRuleDescriptionRequest request) throws OpsPanelException{
		service.updateRuleDescription(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateRuleMessage",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateRuleMessage(@RequestBody UpdateRuleMessageRequest request) throws OpsPanelException{
		service.updateRuleMessage(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateRuleTags",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateRuleTags(@RequestBody UpdateRuleTagsRequest request) throws OpsPanelException{
		service.updateRuleTags(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateTagsForVariable",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateTagsForVariable(@RequestBody UpdateTagsForVariableRequest request) throws OpsPanelException{
		service.updateTagsForVariable(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/updateVelocityVariable",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateVelocityVariable(@RequestBody UpdateVelocityVariableRequest request) throws OpsPanelException{
		service.updateVelocityVariable(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void validate(@RequestBody ValidateRuleVariableExpressionRequest request) throws OpsPanelException{
		service.validate(request);
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getTagsForDomain",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getTagsForDomain(@RequestBody GetTagsForDomainRequest request) throws OpsPanelException{
		return getGenericResponse(service.getTagsForDomain(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getCategoriesForDomain",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getCategoriesForDomain(@RequestBody GetCategoriesForDomainRequest request) throws OpsPanelException{
		return getGenericResponse(service.getCategoriesForDomain(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllTransactionType",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getAllTransactionType() throws OpsPanelException{
		return getGenericResponse(service.getAllTransactionType());
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllTransactionTypeWithProfiles",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getAllTransactionTypeWithProfiles() throws OpsPanelException{
		return getGenericResponse(service.getAllTransactionTypeWithProfiles());
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getAllProfilesForTransactionType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getAllTransactionTypeWithProfiles(@RequestBody GetAllProfilesForTransactionTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getAllProfilesForTransactionType(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getEntityTypes",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getEntityTypes() throws OpsPanelException{
		return getGenericResponse(service.getEntityTypes());
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getTagNamesForEntityType",method=RequestMethod.POST)
	public @ResponseBody GenericResponse getAllTransactionTypeWithProfiles(@RequestBody GetTagNamesForEntityTypeRequest request) throws OpsPanelException{
		return getGenericResponse(service.getTagNamesForEntityType(request));
	}
	
	@PreAuthorize("( hasPermission('OPS_RULE_DASHBOARD_UPDATER'))")
	@Audited(context = "RULE" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value="/getProfilesForTxnTypeWithFilter", method=RequestMethod.POST)
	public @ResponseBody GenericResponse getProfilesForTxnTypeWithFilter(@RequestBody GetProfilesForTxnTypeWithFilterRequest request) throws OpsPanelException{
		
		GetProfilesForTxnTypeWithFilterResponse response = new GetProfilesForTxnTypeWithFilterResponse();
		response.setError(null);
		
		GetLatestProfilesForTransactionTypeRequest getLatestProfilesForTransactionTypeRequest = new GetLatestProfilesForTransactionTypeRequest();
		getLatestProfilesForTransactionTypeRequest.setTransactionType(request.getTransactionType());
		
		GetLatestProfilesForTransactionTypeResponse getLatestProfilesForTransactionTypeResponse = new GetLatestProfilesForTransactionTypeResponse();
		
		getLatestProfilesForTransactionTypeResponse = service.getLatestProfilesForTransactionType(getLatestProfilesForTransactionTypeRequest);
		
		GetLatestActiveProfilesForTxnTypeRequest getLatestActiveProfilesForTxnTypeRequest = new GetLatestActiveProfilesForTxnTypeRequest();
		getLatestActiveProfilesForTxnTypeRequest.setTransactionType(request.getTransactionType());
		
		GetLatestActiveProfilesForTxnTypeResponse getLatestActiveProfilesForTxnTypeResponse = new GetLatestActiveProfilesForTxnTypeResponse();
		
		getLatestActiveProfilesForTxnTypeResponse = service.getLatestActiveProfilesForTxnType(getLatestActiveProfilesForTxnTypeRequest);
		
		List<ProfileInfoWithFilterResponse> list = new ArrayList<ProfileInfoWithFilterResponse>();
		
		if (getLatestProfilesForTransactionTypeResponse != null ) {
			if (getLatestProfilesForTransactionTypeResponse.getProfileInfoList() != null) {
				if (getLatestProfilesForTransactionTypeResponse.getProfileInfoList().size() != 0) {
					for (ProfileInfo profileInfo : getLatestProfilesForTransactionTypeResponse
							.getProfileInfoList()) {

						ProfileInfoWithFilterResponse profileInfoWithFilterResponse = new ProfileInfoWithFilterResponse();

						profileInfoWithFilterResponse.setCreatedBy(profileInfo
								.getCreatedBy());
						profileInfoWithFilterResponse.setCreatedOn(profileInfo
								.getCreatedOn());
						profileInfoWithFilterResponse
								.setProfileName(profileInfo.getProfileName());
						profileInfoWithFilterResponse
								.setProfileVariable(profileInfo
										.getProfileVariable());
						profileInfoWithFilterResponse
								.setProfileVersion(profileInfo
										.getProfileVersion());
						profileInfoWithFilterResponse
								.setRuleAttributeMap(profileInfo
										.getRuleAttributeMap());
						profileInfoWithFilterResponse
								.setTagAttributeMap(profileInfo
										.getTagAttributeMap());
						profileInfoWithFilterResponse
								.setTransactionType(profileInfo
										.getTransactionType());
						profileInfoWithFilterResponse.setUpdatedBy(profileInfo
								.getUpdatedBy());
						profileInfoWithFilterResponse.setUpdatedOn(profileInfo
								.getUpdatedOn());

						profileInfoWithFilterResponse.setActive(false);
						profileInfoWithFilterResponse.setActiveOnVersion(-1);
						profileInfoWithFilterResponse.setRulePercentage(0);

						if (getLatestActiveProfilesForTxnTypeResponse != null && getLatestActiveProfilesForTxnTypeResponse.getProfileDetails() != null && getLatestActiveProfilesForTxnTypeResponse.getProfileDetails().size() !=0) {
							for (ProfileDetail profileDetail : getLatestActiveProfilesForTxnTypeResponse
									.getProfileDetails()) {
								if (profileDetail.getProfile().equals(
										profileInfo.getProfileName())) {
									profileInfoWithFilterResponse
											.setActive(true);
									profileInfoWithFilterResponse
											.setActiveOnVersion(profileDetail
													.getVersion());
									profileInfoWithFilterResponse.setRulePercentage(profileDetail.getRulePercentage());
								}
							}
						} else {
							log.info("getLatestActiveProfilesForTxnTypeResponse == null OR getLatestActiveProfilesForTxnTypeResponse.getProfileDetails() == null OR getLatestActiveProfilesForTxnTypeResponse.getProfileDetails().size() == 0");
							response.setError("getLatestActiveProfilesForTxnTypeResponse == null OR getLatestActiveProfilesForTxnTypeResponse.getProfileDetails() == null OR getLatestActiveProfilesForTxnTypeResponse.getProfileDetails().size() == 0");
						}
						list.add(profileInfoWithFilterResponse);
						
					}
				} else {
					log.info("ERROR : getLatestProfilesForTransactionTypeResponse.getProfileInfoList().size() == 0 ");
					response.setError("getLatestProfilesForTransactionTypeResponse.getProfileInfoList().size() == 0 ");
				}
			} else {
				log.info("ERROR : getLatestProfilesForTransactionTypeResponse.getProfileInfoList() == null ");
				response.setError("getLatestProfilesForTransactionTypeResponse.getProfileInfoList() == null");
			}
		} else {
			log.info("ERROR : getLatestProfilesForTransactionTypeResponse == null ");
			response.setError("getLatestProfilesForTransactionTypeResponse == null");
		}
		
		
		response.setProfileInfoWithFilterList(list);
		
		return getGenericResponse(response);
		
	}
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}

}
