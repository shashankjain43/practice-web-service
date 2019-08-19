package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.validator.TokenValidator;



@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =TokenValidator.class)
@Documented
public @interface Token {

	String message() default "{com.snapdeal.ims.validator.annotation."
			+ "globaltoken.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	

}
