package com.snapdeal.merchant.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.snapdeal.merchant.validator.MobileNumberValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy={MobileNumberValidator.class})
public @interface MobileNumber
{
  String message() default "{com.snapdeal.mob.annotation.MobileNumberValidation.message}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
  boolean mandatory() default false;
  
  @Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public static @interface List
  {
    MobileNumber[] value();
  }
}