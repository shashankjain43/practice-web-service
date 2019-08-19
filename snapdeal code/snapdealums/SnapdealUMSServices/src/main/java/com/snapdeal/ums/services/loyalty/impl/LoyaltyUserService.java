package com.snapdeal.ums.services.loyalty.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramDao;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramStatusDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.services.loyalty.ILoyaltyUserService;
import com.snapdeal.ums.services.loyalty.LoyaltyProgramStatusManager;
import com.snapdeal.ums.services.loyalty.snapBox.impl.SnapBoxService;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * Handles loyalty related service requests - As of now, 21-April-2014,only
 * SNAPBOX is supported.
 * 
 * @author ashish
 * 
 */
@Service
public class LoyaltyUserService implements ILoyaltyUserService

{

    private static final Logger LOG = LoggerFactory.getLogger(LoyaltyUserService.class);

    @Autowired
    private LoyaltyProgramStatusManager loyaltyProgramStatusManager;

    @Autowired
    private ILoyaltyProgramDao loyaltyProgramDao;

    @Autowired
    private ILoyaltyProgramStatusDao loyaltyProgramStatusDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private SnapBoxService snapBoxService;

    /**
     * @param activationResponse
     * @param errors
     * @param emailID
     * @param encryptedVerificationCodeInReq
     */
    @Override
    public SnapBoxActivationResponse verifyActivateSnapBox(SnapBoxVerificationActivationRequest request)
    {

        return snapBoxService.verifyActivateSnapBox(request);
    }

    /**
     * @param activationResponse
     * @param errors
     * @param emailID
     * @param encryptedVerificationCodeInReq
     */
    @Override
    public SnapBoxActivationResponse activateSnapBox(SnapBoxActivationRequest request)
    {

        return snapBoxService.activateSnapBox(request);
    }

   
    @Override
    public LoyaltyUserStatusResponse getLoyaltyStatus(LoyaltyUserStatusRequest statusRequest)
    {

        LoyaltyUserStatusResponse loyaltyUserStatusResponse = new LoyaltyUserStatusResponse();
        LoyaltyProgram loyaltyProgram = null;
        try {
            if (statusRequest == null || statusRequest.getLoyaltyProgram() == null
                || UMSStringUtils.isNullOrEmpty(statusRequest.getUserEmailID())) {
                loyaltyUserStatusResponse.setSuccessful(false);
                loyaltyUserStatusResponse = (LoyaltyUserStatusResponse) validationService.addValidationError(
                    loyaltyUserStatusResponse, ErrorConstants.BAD_REQUEST);

                return loyaltyUserStatusResponse;
            }
            // If all is well, process the request!
            loyaltyProgram = statusRequest.getLoyaltyProgram();
            switch (loyaltyProgram) {
            case SNAPBOX:
                loyaltyUserStatusResponse = snapBoxService.getStatus(statusRequest.getUserEmailID());
                break;

            default:
                LOG.error("UNRECOGNIZED loyalty program in LoyaltyUserStatusRequest! Cant process!");
                loyaltyUserStatusResponse.setSuccessful(false);
                loyaltyUserStatusResponse = (LoyaltyUserStatusResponse) validationService.addValidationError(
                    loyaltyUserStatusResponse, ErrorConstants.BAD_REQUEST);
                break;
            }
        }
        catch (Exception exception) {
            LOG.error("Something went wrong while trying to fetch " + loyaltyProgram + "'s status for "
                + statusRequest.getUserEmailID() + ". Cant process!", exception);
            loyaltyUserStatusResponse.setSuccessful(false);
            loyaltyUserStatusResponse = (LoyaltyUserStatusResponse) validationService.addValidationError(
                loyaltyUserStatusResponse, ErrorConstants.UNEXPECTED_ERROR);
        }
        finally {
            return loyaltyUserStatusResponse;
        }
    }

}
