package com.snapdeal.ims.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.mojoClient.request.MojoRequest;
import com.snapdeal.ims.service.IMojoService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MojoClientService implements IMojoService {

	@Autowired
	private TaskScheduler taskScheduler;

	@Override
	@Timed
	@Marked
	public void createMojoClientTask(MojoRequest mojoRequest) {

		final TaskDTO taskDTO = new TaskDTO();
		taskDTO.setRequest(mojoRequest);
		taskDTO.setCurrentScheduleTime(new Date());
		taskDTO.setTaskType(ServiceCommonConstants.CREATE_MOJOCLIENT);
		try {
			taskScheduler.submitTask(taskDTO);
		} catch (DuplicateTaskException e) {

			final String exceptionMsg = 
					String.format("DuplicateTaskException while creating MojoClient, "
							+ "UserID: %s, taskId: %s", mojoRequest.getUserId(), 
							mojoRequest.getTaskId());
			log.error(exceptionMsg, e);
		} catch (InvalidTaskTypeException e) {

			String exceptionMsg = 
					String.format(
							"InvalidTaskTypeException while creating MojoClient, "
									+ "UserID: %s, taskId: %s", mojoRequest.getUserId(), 
									mojoRequest.getTaskId());
			log.error(exceptionMsg, e);
			throw e;
		}
	}

}
