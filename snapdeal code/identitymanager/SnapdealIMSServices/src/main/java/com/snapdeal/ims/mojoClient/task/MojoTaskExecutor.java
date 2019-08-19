package com.snapdeal.ims.mojoClient.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.freecharge.mojo.MojoClient;
import com.freecharge.mojo.core.MojoResponse;
import com.snapdeal.ims.constants.MojoClientConstant;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.mojoClient.request.MojoRequest;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("MojoClient")
public class MojoTaskExecutor implements TaskExecutor<TaskRequest> {

	@Autowired
	private MojoClient mojoClient;

	@Override
	public ExecutorResponse execute(TaskRequest request) {
		final ExecutorResponse execResponse = new ExecutorResponse();
		final MojoRequest mojoRequest = (MojoRequest) request;

		try {
			MojoResponse response = mojoClient.updateUserMobile(mojoRequest.getUserId(),mojoRequest.getMobileNumber());
			if(response!=null && response.getErrorCode().equalsIgnoreCase(MojoClientConstant.SUCCESS)){
				log.info("Virtual Card has been created successfully for request: "
						+ mojoRequest);
				execResponse.setCompletionLog(mojoRequest.getUserId());
				execResponse.setStatus(Status.SUCCESS);
			}else if(response!=null && (response.getErrorCode().equalsIgnoreCase(MojoClientConstant.USER_AUTHENTICATION_FAILURE) 
					|| response.getErrorCode().equalsIgnoreCase(MojoClientConstant.GENERIC_UNKNOWN_ERROR))){
				log.error(
						"InternalServerException - "+ response.getErrorMessage() + "while creating Virtual Card, metadata: "
								+ mojoRequest + "...retrying");
				RetryInfo retryInfo = new RetryInfo();
				retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
				retryInfo.setExpBase(ServiceCommonConstants.MOJOCLIENT_FAILURE_TASK_EXPONENTIAL_BASE);
				retryInfo
				.setWaitTime(ServiceCommonConstants.MOJOCLIENT_FAILURE_TASK_REEXECUTION_WAIT_TIME);
				execResponse.setAction(retryInfo);
				execResponse.setStatus(Status.RETRY);
			}else{
				log.info("Virtual Card creation have failed for request: " + response.getErrorCode() + response.getErrorMessage()
						+ mojoRequest);
				execResponse.setCompletionLog(mojoRequest.getUserId());
				execResponse.setStatus(Status.FAILURE);
			}
		}
		catch (Exception e) {
			log.error(
					"Exception - " + e.getMessage() + "occured while consuming creating Virtual Card, metadata: "
							+ mojoRequest);
			execResponse.setCompletionLog(e.getMessage());
			execResponse.setStatus(Status.FAILURE);
		}
		return execResponse;
	}

}
