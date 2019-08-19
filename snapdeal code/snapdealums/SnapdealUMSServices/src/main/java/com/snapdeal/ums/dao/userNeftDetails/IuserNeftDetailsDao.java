package com.snapdeal.ums.dao.userNeftDetails;

import java.util.Date;

import org.hibernate.ObjectNotFoundException;

import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;
import com.snapdeal.ums.core.entity.UserNeftDetailsDO;

public interface IuserNeftDetailsDao
{
    
    public UserNeftDetailsDO fetchUserNeftDetailsByID(int userNEFTDetailsID);

    public UserNeftDetailsDO addActiveNeftDetails(UserNeftDetailsDO userNeftDetails);

    public void deactivateUserNeftDetails(int neftDetailsID)  throws ObjectNotFoundException;

    public UserNeftDetailsDO getActiveNeftDetails(String email);

    public void verifyActivateExistingUserNEFTDetails(int neftDetailsID, Date lastVerifiedTime);

    /**
     * Fetches UserNeftDetails with exact match to the attributes in the
     * request. It does not matter if the config is active or not.
     * 
     * @param email
     * @param ifscCode
     * @param branch
     * @param bankName
     * @param accountNo
     * @return
     */
    public UserNeftDetailsDO fetchUserNeftDetails(String name, String email,
        String ifscCode, String branch, String bankName, String accountNo);

    /**
     * Updates isActive status for all the neft details of the user.
     * 
     * @param email
     */
    public int deactivateAllActiveNeftDetails(String email);
    
//    public LoyaltyUserDetailDO saveOrUpdateStatusChange(LoyaltyUserDetailDO loyaltyUserDetail);


}
