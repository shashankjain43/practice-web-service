package com.snapdeal.ims.notifierClient;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.fcNotifier.request.EmailMessage;
import com.snapdeal.fcNotifier.utils.ClientDetails;
import com.snapdeal.notifier.email.template.EmailTemplateCache;
import com.snapdeal.notifier.exception.ValidationException;

@Getter
@Setter
public class FCNotifier {

   private String host;
   private String port;
   private int apiTimeout;
   
      
   public void init() {
      try {
         ClientDetails.init(this.host, this.port,this.apiTimeout);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void destroyApplicationVariables() {}
   
   public static EmailMessage getFcNotifierEmailMessage(com.snapdeal.notifier.email.request.EmailMessage emailMessage) throws ValidationException{
      if(emailMessage==null){
         return null;
      }
      
      EmailTemplateCache emailTemplateCache = EmailTemplateCache.getInstance();
      
      EmailMessage fcNotifierEmailMessage = new EmailMessage();
      fcNotifierEmailMessage.setCc(emailMessage.getCc());
      fcNotifierEmailMessage.setFrom(emailMessage.getFrom());
      fcNotifierEmailMessage.setReplyTo(emailMessage.getReplyTo());
      fcNotifierEmailMessage.setRequestId(emailMessage.getRequestId());
      fcNotifierEmailMessage.setSubject(emailMessage.getSubject());
      fcNotifierEmailMessage.setTags(emailMessage.getTags());
      fcNotifierEmailMessage.setTaskId(emailMessage.getTaskId());
      fcNotifierEmailMessage.setTemplateKey(emailMessage.getTemplateKey());
      fcNotifierEmailMessage.setTo(emailMessage.getTo());
      
      return fcNotifierEmailMessage;
   }
}
