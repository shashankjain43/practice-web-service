package com.snapdeal.ims.Notifier.Emailsender.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.payments.ts.registration.TaskRequest;

import lombok.Data;
@Data
@Component
/*
 * Radhika 
 */
public class NotifierEmailSendingRequest implements TaskRequest, Request
{
	
	EmailNotifierRequest fcNotifierEmailRequest;

	@NotBlank
	@Size(min = 1)
	private String taskId;

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public void validate() throws ValidationException {
		GenericValidator<NotifierEmailSendingRequest> validator = new GenericValidator<NotifierEmailSendingRequest>();
		validator.validate(this);
	}
}
