package com.snapdeal.admin.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.snapdeal.ims.common.constant.RestURIConstants;

@Controller
@RequestMapping(value = "/admin")
@Slf4j
// @RequestMapping(value = "/api/v1/identity/admin")
public class AdminController {
   

	@RequestMapping(value = "", produces = RestURIConstants.APPLICATION_JSON, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String adminDashBoard(ModelMap model) {
		log.debug("Calling Admin Dash Board Method");
		return "/admin/adminReport";
	}
	
	@RequestMapping(value = "/manage/client", produces = RestURIConstants.APPLICATION_JSON, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String manageClient(ModelMap model) {
		log.debug("Calling Manage Client Method");
		return "/admin/manage/client";
	}
	
	@RequestMapping(value = "/manage/configuration", produces = RestURIConstants.APPLICATION_JSON, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String manageConfiguration(ModelMap model) {
		log.debug("Calling Manage Configuration Method");
		return "/admin/manage/configuration";
	}

  @RequestMapping(value = "/manage/ims_apis", produces = RestURIConstants.APPLICATION_JSON, method = {
           RequestMethod.POST, RequestMethod.GET })
     public String manageIMSApis(ModelMap model) {
        log.debug("Calling Manage Configuration Method");
        return "/admin/manage/ims_apis";
     }
	
	@RequestMapping(value = "/manage/whitelist_api/{clientId}", produces = RestURIConstants.APPLICATION_JSON, method = {
         RequestMethod.POST, RequestMethod.GET })
   public String manageApiWhiteList(@PathVariable(value="clientId") String clientId, ModelMap model) {
      log.debug("Calling Manage API whitelist Method");
      model.addAttribute("clientId", clientId);
      return "/admin/manage/whitelistApi";
   }
}
