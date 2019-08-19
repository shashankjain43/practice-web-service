package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.services.userNeftDetails.IUserNEFTDetailsService;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsResponse;

/**
 * Controller for all the user NEFT details related services
 * 
 * @author ashish
 * 
 */
@Controller
@RequestMapping("/service/ums/user/neftDetails/")
public class UserNEFTDetailsServiceController
{

    @Autowired
    private IUserNEFTDetailsService userNEFTDetailsService;

    @RequestMapping(value = "addVerifyActivateUserNEFTDetails", produces = "application/sd-service")
    @ResponseBody
    public AddUserNEFTDetailsResponse addVerifyActivateUserNEFTDetails(@RequestBody AddUserNEFTDetailsRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {

        AddUserNEFTDetailsResponse response = userNEFTDetailsService.addVerifyActivateUserNEFTDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "verifyActivateExistingUserNEFTDetails", produces = "application/sd-service")
    @ResponseBody
    public VerifyUserNEFTDetailsResponse verifyActivateExistingUserNEFTDetails(
        @RequestBody VerifyUserNEFTDetailsRequest request, HttpServletRequest httpServletRequest  , 
        HttpServletResponse httpServletResponse)
        throws TransportException
    {

        VerifyUserNEFTDetailsResponse response = userNEFTDetailsService.verifyActivateExistingUserNEFTDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActiveUserNEFTDetails", produces = "application/sd-service")
    @ResponseBody
    public GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetails(
        @RequestBody GetActiveUserNEFTDetailsRequest request, HttpServletRequest httpServletRequest  , 
        HttpServletResponse httpServletResponse)
        throws TransportException
    {

        GetActiveUserNEFTDetailsResponse response = userNEFTDetailsService.getActiveUserNEFTDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "deActivateUserNEFTDetails", produces = "application/sd-service")
    @ResponseBody
    public DeactivateUserNEFTDetailsResponse deActivateUserNEFTDetails(
        @RequestBody DeactivateUserNEFTDetailsRequest request)
        throws TransportException
    {

        DeactivateUserNEFTDetailsResponse response = userNEFTDetailsService.deActivateUserNEFTDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
