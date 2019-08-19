package com.snapdeal.ims.mojoClient.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.Request;
import com.snapdeal.ims.task.common.request.validator.GenericValidator;
import com.snapdeal.payments.ts.registration.TaskRequest;

import lombok.Data;


@Data
@Component
public class MojoRequest implements TaskRequest, Request{

	@NotBlank
	private String userId;
	
	@NotBlank
	private String mobileNumber;
	
	@NotBlank
	@Size(min = 1)
	private String taskId;
	
	@Override
	public void validate() throws ValidationException {
		GenericValidator<MojoRequest> validator = new GenericValidator<MojoRequest>();
		validator.validate(this);
		
	}
	@Override
	public String getTaskId() {
		return taskId;
	}
}
