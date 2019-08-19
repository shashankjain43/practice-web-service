package com.snapdeal.ims.wallet.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.payments.ts.registration.TaskRequest;
@Data
public class UserMigrationStatusRequest implements TaskRequest, Request {
	@NotBlank
	@Size(max = 128, message = "The field must be less than 128 characters")
	private String userId;

	@NotBlank
	@Size(max = 64, message = "The field must be less than 64 characters")
	private String emailId;

	@NotNull
	private WalletUserMigrationStatus userMigrationStatus;

	@NotBlank
	private String taskId;

	@Override
	public void validate() throws ValidationException {
		GenericValidator<UserMigrationStatusRequest> validator = new GenericValidator<UserMigrationStatusRequest>();
		validator.validate(this);
	}

	@Override
	public String getTaskId() {
		return taskId;
	}
}
