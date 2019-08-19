package com.snapdeal.ims.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionTimestampFilter implements Filter {

   public static final String START_TIME = "startTime";
   public static final String END_TIME = "endTime";
   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
      HtmlResponseWrapper capturingResponseWrapper = new HtmlResponseWrapper(
               (HttpServletResponse) res);
     // HttpServletResponse response = (HttpServletResponse) res;
      long startTime = System.currentTimeMillis();
      try{
         chain.doFilter(request, capturingResponseWrapper);          
         String content =capturingResponseWrapper.getCaptureAsString();
                  res.getWriter().write(content);
         
         
         
      } finally{
         long endTime = System.currentTimeMillis();
         log.debug("Total Time: " + (endTime - startTime));
         capturingResponseWrapper.addHeader(START_TIME, String.valueOf(startTime));
         capturingResponseWrapper.addHeader(END_TIME, String.valueOf(endTime));
      }
   }

   @Override
   public void destroy() {
      // TODO Auto-generated method stub
      
   }

}
