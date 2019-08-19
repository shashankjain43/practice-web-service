package com.snapdeal.ims.otp.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.otp.types.OTPStatus;

/**
 * 
 * @author shagun
 *
 */

@Accessors
@EqualsAndHashCode(of = { "otp", "createdOn", "expiryTime", "otpStatus" })
public @Data class UserOTPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2644770846698623989L;
	private String userId;
	private String mobileNumber;

	private String email;

	private String otp;
	private String otpId;
	private int invalidAttempts;
	private int resendAttempts;
	private Date expiryTime;
	private String otpType;

	private OTPStatus otpStatus;

	private Date createdOn;
	private Date modifiedOn;
	private String clientId;
	private String token;
	private String sendOTPBy;;
	//
	// public OTPInfo() {
	// // TODO Auto-generated constructor stub
	// }
	//
	// @Override
	// public Object clone() throws CloneNotSupportedException {
	// // TODO Auto-generated method stub
	// return super.clone();
	// }
}
