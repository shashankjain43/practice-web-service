/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.server.services;

import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagResponse;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.ext.userAddress.AddUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressStatusResponse;

public interface IUserAddressService {
    
    public GetUserAddressByIdResponse getUserAddressById(GetUserAddressByIdRequest getUserAddressByIdRequest) throws UserAddressException;
    
    public GetUserAddressesByUserIdResponse getUserAddressesByUserId(GetUserAddressesByUserIdRequest getUserAddressByUserIdRequest) throws UserAddressException;
    
    public SetDefaultUserAddressResponse setDefaultUserAddress (SetDefaultUserAddressRequest setDefaultUserAddressRequest) throws UserAddressException;
    
    public UpdateUserAddressResponse updateUserAddress (UpdateUserAddressRequest updateUserAddressRequest) throws UserAddressException;
    
    public AddUserAddressResponse addUserAddress (AddUserAddressRequest addUserAddressRequest) throws UserAddressException;
    
    public DeleteUserAddressByIdResponse deleteUserAddressById(DeleteUserAddressByIdRequest deleteUserAddressByIdRequest) throws UserAddressException;

    public AddUserAddressTagResponse addUserAddressTag(AddUserAddressTagRequest addUserAddressTagRequest) throws UserAddressException;

	UpdateUserAddressStatusResponse updateUserAddressStatus(
			UpdateUserAddressStatusRequest request) throws UserAddressException;
    

}
