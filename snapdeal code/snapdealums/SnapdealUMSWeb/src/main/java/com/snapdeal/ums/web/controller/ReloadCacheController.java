 /*
*  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 10-Jan-2013
*  @author naveen
*/
package com.snapdeal.ums.web.controller;
/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 04-Jan-2013
 *  @author Naveen
 */

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.server.services.IUMSStartupService;


@Controller
public class ReloadCacheController {

    private static final Logger LOG = LoggerFactory.getLogger(ReloadCacheController.class);

    @Autowired
    private IUMSStartupService  startupService;

    @RequestMapping("/reloadcacheinternal")
    @ResponseBody
    public String reloadCache(@RequestParam(required = false, value = "type") String type, @RequestParam(required = false, value = "pwd") String password) {
        if(password.equals(StringUtils.trimToEmpty(CacheManager.getInstance().getCache(UMSPropertiesCache.class).getProperty("reload.cache.manual.password")))){
            try {
                
                startupService.reloadCache(type);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.toString());
                return "unable to reload cache ... check logs for details";
            }
    
           return "Cache Reloaded Successfully";
        }else{
            return "Authentication Failed";
        }
         
    }

    @RequestMapping("/umsHealthCheck")
    @ResponseBody
    public String omsHealthCheck(@RequestParam(required = false, value = "pwd") String password) {
        if ((CacheManager.getInstance().getCache(UMSPropertiesCache.class).getProperty("reload.cache.manual.password").equals(password))) {
            return "Authentication SUCCESS !!! We are UP :)";
        } else {
            return "Authentication FAILURE";
        }
    }

}
