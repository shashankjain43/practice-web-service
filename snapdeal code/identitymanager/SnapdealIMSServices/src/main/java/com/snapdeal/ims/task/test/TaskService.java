package com.snapdeal.ims.task.test;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskService implements ITaskService{

   @Autowired
   private TaskScheduler taskScheduler;

   @Override
   public void createCompleteTask(String imsIdentifier, String businessId, String taskId,
            FortKnoxRequest fortKnoxRequest, String emailId, WalletUserMigrationStatus status) {


      final CompleteTaskRequest taskRequest = new CompleteTaskRequest();

      taskRequest.setUserMigrationStatus(status);
      taskRequest.setEmailId(emailId);
      taskRequest.setTaskId(taskId);
      taskRequest.setUserId(imsIdentifier);
      taskRequest.setBusinessId(businessId);
      taskRequest.setFcUserId(fortKnoxRequest.getFcUserId());
      taskRequest.setMergeType(fortKnoxRequest.getMergeType());
      taskRequest.setSdUserId(fortKnoxRequest.getSdUserId());
 
      final TaskDTO taskDTO = new TaskDTO();
      taskDTO.setRequest(taskRequest);
      taskDTO.setTaskType(ServiceCommonConstants.CREATE_COMPLETE_TASK_TYPE);

      taskDTO.setCurrentScheduleTime(new Date());
      try {
         
         taskScheduler.submitTask(taskDTO);
      } catch (DuplicateTaskException e) {
         
         final String exceptionMsg = 
                  String.format("DuplicateTaskException while creating IMSWalletTask, "
                           + "UserID: %s, BusinessId: %s, taskId: %s", imsIdentifier, 
                           businessId, taskId);
         log.error(exceptionMsg, e);
      } catch (InvalidTaskTypeException e) {
         
         String exceptionMsg = 
                  String.format(
                           "InvalidTaskTypeException while creating IMSWalletTask, "
                           + "UserID: %s, BusinessId: %s, taskId: %s", imsIdentifier, 
                           businessId, taskId);
         log.error(exceptionMsg, e);
         throw e;
      }
      }
}
