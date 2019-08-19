

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.snapdeal.admin.commons.RandomStringGenerator;
import com.snapdeal.admin.constants.RestURIConstants;
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
import com.snapdeal.ims.enums.Merchant;

public class ClientManagementControllerTestData {

	private CreateClientResponse createClientResponse;
	private CreateClientRequest createClientRequest;
	private DeactivateClientResponse deactivateClientResponse;
	private DeactivateClientRequest deactivateClientRequest;
	private ActivateClientResponse activateClientResponse;
	private ActivateClientRequest activateClientRequest;
	private RegenerateClientKeyResponse regenerateClientKeyResponse;
	private RegenerateClientKeyRequest regenerateClientKeyRequest;
	private ClientDetails clientDetails;
	private GetClientResponse getClientResponse;
	private GetAllClientResponse getAllClientResponse;
	private GetClientByNameResponse getClientByNameResponse;
	private GetClientByTypeResponse getClientByTypeResponse;
	private GetClientByMerchantResponse getClientByClientResponse;
	private GetClientByStatusResponse getClientByStatusResponse;
	private Client client;

	public ClientManagementControllerTestData(){
		init();
	}

	private void init() {
		//Generating primary key and secureKey
		final Long hashKey  = UUID.randomUUID().getLeastSignificantBits();
		final String key = Long.toHexString(hashKey).toUpperCase();

		final String clientKey = RandomStringGenerator.nextString(
				RestURIConstants.DEFAULT_SECURE_KEY_LEN);
		clientDetails = new ClientDetails();
		clientDetails.setClientId(key);
		clientDetails.setClientKey(clientKey);
		clientDetails.setClientName("OMS");
		clientDetails.setClientStatus(ClientStatus.ACTIVE);
		clientDetails.setClientType(ClientType.NON_USER_FACING);
		clientDetails.setClientPlatform(ClientPlatform.WEB);
		//clientDetails.setCreatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		clientDetails.setMerchant(Merchant.SNAPDEAL);
		//clientDetails.setUpdatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		List<ClientDetails> clientList = new ArrayList<ClientDetails>();
		clientList.add(clientDetails);


		createClientRequest = new CreateClientRequest();
		createClientRequest.setClientName(clientDetails.getClientName());
		createClientRequest.setClientType(clientDetails.getClientType().name());
		//createClientRequest.setMerchant(clientDetails.getMerchant().name());
		createClientRequest.setClientPlatform(clientDetails.getClientPlatform().name());

		createClientResponse = new CreateClientResponse();
		createClientResponse.setClientDetails(clientDetails);



		deactivateClientRequest = new DeactivateClientRequest();
		deactivateClientRequest.setClientId(clientDetails.getClientId());

		deactivateClientResponse = new DeactivateClientResponse();
		deactivateClientResponse.setClientDetails(clientDetails);



		activateClientRequest = new ActivateClientRequest();
		activateClientRequest.setClientId(clientDetails.getClientId());

		activateClientResponse = new ActivateClientResponse();
		activateClientResponse.setClientDetails(clientDetails);



		regenerateClientKeyRequest = new RegenerateClientKeyRequest();
		regenerateClientKeyRequest.setClientId(clientDetails.getClientId());

		regenerateClientKeyResponse = new RegenerateClientKeyResponse();
		regenerateClientKeyResponse.setClientDetails(clientDetails);


		getClientResponse = new GetClientResponse();
		getClientResponse.setClientDetails(clientDetails);

		getAllClientResponse = new GetAllClientResponse();
		getAllClientResponse.setClientList(clientList);

		getClientByNameResponse = new GetClientByNameResponse();
		getClientByNameResponse.setClientList(clientList);


		getClientByTypeResponse = new GetClientByTypeResponse();
		getClientByTypeResponse.setClientList(clientList);


		getClientByClientResponse = new GetClientByMerchantResponse();
		getClientByClientResponse.setClientList(clientList);


		getClientByStatusResponse = new GetClientByStatusResponse();
		getClientByStatusResponse.setClientList(clientList);

		client = new Client();
		client.setClientId(clientDetails.getClientId());
		client.setClientKey(clientDetails.getClientKey());
		client.setClientName(clientDetails.getClientName());
		client.setClientType(clientDetails.getClientType());
		client.setMerchant(clientDetails.getMerchant());
		client.setClientStatus(clientDetails.getClientStatus());
	//	client.setCreatedTime(clientDetails.getCreatedTime());
		//client.setUpdatedTime(clientDetails.getUpdatedTime());
	}

	public CreateClientResponse getCreateClientResponse() {
		return createClientResponse;
	}

	public CreateClientRequest getCreateClientRequest() {
		return createClientRequest;
	}

	public DeactivateClientResponse getDeactivateClientResponse() {
		return deactivateClientResponse;
	}

	public DeactivateClientRequest getDeactivateClientRequest() {
		return deactivateClientRequest;
	}

	public ActivateClientResponse getActivateClientResponse() {
		return activateClientResponse;
	}

	public ActivateClientRequest getActivateClientRequest() {
		return activateClientRequest;
	}

	public RegenerateClientKeyResponse getRegenerateClientKeyResponse() {
		return regenerateClientKeyResponse;
	}

	public RegenerateClientKeyRequest getRegenerateClientKeyRequest() {
		return regenerateClientKeyRequest;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public GetClientResponse getGetClientResponse() {
		return getClientResponse;
	}

	public GetAllClientResponse getGetAllClientResponse() {
		return getAllClientResponse;
	}

	public GetClientByNameResponse getGetClientByNameResponse() {
		return getClientByNameResponse;
	}

	public GetClientByTypeResponse getGetClientByTypeResponse() {
		return getClientByTypeResponse;
	}

	public GetClientByMerchantResponse getGetClientByClientResponse() {
		return getClientByClientResponse;
	}

	public GetClientByStatusResponse getGetClientByStatusResponse() {
		return getClientByStatusResponse;
	}

	public Client getClient() {
		return client;
	}
}
