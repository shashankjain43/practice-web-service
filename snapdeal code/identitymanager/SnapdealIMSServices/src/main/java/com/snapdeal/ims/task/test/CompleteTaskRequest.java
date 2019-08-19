package com.snapdeal.ims.task.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import com.klickpay.fortknox.MergeType;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.wallet.request.WalletRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.ts.registration.TaskRequest;
@Data
public class CompleteTaskRequest implements TaskRequest, Request {
   
   // Wallet Task Request Parameters
   @NotBlank
   @Size(max = 128, message = "The field must be less than 128 characters")
   private String userId;

   @NotNull
   @Size(min=1)
   private String businessId;

   @NotBlank
   @Size(min=1)
   private String taskId;

   @Override
   public String getTaskId() {
      return taskId;
   }
   
   
   //FortKnox Task Request
/*   @NotBlank
   private String userId;*/

   @NotBlank
   private String sdUserId;

   @NotBlank
   private String fcUserId;

   @NotBlank
   @Size(max = 64, message = "The field must be less than 64 characters")
   private String emailId;

/*   @NotBlank
   @Size(min = 1)
   private String taskId;*/
   
   private MergeType mergeType;
   
   
   //User Migration Status Task Request 
/*   @NotBlank
   @Size(max = 128, message = "The field must be less than 128 characters")
   private String userId;

   @NotBlank
   @Size(max = 64, message = "The field must be less than 64 characters")
   private String emailId;
*/
   @NotNull
   private WalletUserMigrationStatus userMigrationStatus;

/*   @NotBlank
   private String taskId;*/

   @Override
   public void validate() throws ValidationException {
      GenericValidator<CompleteTaskRequest> validator = new GenericValidator<CompleteTaskRequest>();
      validator.validate(this);
   }
}
