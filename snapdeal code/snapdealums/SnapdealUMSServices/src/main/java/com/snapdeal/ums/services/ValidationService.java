package com.snapdeal.ums.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.constants.ErrorConstants;

/**
 * Validation service
 * 
 * @author ashish
 * 
 */
@Service
public class ValidationService
{

    /**
     * Adds error to the response and marks response.setSuccessful(false);
     * 
     * 
     * @param validationErrors
     * @param validationError
     * @return
     */
    public ServiceResponse addValidationError(ServiceResponse response, ErrorConstants errorConstants)
    {

        return addValidationError(response, errorConstants.getCode(), errorConstants.getMsg());
    }

    /**
     * Adds error to the response and marks response.setSuccessful(false);
     * 
     * 
     * @param validationErrors
     * @param validationError
     * @return
     */
    public ServiceResponse addValidationError(ServiceResponse response, int errorCode, String errorMsg)
    {

        if (response == null) {
            response = new ServiceResponse();
        }
        List<ValidationError> errors = response.getValidationErrors();

        if (errors == null) {
            errors = new ArrayList<ValidationError>();
            response.setValidationErrors(errors);
        }

        ValidationError validationError = new ValidationError(errorCode, errorMsg);
        errors.add(validationError);
        response.setSuccessful(false);
        return response;
    }

}
