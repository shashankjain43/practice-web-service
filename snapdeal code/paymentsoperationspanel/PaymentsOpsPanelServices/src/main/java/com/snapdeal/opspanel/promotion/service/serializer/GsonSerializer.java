package com.snapdeal.opspanel.promotion.service.serializer;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.opspanel.promotion.request.BulkTaskRequest;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class GsonSerializer implements TaskSerializer<BulkTaskRequest> {

	private static Gson gson = new Gson();
	
	@Override
	public String toString(BulkTaskRequest request) {
		return gson.toJson(request);
	}

	@Override
	public BulkTaskRequest fromString(String request) {
		return gson.fromJson(request,BulkTaskRequest.class);
	}

}
