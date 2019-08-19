/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.dao.user.userAddress;

import java.util.List;

import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.ext.userAddress.UserAddressStatus;

public interface IUserAddressDao {

    public void persistUserAddress(UserAddress userAddress);

    public UserAddress updateUserAddress(UserAddress userAddress);

    public boolean deleteUserAddressById(Integer userAddressId, Integer userId);

    public UserAddress getUserAddressById(Integer id);

    public List<UserAddress> getUserAddressesByUserId(Integer userId);

    public boolean disabledAllDefault(Integer userId);

    public boolean setDefaultAddress(Integer userAddressId, Integer userId);

    public boolean addUserAddressTag(Integer userId, Integer userAddressId, String addressTag);

    UserAddress mergeUserAddress(UserAddress userAddress);

	public boolean updateUserAddressStatus(int userAddressId, int userId,
			UserAddressStatus userAddressStatus);

	public boolean deactivateUserAddress(int userAddressId, int userId);

	public List<UserAddress> getAllUserAddressesByUserId(int userId);

	public int setIsActiveAddressById(int userAddressId, boolean isActive);

	public int setDefaultUserAddressbyId(int userAddressId);

	public UserAddress addUserAddress(UserAddress userAddress);

}
