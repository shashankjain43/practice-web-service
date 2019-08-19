/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.oms.base.model.GetShippingHistoryRequest;
import com.snapdeal.oms.base.model.GetShippingHistoryResponse;
import com.snapdeal.oms.base.sro.order.AddressDetailSRO;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.core.entity.UserOpenidMapping;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;
import com.snapdeal.ums.dao.user.userAddress.IUserAddressDao;
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
import com.snapdeal.ums.server.services.IUserAddressServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.sun.xml.bind.v2.TODO;

@Service("userAddressService")
@Transactional(rollbackFor=Exception.class)
public class UserAddressServiceImpl implements IUserAddressService {

    private static final Logger         LOG = LoggerFactory.getLogger(UserAddressServiceImpl.class);

    @Autowired
    private IUserAddressServiceInternal userAddressServiceInternal;

    @Autowired
    private IUMSConvertorService        umsConvertorService;
    
    @Autowired
    private IUserServiceInternal		userServiceInternal;
    
    @Autowired
    private IOrderClientService			orderClientService;
    
    @Autowired
    private IMSService imsService;
    
    @Autowired
    private IUserAddressDao				userAddressDao;
    
    private static final int MAX_USER_RESULTS = 20;
    
    private static final int MAX_USER_ADDRESSES_ALLOWED = 3;

    @Override
    public GetUserAddressByIdResponse getUserAddressById(GetUserAddressByIdRequest request) throws UserAddressException {

        GetUserAddressByIdResponse response = new GetUserAddressByIdResponse();
        if (!(request.getUserAddressId() == null || request.getUserAddressId() <= 0)) {
            UserAddressSRO userAddressSRO = umsConvertorService.getUserAddressSROFromEntity(userAddressServiceInternal.getUserAddressById(request.getUserAddressId()));
            response.setUserAddressSRO(userAddressSRO);
            response.setSuccessful(true);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;
    }


    @Override
    public GetUserAddressesByUserIdResponse getUserAddressesByUserId(GetUserAddressesByUserIdRequest getUserAddressByUserIdRequest) throws UserAddressException {

    	GetUserAddressesByUserIdResponse getUserAddressesByUserIdResponse = new GetUserAddressesByUserIdResponse();
    	if (!(getUserAddressByUserIdRequest.getUserId() == null || getUserAddressByUserIdRequest.getUserId() <= 0)) {
    		
    		List<UserAddressSRO> userAddressSROs = umsConvertorService.getUserAddressSROsFromEntities(userAddressServiceInternal.getUserAddressByUserId(getUserAddressByUserIdRequest.getUserId()));
    		getUserAddressesByUserIdResponse.setUserAddresses(userAddressSROs);
    		getUserAddressesByUserIdResponse.setSuccessful(true);

    	} else {
    		throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
    				UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
    	}
    	return getUserAddressesByUserIdResponse;
    }

    @Deprecated
    private List<UserAddressSRO> persistUserAddresses(List<UserAddressSRO> umsAddresses) {
    	List<UserAddressSRO> userAddresses = null;
    	if(CollectionUtils.isNotEmpty(umsAddresses)){
    		userAddresses = new ArrayList<UserAddressSRO>();
    		for(UserAddressSRO umsAddress:umsAddresses){
    			UserAddress userAddress = null;
    			try {
    				userAddress= umsConvertorService.getUserAddressEntityFromSRO(umsAddress);
    				userAddress = userAddressDao.mergeUserAddress(userAddress);
    				UserAddressSRO uaSRO = umsConvertorService.getUserAddressSROFromEntity(userAddress);
    				userAddresses.add(uaSRO);
    			} catch (Exception e) {
    				LOG.error("Exception while storing address:"+userAddress,e);
    			}
    		}
    		return userAddresses;
    	}
    	return null;

    }
    
	@Override
    public SetDefaultUserAddressResponse setDefaultUserAddress(SetDefaultUserAddressRequest request) throws UserAddressException {

        SetDefaultUserAddressResponse response = new SetDefaultUserAddressResponse();

        if (!(request.getUserAddressId() == null || request.getUserAddressId() <= 0 || request.getUserId() == null || request.getUserId() <= 0)) {

            if (!autorizationValidator(request.getEncryptedUserId(), request.getUserId())) {
                throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION,
                        UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION.description());
            }

            userAddressServiceInternal.setDefaultUserAddress(request.getUserAddressId(), request.getUserId());
            response.setSuccessful(true);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;

    }

	@Deprecated
    @Override
    public UpdateUserAddressResponse updateUserAddress(UpdateUserAddressRequest request) throws UserAddressException {

        UpdateUserAddressResponse response = new UpdateUserAddressResponse();
        if ((request.getUserAddressSRO() != null && request.getUserAddressSRO().getId() != null
                && request.getUserAddressSRO().getId() > 0 && request.getUserAddressSRO().getUserId() != null && request.getUserAddressSRO().getUserId() > 0)) {

            if (!autorizationValidator(request.getEncryptedUserId(), request.getUserAddressSRO().getUserId())) {
                throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION,
                        UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION.description());
            }

            UserAddressSRO userAddressSRO = null;
            userAddressSRO = umsConvertorService.getUserAddressSROFromEntity(userAddressServiceInternal.updateUserAddress(umsConvertorService.getUserAddressEntityFromSRO(request.getUserAddressSRO())));
            response.setUserAddress(userAddressSRO);
            response.setSuccessful(true);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;

    }

    @Override
    public AddUserAddressResponse addUserAddress(AddUserAddressRequest request) throws UserAddressException {

        AddUserAddressResponse response = new AddUserAddressResponse();
        if ((request.getUserAddress() != null && request.getUserAddress().getUserId() != null && request.getUserAddress().getUserId() > 0)) {

            if (!autorizationValidator(request.getEncryptedUserId(), request.getUserAddress().getUserId())) {
                throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION,
                        UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION.description());
            }

            int savedAddressId = userAddressServiceInternal.addUserAddress(
            		umsConvertorService.getUserAddressEntityFromSRO(request.getUserAddress()));
            response.setSavedAddressId(savedAddressId);
            response.setSuccessful(true);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;
    }


    @Override
    public DeleteUserAddressByIdResponse deleteUserAddressById(DeleteUserAddressByIdRequest request) throws UserAddressException {

        DeleteUserAddressByIdResponse response = new DeleteUserAddressByIdResponse();
        if (!(request.getUserAddressId() == null || request.getUserAddressId() <= 0 || request.getUserId() == null || request.getUserId() <= 0)) {

            if (!autorizationValidator(request.getEncryptedUserId(), request.getUserId())) {
                throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION,
                        UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION.description());
            }
            boolean success = userAddressServiceInternal.deleteUserAddressById(request.getUserAddressId(), request.getUserId());
            if(!success){
            	throw new UserAddressException(UserAddressException.UserAddressErrorCode.USER_ADDRESS_NOT_EXIST_OR_DELETED,
                        UserAddressException.UserAddressErrorCode.USER_ADDRESS_NOT_EXIST_OR_DELETED.description());
            }
            response.setSuccessful(success);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;
    }

    @Deprecated
    @Override
    public AddUserAddressTagResponse addUserAddressTag(AddUserAddressTagRequest request) throws UserAddressException {

        AddUserAddressTagResponse response = new AddUserAddressTagResponse();
        if (!(request.getUserAddressId() == null || request.getUserAddressId() <= 0 || request.getUserId() == null || request.getUserId() <= 0)) {

            if (!autorizationValidator(request.getEncryptedUserId(), request.getUserId())) {
                throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION,
                        UserAddressException.UserAddressErrorCode.REQUEST_AUTHORIZATION_VIOLATION.description());
            }
            userAddressServiceInternal.addUserAddressTag(request.getUserId(), request.getUserAddressId(), 
                    request.getAddressTag());
            response.setSuccessful(true);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;
    }
    
    @Override
    public UpdateUserAddressStatusResponse updateUserAddressStatus(UpdateUserAddressStatusRequest request) throws UserAddressException {

    	UpdateUserAddressStatusResponse response = new UpdateUserAddressStatusResponse();
        if (!(request.getUserAddressId() <= 0 || request.getUserId() <= 0 || request.getStatus() == null)) {

            boolean success= userAddressServiceInternal.updateUserAddressStatus(request.getUserAddressId(), request.getUserId(),request.getStatus());
            if(!success){
            	throw new UserAddressException(UserAddressException.UserAddressErrorCode.USER_ADDRESS_MAPPING_NOT_EXIST,
                        UserAddressException.UserAddressErrorCode.USER_ADDRESS_MAPPING_NOT_EXIST.description());
            }
            response.setSuccessful(success);
        } else {
            throw new UserAddressException(UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING,
                    UserAddressException.UserAddressErrorCode.REQUEST_PARAM_MISSING.description());
        }
        return response;
    }

    private boolean autorizationValidator(String encriptedUserId, Integer userId) {
        if (StringUtils.isNotEmpty(encriptedUserId) && userId != null && (userId.toString().equals(EncryptionUtils.decrypt(encriptedUserId)))) {
            return true;
        }
        LOG.info("Request violating encryption, BAD REQUEST ");
        return false;
    }
}
