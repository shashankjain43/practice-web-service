package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.clientkeymanagement.entity.TargetApiMapperEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericOnBoardClientRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateClientStatusRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateSecretKeyRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GetClientDetailsRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GetTargetApplicationRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.ShowClientsRequest;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericOnBoardClientResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateClientStatusResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateSecretKeyResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GetClientDetailsResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.ShowClientsResponse;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewTabService;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewerService;
import com.snapdeal.opspanel.clientkeymanagement.service.TargetApiMapperService;
import com.snapdeal.opspanel.clientkeymanagement.utils.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.service.RoleMgtService;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.sdmoney.admin.client.SDMoneyAdminClient;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Controller
@RequestMapping("clientview/")
public class ClientViewController {

	@Autowired
	@Qualifier("clientViewerTabService")
	ClientViewTabService clientViewTabService;
	
	@Autowired
	TargetApiMapperService targetApiMapperService;
	
	@Autowired
	ClientViewerService clientViewerService;

	@Autowired
	SDMoneyAdminClient sdMoneyAdminClient;

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	RoleMgtService roleMgmtService;
	
	@Autowired
	TokenService tokenService;

	@Audited(context = "ClientView", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_view'))")
	@RequestMapping(value = "/getclients", method = RequestMethod.GET)
	public @ResponseBody GenericResponse showClients() throws ClientViewerException, OpsPanelException {

		String token = servletRequest.getHeader("token");
		String userId = tokenService.getEmailFromToken(token);
		
		ShowClientsResponse response = new ShowClientsResponse();
		ShowClientsRequest request = new ShowClientsRequest();
		request.setUserId(userId);
		response = clientViewTabService.showClients(request);
		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "ClientView", searchId = "request.clientName", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_ONBOARDING'))")
	@RequestMapping(value = "/genericonboardclient", method = RequestMethod.POST)
	public @ResponseBody GenericResponse onBoardClient( @RequestBody GenericOnBoardClientRequest request) throws Exception {
		GenericOnBoardClientResponse response = new GenericOnBoardClientResponse();
		response=clientViewTabService.genericOnBoardClient(request);

		for( String emailId : request.getAddUsers() ) {
			try {
				roleMgmtService.createOrUpdateUserWithPermission( emailId, "OPS_KEYPANEL_view" );
			} catch( Exception e ) {
				log.info( "Client viewer onboarding client user while assigning permissions. Email ID: " + emailId + " Exception: " + ExceptionUtils.getFullStackTrace( e ) );
			}
		}

		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}

	@Audited(context = "ClientView", searchId = "request.updateClientStatusRequest.clientName", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_view'))")
	@RequestMapping(value = "/genericupdateclientstatus", method = RequestMethod.POST)
	public @ResponseBody GenericResponse genericupdateClientStatus( @RequestBody GenericUpdateClientStatusRequest request)throws Exception {

		GenericUpdateClientStatusResponse response = new GenericUpdateClientStatusResponse();
		response = clientViewTabService.genericUpdateClientStatus(request);

		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}

	@Audited(context = "ClientView", searchId = "request.updateSecretKeyRequest.clientName", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_view'))")
	@RequestMapping(value = "/genericupdatesecretkey", method = RequestMethod.POST)
	public @ResponseBody GenericResponse genericupdateSecretKey( @RequestBody GenericUpdateSecretKeyRequest request) throws Exception {
		
		GenericUpdateSecretKeyResponse response = new GenericUpdateSecretKeyResponse();
		response = clientViewTabService.genericUpdateSecretKey(request);

		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "ClientView", searchId = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getsupportedtargetapplications", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getSupportedTargetApplications()
			throws ClientViewerException {
		List<String> response = new ArrayList<String>();
		response = clientViewTabService.getSupportedTargetApplications();
		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "ClientView", searchId = "request.targetApplication", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getsupportedapisfortargetapplication", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getSupportedApis(@RequestBody GetTargetApplicationRequest request)
			throws Exception {
		String targetApplication = request.getTargetApplication();
		List<String> response = new ArrayList<String>();

		response = targetApiMapperService.getApisForTargetApplication(targetApplication);

		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "ClientView", searchId = "request.clientName", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_ONBOARDING'))")
	@RequestMapping(value = "/getclientinfo", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getDetailsForNameAndSource(@RequestBody GetClientDetailsRequest request) throws Exception{
		GetClientDetailsResponse response = new GetClientDetailsResponse();

		response = clientViewTabService.getClientDetails(request);

		GenericResponse genericResponse = new GenericResponse(response);
		return genericResponse;
	}
	
	@Audited(context = "ClientView", searchId = "request.targetApplication", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getclientfortargetapplication", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getClientForTargetApplication(@RequestBody GetTargetApplicationRequest request) throws Exception{

/*		String targetApplication = request.getTargetApplication();*/
		List<String> clientNames = clientViewTabService.getClientsForTargetApplication(request);

		GenericResponse genericResponse = new GenericResponse(clientNames);
		return genericResponse;
	}

	@Audited(context = "ClientView", searchId = "request.apiId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_ONBOARDING'))")
	@RequestMapping(value = "/upsertApiForTargetApplication", method = RequestMethod.POST)
	public @ResponseBody GenericResponse upsertApiForTargetApplication(@RequestBody @Valid TargetApiMapperEntity request, BindingResult result ) throws Exception{

		GenericControllerUtils.checkBindingResult( result, "upsertApiForTargetApplication in clientviewcontroller" );
		targetApiMapperService.upsertApiForTargetApplication(request);

		return new GenericResponse( targetApiMapperService.getApisForTargetApplication( request.getTargetApplication() ) );
	}

	@Audited(context = "ClientView", searchId = "request.apiId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_KEYPANEL_ONBOARDING'))")
	@RequestMapping(value = "/deleteApiForTargetApplication", method = RequestMethod.POST)
	public @ResponseBody GenericResponse deleteApiForTargetApplication(@RequestBody @Valid TargetApiMapperEntity request, BindingResult result ) throws Exception{

		GenericControllerUtils.checkBindingResult( result, "deleteApiForTargetApplication in clientviewcontroller" );
		targetApiMapperService.deleteApiForTargetApplication(request);

		return new GenericResponse( targetApiMapperService.getApisForTargetApplication( request.getTargetApplication() ) );
	}
}
