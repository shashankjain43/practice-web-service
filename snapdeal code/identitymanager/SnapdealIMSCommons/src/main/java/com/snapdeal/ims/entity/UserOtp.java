package com.snapdeal.ims.entity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.snapdeal.ims.constants.OtpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserOtp implements Serializable {

	private static final long serialVersionUID = -3439313783078356708L;

	private String userId;
	private String email;
	private String mobileNumber;
	private String otpId;
	private int invalidAttempts;
	private int resendAttempts;
	private Timestamp expiryTime;
	private String otpType;
	private OtpStatus otpstatus;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private String clientId;
	private String token;


}



