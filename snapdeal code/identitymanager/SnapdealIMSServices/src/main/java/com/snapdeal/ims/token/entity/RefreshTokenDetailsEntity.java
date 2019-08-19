package com.snapdeal.ims.token.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class RefreshTokenDetailsEntity extends GlobalTokenDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1981385536868792757L;
	
	private String merchantId;

}