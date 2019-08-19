package com.snapdeal.ims.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.service.ISNSTaskService;
import com.snapdeal.ims.snsClient.request.SNSTaskRequest;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

@Slf4j
@Component
public class SNSTaskService implements ISNSTaskService {

   @Autowired
   TaskScheduler taskScheduler;

   @Override
   @Timed
   @Marked
   public void createSNSTask(String message, String taskId) {

      final SNSTaskRequest snsTaskRequest = new SNSTaskRequest();
      snsTaskRequest.setTaskId(taskId);
      snsTaskRequest.setMessage(message);

      final TaskDTO taskDTO = new TaskDTO();
      taskDTO.setRequest(snsTaskRequest);
      taskDTO.setTaskType(ServiceCommonConstants.CREATE_SNS_TASK_TYPE);
      taskDTO.setCurrentScheduleTime(new Date());

      try {
         taskScheduler.submitTask(taskDTO);
      } catch (DuplicateTaskException e) {

         final String exceptionMsg = String.format(
                  "DuplicateTaskException while creating SNSTask, " + "Message: %s, taskId: %s",
                  message, taskId);
         log.error(exceptionMsg, e);
      } catch (InvalidTaskTypeException e) {

         String exceptionMsg = String.format("InvalidTaskTypeException while creating SNSTask, "
                  + "Message: %s, taskId: %s", message, taskId);
         log.error(exceptionMsg, e);
         throw e;
      }
   }

}
