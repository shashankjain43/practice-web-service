package com.snapdeal.bulkprocess.serializer;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;
import com.snapdeal.payments.ts.registration.TaskSerializer;


@Component("genericBulkSerializer")
public class GenericTaskSerializer implements TaskSerializer<GenericBulkTaskRequest>{

	private static Gson gson = new Gson();
	public String toString(GenericBulkTaskRequest paramT) {
		return gson.toJson(paramT);
	}

	public GenericBulkTaskRequest fromString(String paramString) {
		return gson.fromJson(paramString, GenericBulkTaskRequest.class);
	}

}
