package com.snapdeal.payments.view.request.commons.validator.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.payments.view.request.commons.validator.RequestViewSearchValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { RequestViewSearchValidator.class })
@Documented
public @interface RequestViewSearchRequestValidation {
	String message() default "{com.snapdeal.payments.requestVview.commons.validator.annotation."
			+ "RequestViewSearchRequestValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}	