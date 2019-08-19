package com.snapdeal.ims.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.enums.AccountOwner;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;

/**
 * User details.
 */
@Getter
@Setter
@ToString(exclude = { "createdTime", "walletStatus", "firstName", "middleName",
		"lastName", "displayName", "gender", "dob", "languagePref","emailId" })
public class UserDetailsDTO implements Serializable {

	private static final long serialVersionUID = 7436707420270236370L;
	private AccountOwner accountOwner;
	private String emailId;
	private String userId;
	private int sdUserId;
	private int fcUserId;
	private String mobileNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String displayName;
	private Gender gender;
	private String dob;
	private Language languagePref;
	private boolean mobileVerified;
	private boolean emailVerified;
	private String fbSocialId;
	private String googleSocialId;
	private String accountState;
	private Timestamp createdTime;
	private boolean enabledState;
	private String walletStatus;
	private boolean mobileOnly;
}