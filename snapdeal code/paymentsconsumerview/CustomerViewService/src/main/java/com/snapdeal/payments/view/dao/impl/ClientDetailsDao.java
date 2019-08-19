package com.snapdeal.payments.view.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;
import com.snapdeal.payments.view.dao.IClientDetailsDao;
import com.snapdeal.payments.view.mapper.IClientDetailsMapper;

@Repository
public class ClientDetailsDao implements IClientDetailsDao {

	@Autowired
	private IClientDetailsMapper clientDetailsMapper;

	@Override
	@Timed
	@Marked
	public Client getClientById(String clientId) {
		return clientDetailsMapper.getClientById(clientId);
	}

	@Override
	@Timed
	@Marked
	public Client getClientByName(String clientName) {
		return clientDetailsMapper.getClientByName(clientName);
	}

	@Override
	@Timed
	@Marked
	public List<Client> getAllClients() {
		return clientDetailsMapper.getAllClients();
	}


	@Override
	public void createClient(Client clientDetailEntity) {
		clientDetailEntity.setCreatedTime(new Date());
		clientDetailEntity.setUpdatedTime(new Date());
		clientDetailsMapper.createClient(clientDetailEntity);
	}

	@Override
	public void updateClientStatus(UpdateClientStatusRequest request) {
		clientDetailsMapper.updateClientStatus(request);
	}
}
