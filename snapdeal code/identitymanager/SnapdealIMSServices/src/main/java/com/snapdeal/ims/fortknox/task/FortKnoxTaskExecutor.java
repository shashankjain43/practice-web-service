package com.snapdeal.ims.fortknox.task;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klickpay.fortknox.FortKnoxClient;
import com.klickpay.fortknox.FortKnoxException;
import com.klickpay.fortknox.MergeType;
import com.klickpay.fortknox.model.Response;
import com.snapdeal.ims.constants.FortKnoxExceptionConstant;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

/**
 * This Class is a worker class and it is responsible for one check merge for
 * fortknox post upgrade user step. Execute logic will be called by task
 * scheduler once task is submitted for execution.
 */
@Slf4j
@Component("FortKnoxTask")
public class FortKnoxTaskExecutor implements TaskExecutor<TaskRequest> {

	@Autowired
	private FortKnoxClient fortKnoxClient;

	@Override
	public ExecutorResponse execute(TaskRequest request) {

		final ExecutorResponse execResponse = new ExecutorResponse();
		final FortKnoxRequest fortKnoxRequest = (FortKnoxRequest) request;
		try {

			// Step0: Validate the request
			fortKnoxRequest.validate();

			Response doOneCheckMergeResponse = mergeFortKnox(fortKnoxRequest);

			if (doOneCheckMergeResponse != null && doOneCheckMergeResponse.getErrorCode().equalsIgnoreCase(
					(ServiceCommonConstants.FORTKNOX_SUCCESS_STATUS))) {
				log.info("doOneCheckMerge has been done successfully for request: "
						+ fortKnoxRequest);
				execResponse.setCompletionLog(fortKnoxRequest.getUserId());
				execResponse.setStatus(Status.SUCCESS);
			}else {
				log.error(
						"Error while consuming doOneCheckMerge api, metadata: "
								+ doOneCheckMergeResponse.getErrorMessage());
				execResponse.setCompletionLog(doOneCheckMergeResponse.getErrorMessage());
				execResponse.setStatus(Status.FAILURE);
			}
		} catch (FortKnoxException e) {

			if (e.getErrorCode().equalsIgnoreCase(
					FortKnoxExceptionConstant.NETWORK_ERROR)
					|| e.getErrorCode().equalsIgnoreCase(
							FortKnoxExceptionConstant.INVALID_RESPONSE_CODE)
					|| e.getErrorCode().equalsIgnoreCase(
							FortKnoxExceptionConstant.INTERNAL_CLIENT_ERROR)) {
				log.error(
						"InternalServerException while consuming doOneCheckMerge api, metadata: "
								+ fortKnoxRequest + "...retrying", e);
				log.error(e.getMessage());

				RetryInfo retryInfo = new RetryInfo();
				retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
				retryInfo.setExpBase(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_EXPONENTIAL_BASE);
				retryInfo
						.setWaitTime(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_REEXECUTION_WAIT_TIME);
				execResponse.setAction(retryInfo);
				execResponse.setStatus(Status.RETRY);
				
			} else {
				log.error(
						"Exception occured while consuming doOneCheckMerge api, metadata: "
								+ fortKnoxRequest, e);
				execResponse.setCompletionLog(e.getMessage());
				execResponse.setStatus(Status.FAILURE);
			}
		} catch (Exception e) {

			log.error(
					"Exception occured while consuming doOneCheckMerge api, metadata: "
							+ fortKnoxRequest, e);
			execResponse.setCompletionLog(e.getMessage());
			execResponse.setStatus(Status.FAILURE);
		}

		return execResponse;
	}

	/**
	 * This method is mainly responsible for fortknox task creation
	 * 
	 * @param fortKnoxRequest
	 * @throws FortKnoxException
	 */
	private Response mergeFortKnox(FortKnoxRequest fortKnoxRequest)
			throws FortKnoxException {

		log.debug("Calling doOneCheckMerge FortKnox Request: "
				+ fortKnoxRequest);

		if (fortKnoxRequest.getMergeType() == null) {
			return fortKnoxClient
					.doOneCheckMerge(fortKnoxRequest.getUserId(),
							fortKnoxRequest.getSdUserId(),
							fortKnoxRequest.getFcUserId(),
							fortKnoxRequest.getEmailId());
		} else if (fortKnoxRequest.getMergeType() == MergeType.SDOC) {
			return fortKnoxClient.doPartialOneCheckMerge(
					fortKnoxRequest.getUserId(), fortKnoxRequest.getSdUserId(),
					fortKnoxRequest.getEmailId(), MergeType.SDOC);
		} else if (fortKnoxRequest.getMergeType() == MergeType.FCOC) {
			return fortKnoxClient.doPartialOneCheckMerge(
					fortKnoxRequest.getUserId(), fortKnoxRequest.getFcUserId(),
					fortKnoxRequest.getEmailId(), MergeType.FCOC);
		}
		
		return null;
	}
}
