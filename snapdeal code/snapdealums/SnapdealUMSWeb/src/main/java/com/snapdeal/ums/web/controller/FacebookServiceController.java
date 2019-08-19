package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.ext.facebook.FacebookLikesRequest;
import com.snapdeal.ums.ext.facebook.FacebookLikesResponse;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;
import com.snapdeal.ums.services.facebook.IFacebookLikeService;
import com.snapdeal.ums.services.facebook.IFacebookUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/ums/facebook/")
public class FacebookServiceController {

    @Autowired
    private IFacebookUserService fbUserService;
    
    @Autowired
    private IFacebookLikeService fbLikeService;

    @RequestMapping(value = "addIfNotExistsFacebookUser", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse addIfNotExistsFacebookUser(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.addIfNotExistsFacebookUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateFacebookUser", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse updateFacebookUser(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.updateFacebookUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFacebookUserByUser", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse getFacebookUserByUser(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.getFacebookUserByUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFacebookUserByEmail", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse getFacebookUserByEmail(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.getFacebookUserByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFacebookUserbySDId", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse getFacebookUserbySDId(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.getFacebookUserbySDId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFacebookUserbyFBId", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse getFacebookUserbyFBId(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.getFacebookUserbyFBId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "facebookUserExists", produces = "application/sd-service")
    @ResponseBody
    public FacebookUserResponse facebookUserExists(@RequestBody FacebookUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookUserResponse response = fbUserService.facebookUserExists(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserFacebookLikesBySDId", produces = "application/sd-service")
    @ResponseBody
    public FacebookLikesResponse getUserFacebookLikesBySDId(@RequestBody FacebookLikesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookLikesResponse response = fbLikeService.getUserFacebookLikesBySDId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserFacebookLikesByFBId", produces = "application/sd-service")
    @ResponseBody
    public FacebookLikesResponse getUserFacebookLikesByFBId(@RequestBody FacebookLikesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookLikesResponse response = fbLikeService.getUserFacebookLikesByFBId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserFacebookLikesByEmail", produces = "application/sd-service")
    @ResponseBody
    public FacebookLikesResponse getUserFacebookLikesByEmail(@RequestBody FacebookLikesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookLikesResponse response = fbLikeService.getUserFacebookLikesByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addFaceBookLike", produces = "application/sd-service")
    @ResponseBody
    public FacebookLikesResponse addFaceBookLike(@RequestBody FacebookLikesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookLikesResponse response = fbLikeService.addFaceBookLike(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateFacebookLike", produces = "application/sd-service")
    @ResponseBody
    public FacebookLikesResponse updateFacebookLike(@RequestBody FacebookLikesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        FacebookLikesResponse response = fbLikeService.updateFacebookLike(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
}
