package com.snapdeal.ims.client.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.client.service.IClientService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.response.ClientDetails;
import com.snapdeal.ims.response.GetAllClientResponse;
import com.snapdeal.ims.response.GetClientByMerchantResponse;
import com.snapdeal.ims.response.GetClientByNameResponse;
import com.snapdeal.ims.response.GetClientByStatusResponse;
import com.snapdeal.ims.response.GetClientByTypeResponse;
import com.snapdeal.ims.response.GetClientResponse;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional("transactionManager")
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDetailsDao clientDetailsDao;

	@Override
	@Timed
	@Marked
	@Logged
	public  List<Client> getAllClient() throws ValidationException {
		final List<Client> clientDetailsList = clientDetailsDao.getAllClient();
		if (clientDetailsList == null || clientDetailsList.size()==0) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}
		//final List<Client> clientList = new ArrayList<Client>();
		for (ListIterator<Client> it = clientDetailsList.listIterator(); it.hasNext();) 
		{
			Client clientDetails = it.next();
				if (clientDetails.getClientKey().length()>
				Long.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.DEFAULT_SECURE_KEY_LEN))) {
					try {
						clientDetails.setClientKey(CipherServiceUtil.decrypt(clientDetails.getClientKey()));
					} catch (Exception e) {
						log.error("Error occured while decrypting the client key.");
						clientDetails.setClientKey(clientDetails.getClientKey());
						/*throw new IMSServiceException(IMSServiceExceptionCodes.ERROR_IN_DECRYPTING_KEY.errCode(),
								IMSServiceExceptionCodes.ERROR_IN_DECRYPTING_KEY.errMsg());*/
					}
				}
		}
			return clientDetailsList;	
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientResponse getClientById(String clientId) throws ValidationException {

		final Client client = clientDetailsDao.getClientById(clientId);
		final GetClientResponse response = new GetClientResponse();

		if (client != null) {
			response.setClientDetails(buildResponse(client));
		}
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientByNameResponse getClientByName(String clientName) throws ValidationException {

		final List<Client> clientDetailsList = clientDetailsDao.getClientByName(clientName);

		final GetClientByNameResponse response = new GetClientByNameResponse();
		response.setClientList(buildResponse(clientDetailsList));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientByTypeResponse getClientByType(String clientType) throws ValidationException {

		final List<Client> clientDetailsList = clientDetailsDao
				.getClientByType(validateAndGetTypeAsEnum(clientType));

		final GetClientByTypeResponse response = new GetClientByTypeResponse();
		response.setClientList(buildResponse(clientDetailsList));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientByMerchantResponse getClientByMerchant(String merchant)
			throws ValidationException {

		final List<Client> clientDetailsList = clientDetailsDao.getClientByMerchant(validateAndGetMerchantAsEnum(merchant));

		final GetClientByMerchantResponse response = new GetClientByMerchantResponse();
		response.setClientList(buildResponse(clientDetailsList));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientByStatusResponse getClientByClientStatus(String clientStatus)
			throws ValidationException {

		final List<Client> clientDetailsList = clientDetailsDao
				.getClientByClientStatus(validateAndGetStatusAsEnum(clientStatus));

		final GetClientByStatusResponse response = new GetClientByStatusResponse();
		response.setClientList(buildResponse(clientDetailsList));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public List<ClientDetails> getClientByPlatform(String clientPlatform)
			throws ValidationException {
		
		final List<Client> clientDetailsList = clientDetailsDao
				.getClientByPlatform(validateAndGetPlatformAsEnum(clientPlatform));


		return buildResponse(clientDetailsList);
	}

	
	
	
	/**
     * This function will convert ClientDetailsEntitiy list to ClientDetails
     *
     * @param clientDetailsList
     * @return
     */
    private List<ClientDetails> buildResponse(List<Client> clientDetailsList) {

        final List<ClientDetails> clientList = new ArrayList<ClientDetails>();
        for (ListIterator<Client> it = clientDetailsList.listIterator(); it.hasNext();) {
            Client clientDetailsEntity = it.next();
            clientList.add(buildResponse(clientDetailsEntity));
        }
        return clientList;
    }

    private ClientDetails buildResponse(Client clientDetailsEntity) {

        final ClientDetails clientDetails = new ClientDetails();

        clientDetails.setClientId(clientDetailsEntity.getClientId());
        clientDetails.setClientKey(clientDetailsEntity.getClientKey());
        clientDetails.setMerchant(clientDetailsEntity.getMerchant());
        clientDetails.setClientName(clientDetailsEntity.getClientName());
        clientDetails.setClientType(clientDetailsEntity.getClientType());
        clientDetails.setClientPlatform(clientDetailsEntity.getClientPlatform());
        clientDetails.setClientStatus(clientDetailsEntity.getClientStatus());
        clientDetails.setCreatedTime(clientDetailsEntity.getCreatedTime());
        clientDetails.setUpdatedTime(clientDetailsEntity.getUpdatedTime());
        clientDetails.setImsIntenalAlias(clientDetailsEntity.getImsInternalAlias());
        return clientDetails;
    }

	/**
	 * This is helper function which will validate the client given status.
	 * 
	 * @param clientStatusStr
	 * @return
	 */
	private ClientStatus validateAndGetStatusAsEnum(String clientStatusStr) {
		try {
			ClientStatus clientStatus = ClientStatus.valueOf(clientStatusStr);
			return clientStatus;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(IMSValidationExceptionCodes.INVALID_CLIENT_STATUS.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_STATUS.errMsg());
		}
	}

	/**
	 * This is helper function which will validate the client given type.
	 * 
	 * @param clientTypeStr
	 * @return
	 * @throws ValidationException
	 */
	private ClientType validateAndGetTypeAsEnum(String clientTypeStr) throws ValidationException {
		try {
			ClientType clientType = ClientType.valueOf(clientTypeStr);
			return clientType;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(IMSValidationExceptionCodes.INVALID_CLIENT_TYPE.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_TYPE.errMsg());
		}
	}
	
	private ClientPlatform validateAndGetPlatformAsEnum(String clientPlatformStr) {
		try {
			ClientPlatform clientPlatform = ClientPlatform.valueOf(clientPlatformStr);
			return clientPlatform;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(IMSValidationExceptionCodes.INVALID_CLIENT_PLATFORM.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_PLATFORM.errMsg());
		}
	}
	
	private Merchant validateAndGetMerchantAsEnum(String merchantStr) {
		try {
			Merchant merchant = Merchant.valueOf(merchantStr);
			return merchant;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(IMSValidationExceptionCodes.INVALID_CLIENT_MERCHANT.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_MERCHANT.errMsg());
		}
	}
}

