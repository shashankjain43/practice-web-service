package com.snapdeal.merchant.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.merchant.validator.EmailValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE })
@Constraint(validatedBy = { EmailValidator.class })
public @interface Email {

   String message() default "{com.snapdeal.merchant.annotation." + "EmailValidation.message}";

   Class<?>[]groups() default {};

   Class<? extends Payload>[]payload() default {};

   boolean mandatory() default false;

}
