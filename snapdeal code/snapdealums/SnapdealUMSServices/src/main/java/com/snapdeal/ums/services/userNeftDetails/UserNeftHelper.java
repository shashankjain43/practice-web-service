package com.snapdeal.ums.services.userNeftDetails;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.UserNeftDetailsDO;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * 
 * 
 * @author ashish
 * 
 */
@Service
public class UserNeftHelper
{

    private final static int SHOW_INITIAL_CHAR_COUNT = 3,
        SHOW_FINAL_CHAR_COUNT = 3;

    private final static char MASKING_CHAR = 'X';

    private static long MILISECS_IN_1_DAY = 86400000L;

    private static int VERIFICATION_EXPIRY_DAYS_COUNT = 7;

    /**
     * returns EnhancedUserNEFTDetails which will have MASKED account number
     * 
     * @param userNeftDetails
     * @return
     */
    public EnhancedUserNEFTDetailsSRO getEnhancedUserNEFTDetails(
        UserNeftDetailsDO userNeftDetails)
    {

        if (userNeftDetails == null) {
            return null;
        }

        boolean isExpired = true;
        if (userNeftDetails.isActive()) {
            //If the NEFT details are not active - isExpired should ALWAYS be TRUE
            isExpired = isNEFTDetailsExpired(userNeftDetails.getLastVerified());
        }
        // DB has encrypted account number. Let us first decrypt it.
        String decryptedAccNo = EncryptionUtils.decrypt(userNeftDetails.getAccountNo());
        EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails =
            new EnhancedUserNEFTDetailsSRO(userNeftDetails.getId(),
                userNeftDetails.getEmail(), userNeftDetails.getIfscCode(),
                userNeftDetails.getBranch(), userNeftDetails.getBankName(),
                decryptedAccNo,
                userNeftDetails.getAccHolderName(),
                getMaskedAccountNo(decryptedAccNo), isExpired);

        return enhancedUserNEFTDetails;

    }

    private boolean isNEFTDetailsExpired(Date lastVerifiedDate)
    {

        boolean isExpired = true;

        if (lastVerifiedDate != null) {
            long milisecsDifference = DateUtils.getCurrentTime().getTime() - lastVerifiedDate.getTime();
            if (milisecsDifference < MILISECS_IN_1_DAY * VERIFICATION_EXPIRY_DAYS_COUNT) {
                isExpired = false;
            }
            else {
                isExpired = true;
            }
        }

        return isExpired;
    }

    private String getMaskedAccountNo(String accNo)
    {

        if (UMSStringUtils.isNullOrEmpty(accNo)) {
            return accNo;
        }

        StringBuilder maskedAccNobuilder = new StringBuilder();

        char[] accCharArray = accNo.toCharArray();
        int accNoLength = accCharArray.length;

        for (int i = 0; i < accNoLength; i++) {
            if (i < SHOW_INITIAL_CHAR_COUNT
                || (accNoLength - i) <= SHOW_FINAL_CHAR_COUNT) {
                maskedAccNobuilder.append(accCharArray[i]);

            }
            else {
                maskedAccNobuilder.append(MASKING_CHAR);

            }
        }

        return maskedAccNobuilder.toString();
    }

}
