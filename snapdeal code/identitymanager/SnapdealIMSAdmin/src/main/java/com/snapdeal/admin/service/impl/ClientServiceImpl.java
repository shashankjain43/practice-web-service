package com.snapdeal.admin.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.admin.commons.ClientSorting;
import com.snapdeal.admin.commons.RandomStringGenerator;
import com.snapdeal.admin.constants.CommonConstants;
import com.snapdeal.admin.constants.RestURIConstants;
import com.snapdeal.admin.dao.IClientDetailsDao;
import com.snapdeal.admin.dao.entity.Client;
import com.snapdeal.admin.dao.entity.Client.ClientPlatform;
import com.snapdeal.admin.dao.entity.Client.ClientStatus;
import com.snapdeal.admin.dao.entity.Client.ClientType;
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
import com.snapdeal.admin.service.IClientService;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
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
	public CreateClientResponse createClient(CreateClientRequest request)
			throws ValidationException {

		// Check if client already exists, if yes throw Validation error
		// with message 'Client already exists'
		validate(request);

		final Client existingClient = clientDetailsDao
				.getClientByNameAndMerchant(request.getClientName(), request
						.getMerchant().toString());

		if (existingClient != null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_ALREADY_EXISTS.errCode(),
					IMSServiceExceptionCodes.CLIENT_ALREADY_EXISTS.errMsg());
		}

		final Client clientDetailsEntity = new Client();

		// Generating primary key and secureKey
		final String key = RandomStringGenerator.getRandomKeyUsingUUID();
		final String clientKey = RandomStringGenerator
				.nextString(RestURIConstants.DEFAULT_SECURE_KEY_LEN);

		clientDetailsEntity.setClientId(key);
		clientDetailsEntity.setClientKey(clientKey);
		clientDetailsEntity.setClientName(request.getClientName());
		clientDetailsEntity.setClientType(validateAndGetTypeAsEnum(request
				.getClientType()));
		clientDetailsEntity.setMerchant(request.getMerchant());
		clientDetailsEntity
				.setClientPlatform(validateAndGetPlatformAsEnum(request
						.getClientPlatform()));
		clientDetailsEntity.setClientStatus(ClientStatus.ACTIVE);
		clientDetailsEntity.setImsInternalAlias(request.getImsInternalAlias());

		final long currentTimeInMillis = Calendar.getInstance()
				.getTimeInMillis();
		clientDetailsEntity.setCreatedTime(new Timestamp(currentTimeInMillis));
		clientDetailsEntity.setUpdatedTime(new Timestamp(currentTimeInMillis));

		clientDetailsDao.createClient(clientDetailsEntity);

		final CreateClientResponse response = new CreateClientResponse();
		response.setClientDetails(buildResponse(clientDetailsEntity));
		response.setResult(CommonConstants.OK);
		return response;
	}

	private void validate(CreateClientRequest request)
			throws ValidationException {
		if (isEmpty(request.getClientName())) {
			throw new ValidationException(
					IMSValidationExceptionCodes.INVALID_CLIENT_NAME.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_NAME.errMsg());
		} else if (isEmpty(request.getMerchant().toString())) {
			throw new ValidationException(
					IMSValidationExceptionCodes.INVALID_CLIENT_MERCHANT
							.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_MERCHANT
							.errMsg());
		}
		validateAndGetTypeAsEnum(request.getClientType());
	}

	private boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public DeactivateClientResponse deactivateClient(
			DeactivateClientRequest request) throws ValidationException {

		// Check if client exists or not, if not throw ValidationError:
		// no client exists
		final Client existingClient = clientDetailsDao.getClientById(request
				.getClientId());

		if (existingClient == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}

		clientDetailsDao.updateClientStatus(request.getClientId(),
				ClientStatus.INACTIVE.name());
		final DeactivateClientResponse response = new DeactivateClientResponse();

		existingClient.setClientStatus(ClientStatus.INACTIVE);
		existingClient.setUpdatedTime(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));

		response.setClientDetails(buildResponse(existingClient));
		response.setResult(CommonConstants.OK);
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public ActivateClientResponse activateClient(ActivateClientRequest request)
			throws ValidationException {

		// Check if client exists or not, if not throw ValidationError:
		// no client exists
		final Client existingClient = clientDetailsDao.getClientById(request
				.getClientId());
		if (existingClient == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}

		clientDetailsDao.updateClientStatus(request.getClientId(),
				ClientStatus.ACTIVE.name());

		final ActivateClientResponse response = new ActivateClientResponse();

		existingClient.setClientStatus(ClientStatus.ACTIVE);
		existingClient.setUpdatedTime(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));

		response.setClientDetails(buildResponse(existingClient));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public RegenerateClientKeyResponse regenerateClientKey(
			RegenerateClientKeyRequest request) throws ValidationException {

		// Check if client exists or not, if not throw ValidationError:
		// no client exists
		final Client existingClient = clientDetailsDao.getClientById(request
				.getClientId());
		if (existingClient == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}

		// Generating new key and updating it in IMS db
		final String newKey = RandomStringGenerator
				.nextString(RestURIConstants.DEFAULT_SECURE_KEY_LEN);
		clientDetailsDao.updateClientKey(request.getClientId(), newKey);

		final RegenerateClientKeyResponse response = new RegenerateClientKeyResponse();

		existingClient.setClientKey(newKey);
		existingClient.setUpdatedTime(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));

		response.setClientDetails(buildResponse(existingClient));
		return response;
	}

	// Client Name, Merchant, Client Type, Client Status, Client Platform
	@SuppressWarnings("unchecked")
	@Override
	@Timed
	@Marked
	@Logged
	public GetAllClientResponse getAllClient(HttpServletRequest request) {
		final String clientType = request.getParameter("clientType");
		final String clientName = request.getParameter("clientName");
		final String merchant = request.getParameter("merchant");
		final String clientStatus = request.getParameter("clientStatus");
		final String clientPlatform = request.getParameter("clientPlatform");
		List<Client> clientDetailsList = null;
		if (StringUtils.isBlank(clientType) && StringUtils.isBlank(clientName)
				&& StringUtils.isBlank(merchant)
				&& StringUtils.isBlank(clientStatus)
				&& StringUtils.isBlank(clientPlatform)) {
			clientDetailsList = clientDetailsDao.getAllClient();

			Set<String> type = (Set<String>) request.getSession().getAttribute(
					"clientTypes");
			if (type == null)
				type = new TreeSet<String>();
			Set<String> name = (Set<String>) request.getSession().getAttribute(
					"clientNames");
			if (name == null)
				name = new TreeSet<String>();
			Set<String> merch = (Set<String>) request.getSession()
					.getAttribute("merchants");
			if (merch == null)
				merch = new TreeSet<String>();
			Set<String> status = (Set<String>) request.getSession()
					.getAttribute("clientStatuses");
			if (status == null)
				status = new TreeSet<String>();
			Set<String> platform = (Set<String>) request.getSession()
					.getAttribute("clientPlatforms");
			if (platform == null)
				platform = new TreeSet<String>();
			Date currentTime = new Date();
			Date updatedTime = (Date) request.getSession().getAttribute(
					"clientUpdateTime");
			log.info("current Time : " + currentTime + " updatedTime "
					+ updatedTime);
			if (updatedTime == null
					|| currentTime.getTime() - updatedTime.getTime() > 900000) {
				log.info("updating session attributes");
				if (clientDetailsList != null && clientDetailsList.size() > 0) {
					request.getSession().setAttribute("clientUpdateTime",
							new Date());
				}
				for (Client clientDetail : clientDetailsList) {
					if (clientDetail.getClientName() != null) {
						name.add(clientDetail.getClientName());
						request.getSession().setAttribute("clientNames", name);
					}
					if (clientDetail.getClientType() != null) {
						type.add(clientDetail.getClientType().toString());
						request.getSession().setAttribute("clientTypes", type);
					}
					if (clientDetail.getMerchant() != null) {
						merch.add(clientDetail.getMerchant().toString());
						request.getSession().setAttribute("merchants", merch);
					}
					if (clientDetail.getMerchant() != null) {
						status.add(clientDetail.getClientStatus().toString());
						request.getSession().setAttribute("clientStatuses",
								status);
					}
					if (clientDetail.getMerchant() != null) {
						platform.add(clientDetail.getClientPlatform()
								.toString());
						request.getSession().setAttribute("clientPlatforms",
								platform);
					}
				}
			}

		} else {
			clientDetailsList = clientDetailsDao.getClientByMultipleParam(
					clientPlatform, clientName, merchant, clientStatus,
					clientType);
		}

		final GetAllClientResponse response = new GetAllClientResponse();
		List<ClientDetails> clientDetails = buildResponse(clientDetailsList);
		response.setClientList(clientDetails);

		int startPageIndex = Integer.parseInt(request
				.getParameter("jtStartIndex"));
		int numRecordsPerPage = Integer.parseInt(request
				.getParameter("jtPageSize"));
		String sortingParam = request.getParameter("jtSorting");

		int size = clientDetails.size();
		if (size >= (startPageIndex + numRecordsPerPage)) {
			response.setClientList(clientDetails.subList(startPageIndex,
					startPageIndex + numRecordsPerPage));
		} else if (size >= startPageIndex
				&& size < (startPageIndex + numRecordsPerPage)) {
			response.setClientList(clientDetails.subList(startPageIndex, size));
		}

		Comparator<ClientDetails> comparator = ClientSorting.getComparator(sortingParam);

		if (comparator == null) {
			Collections.sort(clientDetails, ClientSorting.getComparator(ClientSorting.CLIENT_NAME_ASC));

		} else {
			Collections.sort(clientDetails, comparator);
		}

		response.setResult(CommonConstants.OK);
		response.setTotalResult(clientDetailsList.size());
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientResponse getClientById(String clientId)
			throws ValidationException {

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
	public GetClientByNameResponse getClientByName(String clientName)
			throws ValidationException {

		final List<Client> clientDetailsList = clientDetailsDao
				.getClientByName(clientName);

		final GetClientByNameResponse response = new GetClientByNameResponse();
		response.setClientList(buildResponse(clientDetailsList));
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetClientByTypeResponse getClientByType(String clientType)
			throws ValidationException {

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

		final List<Client> clientDetailsList = clientDetailsDao
				.getClientByMerchant(merchant);
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
		for (ListIterator<Client> it = clientDetailsList.listIterator(); it
				.hasNext();) {
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
		clientDetails
				.setClientPlatform(clientDetailsEntity.getClientPlatform());
		clientDetails.setClientStatus(clientDetailsEntity.getClientStatus());

		clientDetails
				.setCreatedTime(convertTimeStampToStringInDateFormat(clientDetailsEntity
						.getCreatedTime()));
		clientDetails
				.setUpdatedTime(convertTimeStampToStringInDateFormat(clientDetailsEntity
						.getUpdatedTime()));
		clientDetails.setImsInternalAlias(clientDetailsEntity
				.getImsInternalAlias());

		return clientDetails;
	}

	private String convertTimeStampToStringInDateFormat(Timestamp stamp) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(stamp.getTime());
		return sdf.format(date);

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
			throw new ValidationException(
					IMSValidationExceptionCodes.INVALID_CLIENT_STATUS.errCode(),
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
	private ClientType validateAndGetTypeAsEnum(String clientTypeStr)
			throws ValidationException {
		try {
			ClientType clientType = ClientType.valueOf(clientTypeStr);
			return clientType;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(
					IMSValidationExceptionCodes.INVALID_CLIENT_TYPE.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_TYPE.errMsg());
		}
	}

	private ClientPlatform validateAndGetPlatformAsEnum(String clientPlatformStr) {
		try {
			ClientPlatform clientPlatform = ClientPlatform
					.valueOf(clientPlatformStr);
			return clientPlatform;
		} catch (IllegalArgumentException e) {
			throw new ValidationException(
					IMSValidationExceptionCodes.INVALID_CLIENT_PLATFORM
							.errCode(),
					IMSValidationExceptionCodes.INVALID_CLIENT_PLATFORM
							.errMsg());
		}
	}

	@Override
	public String getClientIDByMerchantAndClientName(String clientName,
			String merchant) {
		Client client = clientDetailsDao.getClientByNameAndMerchant(clientName,
				merchant);
		return client.getClientId();
	}

}
