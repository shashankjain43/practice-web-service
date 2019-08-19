package com.snapdeal.ims.wallet.request;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class WalletSerializer implements TaskSerializer<WalletRequest> {

   @Override
   public WalletRequest fromString(String request) {
      Gson gson = new Gson();
      WalletRequest target = null;
      if (request != null) {
			target = gson.fromJson(request, WalletRequest.class);
      }

      return target;
   }

   @Override
   public String toString(WalletRequest request) {
      Gson gson = new Gson();
      String json = null;
      if (request != null) {
         json = gson.toJson(request);
      }

      return json;
   }


}
