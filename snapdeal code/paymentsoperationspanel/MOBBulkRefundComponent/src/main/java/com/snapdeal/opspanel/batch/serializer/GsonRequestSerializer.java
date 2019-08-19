package com.snapdeal.opspanel.batch.serializer;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.opspanel.bulk.request.BulkRefundTaskRequest;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class GsonRequestSerializer implements TaskSerializer<BulkRefundTaskRequest> {

	private static Gson gson = new Gson();
	
	public String toString(BulkRefundTaskRequest request) {
		return gson.toJson(request);
	}

	public BulkRefundTaskRequest fromString(String request) {
		return gson.fromJson(request,BulkRefundTaskRequest.class);
	}

}
