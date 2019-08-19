package com.snapdeal.ims.token.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RefreshTokenDetailsDTO extends TokenMetadata {

	private static final long serialVersionUID = -4537054300926225003L;
	private String merchantId;
	
	private String globalTokenId;
	private String machineID;
	private String userAgent;
	private String userId;

	// This is not used for creating token, this is a separate data which marks
	// the validity of a token. This is used in renewal flow and sign-out.
	private Date expiryTime;
	
}