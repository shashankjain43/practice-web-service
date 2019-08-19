package com.snapdeal.ums.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CronExpressionValidator implements
ConstraintValidator<CronExpression, String> {

public void initialize(CronExpression cronExpression) {
//To change body of implemented methods use File | Settings | File Templates.
}

public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
return org.quartz.CronExpression.isValidExpression(s);
}
}