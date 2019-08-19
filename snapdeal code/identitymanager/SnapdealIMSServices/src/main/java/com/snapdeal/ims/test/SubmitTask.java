package com.snapdeal.ims.test;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.util.TaskConstant;

@Controller
@Slf4j
@RequestMapping("/PT")
public class SubmitTask {

	@Autowired
	TaskScheduler taskScheduler;

	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	@ResponseBody
	public String submitTask() {/*
		 schedule now 
		log.info("Task submitted");
		for (int i = 0; i < 100; i++) {
			TestTaskRequest request = new TestTaskRequest();
			request.setEmail(UUID.randomUUID().toString());

			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setTaskType("SMSTaskTemp");
			taskDTO.setRequest(request);
			taskDTO.setCurrentScheduleTime(TaskConstant
					.getDateWithOffset(100 * 1000));
			taskScheduler.submitTask(taskDTO);
		}*/
		return "SUCCESS";
	}
}
