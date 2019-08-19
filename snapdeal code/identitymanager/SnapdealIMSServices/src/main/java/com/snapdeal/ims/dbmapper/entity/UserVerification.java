package com.snapdeal.ims.dbmapper.entity;

import com.snapdeal.ims.enums.Merchant;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;


@Data
public class UserVerification implements Serializable {

	private static final long serialVersionUID = -4593674093085200712L;
	private String code;
	private String userId;
	private Timestamp codeExpiryTime;
	private Timestamp createdTime;
	private VerificationPurpose purpose;
	private String emailId;
	private Merchant merchant;


}