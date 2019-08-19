package com.snapdeal.ims.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.LoginUserRequestValidation;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude={"password"})
@LoginUserRequestValidation
@JsonPropertyOrder(alphabetic=true)
public class LoginUserRequest extends AbstractRequest {
   
	private static final long serialVersionUID = -5862035945866369326L;

	private String mobileNumber;
	
	
	@Email(mandatory = false)
	private String emailId;
	
	private String password;
}