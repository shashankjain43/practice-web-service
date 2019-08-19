package com.snapdeal.ims.activity;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.activity.annotations.CollectActivity;

/**
 * This class intercepts @CollectActivity annotation and the user needs to
 * implement ValidateRequest interface and compute the implements methods.
 * 
 * @author kishan
 *
 */

@Component
@Aspect
@Slf4j
@Order(7)
public class ActivityMethodInterceptor {

	@Autowired
	private IActivityService activityService;

	@Around("within(com.snapdeal..*) && @annotation(collectActivity)")
	public Object logActivity(ProceedingJoinPoint pjp,
			CollectActivity collectActivity) throws Throwable {
		Object[] args = pjp.getArgs();
		String className = pjp.getSignature().getDeclaringType().getName();
		String activityName = className
				.substring(className.lastIndexOf(".") + 1);
		String methodName = pjp.getSignature().getName();
		Object request = args[0];
		Object response = null;
		try {
			response = pjp.proceed();
			boolean isActivityLogged = activityService.logActivity(request,
					response, activityName, methodName);
			if (!isActivityLogged) {
				log.error("Failed to log activity");
			}
		} catch (Exception e) {
			// log failure
			boolean isActivityLogged = activityService.logActivity(request,
					response, activityName, methodName);
			if (!isActivityLogged) {
				log.error("Failed to log activity", e);
			}
			throw e;
		}
		return response;
	}
}
