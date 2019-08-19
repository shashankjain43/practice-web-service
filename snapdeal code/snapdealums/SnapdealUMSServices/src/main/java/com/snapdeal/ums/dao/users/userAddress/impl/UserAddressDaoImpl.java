/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.dao.users.userAddress.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.dao.user.userAddress.IUserAddressDao;
import com.snapdeal.ums.ext.userAddress.UserAddressStatus;

@Repository("userAddressDao")
public class UserAddressDaoImpl implements IUserAddressDao{

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persistUserAddress(UserAddress userAddress) {
        userAddress.setUpdated(DateUtils.getCurrentTime());
        userAddress.setCreated(DateUtils.getCurrentTime());
        sessionFactory.getCurrentSession().persist(userAddress);
        
    }
    
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public UserAddress mergeUserAddress(UserAddress userAddress) {
    	Date date = DateUtils.getCurrentTime();
        userAddress.setUpdated(date);
        userAddress.setCreated(date);
        return (UserAddress)sessionFactory.getCurrentSession().merge(userAddress);
        
    }
    
	@Override
	public UserAddress addUserAddress(UserAddress userAddress) {
		Date date = DateUtils.getCurrentTime();
		userAddress.setUpdated(date);
		userAddress.setCreated(date);
		return (UserAddress) sessionFactory.getCurrentSession().merge(
				userAddress);

	}

    @Override
    public UserAddress updateUserAddress(UserAddress userAddress) {
        userAddress.setUpdated(DateUtils.getCurrentTime());
        return (UserAddress)sessionFactory.getCurrentSession().merge(userAddress);
    }

    @Override
    public boolean deleteUserAddressById(Integer userAddressId, Integer userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from UserAddress where id = :id and user_id = :userId");
        query.setParameter("id", userAddressId);
        query.setParameter("userId",userId);
        return query.executeUpdate() > 0;
    }
    
	/**
	 * This function is used to de-activate(soft delete) an address of user.
	 * This will not be sent across when asked for user addresses. This makes
	 * sure to turn off setDefault flag. Returns false if addressId-userId not
	 * exist in db or already soft-deleted.
	 */
	@Override
	public boolean deactivateUserAddress(int userAddressId, int userId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update UserAddress set isActive = :isActive, isdefault = :isDefault where "
								+ "id = :userAddressId and userId=:userId and isActive = :isActivePreUpdate");
		query.setParameter("userAddressId", userAddressId);
		query.setParameter("isActive", false);
		query.setParameter("isActivePreUpdate", true);
		query.setParameter("isDefault", false);
		query.setParameter("userId", userId);
		return query.executeUpdate() > 0;
	}
    
	@Override
	public int setIsActiveAddressById(int userAddressId, boolean isActive) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update UserAddress set isActive = :isActive where id = :userAddressId");
		query.setParameter("userAddressId", userAddressId);
		query.setParameter("isActive", isActive);
		return query.executeUpdate();

	}
    
	@Override
	public int setDefaultUserAddressbyId(int userAddressId) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update UserAddress set isDefault = :isDefault where id = :userAddressId");
		query.setParameter("userAddressId", userAddressId);
		query.setParameter("isDefault", true);
		return query.executeUpdate();

	}
    
    
    @Override
    public UserAddress getUserAddressById(Integer id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from UserAddress where id = :id");
        query.setParameter("id", id);
        return (UserAddress)query.uniqueResult();
    }

    
    /**
     * This returns only active addresses of the user.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserAddress> getUserAddressesByUserId(Integer userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from UserAddress where userId = :userId and isActive = :isActive order by isDefault desc ,updated desc");
        query.setParameter("userId", userId);
        query.setParameter("isActive", true);
        return query.list();
    }
    
    /**
     * This returns all addresses(active + in-active) of user.
     */
	@Override
	public List<UserAddress> getAllUserAddressesByUserId(int userId) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from UserAddress where userId = :userId order by isDefault desc ,updated desc");
		query.setParameter("userId", userId);
		query.setMaxResults(400);
		return query.list();

	}
    
    @Override
    public boolean disabledAllDefault(Integer userId){
        Query query = sessionFactory.getCurrentSession().createQuery("update UserAddress set isdefault = :isDefault where userId = :userId");
        query.setParameter("userId", userId);
        query.setParameter("isDefault", false);
        return query.executeUpdate() > 0;
    }
    
    @Override
    public boolean setDefaultAddress(Integer userAddressId, Integer userId){
        Query query = sessionFactory.getCurrentSession().createQuery("update UserAddress set isdefault = :isDefault where "
        		+ "userId = :userId and id = :userAddressId and isActive=:isActive");
        query.setParameter("userId", userId);
        query.setParameter("userAddressId", userAddressId);
        query.setParameter("isDefault", true);
        query.setParameter("isActive", true);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean addUserAddressTag(Integer userId,Integer userAddressId, String addressTag){
        Query query = sessionFactory.getCurrentSession().createQuery("update UserAddress set addressTag = :addressTag where userId = :userId and id = :userAddressId");
        query.setParameter("userId", userId);
        query.setParameter("userAddressId", userAddressId);
        query.setParameter("addressTag", addressTag);
        return query.executeUpdate() > 0;
        
    }

	@Override
	public boolean updateUserAddressStatus(int userAddressId, int userId,
			UserAddressStatus userAddressStatus) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update UserAddress set "
								+ "status = :status where id = :userAddressId and userId = :userId");
		query.setParameter("userAddressId", userAddressId);
		query.setParameter("userId", userId);
		query.setParameter("status", userAddressStatus);
		return query.executeUpdate() > 0;
	}
}
