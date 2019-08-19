package com.snapdeal.ims.snsClient.request;

import com.google.gson.Gson;
import com.snapdeal.payments.ts.registration.TaskSerializer;
import org.springframework.stereotype.Component;

@Component
public class SNSTaskSerializer implements TaskSerializer<SNSTaskRequest>{

   @Override
   public SNSTaskRequest fromString(String request) {
      Gson gson = new Gson();
      SNSTaskRequest target = null;
      
      if(request!=null){
         target = gson.fromJson(request, SNSTaskRequest.class);
      }
      return target;
   }

   @Override
   public String toString(SNSTaskRequest request) {
      Gson gson = new Gson();
      String target = null;
      if(request!=null){
         target = gson.toJson(request);
      }
      return target;
   }
   
}
