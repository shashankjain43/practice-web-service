package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.snapdeal.identity.ims.test.data.ClientServiceTestData;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.service.impl.ClientServiceImpl;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.response.GetClientByMerchantResponse;
import com.snapdeal.ims.response.GetClientByNameResponse;
import com.snapdeal.ims.response.GetClientByStatusResponse;
import com.snapdeal.ims.response.GetClientByTypeResponse;
import com.snapdeal.ims.response.GetClientResponse;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	@Mock
	private IClientDetailsDao  clientDetailsDao;

	private ClientServiceTestData clientServiceTestData;

	protected void initConfig() {

		Map<String, String> globalMap = new HashMap<String, String>();
		globalMap.put("ims.client.key.encryption.enabled","false");
		final ConfigCache configCache = new ConfigCache();
		configCache.put("global", globalMap);	
		CacheManager.getInstance().setCache(configCache);
		}
	@Before
	public void setUp(){
		initConfig();

		MockitoAnnotations.initMocks(this);
		clientServiceTestData = new ClientServiceTestData();
	}

/*	@Test
	public void testCreateClientSuccess() {

		Mockito.doNothing().when(clientDetailsDao).createClient(Mockito.any(Client.class));

		Mockito.when(clientDetailsDao.getClientByNameAndMerchant(
				clientManagementControllerTestData.getCreateClientRequest().getClientName(),
				Merchant.valueOf(clientManagementControllerTestData.getCreateClientRequest().getMerchant())))
				.thenReturn(null);

		CreateClientResponse response = 
				clientServiceImpl.createClient(
						clientManagementControllerTestData.getCreateClientRequest());
	}

	@Test(expected = Exception.class)
	public void testCreateClientClientExistsFailure() throws Exception{

		CreateClientRequest request = clientManagementControllerTestData.getCreateClientRequest();

		Mockito.when(clientDetailsDao.getClientByNameAndMerchant(request.getClientName(),
				Merchant.valueOf(request.getMerchant()))).thenReturn(clientManagementControllerTestData.getClient());

		CreateClientResponse response = clientServiceImpl.createClient(request);
	}

	@Test(expected = Exception.class)
	public void testCreateClientFailureWithInvalidType() throws Exception {

		CreateClientRequest request = clientManagementControllerTestData.getCreateClientRequest();

		String clientType = request.getClientType();
		request.setClientType("INTERNAL1");

		Mockito.doNothing().when(clientDetailsDao).createClient(Mockito.any(Client.class));
		CreateClientResponse response = clientServiceImpl.createClient(request);

		request.setClientType(clientType);
	}

	@Test(expected = Exception.class)
	public void testCreateClientFailureWithInvalidName() throws Exception {

		CreateClientRequest request = clientManagementControllerTestData.getCreateClientRequest();

		String clientName = request.getClientName();
		request.setClientName(null);

		Mockito.doNothing().when(clientDetailsDao).createClient(Mockito.any(Client.class));
		CreateClientResponse response = clientServiceImpl.createClient(request);

		request.setClientName(clientName);
	}

	@Test(expected = Exception.class)
	public void testCreateClientFailureWithInvalidMerchant() throws Exception {

		CreateClientRequest request = clientManagementControllerTestData.getCreateClientRequest();

		String merchant = request.getMerchant();
		request.setMerchant(null);

		Mockito.doNothing().when(clientDetailsDao).createClient(Mockito.any(Client.class));
		CreateClientResponse response = clientServiceImpl.createClient(request);

		request.setMerchant(merchant);
	}
*/
	@Test
	public void testGetAllClientSuccess() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getAllClient()).thenReturn(clientList);

		List<Client> list= clientServiceImpl.getAllClient();
		
	}

	@Test
	public void testGetClientByIdSuccess() throws Exception {

		Client client = clientServiceTestData.getClient();

		Mockito.when(clientDetailsDao.getClientById(client.getClientId())).thenReturn(client);

		GetClientResponse response = clientServiceImpl.getClientById(client.getClientId());
	}

	@Test
	public void testGetClientByNameSuccess() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getClientByName(
				clientServiceTestData.getClient().getClientName()))
				.thenReturn(clientList);

		GetClientByNameResponse response = 
				clientServiceImpl.getClientByName(
						clientServiceTestData.getClient().getClientName());
	}

	@Test
	public void testGetClientByTypeSuccess() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getClientByType(
				clientServiceTestData.getClient().getClientType()))
				.thenReturn(clientList);

		GetClientByTypeResponse response = 
				clientServiceImpl.getClientByType(
						clientServiceTestData.getClient().getClientType().name());
	}

	@Test
	public void testGetClientByStatusSuccess() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getClientByClientStatus(
				clientServiceTestData.getClientDetails().getClientStatus()))
				.thenReturn(clientList);

		GetClientByStatusResponse response = 
				clientServiceImpl.getClientByClientStatus(
						clientServiceTestData
						.getClientDetails()
						.getClientStatus()
						.name());
	}

	@Test(expected=ValidationException.class)
	public void testGetClientByStatusFailure() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getClientByClientStatus(
				clientServiceTestData.getClientDetails().getClientStatus()))
				.thenReturn(clientList);

		GetClientByStatusResponse response = 
				clientServiceImpl.getClientByClientStatus("ACTIVE1");
	}

	@Test
	public void testGetClientByMerchantSuccess() throws Exception {

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(clientServiceTestData.getClient());

		Mockito.when(clientDetailsDao.getClientByMerchant(clientServiceTestData.getClient().getMerchant())).thenReturn(clientList);

		GetClientByMerchantResponse response = 
				clientServiceImpl.getClientByMerchant(clientServiceTestData.getClient().getMerchant().name());
	}
/*
	@Test
	public void testDeactivateClientSuccess() throws Exception {

		DeactivateClientRequest request = clientManagementControllerTestData.getDeactivateClientRequest();

		Mockito.when(clientDetailsDao.getClientById(
				request.getClientId())).thenReturn(clientManagementControllerTestData.getClient());

		Mockito.doNothing().when(clientDetailsDao)
		.updateClientStatus(request.getClientId(), (ClientStatus.INACTIVE.name()));

		DeactivateClientResponse response = clientServiceImpl.deactivateClient(request);
	}

	@Test(expected=ValidationException.class)
	public void testDeactivateClientFailure() throws Exception {

		DeactivateClientRequest request = 
				clientManagementControllerTestData.getDeactivateClientRequest();

		Mockito.when(clientDetailsDao.getClientById(request.getClientId())).thenReturn(null);

		DeactivateClientResponse response = clientServiceImpl.deactivateClient(request);
	}


	@Test
	public void testActivateClientSuccess() throws Exception {

		ActivateClientRequest request = 
				clientManagementControllerTestData.getActivateClientRequest();

		Mockito.when(clientDetailsDao.getClientById(
				request.getClientId())).thenReturn(clientManagementControllerTestData.getClient());

		Mockito.doNothing().when(clientDetailsDao)
		.updateClientStatus(request.getClientId(), (ClientStatus.ACTIVE.name()));

		ActivateClientResponse response = clientServiceImpl.activateClient(request);
	}

	@Test(expected=ValidationException.class)
	public void testActivateClientFailure() throws Exception {

		ActivateClientRequest request = 
				clientManagementControllerTestData.getActivateClientRequest();

		Mockito.when(clientDetailsDao.getClientById(request.getClientId())).thenReturn(null);

		ActivateClientResponse response = clientServiceImpl.activateClient(request);
	}


	@Test
	public void testRegenerateClientKeySuccess() throws Exception {

		RegenerateClientKeyRequest request =
				clientManagementControllerTestData.getRegenerateClientKeyRequest();

		Mockito.when(clientDetailsDao.getClientById(
				request.getClientId())).thenReturn(clientManagementControllerTestData.getClient());

		Mockito.doNothing().when(clientDetailsDao)
		.updateClientKey(Mockito.any(String.class), Mockito.any(String.class));

		RegenerateClientKeyResponse response = clientServiceImpl.regenerateClientKey(request);
	}

	@Test(expected=ValidationException.class)
	public void testRegenerateClientKeyFailure() throws Exception {

		RegenerateClientKeyRequest request = 
				clientManagementControllerTestData.getRegenerateClientKeyRequest();

		Mockito.when(clientDetailsDao.getClientById(request.getClientId())).thenReturn(null);

		RegenerateClientKeyResponse response = clientServiceImpl.regenerateClientKey(request);
	}
*/}
