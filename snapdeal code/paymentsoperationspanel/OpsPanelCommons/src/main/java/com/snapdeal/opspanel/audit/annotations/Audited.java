package com.snapdeal.opspanel.audit.annotations;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Audited {

	String context() default "not applicable";
	String searchId() default "not applicable";
	String reason() default "not applicable";
	String[] skipRequestKeys() default {};
	String[] skipResponseKeys() default {};
	int viewable() default 1;
	
}