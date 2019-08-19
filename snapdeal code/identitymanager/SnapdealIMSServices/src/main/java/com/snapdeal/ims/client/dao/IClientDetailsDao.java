package com.snapdeal.ims.client.dao;

import java.util.List;

import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;


/**
 * @author Subhash
 *
 */
public interface IClientDetailsDao {
	/**
	 * Get client by client Id, managed by IMS
	 * @param clientId
	 * @return
	 */
	public Client getClientById(String clientId);

	/**
	 * get client by client name OMS/FC/Shipping etc.
	 * @param clientName
	 * @return
	 */
	public List<Client> getClientByName(String clientName);

	/**
	 * Get client by clientType (External or Internal facing)
	 * @param clientType
	 * @return
	 */
	public List<Client> getClientByType(ClientType clientType);

	/**
	 * Get client by clientBase (Example: Snapdeal)
	 * @param client
	 * @return
	 */
	public List<Client> getClientByMerchant(Merchant merchant);

	/**
	 * Get client by status (Example: ACTIVE or INACTIVE)
	 * @param clientStatus
	 * @return
	 */
	public List<Client> getClientByClientStatus(ClientStatus clientStatus);

	/**
	 * Get client by name and platform, managed by IMS
	 * @param clientName
	 * @param platform
	 * @return
	 */
	public Client getClientByNameAndMerchant(String clientName, Merchant merchant);


	/**
	 * This method is used to get all clients from IMS database
	 * @return
	 */
	public List<Client> getAllClient();

	/**
	 * This method is used to get clients by platform
	 * @return
	 */
	public List<Client> getClientByPlatform(ClientPlatform clientPlatform);
}
