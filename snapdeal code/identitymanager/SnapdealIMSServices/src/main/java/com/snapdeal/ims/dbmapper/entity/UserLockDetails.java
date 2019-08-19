package com.snapdeal.ims.dbmapper.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLockDetails {
	private String userId;

	private Date expiryTime;
	
	private Date createdTime;

	private int loginAttempts;

	private String status;
}
