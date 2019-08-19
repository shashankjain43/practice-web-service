package com.snapdeal.payments.view.utils.validator;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.ValidationException;

@Component
@Slf4j
public class GenericValidator<T> {

	private final ValidatorFactory factory = Validation
            .buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validate(T request) throws ValidationException {
        Set<ConstraintViolation<T>> requestErrors = validator.validate(request);
        StringBuilder invalidFields = new StringBuilder();
        for (ConstraintViolation<T> constraintViolation : requestErrors) {
            invalidFields.append(constraintViolation.getPropertyPath()
                    .toString() +": "+constraintViolation.getMessage()+ ", ");
        }
        if (!requestErrors.isEmpty()) {
            String exceptionMsg = String.format(
                    "Invalid fields : %s in request %s", invalidFields, request
                            .getClass().getName());
            log.error(exceptionMsg);
            throw new ValidationException(PaymentsViewDefaultExceptionCodes.VALIDATION.errCode(),exceptionMsg);
        }
    }
}
