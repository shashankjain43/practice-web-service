package com.snapdeal.ims.otp.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.ValidationException;

@Component
@Slf4j
public class GenericValidator<T> {

	private final ValidatorFactory factory = Validation
			.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	public void validate(T request) throws ValidationException {
		Set<ConstraintViolation<T>> requestErrors = validator.validate(request);
		String errorMsg ;
		StringBuilder invalidFields = new StringBuilder();
		IMSRequestExceptionCodes code=null;
		for (ConstraintViolation<T> constraintViolation : requestErrors) {
			invalidFields.append(constraintViolation.getPropertyPath()
					.toString() + ",");
			errorMsg=constraintViolation
					.getMessageTemplate().toString();
			code= IMSRequestExceptionCodes.valueOf(errorMsg);
		}
		if (!requestErrors.isEmpty()) {
			String exceptionMsg = String.format(
					"invalid fields : %s in request %s", invalidFields, request
							.getClass().getName());
			log.error(exceptionMsg);
			throw new ValidationException(code.errCode(),code.errMsg());
		}
	}

}