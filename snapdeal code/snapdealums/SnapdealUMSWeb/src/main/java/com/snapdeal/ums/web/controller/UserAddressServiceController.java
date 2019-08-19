/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.userAddress.AddUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagResponse;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusResponse;
import com.snapdeal.ums.server.services.IUserAddressService;

@Controller
@RequestMapping("/service/ums/userAddress/")
public class UserAddressServiceController {

    @Autowired
    private IUserAddressService userAddressService;

    private static final Logger LOG = LoggerFactory.getLogger(UserAddressServiceController.class);

    @RequestMapping(value = "getUserAddressById", produces = "application/sd-service")
    @ResponseBody
    public GetUserAddressByIdResponse getUserAddressById(@RequestBody GetUserAddressByIdRequest getUserAddressByIdRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        GetUserAddressByIdResponse response = new GetUserAddressByIdResponse();
        try {
            response = userAddressService.getUserAddressById(getUserAddressByIdRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving getUserAddressById request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(getUserAddressByIdRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserAddressesByUserId", produces = "application/sd-service")
    @ResponseBody
    public GetUserAddressesByUserIdResponse getUserAddressesByUserId(@RequestBody GetUserAddressesByUserIdRequest getUserAddressByUserIdRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        GetUserAddressesByUserIdResponse response = new GetUserAddressesByUserIdResponse();
        try {
            response = userAddressService.getUserAddressesByUserId(getUserAddressByUserIdRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving getUserAddressByUserId request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(getUserAddressByUserIdRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "setDefaultUserAddress", produces = "application/sd-service")
    @ResponseBody
    public SetDefaultUserAddressResponse setDefaultUserAddress(@RequestBody SetDefaultUserAddressRequest setDefaultUserAddressRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SetDefaultUserAddressResponse response = new SetDefaultUserAddressResponse();
        try {
            response = userAddressService.setDefaultUserAddress(setDefaultUserAddressRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving setDefaultUserAddress request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(setDefaultUserAddressRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateUserAddress", produces = "application/sd-service")
    @ResponseBody
    public UpdateUserAddressResponse updateUserAddress(@RequestBody UpdateUserAddressRequest updateUserAddressRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UpdateUserAddressResponse response = new UpdateUserAddressResponse();
        try {
            response = userAddressService.updateUserAddress(updateUserAddressRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving updateUserAddress request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(updateUserAddressRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addUserAddress", produces = "application/sd-service")
    @ResponseBody
    public AddUserAddressResponse addUserAddress(@RequestBody AddUserAddressRequest addUserAddressRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AddUserAddressResponse response = new AddUserAddressResponse();
        try {
            response = userAddressService.addUserAddress(addUserAddressRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving persistUserAddress request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(addUserAddressRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "deleteUserAddressById", produces = "application/sd-service")
    @ResponseBody
    public DeleteUserAddressByIdResponse deleteUserAddressById(@RequestBody DeleteUserAddressByIdRequest deleteUserAddressByIdRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        DeleteUserAddressByIdResponse response = new DeleteUserAddressByIdResponse();
        try {
            response = userAddressService.deleteUserAddressById(deleteUserAddressByIdRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving deleteUserAddressById request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(deleteUserAddressByIdRequest.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addUserAddressTag", produces = "application/sd-service")
    @ResponseBody
    public AddUserAddressTagResponse addUserAddressTag(@RequestBody AddUserAddressTagRequest addUserAddressTagRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AddUserAddressTagResponse response = new AddUserAddressTagResponse();
        try {
            response = userAddressService.addUserAddressTag(addUserAddressTagRequest);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving deleteUserAddressById request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(addUserAddressTagRequest.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "updateUserAddressStatus", produces = "application/sd-service")
    @ResponseBody
    public UpdateUserAddressStatusResponse updateUserAddressStatus(@RequestBody UpdateUserAddressStatusRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	UpdateUserAddressStatusResponse response = new UpdateUserAddressStatusResponse();
        try {
            response = userAddressService.updateUserAddressStatus(request);
        } catch (UserAddressException userAddressException) {
            response.setSuccessful(false);
            response.setMessage(userAddressException.getErrorCode().description());
            response.addValidationError(new ValidationError(userAddressException.getErrorCode().code(), userAddressException.getErrorCode().description()));
            LOG.error("Error while serving updateUserAddressStatus request: ", userAddressException);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setMessage(exception.getMessage());
            response.addValidationError(new ValidationError(UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.code(),
                    UserAddressException.UserAddressErrorCode.UMS_USERADDRESS_INTERNALSERVER_ERROR.description()));
            LOG.error("UMS Internal Server Error", exception);
        }
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
}
