package com.snapdeal.payments.view.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;

public interface IClientDetailsMapper {
	/**
	 * This function will create client in database
	 * @param clientEntity
	 */
	public void createClient(Client clientEntity);

	/**
	 * This function will updateClient status (ACTIVE/INACTIVE)
	 */
	public void updateClientStatus(UpdateClientStatusRequest request);

	/**
	 * Get client by client Id,
	 * @param clientId
	 * @return
	 */
	public Client getClientById(@Param("clientId") String clientId);

	/**
	 * get client by client name 
	 * @param clientName
	 * @return
	 */
	public Client getClientByName(@Param("clientName") String clientName);

	/**
	 * This method is used to get all clients from database
	 * @return
	 */
	public List<Client> getAllClients();

}
