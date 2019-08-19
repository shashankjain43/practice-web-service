package com.snapdeal.ims.token.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * This class holds the common token data, which are not directly related to
 * user information used in token.
 * 
 */
@Data
public abstract class TokenMetadata implements Serializable{

	private static final long serialVersionUID = -7546369793697933664L;

	private String tokenGenerationServiceVersion;
}
