package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.validator.UserUpgradeByEmailRequestValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UserUpgradeByEmailRequestValidator.class })
@Documented
public @interface UserUpgradeByEmailRequestValidation {

	String message() default "{com.snapdeal.payments.ims.validator.annotation."
			+ "UserUpgradeByEmailRequestValidator.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}