package com.snapdeal.merchant.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.merchant.validator.PasswordValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {

	String message() default "{com.snapdeal.merchant.annotation."
			+ "PasswordValidation.message}";
 
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	boolean mandatory() default false;
	
}
