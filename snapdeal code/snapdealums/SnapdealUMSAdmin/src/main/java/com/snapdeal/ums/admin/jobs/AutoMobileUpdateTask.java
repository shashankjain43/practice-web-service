/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 26-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.admin.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aerospike.client.Log;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.PaginationHelper;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.task.base.AbstractTask;
import com.snapdeal.ums.admin.task.ITaskService;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.UmsTask;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.user.UpdateUserRequest;
import com.snapdeal.ums.ext.user.UpdateUserResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserAddressService;
import com.snapdeal.ums.server.services.IUserAddressServiceInternal;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

/**
 * This is batch service to update the mobile number of the user created in last n days.
 * Mobile number is fetched from latest or default address of user.
 * 
 * @author shashank
 *
 */
public class AutoMobileUpdateTask extends AbstractTask implements ITaskPostProcessing {

	private static final Logger LOG = LoggerFactory.getLogger(AutoMobileUpdateTask.class);

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IUserServiceInternal userInternalService;

	@Autowired
	private IUserService userExtService;

	@Autowired
	private IUserAddressService addressExtService;

	@Autowired
	private IEmailServiceInternal emailServiceInternal;

	private static final String TASK_NAME = "AutoMobileUpdateTask";

	private static final String CREATED_LAST_NDAYS = "createdlastndays";

	private static final String DAYS_INTERVAL = "daysinterval";

	private static final int MAX_RESULTS = 1000;

	private int recordUpdated = 0;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		boolean taskResult = true;

		LOG.info(TASK_NAME + ": start here: " + DateUtils.getCurrentTime());

		try {
			UmsTask task = taskService.getRecurrentTaskByName(TASK_NAME);
			Date createdStart = null;
			Date createdEnd = null;
			Integer autoCreatedLastNdays = task.getUmsTaskParameterValue(CREATED_LAST_NDAYS);
			Integer daysInterval = task.getUmsTaskParameterValue(DAYS_INTERVAL);

			if (daysInterval > 0) {
				createdStart = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DATE, -autoCreatedLastNdays);
				createdEnd = DateUtils.addToDate(createdStart, Calendar.DATE, daysInterval);
			} else {
				createdEnd = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DATE, -autoCreatedLastNdays);
				createdStart = DateUtils.addToDate(createdEnd, Calendar.DATE, daysInterval);
			}

			final Date createdStartDate = createdStart;
			final Date createdEndDate = createdEnd;
			LOG.info("Start date: "+createdStartDate.toString());
			LOG.info("End date: "+createdEndDate.toString());
			
			List<Integer> users = userInternalService.getUsersCreatedWithoutMobileByDateRange(createdStartDate,
					createdEndDate);
			
			if (!CollectionUtils.isEmpty(users)) {
				LOG.info("Number of Fetced users without mobile: " + users.size());
				for (Integer userId : users) {
					try {
						// fetch mobile number from default/latest
						// address
						GetUserAddressesByUserIdRequest addrsRequest = new GetUserAddressesByUserIdRequest(
								userId);
						List<UserAddressSRO> addresses = addressExtService
								.getUserAddressesByUserId(addrsRequest).getUserAddresses();
						if (addresses != null && !addresses.isEmpty()) {
							// fetch user details
							GetUserByIdRequest request = new GetUserByIdRequest(userId);
							GetUserByIdResponse response = userExtService.getUserById(request);
							UserSRO userSRO = response.getGetUserById();

							// update mobile number
							UserAddressSRO defaultOrLatestAddress = addresses.get(0);
							userSRO.setMobile(defaultOrLatestAddress.getMobile());
							UpdateUserRequest req = new UpdateUserRequest();
							req.setUser(userSRO);
							UpdateUserResponse res = userExtService.updateUser(req);
							if (res.isSuccessful() == true) {
								LOG.info("Sucessfully updated mobile number: "
										+ defaultOrLatestAddress.getMobile() + " for user: " + userId);
								recordUpdated++;
							}
						}
					} catch (Exception ex) {
						LOG.error(TASK_NAME + ": error while updating mobile number for user: " + userId, ex);
					}
				}
			}
			doPostProcessing(taskResult);
		} catch (Exception ex) {
			taskResult = false;
			doPostProcessing(taskResult);
			LOG.error(TASK_NAME + ": fails with error:", ex);
		}
		LOG.info(TASK_NAME + ": completed successfully " + ", processing of " + recordUpdated + " users at: "
				+ DateUtils.getCurrentTime());
	}

	@Override
	public void doPostProcessing(boolean taskResult) {
		UmsTask task = taskService.getRecurrentTaskByName(TASK_NAME);
		task.setLastExecTime(new Date());
		task.setLastExecResult("Completed = " + taskResult + ", processing of " + recordUpdated + " users");
		taskService.updateTask(task);

		if (StringUtils.isNotEmpty(task.getEmailTemplate())) {
			List<String> recipients = new ArrayList<String>();
			String[] emails = task.getNotificationEmail().split(",");
			for (String email : emails) {
				recipients.add(email);
			}
			EmailMessage message = new EmailMessage(recipients, task.getEmailTemplate());
			message.addTemplateParam("task", task);
			emailServiceInternal.send(message);
		}
	}

}
