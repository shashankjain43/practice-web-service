package com.snapdeal.ums.services.auditing;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuditiOncePerRequestFilter extends OncePerRequestFilter

{

    @Autowired
    private static  Logger log = LoggerFactory
        .getLogger(AuditiOncePerRequestFilter.class);
    @Autowired
    private static  Logger log2 = LoggerFactory
        .getLogger(AuditiOncePerRequestFilter.class);
    
    
    @Override
    protected void initFilterBean() throws ServletException
    {
        super.initFilterBean();
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
                log =log2= ctx.getBean(Logger.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, FilterChain filterchain)
        throws ServletException, IOException
    {
        String uri = httpservletrequest.getRequestURI();
        log.info("In AuditiOncePerRequestFilter. THE URI is "+uri);

        log2.info("In log2#AuditiOncePerRequestFilter. THE URI is "+uri);
        filterchain.doFilter(httpservletrequest, httpservletresponse);

        // TODO Auto-generated method stub

    }

}
