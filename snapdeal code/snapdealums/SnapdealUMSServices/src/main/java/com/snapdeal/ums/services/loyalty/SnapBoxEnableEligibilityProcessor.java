package com.snapdeal.ums.services.loyalty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserDtlsDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;
import com.snapdeal.ums.loyalty.SnapBoxConstants;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.services.loyalty.LoyaltyUploadManager.UserPrerequisiteInfo;
import com.snapdeal.ums.services.loyalty.snapBox.SnapBoxUserManager;
import com.snapdeal.ums.services.loyalty.snapBox.impl.SnapBoxService;
import com.snapdeal.ums.utils.EncryptionUtils;

/**
 * Processor to run SNAPBOX specific tasks related to eligibility upload
 * 
 * @author ashish
 * 
 */
@Service
public class SnapBoxEnableEligibilityProcessor
{

    private static final Logger log = LoggerFactory.getLogger(SnapBoxEnableEligibilityProcessor.class);

    @Autowired
    private IEmailServiceInternal emailServiceInternalImpl;

    @Autowired
    private SnapBoxService snapBoxService;

    @Autowired
    private LoyaltyProgramStatusManager loyaltyProgramStatusManager;

    @Autowired
    private SnapBoxUserManager snapBoxUserManager;

    @Autowired
    private ILoyaltyUserDtlsDao loyaltyUserDtlsDao;

    public void setEligibleEmailIDUserIDMap(Map<String, UserPrerequisiteInfo> eligibleEmailIDUserIDMap)
    {

        log.info("SnapBoxEligibilityProcess inititalized with userMap of size {} !");
    }

    public void processSnapBoxEligibilityRq(Map<String, UserPrerequisiteInfo> eligibleEmailIDUserIDMap)
    {

        long startTime = System.currentTimeMillis();

        log.info("SnapBoxEligibilityProcess inititalized with userMap of size {}!!",
            eligibleEmailIDUserIDMap.size());

        if (eligibleEmailIDUserIDMap == null || eligibleEmailIDUserIDMap.size() < 1) {
            log.info("eligibleEmailIDUserIDMap is NULL...i.e no email to make eligible.");
            return;
        }

        try {
            LoyaltyProgramDO loyaltyProgramDO = loyaltyProgramStatusManager
                .getLoyaltyProgram(LoyaltyProgram.SNAPBOX);
            LoyaltyProgramStatusDO programStatusDO = loyaltyProgramStatusManager
                .getLoyaltyProgramStatus(LoyaltyStatus.ELIGIBLE);

            List<LoyaltyUserDetailDO> loyaltyUserDetailDOs = new ArrayList<LoyaltyUserDetailDO>();

            Iterator<Entry<String, UserPrerequisiteInfo>> it = eligibleEmailIDUserIDMap.entrySet().iterator();
            log.info("Starting iteration of eligible emailIDs.");
            while (it.hasNext()) {
                Entry<String, UserPrerequisiteInfo> tmpEntry = it.next();

                String rawVerificationCode = snapBoxService.generateVerificationCode();
                String encryptedEmailLinkVerificationCode = snapBoxService
                    .getEncryptedCodeForEmailLink(rawVerificationCode);
                String encryptedDBVerificationCode = snapBoxService
                    .getEncryptionCodeLevel2ForDB(rawVerificationCode);

                // Set the code to be emailed
                tmpEntry.getValue().setEncryptedVerificationCode(encryptedEmailLinkVerificationCode);

                String emailID = tmpEntry.getKey();
                int userID = tmpEntry.getValue().getId();
                User tmpUser = new User();
                tmpUser.setId(userID);
                LoyaltyUserDetailDO loyaltyUserDetailDO = new LoyaltyUserDetailDO(emailID, loyaltyProgramDO,
                    programStatusDO, tmpUser, encryptedDBVerificationCode);

                loyaltyUserDetailDOs.add(loyaltyUserDetailDO);

            }

            log.info("Going to persist SNAPBOX eligible users.");
            loyaltyUserDtlsDao.persist(loyaltyUserDetailDOs);
            log.info("Finished saving all the entries for SnapBox eligibility in the db.");

            // If everything goes fine, send out mailers to all the emails
            // in
            // the request

            it = eligibleEmailIDUserIDMap.entrySet().iterator();
            // Now let us send out SNAPBOX invitation emails to the
            // persisted
            // IDs
            log.info("Now let us send out SNAPBOX inivitation emails to {} users.", eligibleEmailIDUserIDMap.size());
            while (it.hasNext()) {
                Entry<String, UserPrerequisiteInfo> tmpEntry = it.next();

                String emailID = tmpEntry.getKey();
                // TODO:ashish REMOVE EMAIL MASKING - keeping the commented code
                // for future
                // if (!emailID.equals("ashish.saxena@snapdeal.com")) {
                // emailID = emailID.replace("@", "#");
                // }

                String link =
                    SnapBoxConstants.MY_SNAPBOX_ACTIVATION_LINK +
                        snapBoxUserManager.getEmailParamString(
                            EncryptionUtils.encrypt(emailID), tmpEntry.getValue().getEncryptedVerificationCode());

                emailServiceInternalImpl.sendSnapBoxInvitationEmail(emailID, tmpEntry.getValue().getName(), link);
            }
            log.info("Sent out all the snapbox invitation emails.");
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            log.info("Took {} ms to persist eligible user details into the db and to send them snapbox invite.",
                totalTime);
            //
        }
        catch (Exception e) {
            log.error("Something went wrong!.",
                e);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            log.info("Took {} ms to persist eligible user details into the db and to send them snapbox invite.",
                totalTime);
        }
    }

}
