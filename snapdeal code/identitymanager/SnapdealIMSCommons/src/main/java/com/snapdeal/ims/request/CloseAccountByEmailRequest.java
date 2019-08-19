package com.snapdeal.ims.request;

import com.snapdeal.ims.validator.annotation.Email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class CloseAccountByEmailRequest extends AbstractRequest {
	private static final long serialVersionUID = 1L;

	@Email
	private String emailId;

}
