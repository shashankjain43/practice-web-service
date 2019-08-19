package com.snapdeal.ims.client.service;

import java.util.List;

import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.response.ClientDetails;
import com.snapdeal.ims.response.GetClientByMerchantResponse;
import com.snapdeal.ims.response.GetClientByNameResponse;
import com.snapdeal.ims.response.GetClientByStatusResponse;
import com.snapdeal.ims.response.GetClientByTypeResponse;
import com.snapdeal.ims.response.GetClientResponse;
/**
 * 
 * @author subhash
 *
 */
public interface IClientService {

	/**
	 * This function is used to retrieve all clients from IMS database
	 * @return
	 * @throws ValidationException
	 */
	public List<Client> getAllClient() throws ValidationException;

	/**
	 * Get client by client Id, managed by IMS
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientResponse getClientById(String clientId)
			throws ValidationException;
	/**
	 * get client by client name OMS/FC/Shipping etc.
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByNameResponse getClientByName(String clientName)
			throws ValidationException;

	/**
	 * Get client by clientType (External or Internal facing) 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByTypeResponse getClientByType(String clientType)
			throws ValidationException;
	
	/**
	 * Get client by clientPlatform (WEB,WAP,APP) 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public List<ClientDetails> getClientByPlatform(String clientPlatform)
			throws ValidationException;

	/**
	 * Get client by clientBase (Example: Snapdeal)
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByMerchantResponse getClientByMerchant(String merchant)
			throws ValidationException;

	/**
	 * Get client by status (Example: ACTIVE or INACTIVE)
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByStatusResponse getClientByClientStatus(String clientStatus)
			throws ValidationException;

}
