package com.snapdeal.ims.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetOAuthTokenDetailsResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String merchantId;

}
