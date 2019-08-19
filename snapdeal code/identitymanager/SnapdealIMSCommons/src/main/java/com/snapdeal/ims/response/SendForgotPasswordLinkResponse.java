package com.snapdeal.ims.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SendForgotPasswordLinkResponse extends AbstractResponse {
	private static final long serialVersionUID = 4697386276691898844L;

	private boolean success;
}