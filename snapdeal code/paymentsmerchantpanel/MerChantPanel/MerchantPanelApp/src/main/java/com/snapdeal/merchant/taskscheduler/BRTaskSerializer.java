package com.snapdeal.merchant.taskscheduler;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class BRTaskSerializer implements TaskSerializer<BulkRefundTaskRequest>{

	private static Gson gson = new Gson();	
	
	@Override
	public String toString(BulkRefundTaskRequest request) {
		return gson.toJson(request);
	}

	@Override
	public BulkRefundTaskRequest fromString(String request) {
		return gson.fromJson(request,BulkRefundTaskRequest.class);
	}

}
