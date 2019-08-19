package com.snapdeal.ims.Notifier.Emailsender.request;


import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class NotifierEmailSendingSerializer implements TaskSerializer<NotifierEmailSendingRequest> {

   @Override
   public NotifierEmailSendingRequest fromString(String request) {
      Gson gson = new Gson();
      NotifierEmailSendingRequest target = null;
      if (request != null) {
			target = gson.fromJson(request, NotifierEmailSendingRequest.class);
      }

      return target;
   }

   @Override
   public String toString(NotifierEmailSendingRequest request) {
      Gson gson = new Gson();
      String json = null;
      if (request != null) {
         json = gson.toJson(request);
      }

      return json;
   }


}
