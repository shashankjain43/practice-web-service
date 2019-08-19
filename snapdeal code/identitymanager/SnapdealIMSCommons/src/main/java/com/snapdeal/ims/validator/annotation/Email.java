package com.snapdeal.ims.validator.annotation;

import com.snapdeal.ims.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmailValidator.class })
@Documented
public @interface Email {
	String message() default "{com.snapdeal.ims.validator.annotation."
			+ "Email.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	
	boolean mandatory() default true;

}
