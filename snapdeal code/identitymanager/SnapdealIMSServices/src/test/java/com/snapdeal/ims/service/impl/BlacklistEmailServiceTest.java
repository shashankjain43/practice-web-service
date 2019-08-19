package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BlacklistEmailServiceTest {

	
	@Autowired
	BlacklistEmailServiceImpl blackEmailService;
	
	@Test
	public void getBlacklistEmail() {
		List<String> blacklistUsers = new ArrayList<String>();
		blacklistUsers = blackEmailService.getBlacklistEmail();
		Assert.assertTrue(!blacklistUsers.isEmpty());
	}
}
