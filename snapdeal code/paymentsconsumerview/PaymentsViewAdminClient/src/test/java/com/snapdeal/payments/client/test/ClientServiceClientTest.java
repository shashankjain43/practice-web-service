package com.snapdeal.payments.client.test;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.payments.view.client.impl.ClientServiceClient;
import com.snapdeal.payments.view.commons.enums.ClientStatus;
import com.snapdeal.payments.view.commons.request.CreateClientRequest;
import com.snapdeal.payments.view.commons.request.GetAllClientsRequest;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;
import com.snapdeal.payments.view.commons.response.CreateClientResponse;
import com.snapdeal.payments.view.commons.response.GetAllClientsResponse;
import com.snapdeal.payments.view.commons.response.UpdateClientStatusResponse;
import com.snapdeal.payments.view.utils.ClientDetails;
	
@Slf4j
public class ClientServiceClientTest {

	ClientServiceClient client = new ClientServiceClient() ;

	@Before
	public void setup() throws Exception {
		//staging
		//ClientDetails.init("https://views-stg.paywithfreecharge.com", "443", "49c9d21c-ed41-4e73-88ed-ebfc1c102304", "Fd1qV0jLp0lCCS",12000);

		//production
		//ClientDetails.init("https://views.paywithfreecharge.com", "443", "20ef18a9-7529-48a1-99d9-e7523d621845", "CustomerService",12000);

		//dev
		//ClientDetails.init("http://localhost", "8080", "z$$s0s4$jj#l", "test_client_for_dev1",120000);
		ClientDetails.init("http://localhost", "8080", "dy%4%ieltfye", "test_client_for_dev2",120000);
	}

	
	@Test
	public void createClientTest() {
		CreateClientRequest request = new CreateClientRequest() ;
		request.setClientName("test_client_for_dev5");
		CreateClientResponse response =   client.createClient(request);
		System.out.println(response);
		System.out.println("client key is: "+response.getClientDetails().getClientKey());
		log.info(response.toString());
	}
	
	@Test
	public void updateClientStatusTest() {
		UpdateClientStatusRequest request = new UpdateClientStatusRequest() ;
		request.setClientName("test_client_for_dev1");
		request.setClientStatus(ClientStatus.INACTIVE);
		UpdateClientStatusResponse response =   client.updateClientStatus(request);
		System.out.println(response);
		log.info(response.toString());
	}
	
	@Test
	public void getAllClientsTest() {
		GetAllClientsRequest request = new GetAllClientsRequest() ;
		GetAllClientsResponse response =   client.getAllClients(request);
		System.out.println(response);
		log.info(response.toString());
	}
	

}