package com.snapdeal.ims.wallet.request;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class UserMigrationStatusSerializer
		implements
			TaskSerializer<UserMigrationStatusRequest> {

	@Override
	public UserMigrationStatusRequest fromString(String request) {
		Gson gson = new Gson();
		UserMigrationStatusRequest target = null;
		if (request != null) {
			target = gson.fromJson(request, UserMigrationStatusRequest.class);
		}

		return target;
	}

	@Override
	public String toString(UserMigrationStatusRequest request) {
		Gson gson = new Gson();
		String json = null;
		if (request != null) {
			json = gson.toJson(request);
		}

		return json;
	}

}
