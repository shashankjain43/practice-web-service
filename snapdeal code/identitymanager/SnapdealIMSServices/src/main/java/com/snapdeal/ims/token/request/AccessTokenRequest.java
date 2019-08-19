package com.snapdeal.ims.token.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.request.AbstractRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccessTokenRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String authCode;

	@NotBlank
	private String merchantId;

}
