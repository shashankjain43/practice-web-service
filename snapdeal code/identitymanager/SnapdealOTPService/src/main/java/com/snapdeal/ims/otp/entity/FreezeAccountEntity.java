package com.snapdeal.ims.otp.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author shagun
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class FreezeAccountEntity {

	private String userId;
	private String otpType;
	private Date expiryTime;
	private String freezeReason;
	private String isdeleted;
}
