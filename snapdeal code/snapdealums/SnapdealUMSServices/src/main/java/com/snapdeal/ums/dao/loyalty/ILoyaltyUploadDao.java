package com.snapdeal.ums.dao.loyalty;

import com.snapdeal.ums.core.entity.LoyaltyUploadDO;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyUploadedFileStatus;

public interface ILoyaltyUploadDao
{

    public LoyaltyUploadDO save(LoyaltyUploadDO loyaltyUploadDO);
    public LoyaltyUploadDO updateStatus(int id, LoyaltyUploadedFileStatus status);

}
