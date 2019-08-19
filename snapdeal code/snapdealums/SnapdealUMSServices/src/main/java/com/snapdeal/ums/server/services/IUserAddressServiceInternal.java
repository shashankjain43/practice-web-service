/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.server.services;

import java.util.List;

import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.userAddress.UserAddressStatus;

public interface IUserAddressServiceInternal {

    public UserAddress getUserAddressById(Integer id);
    
    public List<UserAddress> getUserAddressByUserId(Integer userId);
    
    public boolean setDefaultUserAddress (Integer id,Integer userId) throws UserAddressException;
    
    public UserAddress updateUserAddress (UserAddress userAddress) throws UserAddressException;
    
    public int addUserAddress (UserAddress userAddress) throws UserAddressException;
    
    public boolean deleteUserAddressById(Integer id,Integer userId);

    public boolean addUserAddressTag(Integer userId, Integer UserAddressId, String addressTag);

	public boolean updateUserAddressStatus(int userAddressId, int userId, UserAddressStatus userAddressStatus);

	public UserAddress addOnlyNewAddressFromOMS(UserAddress umsAddressFromOMS, List<UserAddress> existingUserAddresses);

	public List<UserAddress> getAllUserAddressByUserId(Integer userId);
    
}
