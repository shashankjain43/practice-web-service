/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 16, 2010
 *  @author rahul
 */
package com.snapdeal.task.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.task.service.IStartupService;

public class SnapdealTaskContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(SnapdealTaskContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Initializing Context...");
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        IStartupService startupService = context.getBean(IStartupService.class);
        try {
            startupService.loadAll();
        } catch (Exception e) {
            LOG.error("Error while initializing application. Exiting.", e);
            throw new RuntimeException(e);
        }
        startupService.loadAll();
        sce.getServletContext().setAttribute("cache", CacheManager.getInstance());
        sce.getServletContext().setAttribute("dateUtils", new DateUtils());
        LOG.info("Context Initialized Successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing needed here
    }

}
