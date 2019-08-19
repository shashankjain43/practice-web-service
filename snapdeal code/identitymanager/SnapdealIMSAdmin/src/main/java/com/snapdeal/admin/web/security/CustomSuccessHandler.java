/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 22, 2010
 *  @author singla
 */
package com.snapdeal.admin.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger   LOG = LoggerFactory.getLogger(CustomSuccessHandler.class);

	private static final String ADMIN_URL = "/admin";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		DefaultSavedRequest target = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		if(target==null ||( StringUtils.isNotBlank(target.getRequestURI()) && !target.getRequestURI().contains("admin"))){
			getRedirectStrategy().sendRedirect(request, response, ADMIN_URL);
		}else{
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.determineTargetUrl(request, response));
		if (builder.indexOf("?") == -1) {
			builder.append('?');
		} else {
			builder.append('&');
		}
		return builder.append("loginSuccess=success").toString();
	}

}
