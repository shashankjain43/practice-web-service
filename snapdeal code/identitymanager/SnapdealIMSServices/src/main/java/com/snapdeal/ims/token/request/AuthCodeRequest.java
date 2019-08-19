package com.snapdeal.ims.token.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.request.AbstractRequest;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthCodeRequest extends AbstractRequest {

	private static final long serialVersionUID = 2019971677072474784L;

	@NotBlank
	private String userId;
	
	@NotBlank
	private String merchantId;
	
  
}