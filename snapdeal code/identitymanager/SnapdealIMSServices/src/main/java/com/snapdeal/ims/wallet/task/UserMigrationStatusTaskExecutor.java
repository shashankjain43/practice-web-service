package com.snapdeal.ims.wallet.task;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.wallet.request.UserMigrationStatusRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalErrorException;
import com.snapdeal.payments.sdmoney.exceptions.ValidationException;
import com.snapdeal.payments.sdmoney.service.model.UpdateUserMigrationStatusRequest;
import com.snapdeal.payments.sdmoney.service.model.type.UserMigrationStatus;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component("UserMigrationStatusTask")
@Slf4j
public class UserMigrationStatusTaskExecutor
		implements
			TaskExecutor<TaskRequest> {

	@Autowired
	private IUserDao userDaoImpl;

	@Autowired
	private SDMoneyClient sdMoneyServiceClient;

	@Override
	public ExecutorResponse execute(TaskRequest request) {

		final ExecutorResponse execResponse = new ExecutorResponse();
		final UserMigrationStatusRequest userMigrationStatusRequest = (UserMigrationStatusRequest) request;

		try {

			// Step0: Validate the request
			userMigrationStatusRequest.validate();

			/*
			 * Step1: Create wallet Even if Wallet Already Exists for given
			 * user, wallet service de-dupe the request and gives 200 response.
			 * So, we don't require any extra check here to take care
			 * idempotency
			 */
			notifyWalletforUserMigrationStatusChange(userMigrationStatusRequest);

			execResponse.setCompletionLog(userMigrationStatusRequest
					.getUserId());
			execResponse.setStatus(Status.SUCCESS);
		} catch (InternalErrorException e) {

			log.error("InternalServerException while notifying wallet about"
					+ "user migration status change, metadata: "
					+ userMigrationStatusRequest + "...retrying", e);

			RetryInfo retryInfo = new RetryInfo();
			retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
			retryInfo
					.setExpBase(ServiceCommonConstants.WALLET_EXPONENTIAL_BASE);
			retryInfo
					.setWaitTime(ServiceCommonConstants.DEFAULT_ON_FAILURE_TASK_REEXECUTION_WAIT_TIME);
			execResponse.setAction(retryInfo);
			execResponse.setStatus(Status.RETRY);
		} catch (ValidationException e) {

			log.error("ValidationExceptionwhile notifying wallet about"
					+ "user migration status change, metadata: "
					+ userMigrationStatusRequest, e);
			execResponse.setCompletionLog(e.getMessage());
			execResponse.setStatus(Status.FAILURE);
		} catch (Exception e) {

			log.error("Exception occured while creating wallet, metadata: "
					+ userMigrationStatusRequest, e);
			execResponse.setCompletionLog(e.getMessage());
			execResponse.setStatus(Status.FAILURE);
		}

		return execResponse;
	}

	/**
	 * this method is responsible for notifying wallet that user migration
	 * status has been changed.
	 * 
	 * @param UserMigrationStatusRequest
	 */
	private void notifyWalletforUserMigrationStatusChange(
			UserMigrationStatusRequest userMigrationStatusRequest) {

		final UpdateUserMigrationStatusRequest updateUserMigrationStatusRequest = new UpdateUserMigrationStatusRequest();

		updateUserMigrationStatusRequest.setEmailId(userMigrationStatusRequest
				.getEmailId());
		updateUserMigrationStatusRequest
				.setSdIdentity(userMigrationStatusRequest.getUserId());

		WalletUserMigrationStatus status = userMigrationStatusRequest
				.getUserMigrationStatus();
		if (status != null)
			updateUserMigrationStatusRequest
					.setUserMigrationStatus(UserMigrationStatus.valueOf(status
							.toString()));

		log.debug("Calling notifyWallet for user Migration status change Request: "
				+ updateUserMigrationStatusRequest);

		sdMoneyServiceClient
				.updateUserMigrationStatus(updateUserMigrationStatusRequest);

		log.debug("notified SdMoney for user mIgration Status "
				+ updateUserMigrationStatusRequest);

	}
}
