package com.snapdeal.ims.request;

import org.hibernate.validator.constraints.NotEmpty;
import lombok.Data;

public @Data abstract class AbstractClientDetailsRequest {
	@NotEmpty
	private String clientName;

	@NotEmpty
	private String clientType;

	@NotEmpty
	private String merchant;
	
	@NotEmpty
	private String clientPlatform;
}
