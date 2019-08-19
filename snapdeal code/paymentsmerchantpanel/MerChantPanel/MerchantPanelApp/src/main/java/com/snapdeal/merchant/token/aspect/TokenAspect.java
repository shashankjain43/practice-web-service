package com.snapdeal.merchant.token.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.AbstractMerchantRequest;
import com.snapdeal.merchant.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TokenAspect {

   @Autowired
   private HttpServletRequest httpRequest;

   @Pointcut("execution(public * com.snapdeal.merchant.rest.controller.*.*(..))")
   private void extractToken() {
   }

   @Pointcut("!execution(public * com.snapdeal.merchant.rest.controller.SessionController.*(..))")
   private void sessionExclusions() {
   }

  @Pointcut("!execution(public * com.snapdeal.merchant.rest.controller.HealthCheckController.*(..))")
   private void healthExclusions() {
   }
   

   @Before("extractToken() && healthExclusions() && sessionExclusions()")
   public void before(JoinPoint jpoint) throws MerchantException {
      Object[] requestArgs = jpoint.getArgs();
      AbstractMerchantRequest request = null;
      for (int i = 0; i < requestArgs.length; i++) {
         if (requestArgs[i] instanceof AbstractMerchantRequest) {
            request = (AbstractMerchantRequest) requestArgs[i];
            break;
         }
      }
      if(request != null) {
    	  String token = httpRequest.getHeader(AppConstants.token);
          request.setToken(token);
          request.setMerchantId(httpRequest.getHeader(AppConstants.merchantId));
          String userId = (String) httpRequest.getAttribute(AppConstants.userId);
          request.setLoggedUserId(userId);
          String loginName = (String) httpRequest.getAttribute(AppConstants.loginName);
          request.setLoggedLoginName(loginName);
          
      }
      
     
   }

}
