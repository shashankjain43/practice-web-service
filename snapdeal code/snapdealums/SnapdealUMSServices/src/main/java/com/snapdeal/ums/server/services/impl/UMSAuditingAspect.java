package com.snapdeal.ums.server.services.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.snapdeal.base.audit.aspect.AuditingAspect;

@Configurable
@Aspect
public class UMSAuditingAspect 
{
    
    @Autowired
    private AuditingAspect auditingAspect;

    @Around("execution(@com.snapdeal.base.audit.annotation.AuditableMethod * com.snapdeal.ums.server.services.impl.EmailServiceInternalImpl.*(..))")
    /**
     * 
     * Audits the request and response of the services which are @AuditingEnabled 
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable
    {

        return auditingAspect.performAuditing(joinPoint);
    }

}
