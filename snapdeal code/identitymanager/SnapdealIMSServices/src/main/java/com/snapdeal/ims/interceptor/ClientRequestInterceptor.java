package com.snapdeal.ims.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.snapdeal.ims.cache.AccessCache;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ICache;

@Slf4j
@Component
public class ClientRequestInterceptor  extends HandlerInterceptorAdapter {
   public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) 
                     throws Exception {
      log.debug("----Checking for Authentication----");
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {
          ipAddress = request.getRemoteAddr();
      }
      log.debug("---- IPAddress: " + ipAddress + "-----");
		ICache<String, Boolean> accessCache = CacheManager.getInstance()
				.getCache(AccessCache.class);
      Boolean accessGranted = false;
		if (accessCache != null && accessCache.get(ipAddress) != null) {
			accessGranted = accessCache.get(ipAddress);
      }
      request.setAttribute("special", "Acess granted to " + ipAddress + " : " + accessGranted.toString());
      log.debug("----Access granted: " + accessGranted + " to ip address: " + ipAddress+"----");
      /*if(!accessGranted){
         PrintWriter out = response.getWriter();
         String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
         out.println(docType +
                   "<html>" +
                   "<body>" + "Authentication failed for IP: " + ipAddress +
                   "</body></html>" );
      }*/
      
      return accessGranted;
   }
}
