package com.snapdeal.admin.request;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.ims.enums.Merchant;

public @Data abstract class AbstractClientDetailsRequest {
	@NotEmpty
	private String clientName;

	@NotEmpty
	private String clientType;

	@NotEmpty
	private Merchant merchant;

	@NotEmpty
	private String clientPlatform;
	
	private String imsInternalAlias;
}
