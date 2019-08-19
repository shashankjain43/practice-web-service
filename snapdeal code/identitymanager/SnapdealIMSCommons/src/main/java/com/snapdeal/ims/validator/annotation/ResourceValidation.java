package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.validator.ResourceValidator;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ResourceValidator.class })
@Documented
public @interface ResourceValidation {

	String message() default "{com.snapdeal.ims.validator.annotation."
			+ "ResourceValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	boolean mandatory() default false;

}
