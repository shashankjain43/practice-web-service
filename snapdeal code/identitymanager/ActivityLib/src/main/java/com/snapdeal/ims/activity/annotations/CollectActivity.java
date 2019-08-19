package com.snapdeal.ims.activity.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation @CollectActivity should be used to collect activity.
 * 
 * @author kishan
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CollectActivity {

}
