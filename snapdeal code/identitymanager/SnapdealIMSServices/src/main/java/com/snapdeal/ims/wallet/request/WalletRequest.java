package com.snapdeal.ims.wallet.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import org.springframework.stereotype.Component;

import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.payments.ts.registration.TaskRequest;

@Data
@Component
public class WalletRequest implements TaskRequest, Request {

   @NotNull
   @Size(min=1)
   private String userId;

   @NotNull
   @Size(min=1)
   private String businessId;

   @NotNull
   @Size(min=1)
   private String taskId;

   @Override
   public String getTaskId() {
      return taskId;
   }

   @Override
   public void validate() throws ValidationException {
      GenericValidator<WalletRequest> validator = new GenericValidator<WalletRequest>();
      validator.validate(this);
   }
}
