package com.snapdeal.ims.test;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IJobSchedularServiceClient;
import com.snapdeal.ims.client.impl.JobSchedularServiceClientImpl;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class JobSchedularServiceClientTest {

IJobSchedularServiceClient jobSchedularServiceClientImpl = new JobSchedularServiceClientImpl();
	
	@Before
	public void setup() throws Exception {
		//ClientDetails.init("localhost", "8080", "snapdeal", "1", 12000);
		ClientDetails.init("localhost", "8080","q%!8x7tg6df!","AA6F0FD7BBA87D81",12000);
	}
	
	@Test
	public void upgradeUserTest() throws HttpTransportException, ServiceException{
		UpgradeUserByEmailRequest request = new UpgradeUserByEmailRequest();
		request.setEmailId("imstestmatthorn.8199266554@hushmail.ac");
		UpgradeUserByEmailResponse response = jobSchedularServiceClientImpl.upgradeUser(request);
		System.out.println(response);
	}

}
