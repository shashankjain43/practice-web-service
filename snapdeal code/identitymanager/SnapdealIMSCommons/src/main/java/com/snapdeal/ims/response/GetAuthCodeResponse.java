package com.snapdeal.ims.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetAuthCodeResponse extends AbstractResponse {
	
	private static final long serialVersionUID = 1L;
	
	private String authCode;

}
