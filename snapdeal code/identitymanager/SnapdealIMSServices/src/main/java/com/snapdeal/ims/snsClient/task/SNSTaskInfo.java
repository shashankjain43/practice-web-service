package com.snapdeal.ims.snsClient.task;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.snsClient.request.SNSTaskRequest;
import com.snapdeal.ims.snsClient.request.SNSTaskSerializer;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SNSTaskInfo implements TaskRegistrationInfo {

   @Autowired
   SNSTaskExecutor snsTaskExecutor;

   @Autowired
   SNSTaskSerializer serializer;

   @Override
   public long getExecutionTime() {
      return 60 * 1000;
   }

   @Override
   public long getRetryLimit() {
      return ServiceCommonConstants.DEFAULT_CREATE_SNS_TASK_RETRY_LIMIT;
   }

   @Override
   public TaskExecutor getTaskExecutor() {
      return snsTaskExecutor;
   }

   @Override
   public TaskSerializer<SNSTaskRequest> getTaskSerializer() {
      return serializer;
   }

   @Override
   public String getTaskType() {
      return ServiceCommonConstants.CREATE_SNS_TASK_TYPE;
   }

   @Override
   public boolean isRecurring() {
      return false;
   }

}
