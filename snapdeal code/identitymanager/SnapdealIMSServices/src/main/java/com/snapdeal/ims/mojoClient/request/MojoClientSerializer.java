package com.snapdeal.ims.mojoClient.request;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class MojoClientSerializer implements TaskSerializer<MojoRequest> {

	@Override
	public String toString(MojoRequest request) {
	      Gson gson = new Gson();
	      String json = null;
	      if (request != null) {
	         json = gson.toJson(request);
	      }

	      return json;
	}

	@Override
	public MojoRequest fromString(String request) {
	      Gson gson = new Gson();
	      MojoRequest target = null;
	      if (request != null) {
				target = gson.fromJson(request, MojoRequest.class);
	      }
	      return target;
	      
	}

}
