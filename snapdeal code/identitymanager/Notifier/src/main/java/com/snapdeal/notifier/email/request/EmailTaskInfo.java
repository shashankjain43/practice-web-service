package com.snapdeal.notifier.email.request;

import com.snapdeal.notifier.email.constant.NotifierConst;
import com.snapdeal.notifier.email.task.EmailTaskExecutor;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailTaskInfo implements TaskRegistrationInfo {

   @Autowired
   private EmailTaskExecutor executor;

   @Autowired
   private EmailSerializer serializer;

   @Override
   public TaskExecutor getTaskExecutor() {
      return executor;
   }

   @Override
   public TaskSerializer<EmailRequest> getTaskSerializer() {
      return serializer;
   }

   @Override
   public String getTaskType() {
      
      return NotifierConst.EMAIL_TASK_TYPE;
   }

   @Override
   public boolean isRecurring() {
      
      return false;
   }

   @Override
   public long getExecutionTime() {
     
      return NotifierConst.DEFAULT_EMAIL_EXECUTION_TIMEOUT;
   }

   @Override
   public long getRetryLimit() {
   
      return NotifierConst.DEFAULT_EMAIL_RETRY_LIMIT;
   }
}
