package com.snapdeal.ims.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.RetryInfo;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;

@Component
public class TestSchedulerExecutor implements TaskExecutor<TestTaskRequest> {

	File file = new File("/home/ankit/IMS/1secSchedule.txt");

	@Override
	public ExecutorResponse execute(TestTaskRequest taskRequest) {
		ExecutorResponse response = new ExecutorResponse();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// true = append file
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(), true);

			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("Hello :" + taskRequest.getEmail()
					+ " current time is :"
					+ new Date(System.currentTimeMillis()));
			bufferWritter.flush();
			bufferWritter.close();
			
			System.out.println("Hello :" + taskRequest.getEmail()
					+ " current time is :"
					+ new Date(System.currentTimeMillis()));
		} catch (IOException e) {
			System.out.println("Exception: "+e);
		}
		
		/*response.setCompletionLog(taskRequest.getTaskId() + " completed successfully");
		response.setStatus(Status.SUCCESS);*/
		
		if(taskRequest.getTaskNumber() == 0){
			response.setCompletionLog(taskRequest.getTaskId() + " completed successfully");
			response.setStatus(Status.SUCCESS);
		}
		else if(taskRequest.getTaskNumber() == 1){
			RetryInfo retryInfo = new RetryInfo();
			retryInfo.setType(RetryInfo.RetryType.LINEAR);
			//retryInfo.setExpBase(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_EXPONENTIAL_BASE);
			retryInfo
					.setWaitTime(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_REEXECUTION_WAIT_TIME);
			response.setAction(retryInfo);
			response.setStatus(Status.RETRY);
			response.setStatus(Status.RETRY);
		}
		else{
			response.setCompletionLog(taskRequest.getTaskId() + " failed");
			response.setStatus(Status.FAILURE);
		}
		return response;
	}

}
