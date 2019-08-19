package com.snapdeal.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.admin.dao.entity.Client;
import com.snapdeal.admin.dao.entity.Client.ClientPlatform;
import com.snapdeal.admin.dao.entity.Client.ClientStatus;
import com.snapdeal.admin.dao.entity.Client.ClientType;

/**
 * @author Subhash
 *
 */
public interface IClientDetailsDao {
	/**
	 * This function will create client in database
	 * 
	 * @param clientDetailsEntity
	 */
	public void createClient(Client clientDetailsEntity);

	/**
	 * This function will updateClient status (ACTIVE/INACTIVE) in IMS database
	 * 
	 * @param clientId
	 * @param clientStatus
	 */
	public void updateClientStatus(@Param("clientId") String clientId,
			@Param("newStatus") String newStatus);

	/**
	 * Get client by client Id, managed by IMS
	 * 
	 * @param clientId
	 * @return
	 */
	public Client getClientById(@Param("clientId") String clientId);

	/**
	 * get client by client name OMS/FC/Shipping etc.
	 * 
	 * @param clientName
	 * @return
	 */
	public List<Client> getClientByName(@Param("clientName") String clientName);

	/**
	 * Get client by clientType (External or Internal facing)
	 * 
	 * @param clientType
	 * @return
	 */
	public List<Client> getClientByType(
			@Param("clientType") ClientType clientType);

	/**
	 * Get client by clientBase (Example: Snapdeal)
	 * 
	 * @param client
	 * @return
	 */
	public List<Client> getClientByMerchant(@Param("merchant") String merchant);

	/**
	 * Get client by status (Example: ACTIVE or INACTIVE)
	 * 
	 * @param clientStatus
	 * @return
	 */
	public List<Client> getClientByClientStatus(
			@Param("clientStatus") ClientStatus clientStatus);

	/**
	 * Get client by name and platform, managed by IMS
	 * 
	 * @param clientName
	 * @param platform
	 * @return
	 */
	public Client getClientByNameAndMerchant(
			@Param("clientName") String clientName,
			@Param("merchant") String merchant);

	/**
	 * This method will update clientKey in database
	 * 
	 * @param clientId
	 * @param newKey
	 */
	public void updateClientKey(@Param("clientId") String clientId,
			@Param("newKey") String newKey);

	/**
	 * This method is used to get all clients from IMS database
	 * 
	 * @return
	 */
	public List<Client> getAllClient();

	/**
	 * This method is used to get clients by platform
	 * 
	 * @return
	 */
	public List<Client> getClientByPlatform(
			@Param("clientPlatform") ClientPlatform clientPlatform);

	public List<Client> getClientByMultipleParam(
			@Param("clientPlatform") String clientPlatform,
			@Param("clientName") String clientName,
			@Param("merchant") String merchant,
			@Param("clientStatus") String clientStatus,
			@Param("clientType") String clientType);

}
