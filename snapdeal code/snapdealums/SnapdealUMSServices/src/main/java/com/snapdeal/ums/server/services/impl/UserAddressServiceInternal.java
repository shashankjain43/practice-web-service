/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.server.services.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.dao.user.userAddress.IUserAddressDao;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.exception.userAddress.UserAddressException.UserAddressErrorCode;
import com.snapdeal.ums.ext.userAddress.UserAddressStatus;
import com.snapdeal.ums.server.services.IUserAddressServiceInternal;

@Service("userAddressServiceInternal")
public class UserAddressServiceInternal implements IUserAddressServiceInternal {
	
	private static final Logger         LOG = LoggerFactory.getLogger(UserAddressServiceInternal.class);

    @Autowired
    private IUserAddressDao userAddressDao;

    @Override
    public UserAddress getUserAddressById(Integer id) {
        return userAddressDao.getUserAddressById(id);
    }

    @Override
    public List<UserAddress> getUserAddressByUserId(Integer userId) {
        return userAddressDao.getUserAddressesByUserId(userId);
    }
    
    @Override
    public List<UserAddress> getAllUserAddressByUserId(Integer userId) {
        return userAddressDao.getAllUserAddressesByUserId(userId);
    }

    @Override
    public boolean setDefaultUserAddress(Integer id, Integer userId) throws UserAddressException {

        userAddressDao.disabledAllDefault(userId);
        if (userAddressDao.setDefaultAddress(id, userId)) {
            return true;
        } else {
            throw new UserAddressException(UserAddressErrorCode.SET_DEFAULT_FAILURE,
                    UserAddressErrorCode.SET_DEFAULT_FAILURE.description());
        }
    }

    @Override
    public boolean addUserAddressTag(Integer userId, Integer userAddressId, String addressTag) {
        return userAddressDao.addUserAddressTag(userId, userAddressId, addressTag);
    }

    @Override
    public UserAddress updateUserAddress(UserAddress userAddress) throws UserAddressException {

        List<UserAddress> userAddresses = removeFromList(userAddress, userAddressDao.getUserAddressesByUserId(userAddress.getUserId()));
        if (!userAddresses.contains(userAddress)) {
            if (userAddress.isDefault()) {
                userAddressDao.disabledAllDefault(userAddress.getUserId());
            }
            return userAddressDao.updateUserAddress(userAddress);
        } else {
            throw new UserAddressException(UserAddressErrorCode.USER_ADDRESS_ALREADY_EXIST, UserAddressErrorCode.USER_ADDRESS_ALREADY_EXIST.description());
        }
    }

    @Override
    public int addUserAddress(UserAddress userAddress) throws UserAddressException {
    	
    	UserAddress savedAddress = null;

    	List<UserAddress> existingUserAddresses = userAddressDao.getAllUserAddressesByUserId(userAddress.getUserId());
    	List<UserAddress> existingActiveAddresses = userAddressDao.getUserAddressesByUserId(userAddress.getUserId());
        if (existingActiveAddresses.size() == CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit()) {
            throw new UserAddressException(UserAddressErrorCode.USER_ADDRESS_COUNT_LIMIT, UserAddressErrorCode.USER_ADDRESS_COUNT_LIMIT.description());
        }
        if (!existingUserAddresses.contains(userAddress)) {
            if (userAddress.isDefault()) {
            	userAddressDao.disabledAllDefault(userAddress.getUserId());
            }
            savedAddress = userAddressDao.addUserAddress(userAddress);
        } else {
        	//At this point we are sure of a duplicate address already exists in db
        	savedAddress = findAndUpdateVisiblityOfDuplicateAddress(existingUserAddresses, userAddress, true);
        }
		return savedAddress.getId();
    }
    
    /**
     * this method assumes that each new address is a distinct one as 
     * existingUserAdresses is fixed 
     */
    @Override
    public UserAddress addOnlyNewAddressFromOMS(UserAddress umsAddressFromOMS, List<UserAddress> existingUserAddresses) {
    
    	UserAddress savedAddress = null;
    	
    	if(CollectionUtils.isNotEmpty(existingUserAddresses)){
    		if (!existingUserAddresses.contains(umsAddressFromOMS)) {
                savedAddress = userAddressDao.mergeUserAddress(umsAddressFromOMS);
            }
    	}else{
    		//first address ever
    		savedAddress = userAddressDao.mergeUserAddress(umsAddressFromOMS);
    	}
		return savedAddress;
    }
    
	/**
	 * Aim of this function is to locate the duplicate address among all
	 * addresses of user and updates the visibility(isActive) of this duplicate
	 * address as required. This function assumes that userAddress is a part of 
	 * existingAddresses.
	 * 
	 * @param userAddress
	 * @param isActive
	 * @return UserAddress
	 * @throws UserAddressException
	 */
	private UserAddress findAndUpdateVisiblityOfDuplicateAddress(List<UserAddress> existingAddresses,
			UserAddress userAddress, boolean isActive) {

		int index = existingAddresses.indexOf(userAddress);
		UserAddress dupAddress = existingAddresses.get(index);
		// change isActive of existing address if required
		if (dupAddress.isActive() == !isActive) {
			if (setIsActiveAddressById(dupAddress.getId(), isActive)) {
				LOG.info("Successfully updated the IsActive to " + isActive
						+ " of old AddressId: " + dupAddress.getId()
						+ " for userId: " + dupAddress.getUserId());
				dupAddress.setActive(isActive);
			}
		}
		// if given address is Default=true then make existing one as default also
		if (userAddress.isDefault()) {
			userAddressDao.disabledAllDefault(dupAddress.getUserId());
			if (userAddressDao.setDefaultUserAddressbyId(dupAddress.getId()) > 0) {
				LOG.info("Successfully updated the isDefault to True of old AddressId: "
						+ dupAddress.getId()
						+ " for userId: "
						+ dupAddress.getUserId());
			}
			dupAddress.setDefault(true);
		}
		//handle changes of tag if any
		userAddressDao.addUserAddressTag(dupAddress.getUserId(), dupAddress.getId(), userAddress.getAddressTag());
		dupAddress.setAddressTag(userAddress.getAddressTag());
		return dupAddress;
	}
	
	private boolean setIsActiveAddressById(int userAddressId, boolean isActive) {
		return userAddressDao.setIsActiveAddressById(userAddressId, isActive) > 0;
	}

    @Override
    public boolean deleteUserAddressById(Integer id, Integer userId) {
        return userAddressDao.deactivateUserAddress(id, userId);
    }

    public List<UserAddress> removeFromList(UserAddress userAddress, List<UserAddress> userAddresses) {

        if (userAddress.getId() == null || userAddress.getId() < 1)
            return userAddresses;
        List<UserAddress> newUserAddress = new CopyOnWriteArrayList<UserAddress>(userAddresses);

        for (UserAddress userAdd : newUserAddress) {
            if (userAdd.getId() != null && userAdd.getId().equals(userAddress.getId())) {
                newUserAddress.remove(userAdd);
            }
        }
        return newUserAddress;
    }

	@Override
	public boolean updateUserAddressStatus(int userAddressId, int userId,
			UserAddressStatus userAddressStatus) {
		
		return userAddressDao.updateUserAddressStatus(userAddressId, userId,
				userAddressStatus);
	}
}
