/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 02-Nov-2012
 *  @author naveen
 */
package com.snapdeal.ums.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.memcached.service.IMemcachedService;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.metrics.Metrics;
import com.snapdeal.ums.metrics.MetricsReporter;
import com.snapdeal.ums.server.services.IUMSStartupService;

public class UMSContextListener implements ServletContextListener {

	private static final org.slf4j.Logger LOG = LoggerFactory
			.getLogger(UMSContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Context initialized event called...");
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		IUMSStartupService umsStartupService = context
				.getBean(IUMSStartupService.class);
		IMemcachedService memcachedService = context
				.getBean(IMemcachedService.class);
		try {
			umsStartupService.loadAllAtStartup();
			umsStartupService.initReloadCache();
		} catch (Exception e) {
			LOG.error("error while initializing application:", e);
		}
		sce.getServletContext().setAttribute("cache",
				CacheManager.getInstance());
		sce.getServletContext().setAttribute("dateUtils", new DateUtils());
		sce.getServletContext().setAttribute("memcache", memcachedService);

		LOG.info("Settings up Metrics Reporting");
		sce.getServletContext().setAttribute(Metrics.REGISTRY_ATTRIBUTE,
				Metrics.getRegistry());
		MetricsReporter graphiteReporter = new MetricsReporter();
		graphiteReporter.reportToGraphite();

	}
}
