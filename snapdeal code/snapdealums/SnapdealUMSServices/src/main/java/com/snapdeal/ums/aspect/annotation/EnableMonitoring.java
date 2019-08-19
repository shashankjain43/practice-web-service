package com.snapdeal.ums.aspect.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation to enable the services to get monitored. This annotation is available at runtime and can be used only to annotate methods. 
 * 
 * @author lovey
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnableMonitoring {

}
