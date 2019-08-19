 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 05-Nov-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.snapdeal.base.exception.InvalidConfigurationException;
import com.snapdeal.base.exception.InvalidFormatException;

public interface IUMSStartupService {

    public void loadAllAtStartup() throws Exception;
    
    public void initReloadCache();

    void loadMemcachedService() throws Exception;
    
    void loadUMSCacheService();

    void loadUMSProperties();

    void loadEmailTemplates();

    void loadSmsTemplates();

    void loadSmsChannels();

    void loadEmailChannels() throws InstantiationException, IllegalAccessException, ClassNotFoundException;

    void loadEmailDomains();

    void loadZonesAndCities();

    void loadOMSClientConfig();

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

    ApplicationContext getApplicationContext();

    void reloadCache(String type) throws Exception;

    void loadAll() throws Exception;

    void loadShippingClientConfig();

    void loadLocalities();

    void loadSystemFileProperties();

    void loadSystemFilePropertiesCache(String configPath, String fileName) throws InvalidFormatException, InvalidConfigurationException, IllegalArgumentException;

    void loadIPMSClientConfig();
    
    void initializeVelocityLogger();
}

 