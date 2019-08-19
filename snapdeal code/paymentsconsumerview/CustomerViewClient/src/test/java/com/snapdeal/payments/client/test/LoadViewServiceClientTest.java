package com.snapdeal.payments.client.test;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.payments.view.client.impl.LoadCashClient;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsResponse;
import com.snapdeal.payments.view.utils.ClientDetails;
	
@Slf4j
public class LoadViewServiceClientTest {

	LoadCashClient client = new LoadCashClient() ;

	@Before
	public void setup() throws Exception {
		//stage
		//ClientDetails.init("https://views-stg.paywithfreecharge.com", "443", "49c9d21c-ed41-4e73-88ed-ebfc1c102304", "Fd1qV0jLp0lCCS",12000);

		//local
		ClientDetails.init("http://localhost", "8080", "49c9d21c-ed41-4e73-88ed-ebfc1c102304", "Fd1qV0jLp0lCCS",12000);
	}

	
	@Test
	public void getLoadCashTxnsByUserIdTest() {
		GetLoadCashTxnsByUserIdRequest request = new GetLoadCashTxnsByUserIdRequest() ;
		request.setMerchantId("jzlsYEYIUjA4jV");
		request.setLimit(50);
		request.setPage(1);
		request.setOrderby(1);
		request.setUserId("VjAxI2EzYzlkYjhjLWZhZGYtNDVlMy05YzkxLTc0NzRkYjllMjYxYg");
		GetLoadCashTxnsResponse response =   client.getLoadCashTxnsByUserId(request);
		System.out.println(response);
		log.info(response.toString());
	}
	
	@Test
	public void getLoadCashTxnsByTxnIdTest() {
		GetLoadCashTxnsByTxnIdRequest request = new GetLoadCashTxnsByTxnIdRequest() ;
		request.setMerchantId("jzlsYEYIUjA4jV");
		request.setLimit(50);
		request.setPage(1);
		request.setOrderby(1);
		request.setTxnId("100000000211360");
		GetLoadCashTxnsResponse response =   client.getLoadCashTxnsByTxnId(request);
		System.out.println(response);
		log.info(response.toString());
	}
	

}