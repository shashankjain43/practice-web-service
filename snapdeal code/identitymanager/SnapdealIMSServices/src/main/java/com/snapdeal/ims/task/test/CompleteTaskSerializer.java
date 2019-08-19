package com.snapdeal.ims.task.test;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class CompleteTaskSerializer
		implements
			TaskSerializer<CompleteTaskRequest> {

	@Override
	public CompleteTaskRequest fromString(String request) {
		Gson gson = new Gson();
		CompleteTaskRequest target = null;
		if (request != null) {
			target = gson.fromJson(request, CompleteTaskRequest.class);
		}

		return target;
	}

	@Override
	public String toString(CompleteTaskRequest request) {
		Gson gson = new Gson();
		String json = null;
		if (request != null) {
			json = gson.toJson(request);
		}

		return json;
	}

}
