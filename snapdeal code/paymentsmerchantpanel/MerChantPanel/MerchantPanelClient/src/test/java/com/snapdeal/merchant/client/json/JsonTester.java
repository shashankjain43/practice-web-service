package com.snapdeal.merchant.client.json;

import java.lang.reflect.Type;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.entity.response.GenericMerchantError;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;

public class JsonTester {

	public void verifyJson() {
		GenericMerchantResponse response = new GenericMerchantResponse();
		GenericMerchantError error = new GenericMerchantError("MY-001", "TEST MESSAGE");

		/*VerifyAndRefundTxnResponse clientResponse = new VerifyAndRefundTxnResponse(
				"CREDIT");*/

		response.setError(error);
		response.setData(null);

		Gson gson = new Gson();
		String outJson = gson.toJson(response);

		System.out.println(outJson);

		GenericMerchantResponse newResponse = gson.fromJson(outJson,
				GenericMerchantResponse.class);
		System.out.println(newResponse.toString());
	}

	public void verifyGenericJson() {
		GenericMerchantResponse<MerchantRefundAmountRequest> response = new GenericMerchantResponse<MerchantRefundAmountRequest>();
		GenericMerchantError error = new GenericMerchantError("MY-001", "TEST MESSAGE");

		

		response.setError(error);
		response.setData(null);

		Gson gson = new Gson();
		String outJson = gson.toJson(response);

		System.out.println(outJson);
		Type responseType = new TypeToken<GenericMerchantResponse<MerchantRefundAmountRequest>>() {
		}.getType();
		GenericMerchantResponse<?> newResponse = gson
				.fromJson(outJson, responseType);
		System.out.println(newResponse.toString());
	}
	
	public void verifyObjectJson() {
		GenericMerchantResponse response = new GenericMerchantResponse();
		GenericMerchantError error = new GenericMerchantError("MY-001", "TEST MESSAGE");

		

		response.setError(error);
		response.setData(null);

		Gson gson = new Gson();
		String outJson = gson.toJson(response);

		System.out.println(outJson);
		Type responseType = new TypeToken<GenericMerchantResponse>() {
		}.getType();
		GenericMerchantResponse newResponse = gson
				.fromJson(outJson, responseType);
		System.out.println(newResponse.toString());
	}
	
	public static void main(String []args) {
		JsonTester tester = new JsonTester();
		tester.verifyGenericJson();
	}

}
