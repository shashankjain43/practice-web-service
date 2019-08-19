package com.snapdeal.ims.fortknox.request;

import javax.validation.constraints.Size;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.klickpay.fortknox.MergeType;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.payments.ts.registration.TaskRequest;

@Data
@Component
public class FortKnoxRequest implements TaskRequest, Request {

	@NotBlank
	private String userId;

	@NotBlank
	private String sdUserId;

	@NotBlank
	private String fcUserId;

	@Email
	private String emailId;

	@NotBlank
	@Size(min = 1)
	private String taskId;
	
	private MergeType mergeType;

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public void validate() throws ValidationException {
		GenericValidator<FortKnoxRequest> validator = new GenericValidator<FortKnoxRequest>();
		validator.validate(this);
	}
}
