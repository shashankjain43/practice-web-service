package com.snapdeal.ims.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.validator.annotation.Email;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(alphabetic = true)
public class SendForgotPasswordLinkRequest extends AbstractRequest {
	private static final long serialVersionUID = 8520848734575597682L;
	
	@Email
	private String email;

}
