package com.snapdeal.bulkprocess.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;
import com.snapdeal.bulkprocess.service.BulkService;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;

@Service("bulkService")
public class BulkServiceImpl implements BulkService {

	@Autowired
	TaskScheduler taskScheduler;

	public void submitJob(GenericBulkTaskRequest taskRequest) {

		TaskDTO bulkTaskDTO = new TaskDTO();
		bulkTaskDTO.setRequest(taskRequest);
		bulkTaskDTO.setCurrentScheduleTime(new Date());
		bulkTaskDTO.setTaskType("GENERIC_BULK_TASK");
		taskScheduler.submitTask(bulkTaskDTO);

	}

}