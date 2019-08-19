package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.loyalty.LoyaltyUploadRq;
import com.snapdeal.ums.loyalty.LoyaltyUploadRs;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.services.loyalty.ILoyaltyUserService;
import com.snapdeal.ums.services.loyalty.LoyaltyUploadManager;

/**
 * Controller for all the loyalty related requests! As of now, 21-April-2014,
 * only SNAPBOX is supported.
 * 
 * @author ashish
 * 
 */
@Controller
@RequestMapping("/service/ums/loyalty/")
public class LoyaltyController
{

    @Autowired
    private ILoyaltyUserService loyaltyService;

    @RequestMapping(value = "activateLoyalty", produces = "application/sd-service")
    @ResponseBody
    public SnapBoxActivationResponse activateSnapBox(@RequestBody SnapBoxActivationRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {

        SnapBoxActivationResponse response = loyaltyService.activateSnapBox(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "verifyActivateSnapBox", produces = "application/sd-service")
    @ResponseBody
    public SnapBoxActivationResponse verifyActivateSnapBox(@RequestBody SnapBoxVerificationActivationRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {

        SnapBoxActivationResponse response = loyaltyService.verifyActivateSnapBox(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getLoyaltyStatus", produces = "application/sd-service")
    @ResponseBody
    public LoyaltyUserStatusResponse getStatus(@RequestBody LoyaltyUserStatusRequest statusRequest, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {

        LoyaltyUserStatusResponse response = loyaltyService.getLoyaltyStatus(statusRequest);
        response.setProtocol(statusRequest.getResponseProtocol());
        return response;
    }

    @Autowired
    private LoyaltyUploadManager loyaltyUploadManager;

    @RequestMapping(value = "loyaltyUpload", produces = "application/sd-service")
    @ResponseBody
    public LoyaltyUploadRs upload(@RequestBody LoyaltyUploadRq request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {

        LoyaltyUploadRs response = new LoyaltyUploadRs();
        try {
            response = loyaltyUploadManager.processLoyaltyUploads(request);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
