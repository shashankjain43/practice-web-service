package com.snapdeal.notifier.email.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.validator.GenericValidator;
import com.snapdeal.payments.ts.registration.TaskRequest;

@Data
public class EmailRequest implements TaskRequest, Request {
   @NotNull
   private EmailMessage message;
   
   @NotBlank
   private String taskId;
   
   @NotBlank
   private String subject;

   private Map<String, String> tags;
   
   @Override
   public String getTaskId() {
      return taskId;
   }

   @Override
   public void validate() throws ValidationException {
      GenericValidator<EmailRequest> validator = new GenericValidator<EmailRequest>();
      validator.validate(this);
   }
}
