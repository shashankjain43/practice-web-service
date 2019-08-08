package com.service;

import com.exception.InvalidInputException;
import com.request.BaseRequest;

import javax.validation.*;
import java.util.Set;

public class ValidationService {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public boolean validateRequest(BaseRequest request) throws InvalidInputException {

        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseRequest>> validate = validator.validate(request);
        if(!validate.isEmpty()){
            String errorMsg = "Input Validation failure: ";
            for (ConstraintViolation<BaseRequest> cv : validate) {
                errorMsg = errorMsg + cv.getMessage();
            }
            throw new InvalidInputException(errorMsg);
        }
        return true;
    }
}
