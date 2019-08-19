package com.snapdeal.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.snapdeal.admin.request.ActivateClientRequest;
import com.snapdeal.admin.request.CreateClientRequest;
import com.snapdeal.admin.request.DeactivateClientRequest;
import com.snapdeal.admin.request.RegenerateClientKeyRequest;
import com.snapdeal.admin.response.ActivateClientResponse;
import com.snapdeal.admin.response.ClientDetails;
import com.snapdeal.admin.response.CreateClientResponse;
import com.snapdeal.admin.response.DeactivateClientResponse;
import com.snapdeal.admin.response.GetAllClientResponse;
import com.snapdeal.admin.response.GetClientByMerchantResponse;
import com.snapdeal.admin.response.GetClientByNameResponse;
import com.snapdeal.admin.response.GetClientByStatusResponse;
import com.snapdeal.admin.response.GetClientByTypeResponse;
import com.snapdeal.admin.response.GetClientResponse;
import com.snapdeal.admin.response.RegenerateClientKeyResponse;
import com.snapdeal.ims.exception.ValidationException;
/**
 * 
 * @author subhash
 *
 */
public interface IClientService {
	/**
	 * This function will create client in database
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public CreateClientResponse createClient(CreateClientRequest request)
			throws ValidationException;

	/**
	 * This function will deactivate client in IMS database
	 * 
	 * @param DeactivateClientRequest
	 * @throws ValidationException
	 */
	public DeactivateClientResponse deactivateClient(
			DeactivateClientRequest request) throws ValidationException;

	/**
	 * This function will activate client in IMS database
	 * 
	 * @param DeactivateClientRequest
	 * @throws ValidationException
	 */
	public ActivateClientResponse activateClient(ActivateClientRequest request)
			throws ValidationException;

	/**
	 * This function is used to retrieve all clients from IMS database
	 * 
	 * @return
	 * @throws ValidationException
	 */
	public GetAllClientResponse getAllClient(HttpServletRequest request);

	/**
	 * Get client by client Id, managed by IMS
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientResponse getClientById(String clientId)
			throws ValidationException;
	/**
	 * get client by client name OMS/FC/Shipping etc.
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByNameResponse getClientByName(String clientName)
			throws ValidationException;

	/**
	 * Get client by clientType (External or Internal facing)
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByTypeResponse getClientByType(String clientType)
			throws ValidationException;

	/**
	 * Get client by clientPlatform (WEB,WAP,APP)
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public List<ClientDetails> getClientByPlatform(String clientPlatform)
			throws ValidationException;

	/**
	 * Get client by clientBase (Example: Snapdeal)
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByMerchantResponse getClientByMerchant(String merchant);
	/**
	 * Get client by status (Example: ACTIVE or INACTIVE)
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public GetClientByStatusResponse getClientByClientStatus(String clientStatus);
	/**
	 * This function will recreate client salt/key and update it in IMS database
	 * 
	 * @param request
	 * @return
	 * @throws ValidationException
	 */
	public RegenerateClientKeyResponse regenerateClientKey(
			RegenerateClientKeyRequest request);

	public String getClientIDByMerchantAndClientName(String clientName,
			String merchant);
}
