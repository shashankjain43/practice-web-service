package com.snapdeal.ims.dbmapper.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.enums.UserStatus;

@Data
@EqualsAndHashCode(exclude = {"createdTime"})
public class User implements Serializable {

   private static final long serialVersionUID = -3439313783078356708L;

   private String userId;

   // Integer value due to data type in com.freecharge.umsclient.response.vo.User and com.snapdeal.ums.core.sro.user
   private Integer sdUserId;

   // Integer value due to data type in com.freecharge.umsclient.response.vo.User and com.snapdeal.ums.core.sro.user
   private Integer fcUserId;

   // Integer value due to data type in com.freecharge.umsclient.response.vo.User and com.snapdeal.ums.core.sro.user
   private Integer sdFcUserId;

   private Merchant originatingSrc;

   private boolean enabled;

   private String emailId;

   private String password;

   private boolean userSetPassword;

   private String mobileNumber;

   private UserStatus status;

   private boolean googleUser;

   private boolean facebookUser;

   private boolean emailVerified;

   private boolean mobileVerified;

   private String firstName;

   private String middleName;

   private String lastName;

   private String displayName;

   private Gender gender;

   private Date dob;

   private Language languagePref;

   private String purpose;
   
   private CreateWalletStatus walletStatus;

   private Timestamp createdTime;

   private Timestamp updatedTime;
   
   private Platform platform;
   
   private String resource;
   
   private boolean mobileOnly;
}
