/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Mar 27, 2012
 *  @author khushboo
 */
package com.snapdeal.ums.admin.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.PaginationHelper;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.core.entity.Task;
import com.snapdeal.core.utils.SDUtils;
import com.snapdeal.task.base.AbstractTask;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.admin.task.ITaskService;
import com.snapdeal.ums.core.entity.UmsTask;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtBegOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtEndOfMonthRequest;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;

public class SDStatementMailerTask extends AbstractTask implements ITaskPostProcessing{

    private static final long      serialVersionUID  = 170542759662282557L;
    
    private static final String taskName = "SDStatementMailerTask";
    private static final Logger    LOG               = LoggerFactory.getLogger(SDStatementMailerTask.class);

    @Autowired
    private IEmailServiceInternal    emailServiceInternal;

    @Autowired
    private ISDWalletServiceInternal sdWalletServiceInernal;

    @Autowired
    private IUserServiceInternal     userServiceInernal;
    
    @Autowired
    private ITaskService         taskService;
    
    private final String           contentPath       = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContentPath("http://i.sdlcdn.com/");

    private final String           contextPath       = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContextPath("http://www.snapdeal.com");

    private static final int       MAX_RESULTS       = 1000;

    Date                           monthStart        = null;
    Date                           monthEnd          = null;
    Date                           currentMonthEnd   = null;
    DateRange                      lastMonthRange    = null;
    DateRange                      currentMonthRange = null;
    Date                           last3MonthsStart  = null;

    @SuppressWarnings("finally")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        boolean taskResult = true;
        try {
            LOG.info("SDStatementMailerTask - Started");
            monthStart = DateUtils.getStartOfMonth(1);
            monthEnd = DateUtils.getEndOfMonth(1);
            currentMonthEnd = DateUtils.getCurrentTime();
            lastMonthRange = new DateRange(monthStart, monthEnd);
            currentMonthRange = new DateRange(monthStart, currentMonthEnd);
            last3MonthsStart = DateUtils.getStartOfMonth(3);

            PaginationHelper<Integer> paginator = new PaginationHelper<Integer>(MAX_RESULTS) {
                protected List<Integer> moreResults(int start, int pageSize) {
                   return sdWalletServiceInernal.getAllUsersFromSDCashHistory(last3MonthsStart, start, pageSize);
                }

                protected void process(List<Integer> pageItems, int pageIndex) {

                    if (!pageItems.isEmpty()) {
                        LOG.info("Number fo distinct users : " + pageItems.size());
                        for (Integer userId : pageItems) {
                            try {
                                GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest(userId);
                                User u = userServiceInernal.getUserByIdWithoutRoles(userId);
                                LOG.info("Processing user : " + userId + " email id: " + u.getEmail());

                                GetUserSDCashAtBegOfMonthRequest getUserSDWalletAtBegOfMonthRequest = new GetUserSDCashAtBegOfMonthRequest(currentMonthRange, userId);
                                int sdcashAtBegOfMonth = sdWalletServiceInernal.getUserSDCashOnDate(currentMonthRange.getStart(), userId);
                                LOG.info("sdcashAtBegOfMonth for : " + userId + " = " + sdcashAtBegOfMonth);

                                int sdcashEarningOfMonth = sdWalletServiceInernal.getUserSDCashEarningOfMonth(lastMonthRange,userId);
                                LOG.info("sdcashEarningOfMonth for : " + userId + " = " + sdcashEarningOfMonth);

                                int sdcashUsedThisMonth = sdWalletServiceInernal.getUserSDCashUsedThisMonth(lastMonthRange, userId);
                                LOG.info("sdcashUsedThisMonth for : " + userId + " = " + sdcashUsedThisMonth);

                                int sdCashExpiredThisMonth = sdWalletServiceInernal.getUserSDWalletExpiredThisMonth(lastMonthRange, userId);
                                LOG.info("sdcashExpiredThisMonth for : " + userId + " = " + sdCashExpiredThisMonth);

                                GetUserSDCashAtEndOfMonthRequest getUserSDWalletAtEndOfMonthRequest = new GetUserSDCashAtEndOfMonthRequest(lastMonthRange, currentMonthRange, userId);
                                int sdcashAvailable = sdWalletServiceInernal.getUserSDCashOnDate(lastMonthRange.getEnd(), userId);
                                int currSDCash = sdWalletServiceInernal.getAvailableBalanceInSDWalletByUserId(userId);
                                LOG.info("sdcashAvailable for : " + userId + " = " + sdcashAvailable);

                                LOG.info("sending SD Statement to : " + u.getEmail());
                                EmailVerificationCode emailVerificationCode = userServiceInernal.createEmailVerificationCode(u.getEmail(),"Statement_mailer", null);
                                if (!u.isEmailVerified()) {
                                    emailServiceInternal.sendUserSDCashHistory(u.getEmail(), u.getFirstName(), sdcashAtBegOfMonth,
                                            sdcashEarningOfMonth, sdcashUsedThisMonth, sdcashAvailable,sdCashExpiredThisMonth, lastMonthRange, currSDCash, SDUtils.getEmailVerificationLink("resetPassword",
                                                    u.getEmail(), emailVerificationCode), contextPath, contentPath);
                                } else {
                                    emailServiceInternal.sendUserSDCashHistory(u.getEmail(), u.getDisplayName(), sdcashAtBegOfMonth,
                                            sdcashEarningOfMonth, sdcashUsedThisMonth, sdcashAvailable, sdCashExpiredThisMonth, lastMonthRange, currSDCash, SDUtils.getEmailVerificationLink("mysdcash",
                                                    u.getEmail(), emailVerificationCode), contextPath, contentPath);
                                }
                            } catch (Exception e) {
                                LOG.info("Error occured while sending sd statement mail for userId {} ", userId);
                                LOG.error("Exception stackTrace is ", e);
                            }
                        }
                    }
                }
            };
            paginator.paginate();
           
            LOG.info("SDStatementMailerTask- Completed");
        } catch (Throwable e) {
          taskResult = false;
        }

        doPostProcessing(taskResult);
    }

    @Override
    public void doPostProcessing(boolean taskResult) {
      UmsTask task =  taskService.getRecurrentTaskByName(taskName);
      task.setLastExecTime(new Date());
      task.setLastExecResult("Completed successfully = " + taskResult);
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