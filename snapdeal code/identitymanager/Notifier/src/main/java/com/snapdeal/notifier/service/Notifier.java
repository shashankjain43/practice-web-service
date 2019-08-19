package com.snapdeal.notifier.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.email.service.IEmailService;
import com.snapdeal.notifier.email.template.EmailTemplateCache;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.sms.request.SMSRequest;
import com.snapdeal.notifier.sms.service.ISMSService;
import com.snapdeal.sms.client.model.SmsSenderResponse;

@Component("notifierService")
public class Notifier {
   @Autowired
   private ISMSService smsService;
   
   @Autowired
   private IEmailService emailService;
   
   public SmsSenderResponse sendSMS(SMSRequest smsRequest) 
            throws ValidationException,
                   InternalServerException{
      
     return smsService.sendSMS(smsRequest);
   }
   
   public EmailResponse sendEmail(EmailMessage message, boolean transactional)
            throws ValidationException,
                   InternalServerException{
      
      return emailService.sendEmail(message, transactional);
   }

   /**
    * This method will be used to cache all email Templates.
    * User have to call regiterTemplate before using email
    * application
    */
   public void registerOrRefreshEmailTemplate(Map<String, String> emailTemplates){
      EmailTemplateCache.getInstance().registerTemplates(emailTemplates);
   }
}
