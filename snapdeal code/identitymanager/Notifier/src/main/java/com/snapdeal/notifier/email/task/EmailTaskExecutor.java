package com.snapdeal.notifier.email.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.mail.client.model.EmailResponse;
import com.snapdeal.notifier.email.constant.NotifierConst;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.email.request.EmailRequest;
import com.snapdeal.notifier.email.template.EmailTemplateCache;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

@Component("EmailTask")
public class EmailTaskExecutor implements TaskExecutor {
   private static final Logger logger = 
            LoggerFactory.getLogger(EmailTaskExecutor.class);

   @Autowired
   private IEmailSender octaneMailSender;

   @Override
   public ExecutorResponse execute(TaskRequest request) {
      final ExecutorResponse execResponse = new ExecutorResponse();
      final EmailRequest emailRequest = (EmailRequest) request;
      try {
         if (emailRequest != null) {
            emailRequest.validate();
            EmailMessage message = emailRequest.getMessage();
            
            String templateHTMLStr = 
                     EmailTemplateCache.getInstance().getTemplate(message.getTemplateKey());
            
            EmailResponse response = 
                     octaneMailSender.send(
                              buildRequest(message),
                              templateHTMLStr,
                              message.getSubject(),
                              message.getTags()
                              );
            execResponse.setCompletionLog(response.getRequestId());
            execResponse.setStatus(Status.SUCCESS);
         }

      } catch (InternalServerException e) {
         
         logger.error("InternalServerException while sending email "
                  + "using octane client...retrying",
                  e);
         RetryInfo retryInfo = new RetryInfo();
         retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
         retryInfo.setExpBase(NotifierConst.DEFAULT_EMAIL_EXPONENTIALBASE);
         retryInfo.setWaitTime(NotifierConst.DEFAULT_EMAIL_WAIT_TIME);
         execResponse.setAction(retryInfo);
         execResponse.setStatus(Status.RETRY);
      } catch (ValidationException e) {
         
         String msg = "ValidationException while sending email";
         logger.error(msg, e);
         
         if(e.getMessage()!=null)
            execResponse.setCompletionLog(e.getMessage());
         else
            execResponse.setCompletionLog(msg);
         execResponse.setStatus(Status.FAILURE);
      } catch (Exception e) {
         
         String msg = "Exception while sending email";
         logger.error(msg, e);
         if(e.getMessage()!=null)
            execResponse.setCompletionLog(e.getMessage());
         else
            execResponse.setCompletionLog(msg);
         execResponse.setStatus(Status.FAILURE);
      } 
      return execResponse;
   }
   
   private com.snapdeal.base.notification.email.EmailMessage buildRequest(
            EmailMessage message) {

      List<String> tos = new ArrayList<String>();
      tos.addAll(message.getTo());
      com.snapdeal.base.notification.email.EmailMessage emailMessage = 
               new com.snapdeal.base.notification.email.EmailMessage(tos, 
                                                                     message.getFrom(), 
                                                                     message.getReplyTo(), 
                                                                     null);
      return emailMessage;
   }

}
