package com.snapdeal.ims.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.service.IFortKnoxService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

@Slf4j
@Component
public class FortKnoxService implements IFortKnoxService{

   @Autowired
   private TaskScheduler taskScheduler;

   @Override
   @Timed
   @Marked
	public void createFortKnotTask(FortKnoxRequest fortKnoxRequest) {

      final TaskDTO taskDTO = new TaskDTO();
      taskDTO.setRequest(fortKnoxRequest);
      taskDTO.setCurrentScheduleTime(new Date());
      taskDTO.setTaskType(ServiceCommonConstants.CREATE_FORTKNOX_MERGE);

      try {
         
         taskScheduler.submitTask(taskDTO);
      } catch (DuplicateTaskException e) {
         
         final String exceptionMsg = 
                  String.format("DuplicateTaskException while creating IMSFortKnoxTask, "
                           + "UserID: %s, taskId: %s", fortKnoxRequest.getUserId(), 
                           fortKnoxRequest.getTaskId());
         log.error(exceptionMsg, e);
      } catch (InvalidTaskTypeException e) {
         
         String exceptionMsg = 
                  String.format(
                           "InvalidTaskTypeException while creating IMSFortKnoxTask, "
                           + "UserID: %s, taskId: %s", fortKnoxRequest.getUserId(), 
                           fortKnoxRequest.getTaskId());
         log.error(exceptionMsg, e);
         throw e;
      }
   }
}
