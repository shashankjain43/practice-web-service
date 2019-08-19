package com.snapdeal.ims.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IDashBoardServiceClient;
import com.snapdeal.ims.client.impl.DashboardClientServiceImpl;
import com.snapdeal.ims.enums.UserOtpDetailsSearchField;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.request.BlackListRequest;
import com.snapdeal.ims.request.GetDiscrepencyCountRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesRequest;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.request.GtokenSizeRequest;
import com.snapdeal.ims.request.UserSearchRequest;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.request.WalletFilterRequest;
import com.snapdeal.ims.response.BlacklistEmailResponse;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.FailedEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.response.WalletCountResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class DashboardServiceClientTest {
	


	IDashBoardServiceClient dashboardClientServiceImpl = new  DashboardClientServiceImpl();
	
	@Before
	public void setup() throws Exception {
		//ClientDetails.init("localhost", "8080", "snapdeal", "1", 12000);
		 ClientDetails.init("localhost", "8080", "zitsgfqo^a2p", "9782751690B09848",12000);
	}
	
	@Test
	public void getBlacklistEmails() throws HttpTransportException, ServiceException{
		BlackListRequest request = new BlackListRequest();
		BlacklistEmailResponse blacklistEmailResponse = dashboardClientServiceImpl.getBlacklistEmails(request);
		System.out.println(blacklistEmailResponse);
	}

	@Test
	public void getDiscrepencyCountForUsersTest()throws ServiceException, Exception {
		GetDiscrepencyCountRequest request=new GetDiscrepencyCountRequest();
		String t1= "2015-08-01";
		String t2= "2015-08-05";
		request.setFromDate(t1);
		request.setToDate(t2);

		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		GetDiscrepencyCountResponse response=dashboardClientServiceImpl.getSDFCIdDiscrepencyCountForUsers(request);
		System.out.println(response);
	}

	@Test
	public void getAllEmailForDiscrepencyCasesTest()throws ServiceException, Exception {
		GetEmailForDiscrepencyCasesRequest request=new GetEmailForDiscrepencyCasesRequest();
		String t1= "2015-08-01";
		String t2= "2015-08-05";
		request.setFromDate(t1);
		request.setToDate(t2);
		request.setDCase("SD_NULL_COUNTER");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");

		DiscrepencyCasesEmailResponse response=dashboardClientServiceImpl.getAllEmailForSDFCIdDiscrepenceycases(request);
		System.out.println(response);
	}
	
	@Test
	public void getGTokenSizeTest() throws HttpTransportException, ServiceException{
		GtokenSizeRequest gtokenSizeRequest = new GtokenSizeRequest();
		gtokenSizeRequest.setEmailId("abhishekgarg_test123@gmail.com");
		
		GtokenSizeResponse gTokenResponse = dashboardClientServiceImpl.gTokenSizeWithEmailId(gtokenSizeRequest);
		System.out.println(gTokenResponse.getSize());
	}
	
	@Test
	public void getUserOtpDetailsTest() throws ServiceException
	{
		GetUserOtpDetailsRequest request=new GetUserOtpDetailsRequest();
		request.setValue("001d6079-4dc3-4e53-8eb6-beed639bbc64");
		request.setUserMachineIdentifier("jndf");
		request.setUserAgent("abc");
		request.setSearchField(UserOtpDetailsSearchField.OTP_ID);
		GetUserOtpDetailsResponse response=dashboardClientServiceImpl.getUserOtpDetails(request); 
		System.out.println(response);
	}
	
	@Test
	public void userSearch() throws HttpTransportException, ServiceException{
		UserSearchRequest userSearchRequest = new UserSearchRequest();
		userSearchRequest.setUserId("1000");
		userSearchRequest.setName("");
		userSearchRequest.setEmail("");
		userSearchRequest.setMobile("");
		userSearchRequest.setFromDate("");
		userSearchRequest.setToDate("");
		UserSearchResponse userSearchResponse = dashboardClientServiceImpl.UserSearch(userSearchRequest);
		System.out.println(userSearchResponse);
	}
	
	
	@Test
	public void getWalletCountTest() throws HttpTransportException, ServiceException {
		ArrayList<String> upgradeChannel = new ArrayList<String>();
		upgradeChannel.add("MOBILE_WAP");
		//upgradeChannel.add("IOS_APP");
		ArrayList<String> upgradeSource = new ArrayList<String>();
		//upgradeSource.add("SIGN_IN");
		WalletFilterRequest request = new WalletFilterRequest();
		request.setUpgradeChannel(upgradeChannel);
		request.setUpgradeSource(upgradeSource);
		WalletCountResponse response = dashboardClientServiceImpl.getWalletCountBasedOnFilter(request);
		System.out.println(response);
	}
	
	@Test
	public void getFailedEmailList() throws HttpTransportException, ServiceException{
		ArrayList<String> upgradeChannel = new ArrayList<String>();
		upgradeChannel.add("MOBILE_WAP");
		//upgradeChannel.add("IOS_APP");
		ArrayList<String> upgradeSource = new ArrayList<String>();
		//upgradeSource.add("SIGN_IN");
		WalletFilterRequest request = new WalletFilterRequest();
		request.setUpgradeChannel(upgradeChannel);
		request.setUpgradeSource(upgradeSource);
		request.setMerchant("SNAPDEAL");
		FailedEmailResponse failedEmailResponse = dashboardClientServiceImpl.retrieveFailedEmailBasedOnFilter(request);
		System.out.println(failedEmailResponse);
	}
	
	@Test
	public void getStatus() throws HttpTransportException,ServiceException{
		UserStatusRequest userStatusRequest = new UserStatusRequest();
		userStatusRequest.setEmailId("");
		
		UserStatusResponse userStatusResponse  = dashboardClientServiceImpl.getStatus(userStatusRequest);
		System.out.println(userStatusResponse);
	}
	
	@Test
	public void getUserHistoryDetailsTest() throws ServiceException
	{
		GetUserHistoryDetailsRequest request=new GetUserHistoryDetailsRequest();
		request.setUserId("VjAxI2E0M2IxMmI3LTM3ODAtNDE5NS05MmRkLTMzNDBjZjIzYzg3YQ");
		GetUserHistoryDetailsResponse response=dashboardClientServiceImpl.getUserHistoryDetails(request); 
		System.out.println(response);
	}
}
