package com.shashank.apiconsume.APIConsume;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.snapdeal.payments.view.client.impl.MerchantViewClient;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTransactionsHistoryRequest;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTransactionsHistoryResponse;
import com.snapdeal.payments.view.utils.ClientDetails;

/**
 * Hello world!
 *
 */
public class App {
	
	static MerchantViewClient mvClient = new MerchantViewClient();
	
	static {
		try {
			ClientDetails.init("http://localhost", "8080",
					"b1736f66-705c-4966-bc9a-d7fa9393ffdb", "GJvwsW82414zeI",
					120000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getMerchantTxnHistory() {
		GetMerchantTransactionsHistoryRequest request = new GetMerchantTransactionsHistoryRequest();
		request.setMerchantId("l2Paj4COuVZbgU");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setEndDate(sdf.parse("2016-04-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			request.setStartDate(sdf.parse("2015-04-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setLimit(10);
		request.setLastEvaluatedkey(1451983242000L);
		// request.setPrevious(true);
		GetMerchantTransactionsHistoryResponse response = mvClient
				.getMerchantTransactionsHistory(request);
		System.out.println("response " + response);

	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		getMerchantTxnHistory();
	}
}
