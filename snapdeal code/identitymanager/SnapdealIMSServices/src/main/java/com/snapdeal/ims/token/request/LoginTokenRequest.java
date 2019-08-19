package com.snapdeal.ims.token.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.validator.annotation.Email;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginTokenRequest extends AbstractRequest {

	private static final long serialVersionUID = 2019971677072474784L;

	@NotBlank
	private String userId;
	
	@NotBlank
	private String clientId;
	
	@Email(mandatory = false)
   private String emailId;

   // this flag is default to false
   // when user upgrade status is link state, set this attribute as true.
   private boolean upgradeFlow;
}