package com.snapdeal.ims.migration.dto;

import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.service.dto.SocialInfo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeDto implements Serializable {

   private static final long serialVersionUID = 1L;

   private String emailId;
   
   private String sdPassword;

   private String fcPassword;
   
   /**
    *  This password will be generated on the basis of upgrade request origination source,
    *  i.e. if upgrade is being requested from FC then finalOCpassword will same as fcPassword 
    *  and vice versa
    */
   private String finalOCPassword;
   
   // TODO need to take closure of mobile is verified or not on fc, if it is then what will happen
   private String mobileNumber;
   
   private String firstName;
   
   private String middleName;
   
   private String lastName;
   
   private String displayName;
   
   private Gender gender;
   
   private String dob;

   private Language languagePref;
   
   private long sdId;
   
   private long fcId;
   
   private UserStatus userStatus;

   // TODO need to keep a field name "MERCHANT" name in social_user 
   private List<SocialInfo> sdSocialSource;
   
   private List<SocialInfo> fcSocialSource;

   private UserIdentityVerifiedThrough verifiedType;

   public boolean isSocialOnSD() {
      return sdSocialSource != null && !sdSocialSource.isEmpty();
   }
   
   public boolean isSocialOnFC() {
      return fcSocialSource != null && !fcSocialSource.isEmpty();
   }
   
   public boolean isUserSocialOnBothSide() {
      return isSocialOnFC() && isSocialOnSD();
   }

   public boolean isUserSocialOnEitherSide() {
      return isSocialOnFC() || isSocialOnSD();
   }

}