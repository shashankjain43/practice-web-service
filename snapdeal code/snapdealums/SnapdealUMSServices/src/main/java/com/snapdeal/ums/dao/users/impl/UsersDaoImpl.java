/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 16-Aug-2010
 *  @author bala
 */
package com.snapdeal.ums.dao.users.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserReferralTracking;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.utils.QueryNames;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.server.services.impl.UserServiceImpl;

@Repository("umsUsersDao")
@SuppressWarnings("unchecked")
public class UsersDaoImpl implements IUsersDao {

	private static final Logger  LOG = LoggerFactory.getLogger(UsersDaoImpl.class);
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@EnableMonitoring
	@Override
	public User getUserById(int userId) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				QueryNames.GET_USER_BY_ID);
		query.setParameter("userid", userId);
		return (User) query.uniqueResult();
	}

	@Override
	public User getUserByIdWithoutRoles(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User where id = :userId");
		query.setParameter("userId", userId);
		return (User) query.uniqueResult();
	}

	@Override
	public User getUserByEmailWithoutRoles(String emailId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User where email = :emailId");
		query.setParameter("emailId", emailId);
		return (User) query.uniqueResult();
	}

	@Override
	public UserRole getUserRoleById(Integer userRoleId) {
		return (UserRole) sessionFactory.getCurrentSession().get(
				UserRole.class, userRoleId);
	}

	@Override
	public SubscriberProfile getSubscriberProfileById(Integer profileId) {
		return (SubscriberProfile) sessionFactory.getCurrentSession().get(
				SubscriberProfile.class, profileId);
	}

	@Override
	public Locality getLocalityById(Integer localityId) {
		return (Locality) sessionFactory.getCurrentSession().get(
				Locality.class, localityId);
	}

	
	
	
	@EnableMonitoring
	@Override
	public User getUserByEmail(String email) {
		long timeStart= System.currentTimeMillis();
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct u from User u left join fetch u.userRoles ur where u.email=:email");
		query.setParameter("email", email);
		long timeEnd= System.currentTimeMillis();
		long totalTime=timeEnd-timeStart;
		LOG.info("total time taken using join:"+totalTime+"ms");
		return (User) query.uniqueResult();
	}
	
	@Override
	public User getUserByEmailWithoutJoin(String email) {
		long timeStart= System.currentTimeMillis();
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from User u where u.email=:email");
		query.setParameter("email", email);
		
		User user= (User) query.uniqueResult();
		user.getUserRoles();
		long timeEnd= System.currentTimeMillis();
		long totalTime=timeEnd-timeStart;
		LOG.info("total time taken without join:"+totalTime+"ms");
		return user;
		
	}

	public boolean isUserExists(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select id from User where email = :email");
		query.setParameter("email", email);
		List<Integer> users = query.list();
		if (users.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void persistUser(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	@EnableMonitoring
	@Override
	public User updateUser(User user) {
		return (User) sessionFactory.getCurrentSession().merge(user);
	}

	public UserRole persistUserRole(UserRole userRole) {
		sessionFactory.getCurrentSession().persist(userRole);
		return userRole;
	}

	@Override
	public void deleteUserRole(UserRole userRole) {
		sessionFactory.getCurrentSession().delete(userRole);
	}

	@Override
	public void deleteUserRoleById(int userRoleID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"delete from UserRole where id=:id");
		query.setParameter("id", userRoleID);
		query.executeUpdate();
	}

	@Override
	public void createOrUpdateReferralSent(User user, String referralChannel,
			int sentCount) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				QueryNames.GET_USER_REFERRAL_TRACKING);
		query.setParameter("user", user);
		query.setParameter("referralChannel", referralChannel);
		UserReferralTracking referralTracking = (UserReferralTracking) query
				.uniqueResult();

		if (referralTracking != null) {
			referralTracking.setSentCount(referralTracking.getClickCount()
					+ sentCount);
			sessionFactory.getCurrentSession().merge(referralTracking);
		} else {
			referralTracking = new UserReferralTracking();
			referralTracking.setUser(user);
			referralTracking.setReferralChannel(referralChannel);
			referralTracking.setCreated(new Date());
			referralTracking.setSentCount(sentCount);
			sessionFactory.getCurrentSession().persist(referralTracking);
		}
	}

	@Override
	public void createOrUpdateReferralClick(User user, String referralChannel) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				QueryNames.GET_USER_REFERRAL_TRACKING);
		query.setParameter("user", user);
		query.setParameter("referralChannel", referralChannel);
		UserReferralTracking referralTracking = (UserReferralTracking) query
				.uniqueResult();

		if (referralTracking != null) {
			referralTracking
					.setClickCount(referralTracking.getClickCount() + 1);
			sessionFactory.getCurrentSession().merge(referralTracking);
		} else {
			referralTracking = new UserReferralTracking();
			referralTracking.setUser(user);
			referralTracking.setReferralChannel(referralChannel);
			referralTracking.setCreated(new Date());
			referralTracking.setClickCount(1);
			sessionFactory.getCurrentSession().persist(referralTracking);
		}
	}

	public void updateReferralConversion(User user, String referralChannel) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				QueryNames.GET_USER_REFERRAL_TRACKING);
		query.setParameter("user", user);
		query.setParameter("referralChannel", referralChannel);
		UserReferralTracking referralTracking = (UserReferralTracking) query
				.uniqueResult();

		if (referralTracking != null) {
			referralTracking.setConversionCount(referralTracking
					.getConversionCount() + 1);
			sessionFactory.getCurrentSession().merge(referralTracking);
		}
	}

	@Override
	public void persistUserReferral(UserReferral userReferral) {
		userReferral.setCreated(DateUtils.getCurrentTime());
		userReferral.setUpdated(DateUtils.getCurrentTime());
		sessionFactory.getCurrentSession().persist(userReferral);
	}

	@Override
	public List<UserReferral> getReferral(User user) {
		Query q = sessionFactory.getCurrentSession().createQuery(
				"from UserReferral where user=:user");
		q.setParameter("user", user);
		return q.list();
	}

	@Override
	public List<UserInformation> getUserInformationsByUser(User user) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from UserInformation where user.id=:userId");
		query.setParameter("userId", user.getId());
		return query.list();
	}

	@Override
	public List<UserInformation> getUserInformationsByUserAndName(User user,
			String name) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from UserInformation where user.id=:userId and name like :name");
		query.setParameter("userId", user.getId());
		query.setParameter("name", name + "%");
		return query.list();
	}

	public static final String USER_INFO_VISA_CARD = "visacard";

	@Override
	public boolean isVisaBenefitAvailed(int userId, String cardNumber) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from UserInformation where name=:infoName and (user.id=:userId or value = :cardNumber)");
		query.setParameter("userId", userId);
		query.setParameter("infoName", USER_INFO_VISA_CARD);
		query.setParameter("cardNumber", cardNumber);
		return query.list().size() != 0;
	}

	@Override
	public void persistUserInformation(UserInformation information) {
		sessionFactory.getCurrentSession().persist(information);
	}

	@Override
	public ZendeskUser getZendeskUser(int userId) {
		Query q = sessionFactory.getCurrentSession().createQuery(
				"from ZendeskUser where user.id = :userId");
		q.setParameter("userId", userId);
		return (ZendeskUser) q.uniqueResult();
	}

	@Override
	public UserPreference getUserPreferenceByMobile(String phoneNo) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from UserPreference where phoneNo = :phoneNo");
		query.setParameter("phoneNo", phoneNo);
		return (UserPreference) query.uniqueResult();
	}

	@Override
	public UserPreference addUserPreference(UserPreference userPreference) {
		return (UserPreference) sessionFactory.getCurrentSession().merge(
				userPreference);
	}

	@Override
	public CustomerEmailScore mergeCustomerEmailScore(
			CustomerEmailScore customerEmailScore) {
		return (CustomerEmailScore) sessionFactory.getCurrentSession().merge(
				customerEmailScore);
	}

	@Override
	public CustomerMobileScore mergeCustomerMobileScore(
			CustomerMobileScore customerMobileScore) {
		return (CustomerMobileScore) sessionFactory.getCurrentSession().merge(
				customerMobileScore);
	}

	@Override
	public CustomerEmailScore getCustomerScoreByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from CustomerEmailScore where email = :email");
		query.setParameter("email", email);
		return (CustomerEmailScore) query.uniqueResult();
	}

	@Override
	public CustomerMobileScore getCustomerScoreByMobile(String mobile) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from CustomerMobileScore where mobile = :mobile");
		query.setParameter("mobile", mobile);
		return (CustomerMobileScore) query.uniqueResult();
	}

	@Override
	public List<User> getAutoCreatedUnverifiedUsers(Integer resultSize,
			Date startsCreatedDate, Date endsCreatedDate,
			Integer autocreatedNotificationCount) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from User as u where autocreated = :autocreated and autocreatedNotificationCount = :autocreatedNotificationCount and emailVerified = :emailVerified and mobileVerified= :mobileVerified and created > :startCreatedDate and created <= :endsCreatedDate");
		query.setParameter("autocreated", true);
		query.setParameter("emailVerified", false);
		query.setParameter("mobileVerified", false);
		query.setParameter("autocreatedNotificationCount",
				autocreatedNotificationCount);
		query.setParameter("startCreatedDate", startsCreatedDate);
		query.setParameter("endsCreatedDate", endsCreatedDate);
		query.setMaxResults(resultSize);
		return query.list();
	}

	@Override
	public void incrementAutocreatedNotificationCount(String emailId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update User u set u.autocreatedNotificationCount = u.autocreatedNotificationCount+1 where u.email = :emailId)");
		query.setParameter("emailId", emailId);
		query.executeUpdate();
	}

	@Override
	public boolean isMobileExist(String mobile) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User where mobile=:mobile");
		query.setParameter("mobile", mobile);
		return query.list().size() != 0;
	}

	/**
	 * Returns List<Object[]>, in which Object[0] = ID, Object[1]=email and
	 * Object[2]=firstName for the users in request which are registered with
	 * Snapdeal.
	 * 
	 */
	@Override
	public List<Object[]> getActiveSDUserIDNameEmail(Collection<String> emailIDs) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select id,  email, firstName from User where email in (:emailIDs)")
				.setParameterList("emailIDs", emailIDs);

		return query.list();
	}

	public String getUserEmailById(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select email from User where id=:userId");
		query.setParameter("userId", userId);
        return (String) query.uniqueResult();

	}

	public Integer getUserIdByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select id from User where email=:email");
		query.setParameter("email", email);
		return (Integer) query.uniqueResult();

	}

	@Override
	public boolean isUserExistsById(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select id from User where id = :id");
		query.setParameter("id", id);
		List<Integer> users = query.list();
		if (users.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Integer> getUsersCreatedWithoutMobileByDateRange(Date createdStartDate,
			Date createdEndDate) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("select id from User where created >= :startCreatedDate and created <= :endsCreatedDate "
						+ "and (mobile is null or mobile = '')");
		query.setParameter("startCreatedDate", createdStartDate);
		query.setParameter("endsCreatedDate", createdEndDate);
		//query.setMaxResults(pageSize);
		return query.list();
	}

}