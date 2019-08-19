package com.snapdeal.ims.test;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IBlackWhiteListServiceClient;
import com.snapdeal.ims.client.impl.BlackWhiteListServiceClientImpl;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class BlackWhiteListServiceClientTest {

	IBlackWhiteListServiceClient blackWhiteListServiceClient = new BlackWhiteListServiceClientImpl();
	Random r = new Random();
	int number = r.nextInt(10000);

	@Before
	public void setup() throws Exception {
		ClientDetails.init("localhost", "8080", "snapdeal", "1", 12000);
	}

	private WhitelistEmailRequest getWhitelistEmailRequest() {

		WhitelistEmailRequest request = new WhitelistEmailRequest();
		request.setEmailId("abc@snapdeal.com");
		;
		return request;
	}

	@Test
	public void BlackWhiteListEmailTest() throws ServiceException, Exception {

		WhitelistEmailRequest request = getWhitelistEmailRequest();
		WhitelistEmailResponse response = blackWhiteListServiceClient
				.whitelistEmail(request);
		System.out.println(response);

	}

	@Test
	public void testBlacklistEntity() throws Exception {
		BlacklistEntityRequest request = new BlacklistEntityRequest();

		// request.setBlackListType(EntityType.EMAIL);
		request.setEntity("abc" + number + "@snapdeal.com");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		BlacklistEntityResponse response = blackWhiteListServiceClient
				.addBlacklistEntity(request);

		System.out.println(response);
	}

	@Test
	public void testBlacklistEntityRemove() throws Exception {
		BlacklistEntityRequest request = new BlacklistEntityRequest();

		request.setBlackListType(EntityType.EMAIL);
		request.setEntity("abc7878@snapdeal.com");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		BlacklistEntityResponse response = blackWhiteListServiceClient
				.removeBlacklistEntity(request);

		System.out.println(response);
	}
}
