/**
 * 
 */
package com.snapdeal.opspanel.promotion.rp.controller;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.payments.communicator.client.constants.RestURIConstants;
import com.snapdeal.payments.communicator.client.impl.GetAllRulesByCriteriaClientImpl;
import com.snapdeal.payments.communicator.client.impl.GetTemplateByIdClientImpl;
import com.snapdeal.payments.communicator.client.impl.UpdateTemplateClientImpl;
import com.snapdeal.payments.communicator.request.GetAllRulesByCriteriaRequest;
import com.snapdeal.payments.communicator.request.GetTemplateByIdRequest;
import com.snapdeal.payments.communicator.request.UpdateChatTemplateRequest;
import com.snapdeal.payments.communicator.request.UpdateEmailTemplateRequest;
import com.snapdeal.payments.communicator.request.UpdateInAppTemplateRequest;
import com.snapdeal.payments.communicator.request.UpdatePushTemplateRequest;
import com.snapdeal.payments.communicator.request.UpdateSmsTemplateRequest;
import com.snapdeal.payments.communicator.utils.ClientDetails;
import com.snapdeal.payments.communicator.client.IGetAllRulesByCriteriaClient;
import com.snapdeal.payments.communicator.client.IGetTemplateByIdClient;
import com.snapdeal.payments.communicator.client.IUpdateTemplateClient;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

/**
 * @author manisha.varshney
 *
 */

@Slf4j
@RestController
@RequestMapping(RestURIConstants.BASE_URI)
@JsonAutoDetect
public class CommunicatorController {

	private IGetAllRulesByCriteriaClient getAllRulesClient = new GetAllRulesByCriteriaClientImpl();
	private IGetTemplateByIdClient getTemplateClient = new GetTemplateByIdClientImpl();
	private IUpdateTemplateClient updateTemplateClient = new UpdateTemplateClientImpl();

	/*
	 *  TODO: remove console prints, 
	 *  add logs
	 */

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER') )")
	@RequestMapping(value = "/getAllRulesByCriteria", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getAllRulesByCriteria(@RequestParam("start") int start,
			@RequestParam("pageSize") int pageSize,
			HttpServletRequest httpRequest) throws Exception {

		if(start > 1)
			start = 1 + (start-1)*pageSize;

		GetAllRulesByCriteriaRequest getAllRulesRequest = new GetAllRulesByCriteriaRequest();
		getAllRulesRequest.setStart(String.valueOf(start));
		getAllRulesRequest.setPageSize(String.valueOf(pageSize));

		com.snapdeal.payments.communicator.response.GenericResponse communicatoResponse = 
				getAllRulesClient.getAllRulesByCriteria(getAllRulesRequest);

		return OPSUtils.getGenericResponse(communicatoResponse.getData());

	}


	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/getSmsTemplateById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getSmsTemplateById(@RequestParam("templateId") int templateId,
			HttpServletRequest httpRequest) throws Exception {

		GetTemplateByIdRequest getTempRequest = new GetTemplateByIdRequest();
		getTempRequest.setTemplateId(String.valueOf(templateId));
		com.snapdeal.payments.communicator.response.GenericResponse response = 
				getTemplateClient.getSmsTemplateById(getTempRequest);
		return OPSUtils.getGenericResponse(response.getData());

	}


	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/getChatTemplateById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getChatTemplateById(@RequestParam("templateId") int templateId,
			HttpServletRequest httpRequest) throws Exception {

		GetTemplateByIdRequest getTempRequest = new GetTemplateByIdRequest();
		getTempRequest.setTemplateId(String.valueOf(templateId));
		com.snapdeal.payments.communicator.response.GenericResponse response = 
				getTemplateClient.getChatTemplateById(getTempRequest);
		return OPSUtils.getGenericResponse(response.getData());
	}


	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/getEmailTemplateById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getEmailTemplateById(@RequestParam("templateId") int templateId,
			HttpServletRequest httpRequest) throws Exception {

		GetTemplateByIdRequest getTempRequest = new GetTemplateByIdRequest();
		getTempRequest.setTemplateId(String.valueOf(templateId));
		com.snapdeal.payments.communicator.response.GenericResponse response = 
				getTemplateClient.getEmailTemplateById(getTempRequest);
		return OPSUtils.getGenericResponse(response.getData());

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/getPushTemplateById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPushTemplateById(@RequestParam("templateId") int templateId,
			HttpServletRequest httpRequest) throws Exception {

		GetTemplateByIdRequest getTempRequest = new GetTemplateByIdRequest();
		getTempRequest.setTemplateId(String.valueOf(templateId));
		com.snapdeal.payments.communicator.response.GenericResponse response = 
				getTemplateClient.getPushTemplateById(getTempRequest);
		return OPSUtils.getGenericResponse(response.getData());

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_ADMIN_VIEW') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/getInAppTemplateById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getInAppTemplateById(@RequestParam("templateId") int templateId,
			HttpServletRequest httpRequest) throws Exception {

		GetTemplateByIdRequest getTempRequest = new GetTemplateByIdRequest();
		getTempRequest.setTemplateId(String.valueOf(templateId));
		com.snapdeal.payments.communicator.response.GenericResponse response = 
				getTemplateClient.getInAppTemplateById(getTempRequest);
		return OPSUtils.getGenericResponse(response.getData());

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/updateSmsTemplate", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updateSmsTemplate(@RequestBody @Valid UpdateSmsTemplateRequest requestObject,
			HttpServletRequest httpRequest) throws Exception {

		return OPSUtils.getGenericResponse(updateTemplateClient.updateSmsTemplate(requestObject));
	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/updateChatTemplate", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updateChatTemplate(@RequestBody @Valid UpdateChatTemplateRequest requestObject,
			HttpServletRequest httpRequest) throws Exception {

		return OPSUtils.getGenericResponse(updateTemplateClient.updateChatTemplate(requestObject));

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/updateEmailTemplate", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updateEmailTemplate(@RequestBody @Valid UpdateEmailTemplateRequest requestObject,
			HttpServletRequest httpRequest) throws Exception {
		
		return OPSUtils.getGenericResponse(updateTemplateClient.updateEmailTemplate(requestObject));

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/updatePushTemplate", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePushTemplate(@RequestBody @Valid UpdatePushTemplateRequest requestObject,
			HttpServletRequest httpRequest) throws Exception {

		return OPSUtils.getGenericResponse(updateTemplateClient.updatePushTemplate(requestObject));

	}

	@PreAuthorize("(hasPermission('OPS_COMMUNICATOR_ADMIN_UPDATE') or hasPermission('OPS_COMMUNICATOR_SUPERUSER'))")
	@RequestMapping(value = "/updateInAppTemplate", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updateInAppTemplate(@RequestBody @Valid UpdateInAppTemplateRequest requestObject,
			HttpServletRequest httpRequest) throws Exception {

		return OPSUtils.getGenericResponse(updateTemplateClient.updateInAppTemplate(requestObject));

	}
	 
}
