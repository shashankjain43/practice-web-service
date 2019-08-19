/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jan 4, 2012
 *  @author anudeep
 */
package com.snapdeal.web.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.util.StopWatch;

public class ResponseTimeInterceptor extends CustomizableTraceInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        String name = invocation.getMethod().getDeclaringClass().getName() + "." + invocation.getMethod().getName();
        StopWatch stopWatch = new StopWatch(name);
        Object returnValue = null;
        boolean exitThroughException = false;
        try {
            stopWatch.start(name);
            returnValue = invocation.proceed();
            return returnValue;
        } catch (Throwable ex) {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            exitThroughException = true;
            throw ex;
        } finally {
            if (!exitThroughException) {
                if (stopWatch.isRunning()) {
                    stopWatch.stop();
                }
//                if (stopWatch.getLastTaskTimeMillis() > CacheManager.getInstance().getCache(SystemPropertiesCache.class).getResponseTimeTracingThreshold())
//                    LOG.info(stopWatch.getLastTaskName() + " : " + stopWatch.getLastTaskTimeMillis() + " ms");
            }

        }
    }

    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return true;
    }
}
