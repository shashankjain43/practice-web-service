package com.snapdeal.admin.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.admin.request.GetWhiteListAPIsStatusRequest;
import com.snapdeal.admin.request.WhitelistApiUpdateStatusRequest;
import com.snapdeal.admin.response.ClientWhiteListAPIsResponse;
import com.snapdeal.admin.response.ClientWhiteListAPIsUpdateResponse;
import com.snapdeal.admin.service.IWhitelistAPIsService;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.ValidationException;

@Controller
@Slf4j
@RequestMapping(value = "/admin/whitelist_api")
public class WhitelistAPIManagementController extends AbstractController {

   @Autowired
   private IWhitelistAPIsService whitelistAPIsService;
   
   /**
    * This api is responsible for fetching out list of all ims api along with there whitelist status
    * 
    * @param request
    * @param httpRequest
    * @return
    * @throws ValidationException
    */
   @RequestMapping(value = "/{clientId}/get_whitelist_apis_status", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
   @ResponseBody
   public ClientWhiteListAPIsResponse getWhiteListAPIsStatus(@ModelAttribute GetWhiteListAPIsStatusRequest request,
            HttpServletRequest httpRequest) throws ValidationException{
      return whitelistAPIsService.getWhiteListAPIsStatus(request, httpRequest);
   }
   
   /**
    * This api is used to allow client use an IMS API
    * 
    * @param request
    * @param httpRequest
    * @return
    * @throws ValidationException
    */
   @RequestMapping(value = "/update/allow", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public ClientWhiteListAPIsUpdateResponse allowApi(@ModelAttribute WhitelistApiUpdateStatusRequest request,
            HttpServletRequest httpRequest) throws ValidationException{
      return whitelistAPIsService.allowAPI(request);
   }

   /**
    * This api is used to restrict client use an IMS API
    * 
    * @param request
    * @param httpRequest
    * @return
    * @throws ValidationException
    */
   @RequestMapping(value = "/update/restrict", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public ClientWhiteListAPIsUpdateResponse restrictApi(@ModelAttribute WhitelistApiUpdateStatusRequest request,
            HttpServletRequest httpRequest) throws ValidationException{
      return whitelistAPIsService.restrictAPI(request);
   }

}
