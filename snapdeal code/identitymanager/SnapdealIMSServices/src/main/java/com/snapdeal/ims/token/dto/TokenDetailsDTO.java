package com.snapdeal.ims.token.dto;

import java.util.Date;

import lombok.Data;

/**
 * This details are used to create encrypted token.
 */
@Data
public class TokenDetailsDTO extends TokenMetadata {

	private static final long serialVersionUID = 2309512638537933880L;

	private String globalTokenId;
    private String clientId;
	private Date expiryTime;

}