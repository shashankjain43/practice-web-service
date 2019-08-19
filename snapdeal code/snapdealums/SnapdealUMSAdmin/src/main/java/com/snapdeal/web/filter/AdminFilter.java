package com.snapdeal.web.filter;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.services.accesscontrol.IAccessControlService;
import com.snapdeal.web.security.SnapdealUser;
import com.snapdeal.web.utils.WebContextUtils;

public class AdminFilter extends OncePerRequestFilter {

    @Autowired
    private IAccessControlService accessControlService;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        accessControlService = ctx.getBean(IAccessControlService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, FilterChain filterchain) throws ServletException, IOException {
        SnapdealUser snapdealUser = WebContextUtils.getCurrentUser();
        String uri = httpservletrequest.getRequestURI();
        if (uri.endsWith("/"))
            uri = uri.substring(0, uri.lastIndexOf("/"));
        if (uri.equalsIgnoreCase("/admin") || snapdealUser.hasRole("SUPER")) {
            filterchain.doFilter(httpservletrequest, httpservletresponse);
        } else {
            String role = null;
            boolean hasAnyRole = true;
            while (true) {
                role = accessControlService.getUserRoleByPattern(uri);
                if (role != null) {
                    break;
                }
                uri = uri.substring(0, uri.lastIndexOf("/"));
                if (StringUtils.isEmpty(uri))
                    break;
            }
            if (role != null)
                hasAnyRole = snapdealUser.hasAnyRole(role);
            if (hasAnyRole) {
                filterchain.doFilter(httpservletrequest, httpservletresponse);
            } else {
                httpservletresponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }

    }

}

