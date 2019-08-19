package com.snapdeal.ims.test;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class TestSerializer implements TaskSerializer<TestTaskRequest>{

	private static Gson gson = new Gson();

	@Override
	public String toString(TestTaskRequest request) {
		return gson.toJson(request);
	}

	@Override
	public TestTaskRequest fromString(String request) {
		return gson.fromJson(request,TestTaskRequest.class);
	}
	
}
