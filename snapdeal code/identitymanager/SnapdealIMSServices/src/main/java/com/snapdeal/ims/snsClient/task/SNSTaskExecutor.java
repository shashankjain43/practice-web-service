package com.snapdeal.ims.snsClient.task;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.snsClient.IMSSNSClient;
import com.snapdeal.ims.snsClient.request.SNSTaskRequest;
import com.snapdeal.payments.sdmoney.exceptions.InternalErrorException;
import com.snapdeal.payments.sdmoney.exceptions.ValidationException;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.RetryInfo;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("SNSTask")
public class SNSTaskExecutor implements TaskExecutor<TaskRequest> {

	@Autowired
	IMSSNSClient imsSNSClient;

   @Override
   public ExecutorResponse execute(TaskRequest request) {
      ExecutorResponse execResponse = new ExecutorResponse();
      SNSTaskRequest snsTaskRequest = (SNSTaskRequest) request;

      try {
         // validate Request
         snsTaskRequest.validate();

         // try to execute request
         publishMessage(snsTaskRequest);

         execResponse.setCompletionLog(snsTaskRequest.getMessage());
         execResponse.setStatus(Status.SUCCESS);

      } catch (InternalErrorException e) {

         log.error("InternalServerException while executing SNS Task, metadata: " + snsTaskRequest
                  + "...retrying", e);

         // retry if the service on the other end is down
         RetryInfo retryInfo = new RetryInfo();
         retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);

         retryInfo.setExpBase(ServiceCommonConstants.SNS_SERVICE_EXPONENTIAL_BASE);
         retryInfo.setWaitTime(ServiceCommonConstants.DEFAULT_ON_SNS_SERVICE_FAILURE_TASK_REEXECUTION_WAIT_TIME);
         execResponse.setAction(retryInfo);
         execResponse.setStatus(Status.RETRY);
      } catch (ValidationException e) {

         log.error("ValidationException while executing SNS Task, metadata: " + snsTaskRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      } catch (Exception e) {

         log.error("Exception occured executing SNS Task, metadata: " + snsTaskRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      }
      return execResponse;
   }

   private void publishMessage(SNSTaskRequest snsTaskRequest) {
      imsSNSClient.publishMessage(snsTaskRequest.getMessage());
   }
}
