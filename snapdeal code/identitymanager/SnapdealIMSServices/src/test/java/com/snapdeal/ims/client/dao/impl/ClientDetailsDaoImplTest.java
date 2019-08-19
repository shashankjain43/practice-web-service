package com.snapdeal.ims.client.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.IClientDetailsMapper;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;

@ContextConfiguration("classpath:spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientDetailsDaoImplTest {

	@Autowired
	IClientDetailsDao clientDetailsDao;

	@Autowired
	IClientDetailsMapper clientDetailsMApper;

	private static long clientKey = 1234;
	private static long clientId = 1;

	@Test
	public void simpleTest() {
		System.out.println("I am here");
	}

	public Client getClient(String userName, ClientType type,
			ClientPlatform platform, Merchant merchent, ClientStatus status) {
		Client client = new Client();
		client.setClientId(String.valueOf(clientId++));
		client.setClientKey(String.valueOf(++clientKey));
		client.setClientName(userName);
		client.setClientType(type);
		client.setClientPlatform(platform);
		client.setMerchant(merchent);
		client.setClientStatus(status);
		return client;
	}

	@Test
	public void getClientByIdTest() {

		String clientName = "Type_Client_Id";

		Client client1 = new Client();
		client1.setClientId(String.valueOf(++clientId));
		client1.setClientKey(String.valueOf(++clientKey));
		client1.setClientName(clientName);
		client1.setClientType(ClientType.USER_FACING);
		client1.setClientPlatform(ClientPlatform.APP);
		client1.setMerchant(Merchant.FREECHARGE);
		client1.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(client1);
		printClientDetails(client1);
		Client actualClient = clientDetailsDao.getClientById(String
				.valueOf(clientId));
		// printClientDetails(actualClient);
		Assert.assertTrue(isEquals(client1, actualClient));
	}

	@Test
	public void getClientByIdFailTest() {

		String falseClientId = "11111";

		Client returnedClient = clientDetailsDao.getClientById(falseClientId);
		Assert.assertEquals(null, returnedClient);
	}

	@Test
	public void getClientByNameTest() {

		String clientName = "AbhiName";

		Client client2 = new Client();
		client2.setClientId(String.valueOf(++clientId));
		client2.setClientKey(String.valueOf(++clientKey));
		client2.setClientName(clientName);
		client2.setClientType(ClientType.NON_USER_FACING);
		client2.setClientPlatform(ClientPlatform.WEB);
		client2.setMerchant(Merchant.SNAPDEAL);
		client2.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(client2);

		Client client3 = new Client();
		client3.setClientId(String.valueOf(++clientId));
		client3.setClientKey(String.valueOf(++clientKey));
		client3.setClientName(clientName);
		client3.setClientType(ClientType.NON_USER_FACING);
		client3.setClientPlatform(ClientPlatform.WEB);
		client3.setMerchant(Merchant.FREECHARGE);
		client3.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(client3);

		List<Client> cl = clientDetailsDao.getClientByName("AbhiName");
		Assert.assertEquals(cl.size(), 2);
		for (Client c : cl) {
			if (c.getMerchant().equals(Merchant.SNAPDEAL)) {
				Assert.assertTrue(isEquals(client2, c));
			} else if (c.getMerchant().equals(Merchant.FREECHARGE)) {
				Assert.assertTrue(isEquals(client3, c));
			}
		}
	}

	@Test
	public void getClientByNameFailTest() {
		String falseClientName = "NOT PRESENT";
		List<Client> cl = clientDetailsDao.getClientByName(falseClientName);
		Assert.assertEquals(0, cl.size());
	}

	@Test
	public void getClientByTypeTest() {
		String clientName = "Type_Client_Non_User_Facing";

		Client expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		List<Client> clients = clientDetailsDao
				.getClientByType(ClientType.NON_USER_FACING);

		Assert.assertNotEquals(clients.size(), 0);

		boolean isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);
	}

	@Test
	public void getClientByMerchantTest() {
		String clientName = "Type_Client_Merchant";

		Client expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		List<Client> clients = clientDetailsDao
				.getClientByMerchant(Merchant.SNAPDEAL);

		Assert.assertNotEquals(clients.size(), 0);

		boolean isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);
	}

	@Test
	public void getClientByClientStatusTest() {
		String clientName = "Type_Client_Status";

		Client expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		List<Client> clients = clientDetailsDao
				.getClientByClientStatus(ClientStatus.ACTIVE);

		Assert.assertNotEquals(clients.size(), 0);

		boolean isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);
	}

	@Test
	public void getClientByNameAndMerchantTest() {
		String clientName1 = "MerchantFreeCharge";
		String clientName2 = "MerchantSnapDeal";

		Client expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName1);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.FREECHARGE);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		Client actualClient = clientDetailsDao.getClientByNameAndMerchant(
				clientName1, Merchant.FREECHARGE);

		Assert.assertNotEquals(null, actualClient);
		Assert.assertTrue(isEquals(expectedClient, actualClient));

		expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName2);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		actualClient = clientDetailsDao.getClientByNameAndMerchant(clientName2,
				Merchant.SNAPDEAL);

		Assert.assertNotEquals(null, actualClient);
		Assert.assertTrue(isEquals(expectedClient, actualClient));
	}

	@Test
	public void getAllClientsTest() {

		Client client1 = new Client();
		client1.setClientId(String.valueOf(++clientId));
		client1.setClientKey(String.valueOf(++clientKey));
		client1.setClientName("Ashu");
		client1.setClientType(ClientType.USER_FACING);
		client1.setClientPlatform(ClientPlatform.APP);
		client1.setMerchant(Merchant.FREECHARGE);
		client1.setClientStatus(ClientStatus.ACTIVE);

		System.out.println("I am here");
		clientDetailsMApper.createClient(client1);

		Client client2 = new Client();
		client2.setClientId(String.valueOf(++clientId));
		client2.setClientKey(String.valueOf(++clientKey));
		client2.setClientName("Abhi");
		client2.setClientType(ClientType.NON_USER_FACING);
		client2.setClientPlatform(ClientPlatform.WEB);
		client2.setMerchant(Merchant.SNAPDEAL);
		client2.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(client2);

		Client client3 = new Client();
		client3.setClientId(String.valueOf(++clientId));
		client3.setClientKey(String.valueOf(++clientKey));
		client3.setClientName("Abhi");
		client3.setClientType(ClientType.NON_USER_FACING);
		client3.setClientPlatform(ClientPlatform.WEB);
		client3.setMerchant(Merchant.FREECHARGE);
		client3.setClientStatus(ClientStatus.INACTIVE);

		clientDetailsMApper.createClient(client3);

		List<Client> cl = clientDetailsDao.getAllClient();
		Assert.assertNotEquals(cl.size(), 0);
		if (cl != null && cl.size() > 0) {
			for (Client c : cl) {
				if (c.getClientName().equals("Ashu")
						&& c.getMerchant().equals(Merchant.FREECHARGE)) {

					Assert.assertTrue(isEquals(client1, c));
				} else if (c.getClientName().equals("Abhi")
						&& c.getMerchant().equals(Merchant.SNAPDEAL)) {
					Assert.assertTrue(isEquals(client2, c));
				} else if (c.getClientName().equals("Abhi")
						&& c.getMerchant().equals(Merchant.FREECHARGE)) {
					Assert.assertTrue(isEquals(client3, c));

				}
			}
		}
	}

	@Test
	public void getClientByPlatformTest() {
		String clientName = "Type_Client_Platform_App";

		Client expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.APP);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		List<Client> clients = clientDetailsDao
				.getClientByPlatform(ClientPlatform.APP);
		Assert.assertNotEquals(clients.size(), 0);

		boolean isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);

		clientName = "Type_Client_Platform_Wap";

		expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WAP);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		clients = clientDetailsDao.getClientByPlatform(ClientPlatform.WAP);
		Assert.assertNotEquals(clients.size(), 0);

		isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);

		clientName = "Type_Client_Platform_Web";

		expectedClient = new Client();
		expectedClient.setClientId(String.valueOf(++clientId));
		expectedClient.setClientKey(String.valueOf(++clientKey));
		expectedClient.setClientName(clientName);
		expectedClient.setClientType(ClientType.NON_USER_FACING);
		expectedClient.setClientPlatform(ClientPlatform.WEB);
		expectedClient.setMerchant(Merchant.SNAPDEAL);
		expectedClient.setClientStatus(ClientStatus.ACTIVE);

		clientDetailsMApper.createClient(expectedClient);

		clients = clientDetailsDao.getClientByPlatform(ClientPlatform.WEB);
		Assert.assertNotEquals(clients.size(), 0);

		isFound = false;
		for (Client presentClient : clients) {
			if (isEquals(expectedClient, presentClient)) {
				isFound = true;
			}
		}
		Assert.assertTrue(isFound);

	}

	public boolean isEquals(Client expected, Client actual) {
		boolean isEqual = false;
		if (expected.getClientId().equals(actual.getClientId())
				&& expected.getClientKey().equals(actual.getClientKey())
				&& expected.getClientName().equals(actual.getClientName())
				&& expected.getClientPlatform().equals(
						actual.getClientPlatform())
				&& expected.getClientStatus().equals(actual.getClientStatus())
				&& expected.getMerchant().equals(actual.getMerchant())
				&& expected.getClientType().equals(actual.getClientType())) {
			isEqual = true;
		}
		return isEqual;
	}

	public void printClientDetails(Client client) {
		System.out.println(client.getClientId() + " " + client.getClientKey()
				+ " " + client.getClientName() + " "
				+ client.getClientPlatform() + " " + client.getClientStatus()
				+ " " + client.getClientType() + " " + client.getCreatedTime()
				+ " " + client.getMerchant() + " " + client.getUpdatedTime());
	}
}
