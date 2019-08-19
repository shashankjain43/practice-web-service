package com.snapdeal.ums.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.core.DisableServiceRequest;
import com.snapdeal.core.EnableAllServicesRequest;
import com.snapdeal.core.EnableServiceRequest;
import com.snapdeal.core.GetServerBehaviourContextRequest;
import com.snapdeal.core.GetServerBehaviourContextResponse;
import com.snapdeal.ums.server.services.IServerBehaviourContextService;

@Controller
@RequestMapping("/server/behaviour/")
public class ServerBehaviouralContextController {

	@Autowired
	private IServerBehaviourContextService serverBehaviourContextService;

	@RequestMapping(value = "getServerBehaviourContext", produces = "application/sd-service")
	@ResponseBody
	public GetServerBehaviourContextResponse getServerBehaviourContext(
			@RequestBody GetServerBehaviourContextRequest request) {
		GetServerBehaviourContextResponse response = new GetServerBehaviourContextResponse();
		response = serverBehaviourContextService.getCurrentServerBehaviourContext();
		response.setProtocol(request.getResponseProtocol());
		return response;
	}

	@RequestMapping(value = "disableService", produces = "application/sd-service")
	@ResponseBody
	public GetServerBehaviourContextResponse disableUMSService(
			@RequestBody DisableServiceRequest request) {
		GetServerBehaviourContextResponse response = new GetServerBehaviourContextResponse();
		response = serverBehaviourContextService.disableService(request);
		response.setProtocol(request.getResponseProtocol());
		return response;
	}

	@RequestMapping(value = "enableService", produces = "application/sd-service")
	@ResponseBody
	public GetServerBehaviourContextResponse enableUMSService(
			@RequestBody EnableServiceRequest request) {
		GetServerBehaviourContextResponse response = new GetServerBehaviourContextResponse();

		response = serverBehaviourContextService.enableService(request);
		response.setProtocol(request.getResponseProtocol());
		return response;
	}

	@RequestMapping(value = "createServerBehaviourContext", produces = "application/sd-service")
	@ResponseBody
	public GetServerBehaviourContextResponse createServerBehaviourContext(
			@RequestBody GetServerBehaviourContextRequest request) {
		GetServerBehaviourContextResponse response = new GetServerBehaviourContextResponse();

		response = serverBehaviourContextService.createServerBehaviourContext(request);
		response.setProtocol(request.getResponseProtocol());
		return response;
	}
	
	
//	@RequestMapping(value = "enableAllServices", produces = "application/sd-service")
//	@ResponseBody
//	public GetServerBehaviourContextResponse enableUMSService(
//			@RequestBody EnableAllServicesRequest request) {
//		GetServerBehaviourContextResponse response = new GetServerBehaviourContextResponse();
//
//		response = serverBehaviourContextService
//				.enableAllServices(request);
//		response.setProtocol(request.getResponseProtocol());
//		return response;
//	}

}