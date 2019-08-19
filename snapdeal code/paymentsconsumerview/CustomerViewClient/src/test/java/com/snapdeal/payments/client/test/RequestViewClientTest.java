package com.snapdeal.payments.client.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.payments.view.client.impl.RequestViewClient;
import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.request.commons.request.GetPendingActionsBetweenPartyRequest;
import com.snapdeal.payments.view.request.commons.request.GetSplitViewTransactionsRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserActionsHistoryRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserPendingActionsRequest;
import com.snapdeal.payments.view.request.commons.response.GetActionsWithFilterResponse;
import com.snapdeal.payments.view.request.commons.response.GetPendingActionsBetweenPartiesResponse;
import com.snapdeal.payments.view.request.commons.response.GetSplitViewTransactionsResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserPendingActionsResponse;
import com.snapdeal.payments.view.utils.ClientDetails;

@Slf4j
public class RequestViewClientTest {

	RequestViewClient mvClient = new RequestViewClient() ;

	@Before
	public void setup() throws Exception {

	// ClientDetails.init("http://localhost", "8080", "snapdeal", "1",12000);
	 ClientDetails.init("http://localhost", "8080", "49c9d21c-ed41-4e73-88ed-ebfc1c102304", "Fd1qV0jLp0lCCS",12000);
	 ClientDetails.init("http://localhost", "8080", "d866jdc06fd&", "test_auth_client",12000);
	}

	
	/*@Test
	public void getTxnsBySearch()
			throws PaymentsViewServiceException {
		GetSplitViewTransactionsRequest request = new GetSplitViewTransactionsRequest();
		
		GetSplitViewTransactionsResponse response = mvClient.getSplitViewTransactions(request);
		System.out.println(response);
	}*/
	@Test
	public void getUserActionsHistory()
			throws PaymentsViewServiceException {
		GetUserActionsHistoryRequest request = new GetUserActionsHistoryRequest();
		request.setEndDate(new Date());
		request.setStartDate(new Date());
		request.setLimit(50);
		request.setPrevious(true);
		request.setUserId("1233243");
		List<ActionType> actionTypes = new LinkedList<ActionType>() ;
		actionTypes.add(ActionType.P2P_REQUEST_MONEY) ;
		request.setActionType(actionTypes);
		
		GetActionsWithFilterResponse 
		response = mvClient.getUserActionsHistory(request);
		System.out.println(response);
	}
	@Test
	public void getPendingActionsBetweenParties()
			throws PaymentsViewServiceException {
		GetPendingActionsBetweenPartyRequest request = new GetPendingActionsBetweenPartyRequest();
		request.setEndDate(new Date());
		request.setStartDate(new Date());
		request.setLimit(50);
		request.setPrevious(true);
		request.setUserId("1233243");
		List<ActionType> actionTypes = new LinkedList<ActionType>() ;
		actionTypes.add(ActionType.P2P_REQUEST_MONEY) ;
		request.setOtherPartyId("2e32454");
		request.setActionType(actionTypes);
		GetPendingActionsBetweenPartiesResponse response = mvClient.getPendingActionsBetweenParties(request);
		System.out.println(response);
	}
	@Test
	public void getUserPendingActions()
			throws PaymentsViewServiceException {
		GetUserPendingActionsRequest request = new GetUserPendingActionsRequest();
		request.setEndDate(new Date());
		request.setStartDate(new Date());
		request.setLimit(50);
		request.setPrevious(true);
		request.setUserId("1233243");
		List<ActionType> actionTypes = new LinkedList<ActionType>() ;
		actionTypes.add(ActionType.P2P_REQUEST_MONEY) ;
		request.setActionType(actionTypes);
		GetUserPendingActionsResponse  response = mvClient.getUserPendingActions(request);
		System.out.println(response);
	}
	
	/*@Test
	public void getSplitTxnsBySearch()
			throws PaymentsViewServiceException {
		GetSplitViewTransactionsRequest request = new GetSplitViewTransactionsRequest();
		request.setFcTxnId("1234345");
		request.setSrcPartyId("1232342");
		request.setDestPartyId("2332");
		request.setEndDate(new Date());
		request.setStartDate(new Date());
		request.setRequestType("P2P_SEND_MONEY");
		request.setStartDate(new Date());
		request.setEndDate(new Date());
		request.setSrcEmailId("abhi@bhishked");
		request.setDestEmailId("abhi@mdjbfjh.com");
		request.setSrcMobileNumber("2344567678");
		request.setDestMobileNumber("34567687");
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClientName("Fd1qV0jLp0lCCS");
		clientConfig.setClientKey("49c9d21c-ed41-4e73-88ed-ebfc1c102304");
		clientConfig.setAppRequestId(UUID.randomUUID().toString());
		clientConfig.setApiTimeOut(5000);
		request.setClientConfig(clientConfig);
		request.setUserAgent("22uuyty3278t83");
		request.setUserMachineIdentifier("q2yu21tu21huydsbajyg326327");
		request.setClientConfig(clientConfig);
		request.setPage(1);
		request.setSplitId("1232443");
		//request.setS
		request.setLimit(50);
		request.setToken("ashj bjsfdhbsdf");
		GetSplitViewTransactionsResponse response = mvClient.getSplitViewTransactions(request);
		System.out.println(response);
	}*/
}
