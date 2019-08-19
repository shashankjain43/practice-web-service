/**
 * 
 */
package com.snapdeal.ums.services.loyalty.snapBox.impl;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.constants.JobType;
import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;
import com.snapdeal.ums.core.entity.LoyaltyUserHistoryDO;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.job.JobDO;
import com.snapdeal.ums.core.entity.loyalty.SnapBoxVerifyActivateReqBO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserDtlsDao;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserHistoryDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.services.job.JobManager;
import com.snapdeal.ums.services.loyalty.LoyaltyProgramStatusManager;
import com.snapdeal.ums.services.loyalty.LoyaltyUserManager;
import com.snapdeal.ums.services.loyalty.snapBox.SnapBoxUserManager;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * 
 * Services SNAPBOX related requests
 * 
 * @author ashish
 * 
 */
@Service
public class SnapBoxService
{

    // Determines the length of the verification code.
    private static final int SNAPBOX_VERIFICATION_CODE_LENGTH = 7;

    @Autowired
    private SnapBoxUserManager snapBoxUserManager;

    @Autowired
    private ILoyaltyUserHistoryDao loyaltyUserHistoryDao;

    @Autowired
    private ILoyaltyUserDtlsDao loyaltyUserDtlsDao;

    @Autowired
    private IUsersDao usersDao;

    @Autowired
    private LoyaltyUserManager loyaltyUserManager;

    private static final Logger LOG = LoggerFactory.getLogger(SnapBoxService.class);

    @Autowired
    private ILoyaltyUserDtlsDao snapBoxBuyerDtlsDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private LoyaltyProgramStatusManager loyaltyProgramStatusManager;

    @Autowired
    private JobManager jobManager;

    public Set<String> grantSnapBoxEligibility(Set<String> emailIDs)
    {

        if (emailIDs != null && emailIDs.size() > 0) {

            // Load existing snapBox emailUsers which are in the requested list
            // List<String> existingEmailIDs = snapBoxBuyerDtlsDao
            // .getExistingEmails(emailIDs);
            // remove existing IDs
            // emailIDs.remove(existingEmailIDs);
            // Grant eligibility to the remainder emailIDss
            // snapBoxBuyerDtlsDao.grantSnapBoxEligibility(emailIDs);

            // We need to filter out any pre-existing emailIDs

        }
        return emailIDs;
    }

    @Transactional
    public SnapBoxActivationResponse verifyActivateSnapBox(SnapBoxVerificationActivationRequest request)
    {

        String requestedFromEmailID = null;

        SnapBoxActivationResponse snapBoxActivationResponse = new SnapBoxActivationResponse();
        try {
            if (request == null || UMSStringUtils.isNullOrEmpty(request.getRequestedFromEmailID())) {
                snapBoxActivationResponse = (SnapBoxActivationResponse) validationService.addValidationError(
                    new SnapBoxActivationResponse(), ErrorConstants.INVALID_EMAIL_ID);
                LOG.error("Invalidemail ID:" + requestedFromEmailID);

            }
            else {
                requestedFromEmailID = request.getRequestedFromEmailID();
                SnapBoxVerifyActivateReqBO activationRqBO = snapBoxUserManager
                    .formSnapBoxVerificationActivationRqBO(request);

                snapBoxActivationResponse = activateSnapBox(activationRqBO, true);
            }

        }
        catch (Exception exception) {
            LOG.error("Something went wrong while activating SnapBox for " + request.getRequestedFromEmailID(),
                exception);
            snapBoxActivationResponse.setSuccessful(false);
            snapBoxActivationResponse.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(),
                ErrorConstants.UNEXPECTED_ERROR.getMsg()));
        }
        return snapBoxActivationResponse;

    }

    @SuppressWarnings("finally")
    @Transactional
    public SnapBoxActivationResponse activateSnapBox(SnapBoxActivationRequest request)
    {

        String requestedFromEmailID = null;

        SnapBoxActivationResponse snapBoxActivationResponse = new SnapBoxActivationResponse();
        try {
            if (request == null || UMSStringUtils.isNullOrEmpty(request.getRequestedFromEmailID())) {
                snapBoxActivationResponse = (SnapBoxActivationResponse) validationService.addValidationError(
                    new SnapBoxActivationResponse(), ErrorConstants.INVALID_EMAIL_ID);
                LOG.error("Invalidemail ID:" + requestedFromEmailID);

            }
            else {
                requestedFromEmailID = request.getRequestedFromEmailID();
                SnapBoxVerifyActivateReqBO activationRqBO = new SnapBoxVerifyActivateReqBO(
                    requestedFromEmailID, requestedFromEmailID, null);

                snapBoxActivationResponse = activateSnapBox(activationRqBO, false);
            }

        }
        catch (Exception exception) {
            LOG.error("Something went wrong while activating SnapBox for " + request.getRequestedFromEmailID(),
                exception);
            snapBoxActivationResponse.setSuccessful(false);
            snapBoxActivationResponse.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(),
                ErrorConstants.UNEXPECTED_ERROR.getMsg()));
        }
        finally {
            // jobManager
            // .addAndExecute(new JobDO(requestedFromEmailID,
            // JobType.SEND_SNAPBOX_ACTIVATION_EMAIL, new Date()));
            // Collection<JobDO> jobDOs =
            // jobManager.getJobs(JobType.SEND_SNAPBOX_ACTIVATION_EMAIL);
            return snapBoxActivationResponse;
        }

    }

    /**
     * @param activationResponse
     * @param errors
     * @param emailID
     * @param encryptedVerificationCodeInReq
     */
    private SnapBoxActivationResponse activateSnapBox(SnapBoxVerifyActivateReqBO activationRqBO,
        boolean peformCodeVerification)
    {

        LOG.info("Received SnapBox activation service request from:" + activationRqBO.getRequestedFromEmailID());

        // Get the below parameters from the request

        SnapBoxActivationResponse activationResponse = new SnapBoxActivationResponse();

        LoyaltyUserDetailDO loyaltyUserDetail = fetchIfSnapBoxEligible(activationRqBO.getRequestedFromEmailID());

        activationResponse = validateSnapBoxActivationRq(activationRqBO,
            activationResponse, loyaltyUserDetail, peformCodeVerification);

        // Now we are certain that the email is eligible and the
        // verification code also matches!

        if (activationResponse.isSuccessful()) {
            LOG.info("VALIDATIONS successful for SnapBox activation service request from :"
                + activationRqBO.getRequestedFromEmailID());
            // FINALLY! ALL the validations have been passed!!! Now let us
            // process SnapBoxActivation
            processSnapBoxActivation(loyaltyUserDetail, activationResponse);

        }
        else {
            LOG.info("VALIDATIONS FAILED for SnapBox activation service request from :"
                + activationRqBO.getRequestedFromEmailID());

        }

        return activationResponse;
    }

    /**
     * @param activationRq
     * @return
     */
    private LoyaltyUserDetailDO fetchIfSnapBoxEligible(String emailID)
    {

        if (UMSStringUtils.isNullOrEmpty(emailID)) {
            return null;
        }

        return loyaltyUserManager.fetchLoyaltyUserDetail(emailID, loyaltyProgramStatusManager.getLoyaltyProgram(
            LoyaltyConstants.LoyaltyProgram.SNAPBOX), loyaltyProgramStatusManager
            .getLoyaltyProgramStatus(
            LoyaltyConstants.LoyaltyStatus.ELIGIBLE));
    }

    /**
     * @param request
     * @param errors
     * @param activationRq
     * @param activationResponse
     * @return
     */
    private SnapBoxActivationResponse validateSnapBoxActivationRq(
        SnapBoxVerifyActivateReqBO activationRq,
        SnapBoxActivationResponse activationResponse, LoyaltyUserDetailDO loyaltyUserDetailDO,
        boolean peformCodeVerification)
    {

        String recipientEmailID = activationRq.getRecipientEmailID();
        String requestedFromEmailID = activationRq.getRequestedFromEmailID();

        activationResponse.setSuccessful(UMSStringUtils.areEqualNotEmptyNotNull(recipientEmailID,
            requestedFromEmailID));

        if (!activationResponse.isSuccessful()) {

            LOG.error("Request parameters did not match with the email ID: {}" + requestedFromEmailID);
            activationResponse = (SnapBoxActivationResponse) validationService.addValidationError(activationResponse,
                ErrorConstants.REQUEST_MISMATCH_WITH_EMAIL);
            return activationResponse;
        }

        // Ah! The email has been sent from the same emailID which has been
        // encoded and encrypted in the link we provided!!!

        User user = usersDao.getUserByEmailWithoutRoles(activationRq.getRequestedFromEmailID());

        if (user == null) {
            // Implies INVALID USER!!
            activationResponse.setSuccessful(false);
            activationResponse = (SnapBoxActivationResponse) validationService.addValidationError(
                activationResponse,
                ErrorConstants.USER_DOES_NOT_EXIST);
            LOG.error("Invalid snapdeal user email ID.");
            return activationResponse;

        }

        //
        // NOW - Check if user email is eligible for SNAPBOX. Cross check
        // verification
        // code.
        if (loyaltyUserDetailDO == null) {
            activationResponse.setSuccessful(false);
        }

        if (!activationResponse.isSuccessful()) {
            // NO record which confirms NON-eligibility
            activationResponse = (SnapBoxActivationResponse) validationService.addValidationError(
                activationResponse,
                ErrorConstants.LOYALTY_USER_NOT_ELIGIBLE);
            LOG.error(activationRq.getRequestedFromEmailID()
                + " is not eligible for snapBox loyalty program or is already active.");
            return activationResponse;

        }

        // Atleast we now know that the user is SNAPBOX-ELIGIBLE!!! Let
        // us proceed!

        /*
         * SKIP code verification when the request is directly from a logged in
         * user - WEB team to call suitable API
         */
        if (peformCodeVerification && activationResponse.isSuccessful()) {
            activationResponse.setSuccessful(validateVerificationCodeInReq(activationRq, loyaltyUserDetailDO));

            if (!activationResponse.isSuccessful()) {
                activationResponse = (SnapBoxActivationResponse) validationService.addValidationError(
                    activationResponse,
                    ErrorConstants.LOYALTY_VERIFICATION_CODE_MISMATCH);
                LOG.error("Verification code mismatch for: " + activationRq.getRequestedFromEmailID());
                return activationResponse;
            }

        }
        return activationResponse;
    }

    /**
     * @param loyaltyUserDetail
     */

    private void processSnapBoxActivation(
        LoyaltyUserDetailDO loyaltyUserDetailDO, SnapBoxActivationResponse activationResponse)
    {

        String previousStatus = "";
        if (loyaltyUserDetailDO == null || UMSStringUtils.isNullOrEmpty(loyaltyUserDetailDO.getEmail())) {
            if (activationResponse == null) {
                activationResponse = new SnapBoxActivationResponse();
                activationResponse.setSuccessful(false);
                activationResponse.addValidationError(new ValidationError(ErrorConstants.INVALID_EMAIL_ID.getCode(),
                    ErrorConstants.INVALID_EMAIL_ID.getMsg()));
            }
        }

        // SUCCESS! Great! Now let us update the status corresponding to
        // SNAPBOX_ACTIVE
        previousStatus = loyaltyUserDetailDO.getLoyaltyProgramStatus().getName();

        LoyaltyProgramStatusDO status_ACTIVE = loyaltyProgramStatusManager.getLoyaltyProgramStatus(
            LoyaltyConstants.LoyaltyStatus.ACTIVE);
        Date statusChangedOn = DateUtils.getCurrentTime();

        // Now let us update and persist the loyalty user detail
        persistUpdateLoyaltyUserStatus(loyaltyUserDetailDO, status_ACTIVE, statusChangedOn);

        // Create a history element

        // (User user, String loyaltyProgram, String email,
        // String previousStatus, String newStatus
        // , Date statusChangedOn)

        persistHistory(loyaltyUserDetailDO.getUser(), loyaltyUserDetailDO.getEmail(), loyaltyUserDetailDO
            .getLoyaltyProgram().getName(),
            previousStatus, status_ACTIVE.getName(), statusChangedOn);

        // Send out REQ to PROMO module so that it assigns a new
        // PROMO
        // code
        // to the emailID in the Req.

        requestPromoCode(loyaltyUserDetailDO);
        sendSnapBoxActivationEmail(loyaltyUserDetailDO);

        LOG.info("SnapBox activated for: " + loyaltyUserDetailDO.getEmail());
    }

    /**
     * @param loyaltyUserDetailDO
     */
    private void sendSnapBoxActivationEmail(LoyaltyUserDetailDO loyaltyUserDetailDO)
    {

        // Add and execute
        jobManager
            .addAndExecute(new JobDO(loyaltyUserDetailDO.getEmail(), JobType.SEND_SNAPBOX_ACTIVATION_EMAIL, new Date()));

    }

    /**
     * @param loyaltyUserDetailDO
     */
    private void requestPromoCode(LoyaltyUserDetailDO loyaltyUserDetailDO)
    {

        jobManager
            .addAndExecute(new JobDO(loyaltyUserDetailDO.getEmail(), JobType.REQUEST_PROMO_CODE_ASSIGNMENT, new Date()));
    }

    /**
     * @param loyaltyUserDetail
     * @param status_ACTIVE
     * @param statusChangedOn
     */
    private void persistUpdateLoyaltyUserStatus(LoyaltyUserDetailDO loyaltyUserDetail,
        LoyaltyProgramStatusDO status_ACTIVE,
        Date statusChangedOn)
    {

        loyaltyUserDetail.setStatus(status_ACTIVE);
        loyaltyUserDetail.setLastUpdated(statusChangedOn);
        loyaltyUserDtlsDao.saveOrUpdateStatusChange(loyaltyUserDetail);

    }

    /**
     * 
     * @param user
     * @param loyaltyProgram
     * @param email
     * @param previousStatus
     * @param newStatus
     * @param statusChangedOn
     */
    private void persistHistory(User user, String loyaltyProgram, String email,
        String previousStatus, String newStatus
        , Date statusChangedOn)
    {

        LoyaltyUserHistoryDO loyaltyUserHistory = new
            LoyaltyUserHistoryDO(user, loyaltyProgram,
                email, previousStatus,
                newStatus, statusChangedOn);

        loyaltyUserHistoryDao.saveLoyaltyUserHistory(loyaltyUserHistory);
    }

    /**
     * 
     * NOTE: encrypted verification in the request is decrypted again using
     * EncryptionUtils.enrpyt_level2(). We reverse the process here and validate
     * if the verification code in the request is correct!
     * 
     * @param activationRq
     * @param loyaltyUserDetail
     * @return
     */
    private boolean validateVerificationCodeInReq(SnapBoxVerifyActivateReqBO activationRq,
        LoyaltyUserDetailDO loyaltyUserDetail)
    {

        boolean isSuccessful;
        String decrypVerCodeInDB = (loyaltyUserDetail
            .getVerificationCode());

        String enryptedVerCodeInReq = activationRq.getVerificationCode();

        isSuccessful = UMSStringUtils.areEqualNotEmptyNotNull(
            EncryptionUtils.decrpytionLevel2ToLevel1(decrypVerCodeInDB), enryptedVerCodeInReq);
        return isSuccessful;
    }

    /**
     * Returns null if the email in request is null or empty. If valid, returns
     * the snapBox status.
     */
    @Transactional
    public LoyaltyUserStatusResponse getStatus(String emailID)
    {

        LOG.info("Get SnapBox status for: " + emailID);
        LoyaltyUserStatusResponse loyaltyUserStatusResponse = new LoyaltyUserStatusResponse();

        if (UMSStringUtils.isNullOrEmpty(emailID)) {
            loyaltyUserStatusResponse.setSuccessful(false);
            return loyaltyUserStatusResponse;
        }

        LoyaltyStatus programStatus = null;

        LoyaltyUserDetailDO loyaltyUserDetailDO = loyaltyUserDtlsDao.getLoyaltyUserDtl(emailID,
            loyaltyProgramStatusManager.getLoyaltyProgram(LoyaltyProgram.SNAPBOX).getId());

        if (loyaltyUserDetailDO == null)
        {// Implies the user entry is not there in loyalty_user_details. There
         // for we can safely assume that the user if ILLEGIBLE!
            programStatus = LoyaltyStatus.INELIGIBLE;
        }
        else {

            programStatus = LoyaltyStatus.valueOf(loyaltyUserDetailDO.getLoyaltyProgramStatus().getName());
        }

        loyaltyUserStatusResponse.setSuccessful(true);
        loyaltyUserStatusResponse.setUserEmailID(emailID);
        loyaltyUserStatusResponse.setLoyaltyProgram(LoyaltyProgram.SNAPBOX);
        loyaltyUserStatusResponse.setLoyaltyStatus(programStatus);
        LOG.info("Processing of snapBox's getStatus() for " + emailID + "completed.");
        return loyaltyUserStatusResponse;

    }

    /**
     * Generates verification code
     * 
     * @return
     */
    public String generateVerificationCode()
    {

        return UMSStringUtils.generateRandomCode(SNAPBOX_VERIFICATION_CODE_LENGTH);
    }

    /**
     * Encrypts the verification code to be sent via the email
     * 
     * @param verCode
     * @return
     */
    public String getEncryptedCodeForEmailLink(String verCode)
    {

        return EncryptionUtils.encrypt(verCode);
    }

    /**
     * Encrypts @ level 2 - to be saved in the db.
     * 
     * @param verCode
     * @return
     */
    public String getEncryptionCodeLevel2ForDB(String verCode)
    {

        return EncryptionUtils.encrpytionLevel2(verCode);

    }

}
