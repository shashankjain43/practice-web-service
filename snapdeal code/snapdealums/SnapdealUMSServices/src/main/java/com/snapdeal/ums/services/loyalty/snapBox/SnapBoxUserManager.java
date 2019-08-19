package com.snapdeal.ums.services.loyalty.snapBox;

import java.security.InvalidParameterException;

import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.loyalty.SnapBoxVerifyActivateReqBO;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * Manager class to perform tasks related to snapbox loyalty program
 * 
 * @author ashish
 * 
 */
@Service
public class SnapBoxUserManager

{

    // NOTE: do not change this without changing the logic to read back
    // encrypted values from the url that the user clicks to confirm snapbox
    // membership! Also, the existing links sent to customers will stop working
    // !!
    private static final String PARAM_SEPARATOR = "@@@";

    /**
     * Returns the param which is basically encrypted emailID and verification
     * code.
     * 
     * @param encryptedEmailID
     * @param encryptedVerificationCode
     * @return
     */
    public String getEmailParamString(String encryptedEmailID, String encryptedVerificationCode)
    {

        if (UMSStringUtils.isNullOrEmpty(encryptedEmailID) || UMSStringUtils.isNullOrEmpty(encryptedVerificationCode)) {
            throw new InvalidParameterException(
                "All the requisite params to generate email param string are not present in the request!");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(encryptedEmailID).append(PARAM_SEPARATOR).append(encryptedVerificationCode);

        return builder.toString();
    }

    private String[] getParamsFromParamString(String paramString)
    {

        if (UMSStringUtils.isNullOrEmpty(paramString)) {
            return null;
        }
        String[] params;
        params = paramString.split(PARAM_SEPARATOR);

        return params;

    }

    public SnapBoxVerifyActivateReqBO formSnapBoxVerificationActivationRqBO(
        SnapBoxVerificationActivationRequest request)
    {

        if (request == null) {
            return null;
        }
        String emailID = request.getRequestedFromEmailID();
        if (UMSStringUtils.isNullOrEmpty(emailID))
        {
            return null;
        }

        SnapBoxVerifyActivateReqBO snapBoxVerificationActivationRqBO = null;

        String params = request.getParamaterString();

        String[] paramArray = null;
        if (UMSStringUtils.isNotNullNotEmpty(params)) {
            paramArray = getParamsFromParamString(params);
        }// We are expecting two codes in the param
        if (paramArray != null && paramArray.length==2) {
            // It should have two params as per
            // SnapBoxUserManager#getEmailParamString()
            String recipientEmailID = paramArray[0];
            String verificationCode = paramArray[1];
            // NOTE: We must set decrypted recipient ID BUT encrypted
            // VERIFICATION CODE!!!
            snapBoxVerificationActivationRqBO = new SnapBoxVerifyActivateReqBO(
                emailID, EncryptionUtils.decrypt(recipientEmailID), verificationCode);
        }

        return snapBoxVerificationActivationRqBO;
    }

}
