package com.snapdeal.payments.view.dao;


import java.util.List;

import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;

public interface IClientDetailsDao {
	
	public void createClient(Client clientDetailEntity);
	
	/**
	 * Get client by client Id
	 * @param clientId
	 * @return
	 */
	public Client getClientById(String clientId);

	/**
	 * get client by client name 
	 * @param clientName
	 * @return
	 */
	public Client getClientByName(String clientName);

	/**
	 * This method is used to get all clients from database
	 * @return
	 */
	public List<Client> getAllClients();

	public void updateClientStatus(UpdateClientStatusRequest request) ;
}

