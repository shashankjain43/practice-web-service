package com.snapdeal.ums.services.userNeftDetails;

import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsResponse;

public interface IUserNEFTDetailsService
{

    public VerifyUserNEFTDetailsResponse verifyActivateExistingUserNEFTDetails(
        VerifyUserNEFTDetailsRequest verifyUserNEFTDetailsRequest);

    public AddUserNEFTDetailsResponse addVerifyActivateUserNEFTDetails(
        AddUserNEFTDetailsRequest addUserNEFTDetailsRequest);

    public GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetails(
        GetActiveUserNEFTDetailsRequest getActiveUserNEFTDetailsRequest);

    public DeactivateUserNEFTDetailsResponse deActivateUserNEFTDetails(
        DeactivateUserNEFTDetailsRequest deactivateUserNEFTDetailsRequest);
}
