package com.snapdeal.notifier.validator;

import com.snapdeal.notifier.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

public class GenericValidator<T> {

   private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
   private final Validator validator = factory.getValidator();

   public void validate(T request) throws ValidationException {

      Set<ConstraintViolation<T>> requestErrors = validator.validate(request);
      StringBuilder invalidFields = new StringBuilder();
      for (ConstraintViolation<T> constraintViolation : requestErrors) {
         invalidFields.append(constraintViolation.getPropertyPath().toString() + ",");
      }
      if (!requestErrors.isEmpty()) {
         String exceptionMsg = String.format("invalid fields : %s in request %s", invalidFields,
                  request.getClass().getName());
         throw new ValidationException(exceptionMsg);
      }
   }

}