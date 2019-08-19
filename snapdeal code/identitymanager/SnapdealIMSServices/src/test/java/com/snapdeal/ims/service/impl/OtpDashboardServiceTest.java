package com.snapdeal.ims.service.impl;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.dashboard.dbmapper.IUserOtpDetailsDao;
import com.snapdeal.ims.entity.UserOtp;
import com.snapdeal.ims.enums.UserOtpDetailsSearchField;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.service.IUserOtpService;


@ContextConfiguration(locations = {"classpath:/spring/application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OtpDashboardServiceTest {

	@Autowired
	IUserOtpService userOtpService;
	@Autowired
	IUserOtpDetailsDao userOtpDetailsDao;

	@Test
	public void getUserOtpDetailsTest() {
		GetUserOtpDetailsRequest request=new GetUserOtpDetailsRequest();
		request.setValue("aaaaa");
		request.setSearchField(UserOtpDetailsSearchField.OTP_ID);
		GetUserOtpDetailsResponse response=userOtpService.getUserOtpDetails(request) ;
		List<UserOtp> userOtpDetails=response.getUserOtpDetails();
		Assert.assertTrue(userOtpDetails.size()!=0);
	}


}
