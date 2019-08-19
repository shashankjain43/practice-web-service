package com.snapdeal.notifier.email.request;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.stereotype.Component;

@Component
public class EmailSerializer implements TaskSerializer<EmailRequest> {

   @Override
   public EmailRequest fromString(String request) {
      Gson gson = new Gson();
      EmailRequest target = null;
      if (request != null) {
         target = gson.fromJson(request, EmailRequest.class);
      }

      return target;
   }

   @Override
   public String toString(EmailRequest request) {
      Gson gson = new Gson();
      String json = null;
      if (request != null) {
         json = gson.toJson(request);
      }

      return json;
   }

}
