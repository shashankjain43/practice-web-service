/*
 * Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @version 1.0, Aug 21, 2010
 * 
 * @author singla
 */
package com.snapdeal.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.snapdeal.base.utils.CookieManager;
import com.snapdeal.web.utils.WebContextUtils;

public class RequestIdentifierFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {

        CookieManager.setUserTrackingId(request, response);
        if (CookieManager.getCookie(request, "u") != null) {
            MDC.put("requestId", CookieManager.getCookie(request, "u").getValue());
        }
        
        String adminUser = "UMSAdminUser";
        if (null != WebContextUtils.getCurrentUserEmail()) {
            adminUser =WebContextUtils.getCurrentUserEmail();
        }
        MDC.put("adminUser", adminUser);

        chain.doFilter(request, response);

    }
}
