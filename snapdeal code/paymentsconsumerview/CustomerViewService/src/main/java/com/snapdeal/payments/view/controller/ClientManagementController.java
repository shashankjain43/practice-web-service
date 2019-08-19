package com.snapdeal.payments.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.exception.service.ValidationException;
import com.snapdeal.payments.view.commons.request.CreateClientRequest;
import com.snapdeal.payments.view.commons.request.GetAllClientsRequest;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;
import com.snapdeal.payments.view.commons.response.CreateClientResponse;
import com.snapdeal.payments.view.commons.response.GetAllClientsResponse;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.commons.response.UpdateClientStatusResponse;
import com.snapdeal.payments.view.commons.service.IClientService;

@Controller
@RequestMapping(RestURIConstants.VIEW + RestURIConstants.CLIENT)
@Slf4j
public class ClientManagementController extends AbstractViewController {

	@Autowired
	private IClientService clientService;

	/**
	 * This API will be responsible for Client creation
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	@AuthorizeRequest
	@RequestAware
	@RequestMapping(value = RestURIConstants.CREATE_CLIENT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	@ResponseBody
	public ServiceResponse<CreateClientResponse> createClient(
			@RequestBody @Valid CreateClientRequest request,
			HttpServletRequest httpRequest) throws ValidationException {
		ServiceResponse<CreateClientResponse> serviceResponse = new ServiceResponse<CreateClientResponse>();
		serviceResponse.setResponse(clientService.createClient(request));
		return serviceResponse;
	}

	@AuthorizeRequest
	@RequestMapping(value = RestURIConstants.UPDATE_CLIENT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResponse<UpdateClientStatusResponse> updateStatusByClient(
			@RequestBody @Valid UpdateClientStatusRequest request,
			String clientId, HttpServletRequest httpServletRequest) {

		ServiceResponse<UpdateClientStatusResponse> serviceResponse = new ServiceResponse<UpdateClientStatusResponse>();
		serviceResponse.setResponse(clientService.updateClientStatus(request));
		return serviceResponse;
	}

	@AuthorizeRequest
	@RequestMapping(value = RestURIConstants.GET_ALL_CLIENT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse<GetAllClientsResponse> getAllClient(
			@RequestBody @Valid GetAllClientsRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetAllClientsResponse> serviceResponse = new ServiceResponse<GetAllClientsResponse>();
		serviceResponse.setResponse(clientService.getAllClients(request));
		return serviceResponse;
	}
}
