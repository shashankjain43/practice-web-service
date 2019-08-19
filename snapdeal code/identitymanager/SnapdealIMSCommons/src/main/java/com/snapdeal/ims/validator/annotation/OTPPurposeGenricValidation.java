package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.validator.OTPPurposeGenricValidator;


@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { OTPPurposeGenricValidator.class })
@Documented
public @interface OTPPurposeGenricValidation {
	String message() default "{com.snapdeal.ims.validator.annotation."
			+ "OTPPurposeGenricValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}