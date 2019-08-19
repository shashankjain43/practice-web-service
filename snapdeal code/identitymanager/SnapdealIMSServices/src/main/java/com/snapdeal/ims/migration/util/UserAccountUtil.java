package com.snapdeal.ims.migration.util;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.migration.dto.UpgradeDto;

public interface UserAccountUtil {

   /**
    * Matches whether the user has same password at both ends i.e. FC and SD
    * 
    * @param originatingSourceEmail
    * @return
    */
   public boolean isPasswordMatched(String originatingSourceEmail);

   /**
    * This method will get the user details from both side(FC and SD)
    * and merge the details into one. If a value is not null at both
    * sides then we will set the value as per the preference. Preference
    * would depend on the upgrade request origination source i.e. if upgrade
    * request has been initiated by SD then will prefer SD value over FC and
    * vice versa.
    * 
    * @param originatingSource
    * @param upgradeSource 
    * @param sdUser
    * @param fcUser
    * @return
    */
   public UpgradeDto getMergedUserDetails(String email, State currentAccountExistence,
            UserIdentityVerifiedThrough userIdentityVerifiedThrough, String mobileNumber,
            Merchant originatingSource, UpgradeSource upgradeSource);

   /**
    * This method will create the user in IMS side
    * 
    * @param upgradeDto
    */
   public void createUser(UpgradeDto upgradeDto);

   public boolean isSocialLogin(UserIdentityVerifiedThrough userIdentityVerifiedThrough);

}