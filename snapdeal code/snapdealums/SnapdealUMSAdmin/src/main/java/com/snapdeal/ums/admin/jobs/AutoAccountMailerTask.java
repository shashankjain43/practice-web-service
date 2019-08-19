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
import org.springframework.util.CollectionUtils;

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
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;

public class AutoAccountMailerTask extends AbstractTask implements ITaskPostProcessing {

    private static final Logger   LOG                            = LoggerFactory.getLogger(AutoAccountMailerTask.class);

    @Autowired
    private ITaskService          taskService;

    @Autowired
    private IEmailServiceInternal emailServiceInternal;

    @Autowired
    private IUserServiceInternal  userInternalService;

    private static final String   taskName                       = "AutoAccountMailerTask";

    private static final String   AUTOCREATED_LAST_NDAYS         = "autocreatedlastndays";

    private static final String   DAYS_INTERVAL                  = "daysinterval";

    private static final String   AUTOCREATED_NOTIFICATION_COUNT = "autocreatedNotificationCount";

    private static final int      MAX_RESULTS                    = 1000;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        boolean taskResult = true;
        LOG.info("AutoAccountMailerTask: start here: " + DateUtils.getCurrentTime());

        try {
            UmsTask task = taskService.getRecurrentTaskByName(taskName);
            Date createdStart = null;
            Date createdEnd = null;
            Integer autoCreatedLastNdays = task.getUmsTaskParameterValue(AUTOCREATED_LAST_NDAYS);
            Integer daysInterval = task.getUmsTaskParameterValue(DAYS_INTERVAL);
            final Integer autocreatedNotificationCount = task.getUmsTaskParameterValue(AUTOCREATED_NOTIFICATION_COUNT);

            if (daysInterval > 0) {
                createdStart = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DATE, -autoCreatedLastNdays);
                createdEnd = DateUtils.addToDate(createdStart, Calendar.DATE, daysInterval);
            } else {
                createdEnd = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DATE, -autoCreatedLastNdays);
                createdStart = DateUtils.addToDate(createdEnd, Calendar.DATE, daysInterval);
            }

            final Date createdStartDate = createdStart;
            final Date createdEndDate = createdEnd;

            new PaginationHelper<User>(MAX_RESULTS) {

                @Override
                protected List<User> moreResults(int start, int pageSize) {
                    try {
                        return userInternalService.getAutoCreatedUnverifiedUsers(pageSize, createdStartDate, createdEndDate, autocreatedNotificationCount);
                    } catch (Exception e) {
                        LOG.error("AutoAccountMailerTask: error while fetching AutocreatedUnverified Users:", e);
                    }
                    return null;
                }

                @Override
                protected void process(List<User> users, int pageIndex) {
                    int count = 0;
                    if (!CollectionUtils.isEmpty(users)) {
                        for (User user : users) {
                            try {
                                count++;
                                // if emailverification code already exist in memcache then, retrieving the verification code, removing it 
                                // and then again inserting back the same, so increasing the TTL. (so that a earlier confirmation mail should not get expired before 10 days)
                                EmailVerificationCode emailVerificationCode = userInternalService.getEmailVerificationCode(user.getEmail());
                                if (emailVerificationCode != null) {
                                    userInternalService.clearEmailVerificationCode(user.getEmail());
                                    userInternalService.putEmailVerificationCode(emailVerificationCode, user.getEmail());
                                } else {
                                    emailVerificationCode = userInternalService.createEmailVerificationCode(user.getEmail(), "AutoAccountMailerTask", null);
                                }
                                sendAutoCreatedMail(user.getFirstName(), user.getEmail(), emailVerificationCode);
                                LOG.info("AutoAccountMailerTask: successfully sent confirmation mail for email: " + user.getEmail());
                                userInternalService.incrementAutocreatedNotificationCount(user.getEmail());
                            } catch (Exception ex) {
                                LOG.error("AutoAccountMailerTask: error while sending email to emailId: ", ex);
                            }
                        }
                    }
                    LOG.info("AutoAccountMailerTask: Page: {},Number of confirmation emails send : {}", pageIndex, count);
                }
            }.paginate();
            doPostProcessing(taskResult);
        } catch (Exception ex) {
            taskResult = false;
            doPostProcessing(taskResult);
            LOG.error("AutoAccountMailerTask: fails with error:", ex);
        }
        LOG.info("AutoAccountMailerTask: completed successfully at: " + DateUtils.getCurrentTime());
    }

    @Override
    public void doPostProcessing(boolean taskResult) {
        UmsTask task = taskService.getRecurrentTaskByName(taskName);
        task.setLastExecTime(new Date());
        task.setLastExecResult("Completed = " + taskResult);
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

    private void sendAutoCreatedMail(String name, String email, EmailVerificationCode emailVerificationCode) {
        String contextPath = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContextPath("");
        String contentPath = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContentPath("");
        String emailVerificationLink = userInternalService.getEmailVerificationLink("resetPassword", email, emailVerificationCode);
        emailServiceInternal.reSendAutoAccountConfirmationEmail(email, name, contextPath, contentPath, emailVerificationLink);
    }
}
