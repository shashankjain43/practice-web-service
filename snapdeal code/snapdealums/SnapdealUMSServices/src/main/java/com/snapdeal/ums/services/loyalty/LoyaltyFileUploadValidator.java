package com.snapdeal.ums.services.loyalty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.loyalty.LoyaltyUploadRq;
import com.snapdeal.ums.loyalty.LoyaltyUploadRs;
import com.snapdeal.ums.services.ValidationService;

/**
 * First level validator for the uploaded loyalty file
 * 
 * @author ashish
 * 
 */
@Service
public class LoyaltyFileUploadValidator
{

    @Autowired
    private ValidationService validationService;

    private static final Logger log = LoggerFactory.getLogger(LoyaltyFileUploadValidator.class);

    public final static int FILE_SIZE_LIMIT = 7000000;

    LoyaltyUploadRs validateLoyaltyUpload(LoyaltyUploadRq uploadRq)
    {

        LoyaltyUploadRs loyaltyUploadRs = new LoyaltyUploadRs();
        boolean isSuccessfull = true;
        // List<ErrorConstants> errors = new ArrayList<ErrorConstants>();

        if (uploadRq == null) {
            isSuccessfull = false;
            validationService.addValidationError(loyaltyUploadRs, ErrorConstants.BAD_REQUEST);
        }

        if (uploadRq.getFileContent() == null || uploadRq.getFileContent().length == 0) {
            isSuccessfull = false;
            validationService.addValidationError(loyaltyUploadRs, ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY);
            log.error(ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY.getMsg());
        }
        else if (!uploadRq.getFileName().toLowerCase().endsWith(".csv")) {
            isSuccessfull = false;
            validationService.addValidationError(loyaltyUploadRs, ErrorConstants.FILE_FORMAT_UNSUPPORTED);
            log.error(ErrorConstants.FILE_FORMAT_UNSUPPORTED.getMsg());
        }
        else if (uploadRq.getFileContent().length > FILE_SIZE_LIMIT) {
            isSuccessfull = false;
            validationService.addValidationError(loyaltyUploadRs, ErrorConstants.FILE_EXCEEDS_SIZE_LIMIT.getCode(),
                ErrorConstants.FILE_EXCEEDS_SIZE_LIMIT.getMsg() + " Limit is " + FILE_SIZE_LIMIT + " bytes.");
            log.error(ErrorConstants.FILE_EXCEEDS_SIZE_LIMIT.getMsg() + " Limit is " + FILE_SIZE_LIMIT
                + " bytes. In request: " + uploadRq.getFileContent().length + " bytes!");

        }
        loyaltyUploadRs.setSuccessful(isSuccessfull);
        return loyaltyUploadRs;
    }
}
