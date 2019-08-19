package com.snapdeal.ims.token.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This details are used to create encrypted token.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccessTokenDetailsDTO extends TokenMetadata {

	private static final long serialVersionUID = 2309512638537933880L;

	private String globalTokenId;
	private String merhchantId;
	private Date expiryTime;
	private Long expiry;

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
		this.expiry = this.expiryTime.getTime();
	}

	public void setExpiry(Long expiry) {
		this.expiry = expiry;
		this.expiryTime = new Date(this.expiry);
	}

}