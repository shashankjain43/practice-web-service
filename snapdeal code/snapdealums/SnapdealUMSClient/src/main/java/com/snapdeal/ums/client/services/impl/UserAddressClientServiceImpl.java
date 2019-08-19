/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.client.services.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.client.services.IUserAddressClientService;
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
import com.snapdeal.ums.utils.EncryptionUtils;

@Service("userAddressClientServiceImpl")
public class UserAddressClientServiceImpl implements IUserAddressClientService {

    private final static String CLIENT_SERVICE_URL = "/userAddress";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    
    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/userAddress/", "umsserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }
    
    @Override
    public GetUserAddressByIdResponse getUserAddressById(GetUserAddressByIdRequest getUserAddressByIdRequest) throws TransportException {
        String url = getWebServiceURL() + "/getUserAddressById";
        GetUserAddressByIdResponse response = (GetUserAddressByIdResponse) transportService.executeRequest(url, getUserAddressByIdRequest, null, GetUserAddressByIdResponse.class);
        return response;
    }

    @Override
    public GetUserAddressesByUserIdResponse getUserAddressesByUserId(GetUserAddressesByUserIdRequest getUserAddressByUserIdRequest) throws TransportException {
        String url = getWebServiceURL() + "/getUserAddressesByUserId";
        GetUserAddressesByUserIdResponse response = (GetUserAddressesByUserIdResponse) transportService.executeRequest(url, getUserAddressByUserIdRequest, null,
                GetUserAddressesByUserIdResponse.class);
        return response;
    }

    @Deprecated
    public UpdateUserAddressResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest) throws TransportException {

        updateUserAddressRequest.setEncryptedUserId(EncryptionUtils.encrypt(updateUserAddressRequest.getUserAddressSRO().getUserId()));
        String url = getWebServiceURL() + "/updateUserAddress";
        UpdateUserAddressResponse response = (UpdateUserAddressResponse) transportService.executeRequest(url, updateUserAddressRequest, null, UpdateUserAddressResponse.class);
        return response;
    }

    @Override
    public AddUserAddressResponse addUserAddress(AddUserAddressRequest addUserAddressRequest) throws TransportException {

        addUserAddressRequest.setEncryptedUserId(EncryptionUtils.encrypt(addUserAddressRequest.getUserAddress().getUserId()));
        String url = getWebServiceURL() + "/addUserAddress";
        AddUserAddressResponse response = (AddUserAddressResponse) transportService.executeRequest(url, addUserAddressRequest, null, AddUserAddressResponse.class);
        return response;
    }

    @Override
    public DeleteUserAddressByIdResponse deleteUserAddressById(DeleteUserAddressByIdRequest deleteUserAddressByIdRequest) throws TransportException {

        deleteUserAddressByIdRequest.setEncryptedUserId(EncryptionUtils.encrypt(deleteUserAddressByIdRequest.getUserId()));
        String url = getWebServiceURL() + "/deleteUserAddressById";
        DeleteUserAddressByIdResponse response = (DeleteUserAddressByIdResponse) transportService.executeRequest(url, deleteUserAddressByIdRequest, null,
                DeleteUserAddressByIdResponse.class);
        return response;
    }

    @Override
    public SetDefaultUserAddressResponse setDefaultUserAddress(SetDefaultUserAddressRequest setDefaultUserAddressRequest) throws TransportException {

        setDefaultUserAddressRequest.setEncryptedUserId(EncryptionUtils.encrypt(setDefaultUserAddressRequest.getUserId()));
        String url = getWebServiceURL() + "/setDefaultUserAddress";
        SetDefaultUserAddressResponse response = (SetDefaultUserAddressResponse) transportService.executeRequest(url, setDefaultUserAddressRequest, null,
                SetDefaultUserAddressResponse.class);
        return response;
    }

    @Deprecated
    public AddUserAddressTagResponse addUserAddressTag(AddUserAddressTagRequest addUserAddressTagRequest) throws TransportException {

        addUserAddressTagRequest.setEncryptedUserId(EncryptionUtils.encrypt(addUserAddressTagRequest.getUserId()));
        String url = getWebServiceURL() + "/addUserAddressTag";
        AddUserAddressTagResponse response = (AddUserAddressTagResponse) transportService.executeRequest(url, addUserAddressTagRequest, null, AddUserAddressTagResponse.class);
        return response;
    }
    
    @Override
    public UpdateUserAddressStatusResponse updateUserAddressStatus(UpdateUserAddressStatusRequest request) throws TransportException {

        String url = getWebServiceURL() + "/updateUserAddressStatus";
        UpdateUserAddressStatusResponse response = (UpdateUserAddressStatusResponse) transportService.executeRequest(url, request, null,
        		UpdateUserAddressStatusResponse.class);
        return response;
    }


}
