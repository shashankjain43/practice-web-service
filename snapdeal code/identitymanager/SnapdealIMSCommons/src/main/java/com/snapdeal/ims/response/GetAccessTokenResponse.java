package com.snapdeal.ims.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetAccessTokenResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	private String accessToken;
	
	private String accessTokenExpiry;
	
	private String refreshToken;
	
	private String refreshTOkenExpiry;
	
}
