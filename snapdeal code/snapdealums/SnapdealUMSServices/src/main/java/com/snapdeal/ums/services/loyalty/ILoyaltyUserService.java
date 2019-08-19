package com.snapdeal.ums.services.loyalty;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;

public interface ILoyaltyUserService
{

    /**
     * Activates loyalty
     * 
     * @param request
     * @return
     * @throws TransportException
     */
    public SnapBoxActivationResponse verifyActivateSnapBox(
        SnapBoxVerificationActivationRequest request);

    public SnapBoxActivationResponse activateSnapBox(SnapBoxActivationRequest request);

    public LoyaltyUserStatusResponse getLoyaltyStatus(LoyaltyUserStatusRequest statusRequest);

}
