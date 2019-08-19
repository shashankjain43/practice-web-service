package com.snapdeal.payments.view.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.cache.impl.ClientCache;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.enums.ClientStatus;
import com.snapdeal.payments.view.commons.exception.service.ValidationException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.commons.request.CreateClientRequest;
import com.snapdeal.payments.view.commons.request.GetAllClientsRequest;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;
import com.snapdeal.payments.view.commons.response.CreateClientResponse;
import com.snapdeal.payments.view.commons.response.GetAllClientsResponse;
import com.snapdeal.payments.view.commons.response.UpdateClientStatusResponse;
import com.snapdeal.payments.view.commons.service.IClientService;
import com.snapdeal.payments.view.commons.utils.RandomStringGenerator;
import com.snapdeal.payments.view.dao.IClientDetailsDao;
import com.snapdeal.payments.view.utils.validator.GenericValidator;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDetailsDao clientDetailDao;

	@Autowired
	private GenericValidator<AbstractRequest> validator;
	
	@Autowired
	private ClientCache clientCache;

	@Override
	@Timed
	@Marked
	@Logged
	@Transactional
	public CreateClientResponse createClient(CreateClientRequest request)
			throws ValidationException {
		validator.validate(request);
		final CreateClientResponse response = new CreateClientResponse();

		if (setClientIfExists(response, request.getClientName())) {// if already
																	// exists
			return response;
		} else {// new client
			final Client clientEntity = new Client();
			final String randomId = RandomStringGenerator
					.getRandomKeyUsingUUID();
			final String clientKey = RandomStringGenerator
					.nextString(RestURIConstants.DEFAULT_SECURE_KEY_LEN);
			clientEntity.setId(randomId);
			clientEntity.setClientKey(clientKey);
			clientEntity.setClientName(request.getClientName());
			clientEntity.setClientStatus(ClientStatus.ACTIVE);
			clientDetailDao.createClient(clientEntity);
			response.setClientDetails(clientEntity);
		}
		return response;
	}

	private boolean setClientIfExists(CreateClientResponse response,
			String clientName) {

		Client client = clientDetailDao.getClientByName(clientName);
		if (null!=client) {
			response.setClientDetails(client);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public UpdateClientStatusResponse updateClientStatus(
			UpdateClientStatusRequest request) {
		UpdateClientStatusResponse response = new UpdateClientStatusResponse();
		if (isClientExist(request.getClientName())) {
			clientDetailDao.updateClientStatus(request);
			clientCache.loadCache();
			response.setMessage("SUCCESS");
		} else {
			response.setMessage("Client name not present in the system");
		}
		return response;
	}

	private boolean isClientExist(String clientName) {
		boolean isClientExist = false;
		Client client = clientDetailDao.getClientByName(clientName);
		if (client!=null) {
			isClientExist = true;
		}
		return isClientExist;
	}

	@Override
	public GetAllClientsResponse getAllClients(GetAllClientsRequest request) {
		GetAllClientsResponse response = new GetAllClientsResponse();
		List<Client> clients = clientDetailDao.getAllClients();
		response.setClients(clients);
		return response;
	}
}
