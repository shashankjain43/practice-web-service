package com.snapdeal.admin.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.admin.request.CreateIMSApiRequest;
import com.snapdeal.admin.request.UpdateIMSApiRequest;
import com.snapdeal.admin.response.GetAllIMSApisResponse;
import com.snapdeal.admin.response.createIMSApiResponse;
import com.snapdeal.admin.response.updateIMSApiResponse;
import com.snapdeal.admin.service.IIMSApisService;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.ValidationException;

/**
 * @author himanshu
 *
 */
@Controller
@RequestMapping(value = "admin/ims_apis")
@Slf4j
public class IMSApisManagementController {
   
   @Autowired
   private IIMSApisService imsApisService;
   
   @RequestMapping(value = "list", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.GET})
   @ResponseBody
   public GetAllIMSApisResponse listAllApis(
         HttpServletRequest request, ModelMap model)
         throws ValidationException {
      log.debug("Calling list All Apis Method");
      return imsApisService.getAllApis(request);
   }

   @RequestMapping(value = "create", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST})
   @ResponseBody
   public createIMSApiResponse insertApi(@ModelAttribute CreateIMSApiRequest request,
         HttpServletRequest httpRequest, ModelMap model)
         throws ValidationException {
      log.debug("Calling insert Api Method");
      return imsApisService.createIMSApi(request);
   }

   @RequestMapping(value = "update", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST})
   @ResponseBody
   public updateIMSApiResponse updateApi(@ModelAttribute UpdateIMSApiRequest request,
         HttpServletRequest httpRequest, ModelMap model)
         throws ValidationException {
      log.debug("Calling update Api Method");
      return imsApisService.updateIMSApi(request);
   }

}
