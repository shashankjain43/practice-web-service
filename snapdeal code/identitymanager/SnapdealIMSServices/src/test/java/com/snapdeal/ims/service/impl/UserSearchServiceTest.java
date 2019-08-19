package com.snapdeal.ims.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.dashboard.dbmapper.IUserSearchDao;
import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.entity.UserSearchEnteredEntity;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;


@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserSearchServiceTest {

	@Autowired
	IUserSearchDao userSearchDao;

	@Autowired
	UserSearchServiceImpl userSearchService;

	@Test
	@Ignore
	public void getUserSearch(){
		userSearchService.setUserSearchDao(userSearchDao);
		UserSearchEnteredEntity userSearch = new UserSearchEnteredEntity();
		//userSearch.setEmail("john@ex");
		userSearch.setName("john");
		List<UserEntity> users = userSearchService.getUserByBasicSearch(userSearch);
		//Assert.assertTrue(users.get(0).getEmail().contains("john"));
	}

	@Test
	public void getUserHistoryDetailsTest() {
		GetUserHistoryDetailsRequest request=new GetUserHistoryDetailsRequest();
		request.setUserId("56789");
		GetUserHistoryDetailsResponse response=userSearchService.getUserHistoryDetails(request) ;
		List<UserHistory> userHistoryDetails=response.getUserHistoryDetails();
		Assert.assertNotNull(userHistoryDetails);
	}
	
	@Test(expected = IMSServiceException.class)
	public void getUserHistoryDetailsWithNullIdTest() {
		GetUserHistoryDetailsRequest request=new GetUserHistoryDetailsRequest();
		request.setUserId(null);
		GetUserHistoryDetailsResponse response=userSearchService.getUserHistoryDetails(request) ;
	}
	

	@Test(expected = IMSServiceException.class)
	public void getUserHistoryDetailsWithBlankIdTest() {
		GetUserHistoryDetailsRequest request=new GetUserHistoryDetailsRequest();
		request.setUserId("");
		GetUserHistoryDetailsResponse response=userSearchService.getUserHistoryDetails(request) ;
	}
}
