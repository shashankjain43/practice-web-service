package com.snapdeal.ims.response;

import lombok.Data;

@Data
public class ResendEmailVerificationLinkResponse extends AbstractResponse {

	private static final long serialVersionUID = 2407455436983264229L;
	private boolean success;

}
