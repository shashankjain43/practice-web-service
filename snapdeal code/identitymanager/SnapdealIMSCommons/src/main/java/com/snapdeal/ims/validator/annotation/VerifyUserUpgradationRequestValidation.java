package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.validator.VerifyUserUpgradationRequestValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { VerifyUserUpgradationRequestValidator.class })
@Documented
public @interface VerifyUserUpgradationRequestValidation {

	String message() default "{com.snapdeal.payments.ims.validator.annotation."
			+ "VerifyUserUpgradationRequestValidator.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}