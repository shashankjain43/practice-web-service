package com.snapdeal.ums.dao.loyalty;

import java.util.List;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;

public interface ILoyaltyUserDtlsDao
{

    LoyaltyUserDetailDO saveOrUpdateStatusChange(LoyaltyUserDetailDO loyaltyUserDetail);

    LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int loyaltyProgramID, int statusID);
    
    LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int loyaltyProgramID);
    
    List<String> getExistingLoyaltyEmailIDs(List<String> emailIDs, LoyaltyProgramDO loyaltyProgramDo);
    
    public void persist(List<LoyaltyUserDetailDO  >LoyaltyUserDetailDO );
    
    //
    // LoyaltyUserDetail updateStatus(LoyaltyUserDetail loyaltyUserDetail);

    // void grantLoyaltyEligibility(LoyaltyConstants.LOYALTY_PROGRAM
    // loyaltyProgram, Set<String> emaiIDs);
    //
    // // Neeed for Customer Supoprt
    // // int activateSnapBox(String emailID);
    //
    // // int activateSnapBox(String emailID, String verificationCode);
    //
    // String getLoyaltyStatus(String emailID);
    //
    // List<String> getExistingEmails(Collection<String> emailIDs);

}
