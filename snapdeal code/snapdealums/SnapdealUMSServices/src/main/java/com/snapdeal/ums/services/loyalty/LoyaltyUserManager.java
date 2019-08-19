package com.snapdeal.ums.services.loyalty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;
import com.snapdeal.ums.core.entity.loyalty.SnapBoxVerifyActivateReqBO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserDtlsDao;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.utils.UMSStringUtils;

@Service
public class LoyaltyUserManager
{

    @Autowired
    private ILoyaltyUserDtlsDao loyaltyUserDtlsDao;

    /**
     * @param emailID
     * @param loyaltyProgram_SNAPBOX
     * @param loyaltyProgramStatus_ELIGIBLE
     * @return
     */
    public LoyaltyUserDetailDO fetchLoyaltyUserDetail(String emailID, LoyaltyProgramDO loyaltyProgram_SNAPBOX,
        LoyaltyProgramStatusDO loyaltyProgramStatus_ELIGIBLE)
    {

        LoyaltyUserDetailDO loyaltyUserDetail = loyaltyUserDtlsDao
            .getLoyaltyUserDtl(emailID,
                loyaltyProgram_SNAPBOX.getId(), loyaltyProgramStatus_ELIGIBLE.getId());
        return loyaltyUserDetail;
    }

    // public SnapBoxVerificationActivationRqBO
    // formSnapBoxVerificationActivationRqBO(SnapBoxActivationRequest request)
    // {
    // if(request==null){
    // return null;
    // }
    //
    // String emailID = request.getRequestedFromEmailID();
    // if (UMSStringUtils.isNullOrEmpty(emailID))
    // {
    // return null;
    // }
    // SnapBoxVerificationActivationRqBO snapBoxVerificationActivationRqBO = new
    // SnapBoxVerificationActivationRqBO();
    //
    // snapBoxVerificationActivationRqBO.setRecipientEmailID(emailID);
    // snapBoxVerificationActivationRqBO.setRequestedFromEmailID(emailID);
    // return snapBoxVerificationActivationRqBO;
    // }
    //
    //

}