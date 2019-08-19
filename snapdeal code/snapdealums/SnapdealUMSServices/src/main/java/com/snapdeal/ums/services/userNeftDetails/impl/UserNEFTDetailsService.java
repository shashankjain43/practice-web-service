package com.snapdeal.ums.services.userNeftDetails.impl;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserNeftDetailsDO;
import com.snapdeal.ums.dao.userNeftDetails.IuserNeftDetailsDao;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.ISmsServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.services.userNeftDetails.IUserNEFTDetailsService;
import com.snapdeal.ums.services.userNeftDetails.UserNeftHelper;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.UserNEFTDetailsSRO;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsResponse;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * Handles user NEFT details related services.
 * 
 * @author ashish
 * 
 */
@Service
public class UserNEFTDetailsService implements IUserNEFTDetailsService

{

    private static final Logger log = LoggerFactory
        .getLogger(UserNEFTDetailsService.class);
    

    @Autowired
    ISmsServiceInternal smsServiceInternal;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private IuserNeftDetailsDao userNeftDetailsDao;

    @Autowired
    private IUserServiceInternal userServiceInternal;

    @Autowired
    private UserNeftHelper userNeftHelper;

    @Autowired
    private IEmailServiceInternal emailServiceInternal;

    @AuditableMethod
    @Transactional
    public VerifyUserNEFTDetailsResponse verifyActivateExistingUserNEFTDetails(
        VerifyUserNEFTDetailsRequest verifyUserNEFTDetailsRequest)
    {

        
        
        VerifyUserNEFTDetailsResponse verifyUserNEFTDetailsResponse = new VerifyUserNEFTDetailsResponse(true, null);
        String customerEmail = null;
        try {

            int neftDetailsID = -1;

            if (verifyUserNEFTDetailsRequest == null
                || (neftDetailsID = verifyUserNEFTDetailsRequest.getNeftDetailsID()) == 0) {
                // VALIDATION FAILED!
                log.error("Verification of VerifyUserNEFTDetailsRequest failed! Not proceeding further. Returning error response.");
                validationService.addValidationError(verifyUserNEFTDetailsResponse,
                    ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL);
            }
            log.info("Received VerifyUserNEFTDetailsRequest for ID: " + neftDetailsID);
            // If we are here, that means we have the ID in the request. Let us
            // proceed!

            UserNeftDetailsDO existingNEFTDetailsDOInContext = userNeftDetailsDao
                .fetchUserNeftDetailsByID(neftDetailsID);
            // The ID in request does not exist! Add error!
            if (existingNEFTDetailsDOInContext == null) {
                validationService.addValidationError(verifyUserNEFTDetailsResponse,
                    ErrorConstants.NEFT_DETAILS_WITH_ID_IN_REQUEST_DOES_NOT_EXIST);
                log.error(ErrorConstants.NEFT_DETAILS_WITH_ID_IN_REQUEST_DOES_NOT_EXIST.getMsg()
                    + "#VerifyUserNEFTDetailsRequest()#The ID in context is: " + neftDetailsID);

                
                return verifyUserNEFTDetailsResponse;
            }

            customerEmail = existingNEFTDetailsDOInContext.getEmail();

            deactivateAllExistingActiveNEFTDetails(customerEmail);

            userNeftDetailsDao.verifyActivateExistingUserNEFTDetails(neftDetailsID, DateUtils.getCurrentTime());

            EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails = userNeftHelper
                .getEnhancedUserNEFTDetails(existingNEFTDetailsDOInContext);

            // // Update the response
            verifyUserNEFTDetailsResponse.setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);

            sendEmailSMSNeftUpdateNotification(customerEmail, enhancedUserNEFTDetails);
        }
        catch (Exception e) {
            validationService.addValidationError(verifyUserNEFTDetailsResponse, ErrorConstants.UNEXPECTED_ERROR);
            log.error("Something went wrong while trying to service verifyActivateExistingUserNEFTDetails for {}.",
                customerEmail, e);
        }
        finally {
            return verifyUserNEFTDetailsResponse;
        }
    }
    
    @AuditableMethod
    @Transactional
    public AddUserNEFTDetailsResponse addVerifyActivateUserNEFTDetails(
        AddUserNEFTDetailsRequest addUserNEFTDetailsRequest)
    {

        log.info("Received AddUserNEFTDetailsRequest");
        
//        aspectAuditingAspect.audit(addUserNEFTDetailsRequest);

        String emailInReq = null, encryptedAccNo = null;
        AddUserNEFTDetailsResponse addUserNEFTDetailsResponse = new AddUserNEFTDetailsResponse();
        // EnhancedUserNEFTDetails enhancedUserNEFTDetails = null;
        try {

            addUserNEFTDetailsResponse = validateAddUserNEFTDetailsRequest(
                addUserNEFTDetailsRequest, addUserNEFTDetailsResponse);

            if (!addUserNEFTDetailsResponse.isSuccessful()) {
                log.error("Validation of AddUserNEFTDetails request failed!");
                return addUserNEFTDetailsResponse;
            }

            UserNEFTDetailsSRO userNeftDetails = addUserNEFTDetailsRequest
                .getUserNEFTDetails();

            emailInReq = userNeftDetails.getEmail().trim();
            encryptedAccNo = EncryptionUtils.encrypt(userNeftDetails.getAccountNo().trim());

            log.info("Received request to add NEFT details for {}. Proceeding further..", emailInReq);

            // 1. Check if this config exists and is Active

            UserNeftDetailsDO preExistingUserNeftDetails = userNeftDetailsDao
                .fetchUserNeftDetails(userNeftDetails.getAccHolderName().trim(), emailInReq,
                    userNeftDetails
                        .getIfscCode().trim(), userNeftDetails.getBranchName().trim(),
                    userNeftDetails
                        .getBankName().trim(), encryptedAccNo);

            if (preExistingUserNeftDetails != null) {
                // We have established that the NEFT details in the REQUEST
                // exists in the DB!
                // It could already be active or Inactive!
                return whatIfNEFTDetailsExist(addUserNEFTDetailsRequest, emailInReq, addUserNEFTDetailsResponse,
                    userNeftDetails, preExistingUserNeftDetails);
            }
            else {

                // If the cursor is here, it means that the NEFT config in the
                // request is not present in the DB - even in INACTIVE state.

                persistVerifyActivateNewNEFTDetails(addUserNEFTDetailsRequest, emailInReq, encryptedAccNo,
                    addUserNEFTDetailsResponse, userNeftDetails);

            }

        }
        catch (Exception e) {
            validationService.addValidationError(addUserNEFTDetailsResponse, ErrorConstants.UNEXPECTED_ERROR);
            log.error("Something went wrong while trying to service addUserNEFTDetailsRequest for {}.", emailInReq, e);
        }
        finally {
            log.info("Exiting addVerifyActivateUserNEFTDetails.");
//            aspectAuditingAspect.audit(addUserNEFTDetailsResponse);

            return addUserNEFTDetailsResponse;
        }
    }

    /**
     * 
     * Established that NEFT details exist! IF active - throw out validation
     * error. ELSE - deactivate all other and activate this one!
     * 
     * @param addUserNEFTDetailsRequest
     * @param emailInReq
     * @param addUserNEFTDetailsResponse
     * @param userNeftDetails
     * @param preExistingUserNeftDetails
     * @return
     */
    private AddUserNEFTDetailsResponse whatIfNEFTDetailsExist(AddUserNEFTDetailsRequest addUserNEFTDetailsRequest,
        String emailInReq, AddUserNEFTDetailsResponse addUserNEFTDetailsResponse, UserNEFTDetailsSRO userNeftDetails,
        UserNeftDetailsDO preExistingUserNeftDetails)
    {

        if (preExistingUserNeftDetails.isActive()) {
            // error! The address already exists!
            log.info("The NEFT details for user {} already exists and is ACTIVE! Return error in the response",
                userNeftDetails.getEmail());

            validationService.addValidationError(
                addUserNEFTDetailsResponse,
                ErrorConstants.USER_NEFT_DETAILS_EXISTS);

            return addUserNEFTDetailsResponse;

        }
        else {
            // Details exists. De-activate all other details and then
            // activate this one.

            return verifyActivateExistingNEFTDtls(addUserNEFTDetailsRequest, emailInReq,
                addUserNEFTDetailsResponse, userNeftDetails, preExistingUserNeftDetails);
        }
    }

    /**
     * @param addUserNEFTDetailsRequest
     * @param emailInReq
     * @param encryptedAccNo
     * @param addUserNEFTDetailsResponse
     * @param userNeftDetails
     */
    private void persistVerifyActivateNewNEFTDetails(AddUserNEFTDetailsRequest addUserNEFTDetailsRequest,
        String emailInReq,
        String encryptedAccNo, AddUserNEFTDetailsResponse addUserNEFTDetailsResponse, UserNEFTDetailsSRO userNeftDetails)
    {

        log.info("Will deactivate all the existing NEFT details and add the one in the request.",
            userNeftDetails.getEmail());

        // 1. Let us DEACTIVATE all the existing config
        String emailID = addUserNEFTDetailsRequest
            .getUserNEFTDetails().getEmail().trim();

        deactivateAllExistingActiveNEFTDetails(emailID);

        // 2. Now let us add the new config
        UserNeftDetailsDO newUserNeftDetailsDO = userNeftDetailsDao.addActiveNeftDetails(new UserNeftDetailsDO(
            emailInReq, userNeftDetails
                .getIfscCode().trim(), userNeftDetails.getBranchName().trim(), userNeftDetails.getBankName().trim()
                .trim(), encryptedAccNo, true, DateUtils.getCurrentTime(), userNeftDetails.getAccHolderName().trim(),
            DateUtils.getCurrentTime()));

        log.info("New NEFT details in the request were successfully added and activated for {}.",
            userNeftDetails.getEmail());

        EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails = userNeftHelper
            .getEnhancedUserNEFTDetails(newUserNeftDetailsDO);

        sendEmailSMSNeftUpdateNotification(emailInReq, enhancedUserNEFTDetails);

        addUserNEFTDetailsResponse.setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);
    }

    /**
     * @param emailID
     */
    private void deactivateAllExistingActiveNEFTDetails(String emailID)
    {

        userNeftDetailsDao
            .deactivateAllActiveNeftDetails(emailID);
    }

    /**
     * @param addUserNEFTDetailsRequest
     * @param emailInReq
     * @param addUserNEFTDetailsResponse
     * @param userNeftDetails
     * @param preExistingUserNeftDetails
     * @return
     */
    private AddUserNEFTDetailsResponse verifyActivateExistingNEFTDtls(
        AddUserNEFTDetailsRequest addUserNEFTDetailsRequest, String emailInReq,
        AddUserNEFTDetailsResponse addUserNEFTDetailsResponse, UserNEFTDetailsSRO userNeftDetails,
        UserNeftDetailsDO preExistingUserNeftDetails)
    {

        deactivateAllExistingActiveNEFTDetails(emailInReq);

        // Now update the status of the pre-existing NEFT config

        // furnishResponseAndSendEmail(emailInReq,
        // addUserNEFTDetailsResponse, preExistingUserNeftDetails);

        userNeftDetailsDao.verifyActivateExistingUserNEFTDetails(
            preExistingUserNeftDetails.getId(), DateUtils.getCurrentTime());

        EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails = userNeftHelper
            .getEnhancedUserNEFTDetails(preExistingUserNeftDetails);

        addUserNEFTDetailsResponse.setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);

        sendEmailSMSNeftUpdateNotification(emailInReq, enhancedUserNEFTDetails);

        log.info("The NEFT details for user {} already existed but was INACTIVE! Have activated it now.",
            userNeftDetails.getEmail());

        return addUserNEFTDetailsResponse;
    }

    /**
     * 
     * Adds the enhancedUserNEFTDetails to the response and sends out a
     * notification email to the emailID in context.
     * 
     * @param emailInReq
     * @param addUserNEFTDetailsResponse
     * @param preExistingUserNeftDetails
     */
    private void sendEmailSMSNeftUpdateNotification(String emailInReq,
        EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails)
    {

        // smsServiceInternal.sendWelcomeSms("9643800625");
        // smsServiceInternal.sendWelcomeSms("9632103000");

        //
        // EnhancedUserNEFTDetails enhancedUserNEFTDetails;
        // enhancedUserNEFTDetails = userNeftHelper
        // .getEnhancedUserNEFTDetails(preExistingUserNeftDetails);

        // Update the response
        // // addUserNEFTDetailsResponse
        // .setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);

        // Send the NEFT update email
        emailServiceInternal.sendUserNEFTUpdatedEmail(emailInReq,
            enhancedUserNEFTDetails);
        log.info("NEFT update email initiated for: " + emailInReq);

        // Now let us send the SMS
        User user = userServiceInternal.getUserByEmail(emailInReq);
        String mobile = null;
        if (user != null && !UMSStringUtils.isNullOrEmpty(mobile = user.getMobile())) {
            smsServiceInternal.sendNEFTDetailsUpdatedSms(enhancedUserNEFTDetails, mobile);
            log.info("NEFT update SMS initiated for: " + emailInReq + " to: " + mobile);

        }
        else {
            log.info("NEFT update SMS cant be sent due to unavailability of mobile number for: " + emailInReq);
        }
    }

    /**
     * 
     * Return FALSE if any of the neftDetails's fields except for "branch" is
     * null/empty.
     * 
     * @param neftDetails
     * @return
     */
    private boolean hasMandatoryFields(UserNEFTDetailsSRO neftDetails)
    {

        if (neftDetails == null
            || UMSStringUtils.isNullOrEmpty(neftDetails.getAccountNo())
            || UMSStringUtils.isNullOrEmpty(neftDetails.getBankName())
            || UMSStringUtils.isNullOrEmpty(neftDetails.getAccHolderName())
            || UMSStringUtils.isNullOrEmpty(neftDetails.getEmail())
            || UMSStringUtils.isNullOrEmpty(neftDetails.getIfscCode())) {
            log.error("UserNEFTDetails in the request has mantory field(s) as null/empty.");
            return false;
        }
        else {
            return true;
        }

    }

    /**
     * Validates validateAddUserNEFTDetailsRequest
     * 
     * @param addUserNEFTDetailsRequest
     * @param addUserNEFTDetailsResponse
     * @return
     */
    private AddUserNEFTDetailsResponse validateAddUserNEFTDetailsRequest(
        AddUserNEFTDetailsRequest addUserNEFTDetailsRequest,
        AddUserNEFTDetailsResponse addUserNEFTDetailsResponse)
    {

        log.info("Validating validateAddUserNEFTDetailsRequest.");

        boolean isValid = false;
        if (addUserNEFTDetailsRequest != null) {
            isValid = hasMandatoryFields(addUserNEFTDetailsRequest.getUserNEFTDetails());
        }

        if (!isValid) {
            validationService.addValidationError(addUserNEFTDetailsResponse,
                ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL);
        }
        else {
            addUserNEFTDetailsResponse.setSuccessful(true);
        }

        return addUserNEFTDetailsResponse;
    }
    
    @AuditableMethod
    @Transactional
    @Override
    public GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetails(
        GetActiveUserNEFTDetailsRequest getActiveUserNEFTDetailsRequest)
    {

        log.info("Recieved GetActiveUserNEFTDetailsRequest");
        String emailID = null;
        GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetailsResponse = new GetActiveUserNEFTDetailsResponse(true,
            null);
        try {
            if (getActiveUserNEFTDetailsRequest == null
                || UMSStringUtils.isNullOrEmpty(emailID = getActiveUserNEFTDetailsRequest.getEmailID())) {
                validationService.addValidationError(getActiveUserNEFTDetailsResponse,
                    ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL);

                log.error(ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL.getMsg());

                return getActiveUserNEFTDetailsResponse;

            }
            log.info("GetActiveUserNEFTDetailsRequest is for " + emailID);

            UserNeftDetailsDO userNeftDetailsDO = userNeftDetailsDao.getActiveNeftDetails(emailID);

            if (userNeftDetailsDO == null) {
                log.info("Didn't find any active UserNeftDetailsDO for " + emailID);

            }
            else {
                log.info("Active userNeftDetailsDO with ID: " + userNeftDetailsDO.getId() + " found for: " + emailID);

            }

            EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails = userNeftHelper
                .getEnhancedUserNEFTDetails(userNeftDetailsDO);

            getActiveUserNEFTDetailsResponse.setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);
        }
        catch (Exception e) {
            log.error(ErrorConstants.UNEXPECTED_ERROR.getMsg(), e);
            validationService.addValidationError(getActiveUserNEFTDetailsResponse, ErrorConstants.UNEXPECTED_ERROR);

        }
        finally {
            return getActiveUserNEFTDetailsResponse;
        }
    }

    @AuditableMethod
    @Transactional
    @Override
    public DeactivateUserNEFTDetailsResponse deActivateUserNEFTDetails(
        DeactivateUserNEFTDetailsRequest deactivateUserNEFTDetailsRequest)
    {

        DeactivateUserNEFTDetailsResponse deactivateUserNEFTDetailsResponse = new DeactivateUserNEFTDetailsResponse(
            true, null);

        try {
            log.info("Recieved DeactivateUserNEFTDetailsRequest");

            int neftDetailsID;
            if (deactivateUserNEFTDetailsRequest == null
                || (neftDetailsID = deactivateUserNEFTDetailsRequest.getNeftDetailsID()) == 0) {
                validationService.addValidationError(deactivateUserNEFTDetailsResponse,
                    ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL);

                log.error(ErrorConstants.REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL.getMsg());

                return deactivateUserNEFTDetailsResponse;
            }
            try {
                userNeftDetailsDao.deactivateUserNeftDetails(neftDetailsID);
            }
            catch (ObjectNotFoundException objectNotFoundException) {
                validationService.addValidationError(deactivateUserNEFTDetailsResponse,
                    ErrorConstants.NEFT_DETAILS_WITH_ID_IN_REQUEST_DOES_NOT_EXIST);
                log.info("Deactivation of neft details failed for ID#"+neftDetailsID+"."
                    + ErrorConstants.NEFT_DETAILS_WITH_ID_IN_REQUEST_DOES_NOT_EXIST.getMsg());
            }

            EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails = userNeftHelper
                .getEnhancedUserNEFTDetails(userNeftDetailsDao.fetchUserNeftDetailsByID(neftDetailsID));
            deactivateUserNEFTDetailsResponse.setEnhancedUserNEFTDetails(enhancedUserNEFTDetails);
            log.info("If found present,the user NEFT details with ID: " + neftDetailsID + " has been deactivated.");
        }
        catch (Exception e) {
            log.error(ErrorConstants.UNEXPECTED_ERROR.getMsg(), e);
            validationService.addValidationError(deactivateUserNEFTDetailsResponse, ErrorConstants.UNEXPECTED_ERROR);

        }
        finally {
            return deactivateUserNEFTDetailsResponse;
        }
    }

}