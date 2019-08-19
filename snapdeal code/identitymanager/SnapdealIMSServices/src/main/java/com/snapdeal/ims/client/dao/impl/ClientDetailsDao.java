package com.snapdeal.ims.client.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.IClientDetailsMapper;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;


@Repository
public class ClientDetailsDao implements IClientDetailsDao{
	
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
	public List<Client> getClientByName(String clientName) {
		return clientDetailsMapper.getClientByName(clientName);
	}

	@Override
   @Timed
   @Marked
	public List<Client> getClientByType(ClientType clientType) {
		return clientDetailsMapper.getClientByType(clientType);
	}

	@Override
   @Timed
   @Marked
	public List<Client> getClientByMerchant(Merchant merchant) {
		return clientDetailsMapper.getClientByMerchant(merchant);
	}

	@Override
   @Timed
   @Marked
	public List<Client> getClientByClientStatus(ClientStatus clientStatus) {
		return clientDetailsMapper.getClientByClientStatus(clientStatus);
	}

	@Override
   @Timed
   @Marked
	public Client getClientByNameAndMerchant(String clientName, Merchant merchant) {
		return clientDetailsMapper.getClientByNameAndMerchant(clientName, merchant);
	}

	@Override
   @Timed
   @Marked
	public List<Client> getAllClient() {
		return clientDetailsMapper.getAllClient();
	}

	@Override
   @Timed
   @Marked
	public List<Client> getClientByPlatform(ClientPlatform clientPlatform) {
		return clientDetailsMapper.getClientByPlatform(clientPlatform);
	}
}
