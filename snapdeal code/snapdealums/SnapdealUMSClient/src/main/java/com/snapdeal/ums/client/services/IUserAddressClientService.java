/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.client.services;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.ext.userAddress.AddUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusResponse;

public interface IUserAddressClientService {

    public GetUserAddressByIdResponse getUserAddressById(GetUserAddressByIdRequest getUserAddressByIdRequest) throws TransportException;
    
    public GetUserAddressesByUserIdResponse getUserAddressesByUserId(GetUserAddressesByUserIdRequest getUserAddressByUserIdRequest) throws TransportException;
    
    public SetDefaultUserAddressResponse setDefaultUserAddress (SetDefaultUserAddressRequest setDefaultUserAddressRequest) throws TransportException;
    
    public AddUserAddressResponse addUserAddress (AddUserAddressRequest addUserAddressRequest) throws TransportException;
    
    public DeleteUserAddressByIdResponse deleteUserAddressById(DeleteUserAddressByIdRequest deleteUserAddressByIdRequest) throws TransportException;
    
    public UpdateUserAddressStatusResponse updateUserAddressStatus(UpdateUserAddressStatusRequest request) throws TransportException;

}
