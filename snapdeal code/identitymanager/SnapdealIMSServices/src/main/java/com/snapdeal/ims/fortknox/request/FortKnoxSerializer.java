package com.snapdeal.ims.fortknox.request;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class FortKnoxSerializer implements TaskSerializer<FortKnoxRequest> {

   @Override
   public FortKnoxRequest fromString(String request) {
      Gson gson = new Gson();
      FortKnoxRequest target = null;
      if (request != null) {
			target = gson.fromJson(request, FortKnoxRequest.class);
      }

      return target;
   }

   @Override
   public String toString(FortKnoxRequest request) {
      Gson gson = new Gson();
      String json = null;
      if (request != null) {
         json = gson.toJson(request);
      }

      return json;
   }


}
