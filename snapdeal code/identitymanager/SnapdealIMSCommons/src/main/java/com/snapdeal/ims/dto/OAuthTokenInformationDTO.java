package com.snapdeal.ims.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OAuthTokenInformationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String merchantId;
	
}
