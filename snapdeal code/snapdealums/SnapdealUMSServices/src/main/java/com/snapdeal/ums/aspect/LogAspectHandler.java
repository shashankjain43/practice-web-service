/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Feb 17, 2012
 *  @author anudeep
 */
package com.snapdeal.ums.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;

@Aspect
@Component
public class LogAspectHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspectHandler.class);
    
    @Pointcut("execution(* com.snapdeal.ums.dao..**(..))")
    public void executeDaoMethods() {
    }

    @Pointcut("execution(* com.snapdeal.ums.admin.tasks..**(..))")
    public void executeAdminMethods() {
    }

    @Pointcut("execution(* com.snapdeal.mao..**(..))")
    public void executeMaoMethods() {
    }

    @Pointcut("execution(* com.snapdeal.base.transport.service..**(..))")
    public void executeWebServiceRequests() {

    }

    @Pointcut("execution(* com.snapdeal.concurrent.ConcurrentRequestExecutorImpl.executeConcurrently(..))")
    public void executeConcurrentWebServiceRequests() {

    }
    
    @Pointcut("execution(* com.snapdeal.base.velocity..**(..))")
    public void executeVelocityTemplateMethods() {

    }

    @Pointcut("execution(* com.snapdeal.core.utils..**(..))")
    public void executeSearchMethods() {

    }

    @Pointcut("within(@org.springframework.stereotype.Service *) && !execution(* com.snapdeal.base.transport.service..**(..))")
    public void executeServiceMethods() {

    }
    
    @Around("executeDaoMethods() || executeAdminMethods() || executeMaoMethods() || executeVelocityTemplateMethods() || executeSearchMethods()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        return logMethodExecutionTime(pjp, false);
    }

    @Around("executeWebServiceRequests()")
    public Object profileWebServiceCalls(ProceedingJoinPoint pjp) throws Throwable {
        return logMethodExecutionTime(pjp, true);
    }
    
    @Around("executeServiceMethods()")
    public Object profileServiceCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        if(runServiceLogger()){
            return logMethodExecutionTime(joinPoint, false);
        }
        return joinPoint.proceed();
    }
    
    private Object logMethodExecutionTime(ProceedingJoinPoint joinPoint, boolean webRequest) throws Throwable {
        long timeStartInMillisecs = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object output = joinPoint.proceed();
        if(webRequest){
            LOG.info("Time Taken to execute : " + signature + " " + joinPoint.getArgs()[0] + " " + (System.currentTimeMillis() - timeStartInMillisecs) + " ms");
        } else {
            LOG.info("Time Taken to execute : " + signature + " " + (System.currentTimeMillis() - timeStartInMillisecs) + " ms");
        }
        return output;
    }
    
    private boolean runServiceLogger() {
        UMSPropertiesCache cache = CacheManager.getInstance().getCache(UMSPropertiesCache.class);
        if(cache != null){
            return Boolean.valueOf(cache.getProperty("log.service.calls", "false"));
        }
        return false;
    }
}
