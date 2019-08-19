package com.snapdeal.ims.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.service.IWalletService;
import com.snapdeal.ims.wallet.request.UserMigrationStatusRequest;
import com.snapdeal.ims.wallet.request.WalletRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

@Slf4j
@Component
public class WalletService implements IWalletService{

   @Autowired
   private TaskScheduler taskScheduler;

   @Override
   @Timed
   @Marked
   public void createSDWalletTask(String imsIdentifier, 
                                  String businessId, 
                                  String taskId) {

      final WalletRequest createWalletRequest = new WalletRequest();

      createWalletRequest.setTaskId(taskId);
      createWalletRequest.setUserId(imsIdentifier);
      createWalletRequest.setBusinessId(businessId);
 
      final TaskDTO taskDTO = new TaskDTO();
      taskDTO.setRequest(createWalletRequest);
      taskDTO.setTaskType(ServiceCommonConstants.CREATE_SDWALLET_TASK_TYPE);

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

	@Override
	public void createNotificationMigrationStateChangeTask(
			String imsIdentifier, String businessId, String emailId,
			WalletUserMigrationStatus status) {
		String taskId = imsIdentifier;
		final UserMigrationStatusRequest userMigrationStatusRequest = new UserMigrationStatusRequest();

		userMigrationStatusRequest.setTaskId(taskId);
		userMigrationStatusRequest.setUserId(imsIdentifier);
		userMigrationStatusRequest.setUserMigrationStatus(status);
		userMigrationStatusRequest.setEmailId(emailId);

		final TaskDTO taskDTO = new TaskDTO();
		taskDTO.setRequest(userMigrationStatusRequest);
		taskDTO.setTaskType(ServiceCommonConstants.NOTIFY_MIGRATION_STATE_CHANGE);

		taskDTO.setCurrentScheduleTime(new Date());
		try {
			taskScheduler.submitTask(taskDTO);
		} catch (DuplicateTaskException e) {

			final String exceptionMsg = String.format(
					"DuplicateTaskException while creating NotifyUserMigrationStatusTask, "
							+ "UserID: %s, BusinessId: %s, taskId: %s",
					imsIdentifier, businessId, taskId);
			log.error(exceptionMsg, e);
		} catch (InvalidTaskTypeException e) {

			String exceptionMsg = String.format(
					"InvalidTaskTypeException while creating NotifyUserMigrationStatusTask, "
							+ "UserID: %s, BusinessId: %s, taskId: %s",
					imsIdentifier, businessId, taskId);
			log.error(exceptionMsg, e);
			throw e;
		}

	}
}
