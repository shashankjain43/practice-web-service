package com.snapdeal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ums.admin.startup.IAdminStartupService;


@Controller
public class ReloadCacheController {
	private static final Logger LOG = LoggerFactory.getLogger(ReloadCacheController.class);
	
	@Autowired
	private IAdminStartupService startupService;
	
	@RequestMapping("/admin/reloadcacheinternal")
    @ResponseBody
	public String reloadCache(){
		try {
            startupService.loadAll();
        } catch (Exception e) {
            LOG.error("Exception while reloading Cache",e);
            return "unable to reload cache ... check logs for details";
        }
        return "Cache Reloaded SUCCESSFULLY";
		
	}

}
