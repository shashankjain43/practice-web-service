package com.snapdeal.ims.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.ims.snsClient.IMSSNSClient;
/**
 * Test case to  publish message into sns.
 * @author abhishek.jajoria
 *
 */
public class IMSSNSClientTest {
	
	private IMSSNSClient imsSNSClient  = new IMSSNSClient();

	/*@Test
	public void testPublishMessage() {
		String dummyMessage = "{\"event\":\"Testing\"}";
		boolean isPublished = imsSNSClient.publishMessage(dummyMessage);
		Assert.assertTrue(isPublished);
	}*/
}
