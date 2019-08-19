package com.snapdeal.ims.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccessTokenInformationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accessToken;
	
	private String globalToken;
	
	private String globalTokenExpiry;
	
	private String accessTokenExpiry;
	
}
