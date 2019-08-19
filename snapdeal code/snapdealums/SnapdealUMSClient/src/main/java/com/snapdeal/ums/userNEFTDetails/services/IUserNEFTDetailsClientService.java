package com.snapdeal.ums.userNEFTDetails.services;

import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsResponse;

/**
 * Client to avail user NEFT details related services.
 * 
 * @author ashish
 * 
 */
public interface IUserNEFTDetailsClientService
{

    public void setWebServiceBaseURL(String webServiceBaseURL);

    /**
     * Adds/updates (if all the details already exists!) and verifies user neft
     * details in the request.
     */
    public AddUserNEFTDetailsResponse addVerifyActivateUserNEFTDetails(AddUserNEFTDetailsRequest request);

    /**
     * Verifies the user NEFT details record as represented by the ID in the
     * request.
     */
    public VerifyUserNEFTDetailsResponse verifyActivateExistingUserNEFTDetails(
        VerifyUserNEFTDetailsRequest request);

    /**
     * Returns active user NEFT details, if available for the email ID in the
     * request.
     */
    public GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetails(
        GetActiveUserNEFTDetailsRequest request);

    /**
     * De-activates the user NEFT details as represented by the ID the in
     * request.
     */
    public DeactivateUserNEFTDetailsResponse deActivateUserNEFTDetails(
        DeactivateUserNEFTDetailsRequest request);
}
