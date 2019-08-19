package com.snapdeal.ims.service.dto;

import com.snapdeal.ims.enums.AccountOwner;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1657574942906425004L;
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

   List<SocialInfo> socialInfo;
	// Social Details.
   // private String socialId;
   // private String aboutMe;
   // private String photoURL;
}