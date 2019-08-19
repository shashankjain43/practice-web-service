package com.snapdeal.ims.snsClient.request;

import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.snapdeal.ims.task.common.request.validator.GenericValidator;

@Data
@Component
public class SNSTaskRequest implements TaskRequest, Request {

   @NotNull
   @Size(min = 1)
   private String taskId;

   @NotNull
   @Size(min = 1)
   private String message;

   @Override
   public void validate() throws ValidationException {
      GenericValidator<SNSTaskRequest> validator = new GenericValidator<SNSTaskRequest>();
      validator.validate(this);
   }
}
