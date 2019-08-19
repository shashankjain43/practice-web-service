package com.snapdeal.ims.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.validator.NameValidator;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NameValidator.class })
@Documented
public @interface Name {
	String message() default "{com.snapdeal.ims.validator.annotation."
			+ "NameVailidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
		
	NameType type() default NameType.FIRST;
	enum NameType {
	   FIRST, MIDDLE, LAST,DISPLAY;
	}
}
