package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
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

public class ClientServiceClient extends AbstractPaymentsViewClient implements IClientService{

	public CreateClientResponse createClient(CreateClientRequest request)
			throws ValidationException {
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<CreateClientResponse>>(){},
								HttpMethod.POST,
								RestURIConstants.CLIENT+RestURIConstants.CREATE_CLIENT); 
	}

	public UpdateClientStatusResponse updateClientStatus(
			UpdateClientStatusRequest request) {
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<UpdateClientStatusResponse>>(){},
								HttpMethod.PUT,
								RestURIConstants.CLIENT+RestURIConstants.UPDATE_CLIENT); 
	}

	public GetAllClientsResponse getAllClients(GetAllClientsRequest request) {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetAllClientsResponse>>(){},
								HttpMethod.GET,
								RestURIConstants.CLIENT+RestURIConstants.GET_ALL_CLIENT); 
	}

}
