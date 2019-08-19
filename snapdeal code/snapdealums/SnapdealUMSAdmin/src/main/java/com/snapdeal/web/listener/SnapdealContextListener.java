/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 16, 2010
 *  @author rahul
 */
package com.snapdeal.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.admin.startup.IAdminStartupService;
import com.snapdeal.ums.server.services.IUMSStartupService;
import com.snapdeal.web.utils.PathResolver;
import com.snapdeal.web.utils.WebContextUtils;

public class SnapdealContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(SnapdealContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Context initialized event called...");
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        IAdminStartupService startupService = context.getBean(IAdminStartupService.class);
        try {
            startupService.loadAll();
            startupService.initReloadCache();
        } catch (Exception e) {
            LOG.error("error while initializing application:", e);
        }
        
        sce.getServletContext().setAttribute("cache", CacheManager.getInstance());
        sce.getServletContext().setAttribute("path", new PathResolver());
        sce.getServletContext().setAttribute("dateUtils", new DateUtils());
        
        // Set the WEB-INF path separately as it is only available at web layer.
        String webInfPath = WebContextUtils.getServletContext().getRealPath("/WEB-INF");
        LOG.info("Setting the WEB-INF path to: {}", webInfPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing needed here
    }

}
