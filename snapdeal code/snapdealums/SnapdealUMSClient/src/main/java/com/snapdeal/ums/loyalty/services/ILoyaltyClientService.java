package com.snapdeal.ums.loyalty.services;

import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;

/**
 * This is the interface for the clients to interact with SD loyalty service .
 * It has APIs to activate loyalty program and get status
 * 
 * 
 * ashish.saxena@snapdeal.com
 */
public interface ILoyaltyClientService
{
    public void setWebServiceBaseURL(String webServiceBaseURL);

    /**
     * Activates SNAPBOX if the email in the request is eligible. Ony the email
     * ID of the user is required in the request
     * 
     * NOTE: To be used when user "has already logged in" to his account.
     * Verification of activation code will be skipped.
     * 
     * 
     * @param request
     * @return
     */
    public SnapBoxActivationResponse activateSnapBox(
            SnapBoxActivationRequest request);

    /**
     * Activates SNAPBOX if the email in the request is eligible. All the fields
     * of the request needs to be furnished for proper processing of the
     * request.
     * 
     * @param request
     * @return
     */
    public SnapBoxActivationResponse verifyAndActivateSnapBox(
            SnapBoxVerificationActivationRequest request);

    /**
     * 
     * Gets the status of the user based on his emailID and the loyalty program
     * in the request
     * 
     * @param request
     * @return
     */
    public LoyaltyUserStatusResponse getLoyaltyStatus(
            LoyaltyUserStatusRequest request);
}
