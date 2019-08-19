package com.snapdeal.ims.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.dashboard.dbmapper.IDiscrepencyCountDao;
import com.snapdeal.ims.enums.DiscrepencyCase;
import com.snapdeal.ims.request.GetDiscrepencyCountServiceRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesServiceRequest;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.service.IDiscrepencyCountService;

@ContextConfiguration("classpath:/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)

public class DiscrepencyCountTest {
	
	@Autowired
	IDiscrepencyCountService discrepencyCountService;
	@Autowired
	IDiscrepencyCountDao discrepencyCountDao;
	
	@Test
	public void getDiscrepencyCountForUsersTest() {
		GetDiscrepencyCountServiceRequest request=new GetDiscrepencyCountServiceRequest();
		Timestamp t1= Timestamp.valueOf("2015-08-25 00:00:00.0");
		Timestamp t2= Timestamp.valueOf("2015-08-25 23:59:59.0");
		request.setFromDate(t1);
		request.setToDate(t2);
		GetDiscrepencyCountResponse response=discrepencyCountService.getDiscrepencyCountForUsers(request);
		Assert.assertNotNull(response.getFcNullCount());
		Assert.assertNotNull(response.getSdNullCount());
		Assert.assertNotNull(response.getSdFcNullcount());
	}
	
	@Test
	public void  getAllEmailForDiscrepencyCasesTest(){
		GetEmailForDiscrepencyCasesServiceRequest request=new GetEmailForDiscrepencyCasesServiceRequest();
		request.setDCase(DiscrepencyCase.FC_NULL_COUNTER);
		Timestamp t1= Timestamp.valueOf("2015-08-25 00:00:00.0");
		Timestamp t2= Timestamp.valueOf("2015-08-25 23:59:59.0");
		request.setFromDate(t1);
		request.setToDate(t2);

		List<String> emailIds=new ArrayList<String>();
		emailIds.add("test123@gmail.com");

		DiscrepencyCasesEmailResponse response=discrepencyCountService.getAllEmailForDiscrepencyCases(request); 
		Assert.assertNotNull(response.getEmailIds());
		
	}
}
