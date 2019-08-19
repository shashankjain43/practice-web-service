package com.snapdeal.ims.token.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TokenDetailsEntity implements Serializable {

	private static final long serialVersionUID = -4476852098321724960L;

	private String token;

	private String clientId;

	private String globalTokenId;

	private Date expiryTime;

	private Date createdTime;
}